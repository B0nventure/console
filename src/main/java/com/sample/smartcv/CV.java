package com.sample.smartcv;

import java.util.stream.Collectors;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CV {
    private final String name; // Candidate's name
    private final String email; // Candidate's email address
    private final Set<String> skills; // Set of skills the candidate possesses
    private final int yearsExperience; // Total years of experience the candidate has
    private final List<Education> education; // List of candidate's educational qualifications

    // Constructor to initialize the candidate's details
    public CV(String name, String email, Set<String> skills,
              int yearsExperience, List<Education> education) {
        this.name = validateName(name); // Validate and set name
        this.email = validateEmail(email); // Validate and set email
        // Normalize skills (trim, lowercase, remove duplicates)
        this.skills = skills.stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
        this.yearsExperience = yearsExperience;
        this.education = List.copyOf(education); // Make a defensive copy of education list
    }

    // Validate the name, ensuring it is not blank
    private String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        return name.trim();
    }

    // Validate the email format using a regex pattern
    private String validateEmail(String email) {
        if (email == null || !email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return email.trim().toLowerCase(); // Normalize email to lowercase
    }

    // Check if the candidate has a specific skill
    public boolean hasSkill(String skill) {
        if (skill == null || skill.isBlank()) {
            return false;
        }
        String normalizedSkill = skill.trim().toLowerCase(); // Normalize skill before checking
        return skills.contains(normalizedSkill);
    }

    // Get the skills that the candidate is missing compared to the required skills
    public Set<String> getMissingSkills(Set<String> requiredSkills) {
        return requiredSkills.stream()
                .filter(skill -> !hasSkill(skill)) // Filter out the skills already possessed by the candidate
                .collect(Collectors.toSet());
    }

    // Standard getters for the fields
    public String getName() { return name; }
    public String getEmail() { return email; }
    public Set<String> getSkills() { return new HashSet<>(skills); } // Return a new HashSet to avoid modification
    public int getYearsExperience() { return yearsExperience; }
    public List<Education> getEducation() { return new ArrayList<>(education); } // Return a new ArrayList to avoid modification

    // Method to display candidate details in a human-readable format
    public void displayDetails() {
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Skills: " + String.join(", ", skills));
        System.out.println("Experience: " + yearsExperience + " years");
        System.out.println("Education: " + education.stream()
                .map(e -> e.degree() + " in " + e.field())
                .collect(Collectors.joining(", "))); // Display each education detail in a formatted string
    }

    // Nested record to store education details for a candidate
    public record Education(String institution, String degree, String field, int graduationYear) {
        @Override
        public String toString() {
            return degree + " in " + field + " (" + institution + ", " + graduationYear + ")";
        }
    }

    // Utility method to parse the number of years from a string representation of experience
    public static int parseExperienceYears(String experience) {
        if (experience == null) return 0;
        Matcher matcher = Pattern.compile("(\\d+)").matcher(experience); // Match digits in the experience string
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : 0; // Return the first number found, or 0 if none
    }
}
