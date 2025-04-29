package com.sample.smartcv;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

public final class AnalysisResultPrinter {

    // Method to print a detailed analysis report comparing candidates' matches to the job description
    public static void printDetailedReport(List<CandidateMatch> matches, JobDescription job) {
        // Print the header of the report
        System.out.println("\n=== Detailed Analysis Report ===");

        // Display job title and details about the required skills and experience
        System.out.println("Job Title: " + job.getTitle());
        System.out.println("Required Skills: " + String.join(", ", job.getRequiredSkills()));
        System.out.println("Required Experience: " + job.getRequiredExperience().minYears() + "+ years");
        System.out.println("------------------------------------------------");

        // Iterate over each candidate match and display their details
        for (int i = 0; i < matches.size(); i++) {
            CandidateMatch match = matches.get(i);
            CV candidate = match.getCandidate();

            // Display candidate's name and match score
            System.out.printf("%d. %s (Score: %.1f%%)\n", i + 1, candidate.getName(), match.getScore() * 100);

            // Display candidate's contact email and years of experience
            System.out.println("   Email: " + candidate.getEmail());
            System.out.println("   Experience: " + candidate.getYearsExperience() + " years");

            // Find the set of skills that match between the candidate and job requirements
            Set<String> matchedSkills = new HashSet<>(candidate.getSkills());
            matchedSkills.retainAll(job.getRequiredSkills()); // Keep only the common skills
            System.out.println("   Matched Skills: " + matchedSkills);

            // Check and display if there are any missing skills
            Set<String> missingSkills = candidate.getMissingSkills(job.getRequiredSkills());
            if (!missingSkills.isEmpty()) {
                System.out.println("   Missing Skills: " + missingSkills);
            }
        }
    }
}
