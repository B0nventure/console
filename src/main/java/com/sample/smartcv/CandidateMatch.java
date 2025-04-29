package com.sample.smartcv;

public final class CandidateMatch {
    private final CV candidate; // The candidate associated with the match
    private final double score; // The score that represents how well the candidate matches the job

    // Constructor to initialize a CandidateMatch with a candidate and score
    public CandidateMatch(CV candidate, double score) {
        this.candidate = candidate;
        this.score = score;
    }

    // Getter for the candidate
    public CV getCandidate() {
        return candidate;
    }

    // Getter for the match score
    public double getScore() {
        return score;
    }
}
