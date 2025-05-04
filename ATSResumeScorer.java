import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ATSResumeScorer {
    // Weights for different scoring categories
    private static final double KEYWORD_WEIGHT = 0.4;
    private static final double FORMATTING_WEIGHT = 0.2;
    private static final double EXPERIENCE_WEIGHT = 0.2;
    private static final double EDUCATION_WEIGHT = 0.1;
    private static final double SKILLS_WEIGHT = 0.1;

    // Job-specific keywords (can be expanded or made configurable)
    private static final List<String> REQUIRED_KEYWORDS = Arrays.asList(
        "project management", "leadership", "communication", 
        "problem solving", "team collaboration", "analytics"
    );

    public static class ResumeScore {
        double totalScore;
        Map<String, Double> categoryScores;

        public ResumeScore(double totalScore, Map<String, Double> categoryScores) {
            this.totalScore = totalScore;
            this.categoryScores = categoryScores;
        }
    }

    public static ResumeScore calculateATSScore(String resumeText, String jobDescription) {
        Map<String, Double> categoryScores = new HashMap<>();

        // 1. Keyword Matching Score
        double keywordScore = calculateKeywordScore(resumeText, jobDescription);
        categoryScores.put("Keyword Match", keywordScore);

        // 2. Formatting Score
        double formattingScore = calculateFormattingScore(resumeText);
        categoryScores.put("Formatting", formattingScore);

        // 3. Experience Score
        double experienceScore = calculateExperienceScore(resumeText);
        categoryScores.put("Experience", experienceScore);

        // 4. Education Score
        double educationScore = calculateEducationScore(resumeText);
        categoryScores.put("Education", educationScore);

        // 5. Skills Score
        double skillsScore = calculateSkillsScore(resumeText);
        categoryScores.put("Skills", skillsScore);

        // Calculate weighted total score
        double totalScore = (keywordScore * KEYWORD_WEIGHT) +
                             (formattingScore * FORMATTING_WEIGHT) +
                             (experienceScore * EXPERIENCE_WEIGHT) +
                             (educationScore * EDUCATION_WEIGHT) +
                             (skillsScore * SKILLS_WEIGHT);

        return new ResumeScore(totalScore * 100, categoryScores);
    }

    private static double calculateKeywordScore(String resumeText, String jobDescription) {
        resumeText = resumeText.toLowerCase();
        jobDescription = jobDescription.toLowerCase();

        // Extract keywords from job description
        List<String> jobKeywords = extractKeywords(jobDescription);
        
        // Count matches
        long matchedKeywords = jobKeywords.stream()
            .filter(resumeText::contains)
            .count();

        return Math.min(1.0, (double) matchedKeywords / jobKeywords.size());
    }

    private static double calculateFormattingScore(String resumeText) {
        double score = 1.0;

        // Check for consistent formatting
        if (!isConsistentFormat(resumeText)) {
            score -= 0.2;
        }

        // Check for proper section headers
        if (!hasProperSectionHeaders(resumeText)) {
            score -= 0.2;
        }

        return Math.max(0, score);
    }

    private static double calculateExperienceScore(String resumeText) {
        // Detect experience sections and professional language
        Pattern experiencePattern = Pattern.compile("(work experience|professional experience|career)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = experiencePattern.matcher(resumeText);

        if (!matcher.find()) {
            return 0.0;
        }

        // Count years of experience
        Pattern yearPattern = Pattern.compile("\\b(\\d+)\\s*(?:year|yr)s?\\b");
        matcher = yearPattern.matcher(resumeText);

        int totalYears = 0;
        while (matcher.find()) {
            totalYears += Integer.parseInt(matcher.group(1));
        }

        return Math.min(1.0, totalYears / 10.0);
    }

    private static double calculateEducationScore(String resumeText) {
        // Check for educational qualifications
        Pattern educationPattern = Pattern.compile("(bachelor|master|phd|degree)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = educationPattern.matcher(resumeText);

        return matcher.find() ? 1.0 : 0.0;
    }

    private static double calculateSkillsScore(String resumeText) {
        // Check for skills section and technical keywords
        Pattern skillsPattern = Pattern.compile("(skills|technical skills)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = skillsPattern.matcher(resumeText);

        return matcher.find() ? 1.0 : 0.5;
    }

    private static List<String> extractKeywords(String text) {
        // Basic keyword extraction (can be enhanced)
        return Arrays.asList(text.split("\\W+"));
    }

    private static boolean isConsistentFormat(String resumeText) {
        // Check for consistent capitalization and spacing
        return !resumeText.matches(".*[A-Z]{2,}.*");
    }

    private static boolean hasProperSectionHeaders(String resumeText) {
        List<String> requiredSections = Arrays.asList(
            "experience", "education", "skills", "summary"
        );

        return requiredSections.stream()
            .allMatch(section -> resumeText.toLowerCase().contains(section));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("ATS Resume Scorer");
        System.out.println("Current Working Directory: " + System.getProperty("user.dir"));
    
        System.out.print("Enter path to resume file (default: SampleResume.txt): ");
        String resumePath = scanner.nextLine().trim();
        if (resumePath.isEmpty()) {
            resumePath = "SampleResume.txt";
        }
    
        System.out.print("Enter path to job description file (default: JobDescription.txt): ");
        String jobDescriptionPath = scanner.nextLine().trim();
        if (jobDescriptionPath.isEmpty()) {
            jobDescriptionPath = "JobDescription.txt";
        }
    
        try {
            File resumeFile = new File(resumePath);
            File jobDescriptionFile = new File(jobDescriptionPath);
    
            if (!resumeFile.exists()) {
                System.err.println("Resume file does not exist: " + resumePath);
                return;
            }
    
            if (!jobDescriptionFile.exists()) {
                System.err.println("Job description file does not exist: " + jobDescriptionPath);
                return;
            }
    
            String resumeText = Files.readString(resumeFile.toPath());
            String jobDescription = Files.readString(jobDescriptionFile.toPath());
    
            ResumeScore score = calculateATSScore(resumeText, jobDescription);
    
            System.out.println("\nATS Score Breakdown:");
            score.categoryScores.forEach((category, categoryScore) -> 
                System.out.printf("%s Score: %.2f%%\n", category, categoryScore * 100)
            );
            System.out.printf("\nTotal ATS Score: %.2f%%\n", score.totalScore);
    
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
