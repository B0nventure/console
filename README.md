SID: 2336318

# Smart CV Analyzer
## Overview
The Smart CV Analyzer is a Java-based application designed to help HR professionals and hiring managers analyze and match candidates to job descriptions. The system evaluates candidates based on their skills, experience, and education, and ranks them accordingly to help users find the best possible matches for job roles.

## Features
- **Search by Skills:** Allows users to search for candidates based on required skills.
- **Search by Experience:** Enables filtering candidates based on years of experience.
- **Search by Education Level:** Filters candidates by their educational qualifications.
- **View All Candidates:** Displays a list of all candidates in the system.

## System Specifications
### Requirements
- **Java Version:** Java 17 or higher.
- **IDE:** Any Java IDE (e.g., IntelliJ IDEA, Eclipse) or terminal-based setup.
- **Maven:** Used for dependency management and building the project (optional but recommended).
- **Operating System:** The application is cross-platform and can be run on Windows, macOS, or Linux.

### Hardware
- **Processor:** Any modern processor (2.0 GHz or higher).
- **RAM:** Minimum 4 GB of RAM.
- **Storage:** At least 50 MB of free storage for the application and dependencies.

## Setup Instructions
To set up and run the Smart CV Analyzer on your local machine, follow these steps:

1. **Download the Repository**
    - Download the source code to your local machine.

2. **Open the Project**
   - Open the project in your preferred IDE (IntelliJ IDEA, Eclipse, etc.), or navigate to the project directory if using a terminal.

3. **Compile the Code**
   - Use Gradle to build the project.
   - `./gradlew build`

4. **Run the Application**
    - After compiling the project, run the SmartCVAnalyzer class to launch the application.
    - `./gradlew run`

## Using the Application
The application will display a menu with the following options:

- **Search by Skills:** Enter a list of required skills to search for candidates.
- **Search by Minimum Experience:** Enter a minimum number of years of experience.
- **Search by Education Level:** Enter a degree level (e.g., Bachelor's, Master’s).
- **View All Candidates:** Display all candidates in the system.
- **Exit:** Quit the application.

## Interacting with the System
When you search by skills, experience, or education, input your criteria, and the system will rank candidates based on how closely they match the provided parameters.

### Procedures
How the Matching Works
The system evaluates candidates based on three main criteria:
- **Skills Matching:** The system checks how many of the required skills the candidate possesses and calculates a match score based on this.
- **Experience Matching:** It compares the candidate’s experience against the required experience range, assigning a score accordingly (e.g., overqualified, underqualified, or a perfect match).
- **Education Matching:** The system checks if the candidate’s education meets the required degree level.

Once all the scores are calculated for skills, experience, and education, the application ranks candidates based on their total match score. Higher scores indicate better matches for the job description.

### Example Match Calculation
For instance, if a candidate has:
- 3 out of 5 required skills, the skills match would be 60%.
- 5 years of experience for a job requiring at least 3 years of experience, the experience match would be 100%.
- A Bachelor's degree for a job requiring a Bachelor's, the education match would be 100%.

The total match score is then calculated by weighting the individual components:
- **Skills:** 60% * 0.6 = 36%
- **Experience:** 100% * 0.3 = 30%
- **Education:** 100% * 0.1 = 10%

Thus, the total match score for this candidate would be 76%.