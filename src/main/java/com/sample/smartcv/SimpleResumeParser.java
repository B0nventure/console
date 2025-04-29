package com.sample.smartcv;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.*;

public class SimpleResumeParser implements ResumeParser {
    // Pattern to match names with flexibility for multiple first and last names
    private static final Pattern NAME_PATTERN = Pattern.compile("([A-Za-z]+(?: [A-Za-z]+)*\\s+[A-Za-z]+(?: [A-Za-z]+)*)");

    // Pattern to match email addresses
    private static final Pattern EMAIL_PATTERN = Pattern.compile("([\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4})");

    // Pattern to find skills listed in the resume (e.g., "Skills: Java, Python, SQL")
    private static final Pattern SKILLS_PATTERN = Pattern.compile("Skills?: ([\\w ,]+)");

    // Pattern to extract years of experience from the resume (e.g., "5 years")
    private static final Pattern EXPERIENCE_PATTERN = Pattern.compile("(\\d+) years?");

    @Override
    public CV parse(String rawText) {
        // Extract the candidate's name using the defined pattern
        Matcher nameMatcher = NAME_PATTERN.matcher(rawText);
        String name = nameMatcher.find() ? nameMatcher.group(1) : "Unknown"; // Default to "Unknown" if no name is found

        // Extract the candidate's email using the email pattern
        Matcher emailMatcher = EMAIL_PATTERN.matcher(rawText);
        String email = emailMatcher.find() ? emailMatcher.group(1) : ""; // Default to empty if no email is found

        // Extract the skills from the resume, split by commas, and store them in a set
        Set<String> skills = new HashSet<>();
        Matcher skillsMatcher = SKILLS_PATTERN.matcher(rawText);
        if (skillsMatcher.find()) {
            String[] skillArray = skillsMatcher.group(1).split(",\\s*"); // Split skills by commas
            skills.addAll(Arrays.asList(skillArray));
        }

        // Extract the years of experience from the resume
        int yearsExperience = 0;
        Matcher expMatcher = EXPERIENCE_PATTERN.matcher(rawText);
        if (expMatcher.find()) {
            yearsExperience = Integer.parseInt(expMatcher.group(1)); // Convert found years to integer
        }

        // Return a new CV object with the parsed data. Education is not parsed in this example, so an empty list is used.
        return new CV(name, email, skills, yearsExperience, List.of());
    }
}
