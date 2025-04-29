package com.sample.smartcv;

import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;

public class WeightedMatchingService implements MatchingService {
    // Weights for different matching criteria (skills, experience, education)
    private static final double SKILL_WEIGHT = 0.6;
    private static final double EXPERIENCE_WEIGHT = 0.3;
    private static final double EDUCATION_WEIGHT = 0.1;

    @Override
    public List<CandidateMatch> analyzeAndRank(List<CV> candidates, JobDescription job) {
        List<CandidateMatch> matches = new ArrayList<>();

        // Calculate the match score for each candidate and store it along with the candidate's CV
        for (CV candidate : candidates) {
            double score = calculateMatchScore(candidate, job);
            matches.add(new CandidateMatch(candidate, score));
        }

        // Sort the candidates by their match score in descending order
        matches.sort(Comparator.comparingDouble(CandidateMatch::getScore).reversed());

        return matches;
    }

    // Calculate the overall match score for a candidate based on skills, experience, and education
    private double calculateMatchScore(CV candidate, JobDescription job) {
        double skillScore = calculateSkillMatch(candidate, job); // Matching skills score
        double expScore = calculateExperienceMatch(candidate, job); // Matching experience score
        double eduScore = calculateEducationMatch(candidate, job); // Matching education score

        // Weighted sum of individual match scores
        return (skillScore * SKILL_WEIGHT) +
                (expScore * EXPERIENCE_WEIGHT) +
                (eduScore * EDUCATION_WEIGHT);
    }

    // Calculate how well the candidate's skills match the required skills for the job
    private double calculateSkillMatch(CV candidate, JobDescription job) {
        // Count how many required skills the candidate possesses
        long matchedCount = job.getRequiredSkills().stream()
                .filter(candidate::hasSkill) // Check if the candidate has the skill
                .count();
        // Return the proportion of required skills that the candidate matches
        return (double) matchedCount / job.getRequiredSkills().size();
    }

    // Calculate how well the candidate's experience matches the required experience for the job
    private double calculateExperienceMatch(CV candidate, JobDescription job) {
        // Check if the candidate's experience matches the required experience range
        if (job.getRequiredExperience().matches(candidate.getYearsExperience())) {
            return 1.0; // Exact match
        } else if (candidate.getYearsExperience() > job.getRequiredExperience().minYears()) {
            return 0.8; // Overqualified
        } else {
            // Calculate a fractional match based on how much the candidate's experience exceeds the minimum requirement
            return 0.5 * ((double) candidate.getYearsExperience() / job.getRequiredExperience().minYears());
        }
    }

    // Calculate how well the candidate's education matches the required education for the job
    private double calculateEducationMatch(CV candidate, JobDescription job) {
        // In this simplified version, we return a score of 1 if there are education requirements, otherwise 0
        return !job.getRequiredEducation().isEmpty() ? 1.0 : 0.0;
    }
}
