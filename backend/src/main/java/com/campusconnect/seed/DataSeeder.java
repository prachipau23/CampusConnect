package com.campusconnect.seed;

import com.campusconnect.entity.*;
import com.campusconnect.entity.Notification.EntityType;
import com.campusconnect.repository.*;
import com.campusconnect.service.ResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    @Value("${seed.enabled:false}")
    private boolean seedEnabled;

    private final UserRepository userRepository;
    private final StudentProfileRepository profileRepository;
    private final ProjectRepository projectRepository;
    private final CircleRepository circleRepository;
    private final CircleMembershipRepository circleMembershipRepository;
    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final HackathonRepository hackathonRepository;
    private final InternshipRepository internshipRepository;
    private final ResourceService resourceService;
    private final NotificationRepository notificationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!seedEnabled) {
            log.info("Seed data is disabled. Set seed.enabled=true to populate.");
            return;
        }
        try {
            log.info("=== Checking CampusConnect database seed status ===");

            List<User> students;
            List<User> admins;
            if (userRepository.count() == 0) {
                students = seedStudents();
                admins = seedAdmins();
            } else {
                students = userRepository.findAll().stream().filter(u -> u.getRole() == User.Role.STUDENT).collect(Collectors.toList());
                admins = userRepository.findAll().stream().filter(u -> u.getRole() == User.Role.ADMIN || u.getRole() == User.Role.TEACHER).collect(Collectors.toList());
            }

            if (projectRepository.count() == 0 && !students.isEmpty()) {
                seedProjects(students);
            }

            if (circleRepository.count() == 0) {
                List<Circle> circles = seedCircles();
                if (!students.isEmpty()) seedCircleMemberships(circles, students);
            }

            if (teamRepository.count() == 0 && !students.isEmpty()) {
                seedTeams(students, projectRepository.findAll());
            }

            if (hackathonRepository.count() == 0) {
                seedHackathons();
            }

            if (internshipRepository.count() == 0) {
                seedInternships();
            }

            if (resourceRepository.count() == 0) {
                User uploader = !admins.isEmpty() ? admins.get(0) : (!students.isEmpty() ? students.get(0) : null);
                if (uploader != null) {
                    seedResources(uploader, students);
                }
            }

            if (notificationRepository.count() == 0 && !students.isEmpty()) {
                seedNotifications(students, projectRepository.findAll(), teamRepository.findAll(), circleRepository.findAll());
            }

            log.info("=== Seed check complete ===");
        } catch (Exception e) {
            log.error("Error during database seeding: {}", e.getMessage(), e);
        }
    }

    private List<User> seedStudents() {
        String[][] data = {
            {"aarav.sharma", "aarav.sharma@campus.edu", "Aarav Sharma", "Computer Science & Engineering", "3rd Year", "8.7", "Passionate Full-Stack Developer. Loves OSS."},
            {"priya.patel", "priya.patel@campus.edu", "Priya Patel", "Data Science", "2nd Year", "9.2", "ML researcher interested in NLP."},
            {"rohan.verma", "rohan.verma@campus.edu", "Rohan Verma", "Electronics Engineering", "4th Year", "7.8", "Embedded systems and IoT enthusiast."},
            {"sneha.iyer", "sneha.iyer@campus.edu", "Sneha Iyer", "Computer Science & Engineering", "1st Year", "9.5", "Competitive programmer."},
            {"arjun.nair", "arjun.nair@campus.edu", "Arjun Nair", "Mechanical Engineering", "3rd Year", "7.2", "Robotics club president."},
            {"kavya.reddy", "kavya.reddy@campus.edu", "Kavya Reddy", "Data Science", "4th Year", "8.9", "Data visualization specialist."},
            {"vivek.kumar", "vivek.kumar@campus.edu", "Vivek Kumar", "Computer Science & Engineering", "2nd Year", "8.1", "Android developer. Open source contributor."},
            {"ananya.singh", "ananya.singh@campus.edu", "Ananya Singh", "Information Technology", "3rd Year", "9.0", "UX designer and frontend developer."},
            {"karan.mehta", "karan.mehta@campus.edu", "Karan Mehta", "Computer Science & Engineering", "4th Year", "7.5", "Blockchain and Web3 enthusiast."},
            {"divya.krishna", "divya.krishna@campus.edu", "Divya Krishna", "Data Science", "1st Year", "9.1", "Statistics background, deep learning focus."},
            {"rahul.joshi", "rahul.joshi@campus.edu", "Rahul Joshi", "Electronics Engineering", "2nd Year", "6.8", "Signal processing and VLSI design."},
            {"meera.nambiar", "meera.nambiar@campus.edu", "Meera Nambiar", "Computer Science & Engineering", "3rd Year", "8.5", "Distributed systems researcher."},
            {"aditya.shetty", "aditya.shetty@campus.edu", "Aditya Shetty", "Mechanical Engineering", "4th Year", "7.0", "CAD/CAM and 3D printing projects."},
            {"pooja.agarwal", "pooja.agarwal@campus.edu", "Pooja Agarwal", "Information Technology", "2nd Year", "8.8", "Cybersecurity and ethical hacking."},
            {"siddharth.rao", "siddharth.rao@campus.edu", "Siddharth Rao", "Computer Science & Engineering", "1st Year", "9.3", "Competitive programmer, ACM ICPC qualifier."},
            {"ishaan.bose", "ishaan.bose@campus.edu", "Ishaan Bose", "Data Science", "3rd Year", "8.2", "Sports analytics and predictive modeling."},
            {"tanvi.gupta", "tanvi.gupta@campus.edu", "Tanvi Gupta", "Computer Science & Engineering", "4th Year", "7.9", "Game developer, Unity expert."},
            {"nikhil.saxena", "nikhil.saxena@campus.edu", "Nikhil Saxena", "Electronics Engineering", "2nd Year", "6.5", "RF engineering and antenna design."},
            {"ritika.chaudhary", "ritika.chaudhary@campus.edu", "Ritika Chaudhary", "Information Technology", "3rd Year", "8.4", "Cloud computing and DevOps."},
            {"harshit.pandey", "harshit.pandey@campus.edu", "Harshit Pandey", "Computer Science & Engineering", "4th Year", "9.8", "AI/ML research, published 2 papers."},
        };

        String[][] skillsData = {
            {"Java", "Spring Boot", "React", "PostgreSQL", "Docker"},
            {"Python", "TensorFlow", "PyTorch", "Pandas", "Scikit-learn"},
            {"C", "C++", "Arduino", "Raspberry Pi", "MATLAB"},
            {"C++", "Python", "Algorithms", "Data Structures"},
            {"SolidWorks", "Arduino", "ROS", "Python"},
            {"Python", "Tableau", "PowerBI", "SQL", "R"},
            {"Kotlin", "Android", "Firebase", "Java", "Git"},
            {"Figma", "HTML", "CSS", "JavaScript", "React"},
            {"Solidity", "Web3.js", "Ethereum", "JavaScript", "Node.js"},
            {"Python", "Deep Learning", "Statistics", "Keras", "NumPy"},
            {"MATLAB", "Verilog", "FPGA", "Signal Processing"},
            {"Java", "Go", "Kubernetes", "Kafka", "Redis"},
            {"SolidWorks", "ANSYS", "3D Printing", "AutoCAD"},
            {"Cybersecurity", "Kali Linux", "Python", "Networking"},
            {"C++", "Python", "Competitive Programming", "Algorithms"},
            {"Python", "R", "Sports Analytics", "Machine Learning"},
            {"Unity", "C#", "Blender", "Game Design"},
            {"MATLAB", "Antenna Design", "RF Engineering", "EM Simulation"},
            {"AWS", "GCP", "Docker", "Kubernetes", "Terraform"},
            {"Python", "PyTorch", "Research", "NLP", "Computer Vision"},
        };

        String[] depts = {"CS", "DS", "EC", "CS", "ME", "DS", "CS", "IT", "CS", "DS",
                          "EC", "CS", "ME", "IT", "CS", "DS", "CS", "EC", "IT", "CS"};

        List<User> users = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            String[] d = data[i];
            User user = User.builder()
                    .username(d[0])
                    .email(d[1])
                    .passwordHash(passwordEncoder.encode("Campus@123"))
                    .role(User.Role.STUDENT)
                    .build();
            user = userRepository.save(user);

            String[] nameParts = d[2].split(" ");
            String initials = nameParts.length >= 2
                    ? String.valueOf(nameParts[0].charAt(0)) + String.valueOf(nameParts[1].charAt(0))
                    : d[0].substring(0, 2).toUpperCase();

            StudentProfile profile = StudentProfile.builder()
                    .user(user)
                    .fullName(d[2])
                    .department(d[3])
                    .yearOfStudy(d[4])
                    .gpa(new BigDecimal(d[5]))
                    .bio(d[6])
                    .skills(String.join(", ", skillsData[i]))
                    .avatarInitials(initials.toUpperCase())
                    .githubUrl("https://github.com/" + d[0].replace(".", "-"))
                    .linkedinUrl("https://linkedin.com/in/" + d[0].replace(".", "-"))
                    .performanceNotes("Department rank: " + (i + 1) + ". Consistent performance in " + d[3] + ".")
                    .build();
            profileRepository.save(profile);
            users.add(user);
        }
        log.info("Seeded {} students", users.size());
        return users;
    }

    private List<User> seedAdmins() {
        String[][] admins = {
            {"admin.campusconnect", "admin@campus.edu", "Dr. Meenakshi Rajan", "Admin"},
            {"teacher.cs", "teacher.cs@campus.edu", "Prof. Suresh Krishnaswamy", "Teacher"},
        };
        List<User> adminUsers = new ArrayList<>();
        for (String[] a : admins) {
            User.Role role = a[3].equals("Admin") ? User.Role.ADMIN : User.Role.TEACHER;
            User admin = User.builder()
                    .username(a[0])
                    .email(a[1])
                    .passwordHash(passwordEncoder.encode("Admin@123"))
                    .role(role)
                    .build();
            admin = userRepository.save(admin);

            StudentProfile profile = StudentProfile.builder()
                    .user(admin)
                    .fullName(a[2])
                    .department("Administration")
                    .avatarInitials(a[2].substring(0, 2).toUpperCase())
                    .gpa(null)
                    .performanceNotes(role.name() + " account")
                    .build();
            profileRepository.save(profile);
            adminUsers.add(admin);
        }
        log.info("Seeded {} admin/teacher accounts", adminUsers.size());
        return adminUsers;
    }

    private List<Project> seedProjects(List<User> students) {
        Object[][] projectData = {
            {"CampusConnect Platform", "Student collaboration platform with real-time notifications, project showcase, and team formation.", "Java, Spring Boot, PostgreSQL, React, Docker", "https://github.com/sample/campusconnect", students.get(0), Project.ProjectStatus.ACTIVE},
            {"ML-Powered Resume Screener", "AI tool that parses resumes and ranks candidates for internship roles using NLP.", "Python, BERT, FastAPI, React, MongoDB", "https://github.com/sample/resume-ai", students.get(1), Project.ProjectStatus.ACTIVE},
            {"Smart Campus Navigation", "IoT-based indoor navigation system using BLE beacons and a mobile app.", "Android, Kotlin, Arduino, BLE, Firebase", "https://github.com/sample/smart-nav", students.get(2), Project.ProjectStatus.COMPLETED},
            {"Open Source Code Review Bot", "GitHub Action that automatically reviews PRs for code quality and security issues.", "Python, GitHub API, OpenAI API, Docker", "https://github.com/sample/review-bot", students.get(6), Project.ProjectStatus.ACTIVE},
            {"Decentralized Voting System", "Tamper-proof voting system using Ethereum smart contracts for college elections.", "Solidity, Web3.js, React, Hardhat", "https://github.com/sample/decentral-vote", students.get(8), Project.ProjectStatus.ACTIVE},
            {"Sports Analytics Dashboard", "Real-time analytics for college sports teams with predictive performance modeling.", "Python, FastAPI, Tableau, PostgreSQL, scikit-learn", "https://github.com/sample/sports-analytics", students.get(15), Project.ProjectStatus.ACTIVE},
            {"Campus Resource Finder", "Aggregates and searches campus resources (labs, library slots, sports fields) in one app.", "React, Node.js, MongoDB, Google Maps API", "https://github.com/sample/resource-finder", students.get(7), Project.ProjectStatus.COMPLETED},
            {"Cyber Threat Intelligence System", "OSINT-based threat intelligence aggregator for monitoring campus network security.", "Python, Elasticsearch, Kibana, Kafka", "https://github.com/sample/cyber-threat", students.get(13), Project.ProjectStatus.ACTIVE},
            {"AR Chemistry Lab Simulator", "Augmented reality app simulating chemistry experiments for remote lab sessions.", "Unity, C#, ARCore, Firebase", "https://github.com/sample/ar-chem-lab", students.get(16), Project.ProjectStatus.ARCHIVED},
            {"Cloud Cost Optimizer", "Tool that analyzes AWS/GCP usage and recommends cost-saving configurations.", "Python, Terraform, AWS SDK, React", "https://github.com/sample/cloud-optimizer", students.get(18), Project.ProjectStatus.ACTIVE},
        };

        List<Project> projects = new ArrayList<>();
        for (Object[] pd : projectData) {
            Project project = Project.builder()
                    .title((String) pd[0])
                    .description((String) pd[1])
                    .techStack((String) pd[2])
                    .githubUrl((String) pd[3])
                    .owner((User) pd[4])
                    .status((Project.ProjectStatus) pd[5])
                    .build();
            projects.add(projectRepository.save(project));
        }
        log.info("Seeded {} projects", projects.size());
        return projects;
    }

    private List<Circle> seedCircles() {
        Object[][] circleData = {
            {"Web & App Developers", "Build web and mobile applications together. Share resources, pair-program, and ship projects!", "Technology", "💻", 145},
            {"AI/ML Research Circle", "Cutting-edge discussions on machine learning, deep learning, and AI research papers.", "Technology", "🤖", 112},
            {"Robotics & IoT Club", "Hardware meets software. Build robots, IoT systems, and embedded projects.", "Engineering", "🔧", 67},
            {"Game Development Guild", "From indie games to AAA concepts. Unity, Unreal, and game design discussions.", "Creative", "🎮", 89},
            {"Open Source Warriors", "Contribute to open source projects, organize hackathons, and build your GitHub profile.", "Community", "⚡", 203},
            {"Entrepreneurship & Startups", "Business ideas, pitch practice, startup resources, and mentor connections.", "Business", "🚀", 156},
        };

        List<Circle> circles = new ArrayList<>();
        for (Object[] cd : circleData) {
            Circle circle = Circle.builder()
                    .name((String) cd[0])
                    .description((String) cd[1])
                    .category((String) cd[2])
                    .iconEmoji((String) cd[3])
                    .memberCount((Integer) cd[4])
                    .build();
            circles.add(circleRepository.save(circle));
        }
        log.info("Seeded {} circles", circles.size());
        return circles;
    }

    private void seedCircleMemberships(List<Circle> circles, List<User> students) {
        // Assign students to circles in a spread pattern
        int[][] memberships = {
            {0, 1, 2, 6, 7, 11, 14, 18, 19}, // Circle 0: Web & App
            {1, 9, 10, 15, 19},                // Circle 1: AI/ML
            {2, 4, 10, 12, 17},                // Circle 2: Robotics
            {6, 7, 16},                         // Circle 3: Game Dev
            {0, 3, 5, 6, 13, 14, 18},          // Circle 4: OSS
            {0, 4, 5, 8, 15},                   // Circle 5: Entrepreneurship
        };
        for (int ci = 0; ci < circles.size(); ci++) {
            for (int si : memberships[ci]) {
                if (si < students.size()) {
                    CircleMembership m = CircleMembership.builder()
                            .circle(circles.get(ci))
                            .user(students.get(si))
                            .build();
                    circleMembershipRepository.save(m);
                }
            }
        }
        log.info("Seeded circle memberships");
    }

    private List<Team> seedTeams(List<User> students, List<Project> projects) {
        Object[][] teamData = {
            {"NeuralNinjas", "AI research team building a campus-specific recommendation engine.", 4, Team.TeamStatus.OPEN, 0, 1},
            {"FullStack Foundry", "Web app development team — front to back, design to deploy.", 5, Team.TeamStatus.OPEN, 3, 6},
            {"Blockchain Builders", "Decentralized app team working on campus voting and credential verification.", 3, Team.TeamStatus.CLOSED, 8, 4},
            {"RoboRangers", "Robotics competition team preparing for the National Robotics Championship.", 6, Team.TeamStatus.OPEN, 4, 2},
            {"SecureStack", "Cybersecurity red team competing in CTF competitions.", 4, Team.TeamStatus.OPEN, 13, 7},
            {"CloudCrafters", "Cloud infrastructure and DevOps automation team.", 5, Team.TeamStatus.CLOSED, 18, 9},
            {"GameForge", "Indie game development team building a campus-themed RPG.", 4, Team.TeamStatus.OPEN, 16, 8},
            {"DataDriven", "Data science team analyzing campus trends and student performance data.", 5, Team.TeamStatus.OPEN, 15, 5},
        };

        List<Team> teams = new ArrayList<>();
        for (Object[] td : teamData) {
            int projectIdx = (int) td[5];
            Team team = Team.builder()
                    .name((String) td[0])
                    .description((String) td[1])
                    .maxSize((int) td[2])
                    .status((Team.TeamStatus) td[3])
                    .linkedProject(projectIdx < projects.size() ? projects.get(projectIdx) : null)
                    .build();
            team = teamRepository.save(team);

            int leadIdx = (int) td[4];
            TeamMember lead = TeamMember.builder()
                    .team(team)
                    .user(students.get(leadIdx))
                    .role(TeamMember.MemberRole.LEAD)
                    .build();
            teamMemberRepository.save(lead);

            // Add 1-2 more members per team
            int memberIdx = (leadIdx + 1) % students.size();
            TeamMember member = TeamMember.builder()
                    .team(team)
                    .user(students.get(memberIdx))
                    .role(TeamMember.MemberRole.MEMBER)
                    .build();
            teamMemberRepository.save(member);

            teams.add(team);
        }
        log.info("Seeded {} teams", teams.size());
        return teams;
    }

    private void seedHackathons() {
        Object[][] hackData = {
            {"Smart India Hackathon 2026", "National-level hackathon for problem statements from Indian government ministries.", "Ministry of Education, India", "Pan-India (Online)", LocalDate.of(2026, 9, 20), LocalDate.of(2026, 9, 22), new BigDecimal("100000"), "https://sih.gov.in", "Online"},
            {"Google Solution Challenge 2026", "Build a solution for one of the 17 UN Sustainable Development Goals using Google tech.", "Google", "Global (Online)", LocalDate.of(2026, 10, 1), LocalDate.of(2026, 10, 3), new BigDecimal("500000"), "https://developers.google.com/community/gdsc-solution-challenge", "Online"},
            {"HackWithInfy 2026", "Infosys hackathon for engineering students — real business problem statements.", "Infosys", "Bengaluru / Online", LocalDate.of(2026, 11, 5), LocalDate.of(2026, 11, 6), new BigDecimal("200000"), "https://infosys.com/hackathon", "Hybrid"},
            {"IIT Bombay Techfest Coding Sprint", "48-hour coding sprint at one of India's premier tech festivals.", "IIT Bombay", "Mumbai, Maharashtra", LocalDate.of(2026, 12, 10), LocalDate.of(2026, 12, 12), new BigDecimal("150000"), "https://techfest.org", "Offline"},
            {"Microsoft Imagine Cup 2026", "Global student innovation competition — build solutions using Microsoft Azure.", "Microsoft", "Global (Online)", LocalDate.of(2026, 10, 15), LocalDate.of(2027, 1, 30), new BigDecimal("750000"), "https://imaginecup.microsoft.com", "Online"},
            {"NASSCOM GenAI Hackathon", "Build generative AI solutions for real-world enterprise problems.", "NASSCOM", "Hyderabad / Online", LocalDate.of(2026, 9, 28), LocalDate.of(2026, 9, 29), new BigDecimal("300000"), "https://nasscom.in", "Hybrid"},
        };

        for (Object[] hd : hackData) {
            Hackathon h = Hackathon.builder()
                    .name((String) hd[0])
                    .description((String) hd[1])
                    .organizer((String) hd[2])
                    .location((String) hd[3])
                    .startDate((LocalDate) hd[4])
                    .endDate((LocalDate) hd[5])
                    .prizeAmount((BigDecimal) hd[6])
                    .registrationUrl((String) hd[7])
                    .mode((String) hd[8])
                    .build();
            hackathonRepository.save(h);
        }
        log.info("Seeded 6 hackathons");
    }

    private void seedInternships() {
        Object[][] internData = {
            {"Software Development Intern", "Google", "Work on large-scale distributed systems and contribute to Google's core infrastructure.", "Hyderabad, India", "Hybrid", "3 months", new BigDecimal("80000"), LocalDate.of(2026, 10, 15), "https://careers.google.com"},
            {"Data Science Intern", "Microsoft", "Build ML models for Microsoft Teams analytics. Work with petabyte-scale datasets.", "Bengaluru, India", "Hybrid", "6 months", new BigDecimal("70000"), LocalDate.of(2026, 9, 30), "https://careers.microsoft.com"},
            {"Backend Engineering Intern", "Razorpay", "Design and implement payment processing microservices in Java/Kotlin.", "Bengaluru, India", "Onsite", "4 months", new BigDecimal("60000"), LocalDate.of(2026, 11, 1), "https://razorpay.com/jobs"},
            {"DevOps & Cloud Intern", "Infosys", "Automate CI/CD pipelines and manage Kubernetes clusters for enterprise clients.", "Pune, India", "Hybrid", "3 months", new BigDecimal("40000"), LocalDate.of(2026, 10, 30), "https://infosys.com/careers"},
            {"ML Research Intern", "Samsung Research", "Research on on-device AI models for next-gen Galaxy devices.", "Noida, India", "Onsite", "6 months", new BigDecimal("55000"), LocalDate.of(2026, 9, 15), "https://samsung.com/research"},
            {"Cybersecurity Analyst Intern", "Wipro", "Conduct vulnerability assessments and implement security policies for enterprise clients.", "Chennai, India", "Onsite", "3 months", new BigDecimal("35000"), LocalDate.of(2026, 12, 1), "https://wipro.com/careers"},
        };

        for (Object[] id : internData) {
            Internship internship = Internship.builder()
                    .title((String) id[0])
                    .company((String) id[1])
                    .description((String) id[2])
                    .location((String) id[3])
                    .mode((String) id[4])
                    .duration((String) id[5])
                    .stipend((BigDecimal) id[6])
                    .deadline((LocalDate) id[7])
                    .applyUrl((String) id[8])
                    .build();
            internshipRepository.save(internship);
        }
        log.info("Seeded 6 internships");
    }

    private void seedResources(User admin, List<User> students) throws Exception {
        Object[][] resourceData = {
            {"Java Spring Boot Complete Guide", "Comprehensive guide covering Spring Boot 3.x, JPA, Security, and REST APIs.", "PDF", "Guides", "spring-boot-guide.pdf", "application/pdf"},
            {"Data Structures & Algorithms Cheatsheet", "Quick reference for common DSA patterns: sorting, searching, dynamic programming, graphs.", "PDF", "CS Fundamentals", "dsa-cheatsheet.pdf", "application/pdf"},
            {"System Design Interview Handbook", "Covers load balancing, caching, databases, microservices, and real-world system architectures.", "PDF", "Interview Prep", "system-design-handbook.pdf", "application/pdf"},
            {"Machine Learning Model Deployment Guide", "Step-by-step guide to deploying ML models with Docker, FastAPI, and cloud services.", "PDF", "AI/ML", "ml-deployment-guide.pdf", "application/pdf"},
            {"PostgreSQL Performance Tuning", "Advanced PostgreSQL tuning tips: indexing strategies, query planning, and connection pooling.", "PDF", "Databases", "postgresql-tuning.pdf", "application/pdf"},
            {"React Hooks and State Management", "Deep dive into React Hooks, Context API, Redux Toolkit, and Zustand.", "PDF", "Frontend", "react-hooks-guide.pdf", "application/pdf"},
            {"Kubernetes for Developers", "Getting started with Kubernetes: pods, services, deployments, and helm charts.", "PDF", "DevOps", "kubernetes-intro.pdf", "application/pdf"},
            {"Resume Tips for Tech Internships", "Industry-vetted resume writing guide specifically for software and data engineering roles.", "PDF", "Career", "resume-tips.pdf", "application/pdf"},
            {"Git & GitHub Best Practices", "Branching strategies, commit conventions, code review etiquette, and CI/CD integration.", "TXT", "DevOps", "git-best-practices.txt", "text/plain"},
            {"Competitive Programming Contest Strategies", "Time complexity analysis, greedy algorithms, DP tricks, and contest-day tips.", "TXT", "CS Fundamentals", "cp-strategies.txt", "text/plain"},
        };

        String[] pdfContents = {
            "%PDF-1.4\n1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] /Contents 4 0 R >>\nendobj\n4 0 obj\n<< /Length 200 >>\nstream\nBT\n/F1 24 Tf\n50 750 Td\n(CampusConnect - Spring Boot Guide) Tj\n0 -40 Td\n/F1 12 Tf\n(Chapter 1: Project Setup - Use Spring Initializr) Tj\n0 -20 Td\n(Chapter 2: JPA Entities and Repositories) Tj\n0 -20 Td\n(Chapter 3: Spring Security with JWT) Tj\n0 -20 Td\n(Chapter 4: REST API Design Best Practices) Tj\nET\nendstream\nendobj\nxref\n0 5\n0000000000 65535 f\n0000000009 00000 n\n0000000058 00000 n\n0000000115 00000 n\n0000000206 00000 n\ntrailer\n<< /Size 5 /Root 1 0 R >>\nstartxref\n460\n%%EOF",
            "%PDF-1.4\n1 0 obj\n<< /Type /Catalog /Pages 2 0 R >>\nendobj\n2 0 obj\n<< /Type /Pages /Kids [3 0 R] /Count 1 >>\nendobj\n3 0 obj\n<< /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] >>\nendobj\ntrailer\n<< /Size 4 /Root 1 0 R >>\nstartxref\n150\n%%EOF",
        };

        String txtContent1 = "CampusConnect - Git Best Practices\n\nBRANCHING STRATEGY\n==================\n- main: production-ready code only\n- develop: integration branch\n- feature/*: feature branches\n- hotfix/*: critical production fixes\n\nCOMMIT CONVENTIONS\n==================\nfeat: add new feature\nfix: bug fix\ndocs: documentation changes\nchore: build/tool changes\n\nCODE REVIEW ETIQUETTE\n====================\n- Review within 24 hours\n- Comment on the code, not the author\n- Approve only when confident\n- Request changes clearly\n";

        String txtContent2 = "CampusConnect - Competitive Programming Strategies\n\nTIME COMPLEXITY GUIDE\n=====================\nO(1) - Hash table lookups\nO(log n) - Binary search, balanced BST\nO(n) - Linear scan, BFS/DFS\nO(n log n) - Merge sort, efficient sorting\nO(n^2) - Nested loops (avoid for n>10^4)\n\nCOMMON PATTERNS\n===============\n1. Two Pointers - sorted array problems\n2. Sliding Window - subarray/substring problems\n3. Binary Search on Answer - optimization problems\n4. Union-Find - connectivity problems\n5. Segment Tree - range query problems\n";

        for (int i = 0; i < resourceData.length; i++) {
            Object[] rd = resourceData[i];
            byte[] fileBytes;
            if (rd[2].equals("TXT")) {
                fileBytes = (i == 8 ? txtContent1 : txtContent2).getBytes();
            } else {
                // Alternate between two real PDF skeletons
                fileBytes = pdfContents[i % 2].getBytes();
            }

            User uploader = (i < 5 || students.isEmpty()) ? admin : students.get(i % students.size());
            resourceService.createWithBytes(
                    (String) rd[0],
                    (String) rd[1],
                    (String) rd[3],
                    (String) rd[4],
                    (String) rd[5],
                    fileBytes,
                    uploader
            );
        }
        log.info("Seeded 10 resource files with real content");
    }

    private void seedNotifications(List<User> students, List<Project> projects,
                                   List<Team> teams, List<Circle> circles) {
        Object[][] notifData = {
            {0, "New Project Star", "Your project 'CampusConnect Platform' received 12 new stars!", EntityType.PROJECT, 0L},
            {1, "Team Recruitment", "New team 'NeuralNinjas' is looking for ML researchers. Apply now!", EntityType.TEAM, 0L},
            {2, "Hackathon Reminder", "Smart India Hackathon registration closes in 3 days!", EntityType.HACKATHON, 0L},
            {3, "Internship Deadline", "Google internship deadline is approaching — apply before Oct 15!", EntityType.INTERNSHIP, 0L},
            {4, "Team Invitation", "You've been added to the 'RoboRangers' team.", EntityType.TEAM, 3L},
            {5, "Project Feedback", "Kavya Reddy commented on your Data Science project.", EntityType.PROJECT, 5L},
            {6, "New Learning Resource", "New resource uploaded: 'Kubernetes for Developers' — check it out!", EntityType.RESOURCE, 6L},
            {7, "Circle Invite", "Ananya Singh invited you to join the 'Web & App Developers' circle.", EntityType.CIRCLE, 0L},
            {8, "Team Need", "Decentralized Voting System project needs a frontend developer.", EntityType.PROJECT, 4L},
            {9, "Hackathon Open", "Microsoft Imagine Cup registration is open. Team up and register!", EntityType.HACKATHON, 4L},
            {10, "Internship Update", "Infosys DevOps internship has new openings!", EntityType.INTERNSHIP, 3L},
            {11, "Workspace Message", "New workspace post in 'CloudCrafters': deployment pipeline is live.", EntityType.WORKSPACE, 5L},
            {12, "Profile View", "Your profile was viewed 45 times this week.", EntityType.PROFILE, 0L},
            {13, "Workspace Update", "SecureStack team leader posted a new update in the workspace.", EntityType.WORKSPACE, 4L},
            {14, "Internship Application", "Samsung Research ML internship deadline: Sep 15. Apply now!", EntityType.INTERNSHIP, 4L},
        };

        for (Object[] nd : notifData) {
            int studentIdx = (int) nd[0];
            if (studentIdx >= students.size()) continue;
            Notification n = Notification.builder()
                    .targetUser(students.get(studentIdx))
                    .title((String) nd[1])
                    .message((String) nd[2])
                    .targetEntityType((EntityType) nd[3])
                    .targetEntityId((Long) nd[4])
                    .read(studentIdx % 3 == 0)
                    .build();
            notificationRepository.save(n);
        }
        log.info("Seeded 15 notifications across {} entity types", EntityType.values().length);
    }
}
