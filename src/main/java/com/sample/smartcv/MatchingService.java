package com.sample.smartcv;

import java.util.List;

public interface MatchingService {
    // Analyzes a list of candidates against a job description, then ranks them based on their fit
    List<CandidateMatch> analyzeAndRank(List<CV> candidates, JobDescription job);
}
