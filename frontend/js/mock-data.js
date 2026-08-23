/* CampusConnect Mock Rest API Data */
const MOCK_DATA = {
  currentUser: {
    id: "std-101",
    name: "Aarav Sharma",
    email: "aarav.sharma@campus.edu",
    department: "Computer Science & Engineering",
    year: "3rd Year",
    avatar: "AS",
    profileCompletion: 85,
    bio: "Passionate Full-Stack Developer & AI Enthusiast. Building next-gen campus innovation tools and open-source projects.",
    skills: ["React", "Java Spring Boot", "TypeScript", "Python", "PostgreSQL", "Docker", "Tailwind CSS"],
    githubUrl: "https://github.com/aaravsharma",
    linkedinUrl: "https://linkedin.com/in/aaravsharma-dev",
    resumeUploaded: true,
    resumeName: "Aarav_Sharma_Resume_2026.pdf",
    resumeUpdated: "2026-07-15"
  },

  projects: [
    {
      id: "prj-201",
      title: "CampusConnect Innovation Platform",
      description: "A centralized platform for university students to showcase projects, find team members, join interest circles, and access hackathons.",
      detailedDescription: "CampusConnect solves the fragmentation of student collaboration across universities. It features a project showcase, role-matching team finder, skill-tagged directory, and real-time community circles. Built with Java Spring Boot backend architecture and responsive vanilla frontend.",
      category: "Web & Mobile",
      status: "In Development",
      stars: 42,
      views: 380,
      techStack: ["Java", "Spring Boot", "HTML5", "CSS3", "JavaScript", "PostgreSQL"],
      author: "Aarav Sharma",
      authorDept: "Computer Science",
      teamMembers: [
        { name: "Aarav Sharma", role: "Project Lead & Backend", avatar: "AS" },
        { name: "Priya Patel", role: "UI/UX Designer", avatar: "PP" },
        { name: "Rohan Verma", role: "Frontend Dev", avatar: "RV" }
      ],
      openRolesNeeded: ["DevOps Engineer", "Mobile App Dev (React Native)"],
      repoUrl: "https://github.com/campusconnect/platform",
      createdDate: "2026-06-10"
    },
    {
      id: "prj-202",
      title: "EcoPulse - Smart Campus Energy Monitor",
      description: "IoT-based energy monitoring system tracking electricity usage across university hostel blocks with predictive AI analytics.",
      detailedDescription: "EcoPulse utilizes ESP32 smart meters installed in university dorms to record real-time energy usage. An AI predictive model alerts campus administrators of abnormal spikes and offers students gamified energy saving leaderboards.",
      category: "IoT & Hardware",
      status: "Beta Testing",
      stars: 38,
      views: 290,
      techStack: ["Python", "TensorFlow", "ESP32", "MQTT", "React", "Grafana"],
      author: "Sneha Reddy",
      authorDept: "Electrical Engineering",
      teamMembers: [
        { name: "Sneha Reddy", role: "Hardware Lead", avatar: "SR" },
        { name: "Kiran Kumar", role: "Data Scientist", avatar: "KK" }
      ],
      openRolesNeeded: ["Embedded C Developer", "Technical Writer"],
      repoUrl: "https://github.com/ecopulse-campus/iot",
      createdDate: "2026-05-22"
    },
    {
      id: "prj-203",
      title: "MedAssist AI - Clinical Symptom Triage",
      description: "An AI-powered initial symptom assessment tool for student health centers providing instant guidance and appointment scheduling.",
      detailedDescription: "MedAssist AI leverages fine-tuned medical LLMs to process natural language symptom reports from students and categorize urgency levels for the campus health center, drastically reducing triage waiting times.",
      category: "Artificial Intelligence",
      status: "Concept",
      stars: 55,
      views: 510,
      techStack: ["Python", "FastAPI", "Gemini API", "PyTorch", "Tailwind CSS"],
      author: "Dr. Vikram Malhotra & Ananya Das",
      authorDept: "AI & Data Science",
      teamMembers: [
        { name: "Ananya Das", role: "ML Researcher", avatar: "AD" },
        { name: "Kabir Mehta", role: "Backend Developer", avatar: "KM" }
      ],
      openRolesNeeded: ["Frontend Developer", "Clinical Compliance Advisor"],
      repoUrl: "https://github.com/medassist/ai-triage",
      createdDate: "2026-07-01"
    },
    {
      id: "prj-204",
      title: "PeerGrade - Automated Code Peer Reviewer",
      description: "Static code analysis and peer review feedback tool integrated with university LMS for programming assignments.",
      detailedDescription: "PeerGrade streamlines programming assignment grading by automating style checks, test case coverage, and enabling double-blind peer feedback among computer science students.",
      category: "Developer Tools",
      status: "Active",
      stars: 29,
      views: 210,
      techStack: ["Node.js", "Docker", "Go", "Vue.js", "MongoDB"],
      author: "Rahul Nair",
      authorDept: "Information Technology",
      teamMembers: [
        { name: "Rahul Nair", role: "Full Stack Dev", avatar: "RN" }
      ],
      openRolesNeeded: ["Security Auditor", "UX Tester"],
      repoUrl: "https://github.com/peergrade/dev-tool",
      createdDate: "2026-04-18"
    }
  ],

  students: [
    {
      id: "std-101",
      name: "Aarav Sharma",
      department: "Computer Science",
      year: "3rd Year",
      skills: ["Java", "Spring Boot", "React", "TypeScript", "SQL"],
      avatar: "AS",
      bio: "Full-stack developer building scalable web systems and campus tools.",
      github: "https://github.com/aaravsharma",
      linkedin: "https://linkedin.com/in/aaravsharma"
    },
    {
      id: "std-102",
      name: "Priya Patel",
      department: "Information Technology",
      year: "4th Year",
      skills: ["Figma", "UI/UX", "User Research", "Prototyping", "HTML/CSS"],
      avatar: "PP",
      bio: "Product designer focused on clean, accessible human-centered interfaces.",
      github: "https://github.com/priyapatel-ui",
      linkedin: "https://linkedin.com/in/priyapatel-design"
    },
    {
      id: "std-103",
      name: "Sneha Reddy",
      department: "Electrical Engineering",
      year: "3rd Year",
      skills: ["Embedded C", "IoT", "Arduino", "ESP32", "Python"],
      avatar: "SR",
      bio: "Hardware enthusiast connecting real-world sensors to cloud dashboards.",
      github: "https://github.com/snehareddy-iot",
      linkedin: "https://linkedin.com/in/snehareddy"
    },
    {
      id: "std-104",
      name: "Ananya Das",
      department: "AI & Data Science",
      year: "2nd Year",
      skills: ["Python", "PyTorch", "Machine Learning", "NLP", "Pandas"],
      avatar: "AD",
      bio: "Machine learning researcher exploring generative AI and healthcare applications.",
      github: "https://github.com/ananyadas-ml",
      linkedin: "https://linkedin.com/in/ananyadas"
    },
    {
      id: "std-105",
      name: "Rohan Verma",
      department: "Computer Science",
      year: "3rd Year",
      skills: ["React", "JavaScript", "Next.js", "Tailwind CSS", "Git"],
      avatar: "RV",
      bio: "Frontend engineer crafting responsive web experiences and interactive tools.",
      github: "https://github.com/rohanverma-dev",
      linkedin: "https://linkedin.com/in/rohanverma"
    },
    {
      id: "std-106",
      name: "Kiran Kumar",
      department: "Mechanical Engineering",
      year: "4th Year",
      skills: ["CAD/CAM", "SolidWorks", "Robotics", "ROS", "Python"],
      avatar: "KK",
      bio: "Robotics engineer specializing in autonomous rover systems.",
      github: "https://github.com/kirankumar-robotics",
      linkedin: "https://linkedin.com/in/kirankumar"
    }
  ],

  circles: [
    {
      id: "crc-301",
      name: "AI & Machine Learning Guild",
      category: "Artificial Intelligence",
      description: "Weekly paper reading, hands-on model training sessions, and Kaggle competition teams.",
      membersCount: 142,
      joined: true,
      icon: "🤖",
      activeDiscussions: "Fine-tuning Llama 3 for campus query assistant",
      leadName: "Ananya Das"
    },
    {
      id: "crc-302",
      name: "Web3 & Blockchain Developers",
      category: "Software Development",
      description: "Exploring smart contracts, decentralized identity on campus, and zero-knowledge proofs.",
      membersCount: 88,
      joined: false,
      icon: "⛓️",
      activeDiscussions: "Deploying DAO voting smart contract on testnet",
      leadName: "Vikram Shah"
    },
    {
      id: "crc-303",
      name: "UI/UX & Product Design Lab",
      category: "Design & Creative",
      description: "Design critiques, Figma design systems workshops, and usability testing sessions.",
      membersCount: 115,
      joined: true,
      icon: "🎨",
      activeDiscussions: "CampusConnect design tokens and color accessibility check",
      leadName: "Priya Patel"
    },
    {
      id: "crc-304",
      name: "Robotics & Embedded Systems",
      category: "Hardware & IoT",
      description: "Building autonomous micro-rovers, drone navigation, and microcontroller firmware.",
      membersCount: 94,
      joined: false,
      icon: "🦾",
      activeDiscussions: "LIDAR sensor calibration for annual hardware hackathon",
      leadName: "Kiran Kumar"
    },
    {
      id: "crc-305",
      name: "Open Source Contributor Guild",
      category: "Software Engineering",
      description: "Mentoring first-time open source contributors for GSoC, Hacktoberfest, and university libraries.",
      membersCount: 165,
      joined: true,
      icon: "🌐",
      activeDiscussions: "Preparing 10 beginner-friendly issues in Spring Boot backend repo",
      leadName: "Aarav Sharma"
    }
  ],

  teams: [
    {
      id: "tm-401",
      name: "Team CampusConnect Core",
      project: "CampusConnect Innovation Platform",
      membersCount: 3,
      neededRoles: ["DevOps Engineer", "QA Engineer"],
      status: "Recruiting",
      lead: "Aarav Sharma",
      description: "Building the main portal for student collaboration across university departments."
    },
    {
      id: "tm-402",
      name: "EcoPulse Hardware Squad",
      project: "EcoPulse - Smart Campus Energy Monitor",
      membersCount: 2,
      neededRoles: ["Embedded C Developer", "Mobile App Dev"],
      status: "Recruiting",
      lead: "Sneha Reddy",
      description: "Deploying micro-metering IoT devices in campus dormitories."
    },
    {
      id: "tm-403",
      name: "MedAssist AI Researchers",
      project: "MedAssist AI - Clinical Symptom Triage",
      membersCount: 2,
      neededRoles: ["Frontend Dev (React)", "Data Annotator"],
      status: "Recruiting",
      lead: "Ananya Das",
      description: "Researching medical LLM fine-tuning for student health clinics."
    }
  ],

  teamWorkspace: {
    teamId: "tm-401",
    teamName: "Team CampusConnect Core",
    projectName: "CampusConnect Innovation Platform",
    members: [
      { name: "Aarav Sharma", role: "Team Lead & Backend", dept: "Computer Science", avatar: "AS", status: "Active" },
      { name: "Priya Patel", role: "UI/UX Designer", dept: "IT", avatar: "PP", status: "Active" },
      { name: "Rohan Verma", role: "Frontend Developer", dept: "Computer Science", avatar: "RV", status: "Active" }
    ],
    discussions: [
      {
        id: "dsc-1",
        author: "Priya Patel",
        avatar: "PP",
        time: "2 hours ago",
        content: "Updated the Figma design tokens using the exact bordeaux & taupe custom palette! Please review the profile edit modal mockups."
      },
      {
        id: "dsc-2",
        author: "Aarav Sharma",
        avatar: "AS",
        time: "1 day ago",
        content: "Mapped out the Spring Boot REST API endpoints for /api/v1/projects and /api/v1/students. Ready to connect with frontend fetch API."
      }
    ],
    tasks: [
      { id: "tsk-1", title: "Finalize static HTML structure for directory.html", status: "Done", assignee: "Rohan Verma" },
      { id: "tsk-2", title: "Create mockup REST payload for circles and teams", status: "Done", assignee: "Aarav Sharma" },
      { id: "tsk-3", title: "Design team workspace tab navigation UI", status: "In Progress", assignee: "Priya Patel" },
      { id: "tsk-4", title: "Integrate Resume PDF upload drag-and-drop zone", status: "To Do", assignee: "Rohan Verma" }
    ],
    announcements: [
      {
        id: "anc-1",
        title: "Sprint Review & Vercel Preview Deploy",
        date: "July 30, 2026",
        content: "We are presenting our static frontend prototype during the Friday Tech Showcase. Ensure all mock data is populated and responsive."
      },
      {
        id: "anc-2",
        title: "JSP View Porting Strategy Meeting",
        date: "August 2, 2026",
        content: "We will review how class names and data attributes will map into Spring Boot JSTL tag libraries."
      }
    ]
  },

  hackathons: [
    {
      id: "hck-501",
      title: "CampusHack 2026 - Annual Innovation Challenge",
      organizer: "University Tech Council",
      dates: "Aug 15 - Aug 17, 2026",
      status: "Upcoming",
      prizePool: "$5,000 + Incubation Support",
      teamSize: "2 - 4 Members",
      tracks: ["AI for Education", "Smart Campus Sustainability", "Web3 Infrastructure"],
      registeredCount: 128,
      bannerBg: "var(--night-bordeaux)"
    },
    {
      id: "hck-502",
      title: "EcoInnovate Sprint",
      organizer: "Green Earth Student Club",
      dates: "July 28 - Aug 2, 2026",
      status: "Ongoing",
      prizePool: "$2,500 Prize Money",
      teamSize: "1 - 3 Members",
      tracks: ["IoT Hardware", "Clean Energy", "Waste Reduction"],
      registeredCount: 64,
      bannerBg: "var(--stone-brown)"
    },
    {
      id: "hck-503",
      title: "UI/UX Design-a-Thon",
      organizer: "Design Department Guild",
      dates: "Sept 5 - Sept 6, 2026",
      status: "Upcoming",
      prizePool: "$1,500 + Figma Subscriptions",
      teamSize: "Solo or Pair",
      tracks: ["Campus Mobile Apps", "Accessibility in Tech"],
      registeredCount: 45,
      bannerBg: "var(--dusty-taupe)"
    }
  ],

  internships: [
    {
      id: "int-601",
      title: "Full-Stack Software Engineering Intern",
      company: "InnovateTech Campus Lab",
      location: "On-Campus / Hybrid",
      stipend: "$1,200 / month",
      duration: "3 Months (Fall 2026)",
      departmentReq: "Computer Science, IT, ECE",
      deadline: "Aug 10, 2026",
      type: "Research & Development"
    },
    {
      id: "int-602",
      title: "UI/UX Product Design Apprentice",
      company: "Nexus Digital Studio",
      location: "Remote",
      stipend: "$900 / month",
      duration: "6 Months",
      departmentReq: "All Departments (Portfolio Required)",
      deadline: "Aug 20, 2026",
      type: "Industry Partner"
    },
    {
      id: "int-603",
      title: "AI Research Assistant - Computer Vision",
      company: "University Robotics Research Center",
      location: "On-Campus Lab",
      stipend: "$1,500 / month",
      duration: "Academic Year 2026-2027",
      departmentReq: "AI & Data Science, Robotics",
      deadline: "Aug 05, 2026",
      type: "Academic Research"
    }
  ],

  resources: [
    {
      id: "res-701",
      title: "Java Spring Boot & Jakarta EE Core Cheatsheet",
      category: "Cheatsheets & Guides",
      uploader: "Aarav Sharma",
      dept: "Computer Science",
      downloads: 342,
      format: "PDF",
      size: "2.4 MB",
      date: "2026-06-20"
    },
    {
      id: "res-702",
      title: "University Past Year Exam Papers (Data Structures 2023-2025)",
      category: "Exam Papers",
      uploader: "CS Student Association",
      dept: "Computer Science",
      downloads: 890,
      format: "ZIP",
      size: "18.5 MB",
      date: "2026-05-10"
    },
    {
      id: "res-703",
      title: "Responsive UI Component Kit & Custom CSS Tokens",
      category: "Design Kits",
      uploader: "Priya Patel",
      dept: "IT",
      downloads: 215,
      format: "Figma / CSS",
      size: "5.1 MB",
      date: "2026-07-02"
    },
    {
      id: "res-704",
      title: "IoT ESP32 Microcontroller Starter Code",
      category: "Boilerplates",
      uploader: "Sneha Reddy",
      dept: "Electrical Engineering",
      downloads: 178,
      format: "GitHub / ZIP",
      size: "1.2 MB",
      date: "2026-07-14"
    }
  ],

  notifications: [
    {
      id: "ntf-1",
      unread: true,
      icon: "👥",
      title: "Team Join Request",
      message: "Ananya Das requested to join Team CampusConnect Core as ML Researcher.",
      time: "10 mins ago"
    },
    {
      id: "ntf-2",
      unread: true,
      icon: "🤖",
      title: "New Circle Discussion",
      message: "New topic in AI & Machine Learning Guild: 'Fine-tuning Llama 3'.",
      time: "1 hour ago"
    },
    {
      id: "ntf-3",
      unread: false,
      icon: "🏆",
      title: "Hackathon Reminder",
      message: "CampusHack 2026 early bird team registration closes in 5 days.",
      time: "Yesterday"
    },
    {
      id: "ntf-4",
      unread: false,
      icon: "💼",
      title: "New Internship Posted",
      message: "InnovateTech Campus Lab posted a Full-Stack Engineering Internship.",
      time: "2 days ago"
    }
  ]
};

// LocalStorage Persistence Layer
function getStoredData(key, fallback) {
  try {
    const data = localStorage.getItem(`campusconnect_${key}`);
    return data ? JSON.parse(data) : fallback;
  } catch (e) {
    return fallback;
  }
}

function setStoredData(key, value) {
  try {
    localStorage.setItem(`campusconnect_${key}`, JSON.stringify(value));
  } catch (e) {
    console.error("LocalStorage write error", e);
  }
}
