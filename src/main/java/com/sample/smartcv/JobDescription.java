package com.sample.smartcv;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Objects;

public final class JobDescription {
    private final String title; // The job title (e.g., "Software Engineer")
    private final Set<String> requiredSkills; // A set of skills required for the job
    private final ExperienceRange requiredExperience; // The experience range required for the job
    private final List<DegreeRequirement> requiredEducation; // A list of education requirements for the job

    // Constructor to initialize the job description with title, skills, experience range, and education requirements
    public JobDescription(String title, Set<String> requiredSkills,
                          ExperienceRange requiredExperience,
                          List<DegreeRequirement> requiredEducation) {
        this.title = validateTitle(title); // Validate and set the job title
        this.requiredSkills = Set.copyOf(requiredSkills); // Create an unmodifiable copy of required skills
        this.requiredExperience = Objects.requireNonNull(requiredExperience); // Ensure required experience is not null
        this.requiredEducation = List.copyOf(requiredEducation); // Create an unmodifiable copy of required education
    }

    // Validate the job title, ensuring it's not blank
    private String validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Job title cannot be blank");
        }
        return title.trim(); // Remove any leading/trailing whitespace
    }

    // Getter for the job title
    public String getTitle() {
        return title;
    }

    // Getter for the required skills
    public Set<String> getRequiredSkills() {
        return new HashSet<>(requiredSkills); // Return a new set to prevent modification
    }

    // Getter for the required experience range
    public ExperienceRange getRequiredExperience() {
        return requiredExperience;
    }

    // Getter for the required education list
    public List<DegreeRequirement> getRequiredEducation() {
        return new ArrayList<>(requiredEducation); // Return a new list to prevent modification
    }

    // Inner record to represent the experience range (min and max years)
    public record ExperienceRange(int minYears, Integer maxYears) {
        // Method to check if a candidate's experience matches the required range
        public boolean matches(int years) {
            return years >= minYears && (maxYears == null || years <= maxYears);
        }
    }

    // Inner record to represent the required degree type and field (e.g., "Bachelor's in Computer Science")
    public record DegreeRequirement(String degreeType, String field) {}
}
