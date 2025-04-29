package com.sample.smartcv;

public interface ResumeParser {
    // Parses the raw text of a resume and converts it into a structured CV object
    CV parse(String rawText);
}
