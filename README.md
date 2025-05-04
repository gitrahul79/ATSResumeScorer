# ResumeSmart ATS Scorer

ResumeSmart ATS Scorer is a Java-based tool designed to evaluate resumes against job descriptions using an Applicant Tracking System (ATS) scoring approach. The tool assigns scores based on keyword matching, formatting, experience, education, and skills, helping job seekers optimize their resumes for better ATS performance.

## Features

- **Keyword Matching:** Evaluates how well the resume matches the keywords from the job description.
- **Formatting Check:** Scores the consistency and structure of the resume.
- **Experience Evaluation:** Analyzes the years of experience mentioned in the resume.
- **Education Analysis:** Checks for the presence of educational qualifications like Bachelor's, Master's, or Ph.D.
- **Skills Assessment:** Detects the inclusion of a skills section and evaluates its relevance.

## How It Works

1. The user provides the paths to:
   - A resume file (`SampleResume.txt`)
   - A job description file (`JobDescription.txt`)
2. The program reads the files, processes their content, and calculates scores for five categories:
   - Keyword Match
   - Formatting
   - Experience
   - Education
   - Skills
3. A total ATS score is calculated as a weighted combination of the category scores.

## Scoring Weights

| Category         | Weight  |
|-------------------|---------|
| Keyword Matching  | 40%     |
| Formatting        | 20%     |
| Experience        | 20%     |
| Education         | 10%     |
| Skills            | 10%     |

## Getting Started

### Prerequisites

- Java 8 or later installed on your system.
- A text-based resume file (`SampleResume.txt`).
- A text-based job description file (`JobDescription.txt`).

### Installation

1. Clone this repository to your local machine:

   ```bash
   git clone https://github.com/AdityaRaj-X21/Resume_ATS_Scorer.git
   cd ResumeSmart-ATS-Scorer
   ```

2. Compile the Java code:

   ```bash
   javac ATSResumeScorer.java
   ```

3. Run the program:

   ```bash
   java ATSResumeScorer
   ```

## Example

Place the following example files in the same directory as the project:

### SampleResume.txt
```
John Doe
Professional Summary: Skilled Project Manager with 5 years of experience in leadership, team collaboration, and analytics.
Work Experience: 5 years of professional experience in problem solving and communication.
Education: Bachelor’s degree in Business Administration.
Skills: Leadership, Problem Solving, Communication, Analytics.
```

### JobDescription.txt
```
We are seeking a Project Manager with experience in leadership, communication, and problem solving. The ideal candidate will have a Bachelor’s degree and strong skills in team collaboration and analytics.
```

### Output
```
ATS Resume Scorer
Current Working Directory: <path-to-project>
Enter path to resume file (default: SampleResume.txt):
Enter path to job description file (default: JobDescription.txt):

ATS Score Breakdown:
Keyword Match Score: 66.23%
Experience Score: 50.00%
Education Score: 100.00%
Skills Score: 100.00%
Formatting Score: 100.00%

Total ATS Score: 76.49%
```

## Project Structure

```
├── ATSResumeScorer.java     # Main Java source code
├── SampleResume.txt         # Example resume file
├── JobDescription.txt       # Example job description file
└── README.md                # Project documentation
```

## Future Enhancements

- Add support for multiple file formats (e.g., PDF, DOCX).
- Include advanced natural language processing (NLP) for better keyword matching.
- Provide a graphical user interface (GUI) for enhanced usability.
- Enable customization of scoring weights and keywords.

## Contributing

Contributions are welcome! If you have suggestions or want to report a bug, please open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

## Author

Developed by **Aditya**.
