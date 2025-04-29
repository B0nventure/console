package com.sample.smartcv;

/*
 * SID: 2336318
 * Team: HR&DEVELOP
 * Smart CV Analyzer application.
 */

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors; // <-- Add this to handle stream operations
import static java.lang.System.*;

public class SmartCVAnalyzer {
    // Logger for tracking application events
    private static final Logger logger = Logger.getLogger(SmartCVAnalyzer.class.getName());

    // Scanner to read user input
    private static final Scanner scanner = new Scanner(in);

    // Pre-loaded candidate profiles to search through
    private static final List<CV> CANDIDATE_LIBRARY = createCandidateLibrary();

    public static void main(String[] args) {
        logger.info("Starting Smart CV Analyzer");

        // Display initial information about the candidate library
        out.println("\n=== Smart CV Analyzer ===");
        out.println("Candidate Library: " + CANDIDATE_LIBRARY.size() + " profiles loaded");

        // Loop to continuously display menu options and accept user input
        while (true) {
            out.println("\nSearch Options:");
            out.println("1. Search by Skills");
            out.println("2. Search by Minimum Experience");
            out.println("3. Search by Education Level");
            out.println("4. View All Candidates");
            out.println("5. Exit");
            out.print("Select option: ");

            // Read user selection
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after reading the choice

            // Perform the corresponding action based on user input
            switch (choice) {
                case 1 -> searchBySkills();       // Search candidates by skills
                case 2 -> searchByExperience();   // Search candidates by minimum experience
                case 3 -> searchByEducation();    // Search candidates by education level
                case 4 -> displayAllCandidates(); // Display all candidates
                case 5 -> {
                    // Exit the application
                    logger.info("Exiting application");
                    return;
                }
                default -> out.println("Invalid option! Please choose a valid option."); // Handle invalid input
            }
        }
    }

    // Search candidates based on required skills
    private static void searchBySkills() {
        out.print("\nEnter required skills (comma separated): ");
        String[] requiredSkills = scanner.nextLine().split(",");

        // Normalize skills input
        Set<String> skills = new HashSet<>();
        for (String skill : requiredSkills) {
            skills.add(skill.trim().toLowerCase());
        }

        // Create a job description with the required skills (no experience or education filter)
        JobDescription job = new JobDescription("Custom Search", skills,
                new JobDescription.ExperienceRange(0, null), List.of());

        // Analyze the job description against candidates and display results
        analyzeAndDisplay(job);
    }

    // Search candidates based on minimum years of experience
    private static void searchByExperience() {
        out.print("\nEnter minimum years of experience: ");
        int years = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Create a job description with the minimum experience required (no skills or education filter)
        JobDescription job = new JobDescription("Experience Search", Set.of(),
                new JobDescription.ExperienceRange(years, null), List.of());

        // Analyze and display results
        analyzeAndDisplay(job);
    }

    // Search candidates based on required education level (e.g., Bachelor's, Master's)
    private static void searchByEducation() {
        out.print("\nEnter required degree level (e.g. Bachelor's, Master's): ");
        String degree = scanner.nextLine();

        // Create a job description with the required degree level (no skills or experience filter)
        JobDescription job = new JobDescription("Education Search", Set.of(),
                new JobDescription.ExperienceRange(0, null),
                List.of(new JobDescription.DegreeRequirement(degree, "")));

        // Analyze and display results
        analyzeAndDisplay(job);
    }

    // Display a list of all candidates in the system
    private static void displayAllCandidates() {
        out.println("\n=== All Candidates ===");
        CANDIDATE_LIBRARY.forEach(candidate -> {
            out.println("\nName: " + candidate.getName());
            out.println("Email: " + candidate.getEmail());
            out.println("Skills: " + String.join(", ", candidate.getSkills()));
            out.println("Experience: " + candidate.getYearsExperience() + " years");
            out.println("Education: " + candidate.getEducation());
        });
    }

    // Analyze candidates based on a job description and display ranking results
    private static void analyzeAndDisplay(JobDescription job) {
        MatchingService matcher = new WeightedMatchingService();
        List<CandidateMatch> results = matcher.analyzeAndRank(CANDIDATE_LIBRARY, job);

        // Display the ranking of candidates
        out.println("\n=== Ranking Results ===");
        out.printf("%-20s %-10s %s\n", "Name", "Score", "Match Details");
        out.println("----------------------------------------");

        // Display details for each candidate in the ranking
        for (CandidateMatch match : results) {
            CV candidate = match.getCandidate();
            out.printf("%-20s %-10.1f%%\n", candidate.getName(), match.getScore() * 100);
            out.println("   Skills: " + String.join(", ", candidate.getSkills()));
            out.println("   Experience: " + candidate.getYearsExperience() + " years");
            out.println("   Education: " + candidate.getEducation().stream()
                    .map(e -> e.degree())
                    .collect(Collectors.joining(", ")));
            out.println("----------------------------------------");
        }
    }

    // Create a sample candidate library with random profiles
    private static List<CV> createCandidateLibrary() {
        List<CV> candidates = new ArrayList<>();
        ResumeParser parser = new SimpleResumeParser();
        Random random = new Random();

        // Example first and last names
        String[] firstNames = {"John", "Emma", "Michael", "Sophia", "William",
                "Olivia", "James", "Ava", "Robert", "Mia"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones",
                "Miller", "Davis", "Garcia", "Rodriguez", "Wilson"};

        // Example skill sets for candidates
        String[][] skillsSets = {
                {"Java", "Spring", "SQL"},
                {"Python", "Data Analysis", "Machine Learning"},
                {"JavaScript", "React", "Node.js"},
                {"C#", ".NET", "Azure"},
                {"HTML", "CSS", "JavaScript", "React"},
                {"Python", "Django", "PostgreSQL"},
                {"Java", "Hibernate", "Microservices"},
                {"Swift", "iOS Development", "Xcode"},
                {"Kotlin", "Android Development", "Firebase"},
                {"Go", "Docker", "Kubernetes"}
        };

        // Randomly generate candidate profiles
        for (int i = 0; i < 10; i++) {
            String firstName = firstNames[i];
            String lastName = lastNames[i];
            String email = firstName.toLowerCase() + lastName.toLowerCase() +
                    (random.nextInt(90) + 10) + "@gmail.com";
            int experience = random.nextInt(10) + 1;
            String degree = random.nextBoolean() ? "Bachelor's" : "Master's";
            String university = "University of " +
                    new String[]{"Tech", "State", "City", "National"}[random.nextInt(4)];
            int gradYear = 2023 - experience - random.nextInt(5);

            // Build a candidate's resume text and parse it
            String candidateText = String.format("""
                Name: %s %s
                Email: %s
                Skills: %s
                Experience: %d years
                Education: %s in Computer Science, %s (%d)
                """,
                    firstName, lastName,
                    email,
                    String.join(", ", skillsSets[i]),
                    experience,
                    degree, university, gradYear
            );

            // Add the parsed CV to the list
            candidates.add(parser.parse(candidateText));
        }

        return candidates;
    }
}
