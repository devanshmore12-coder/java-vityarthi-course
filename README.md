This project implements a Minimum Viable Product (MVP) for a University Management System using core Java concepts, demonstrating object-oriented programming (OOP), multithreading, collections, file I/O, and custom exception handling across three integrated modules.
Module	Core Features	Java Concepts Demonstrated
1. Student Management	Registration, Viewing, Updating, Searching.	OOP (Inheritance/Polymorphism), Custom Exceptions, Collections (ArrayList).
2. Course & Enrollment	Concurrent Enrollment Check, Capacity Management.	Multithreading with Synchronization (synchronized), Collections (HashMap).
3. Examination & Grade	GPA Calculation, Transcript Generation.	I/O Streams (BufferedWriter) for reporting, Collections (TreeMap) for sorting, Enums (GradeLetter).
Project Architecture:  The system follows a classic layered architecture (Model-Service-DAO-Controller) implemented entirely in Java.Package StructurePackagePurposeKey Filescom.university.modelEntity classes and OOP hierarchy.Student, UndergraduateStudent, GraduateStudent.com.university.daoIn-memory data persistence (Collections).StudentDAO, CourseDAO, EnrollmentDAO.com.university.serviceBusiness logic and calculation (e.g., GPA, thread-safe enrollment).StudentService, EnrollmentService, GradeService.com.university.controllerHandles console I/O and orchestrates service calls.StudentController, GradeController.com.university.exceptionCustom exceptions for business rules.StudentNotFoundException, CourseFullException, etc.com.university.utilUtilities for reporting and calculations.ReportGenerator, GradeCalculator.
Prerequisites
Java Development Kit (JDK): Version 8 or higher.
A Java IDE: VS Code (with Java Extension Pack) or IntelliJ IDEA.
Installation and Setup
1. Clone the Repository:
git clone [YOUR_REPOSITORY_URL]
cd UniversityManagementSystem
2. Compile and Run (Manual): Navigate to the src/main/java directory:
Bash
cd src/main/java
Compile all files:
Bash
javac com/university/Main.java com/university/controller/*.java com/university/service/*.java com/university/dao/*.java com/university/model/*.java com/university/exception/*.java com/university/enums/*.java com/university/util/*.java
Run the application:
Bash
java -cp . com.university.Main
4. Core Demos (Must-Run Scenarios)
The following options demonstrate the key technical requirements of the MVP.
A. OOP & Polymorphism Demo (Module 1)
This demonstrates the inheritance hierarchy and how tuition calculation differs based on the student type
Go to 1. Student Management.
Run 1.1 Register New Student (Register one Undergraduate, one Graduate (RA=No)).
Run 1.4 List All Students.
Verify: Undergraduate students show a different tuition fee calculation than Graduate students.
B. Multithreading & Synchronization Demo (Module 2)
This demonstrates thread safety by ensuring only one thread can modify the course enrollment count at a time, preventing over-enrollment.
Go to 3. Enrollment Management.
Run 3.4 RUN CONCURRENT ENROLLMENT DEMO.
Scenario: The demo attempts to enroll 10 students into Course CS201 (max capacity 3, 2 spots are already taken).
Verify: Exactly one student succeeds in enrolling (filling the final spot), and the remaining 9 fail with a CourseFullException. The final count remains 3/3.
C. I/O Stream Report Generation Demo (Module 3)
This demonstrates data processing and writing output to the file system using Java I/O Streams.
Go to 4. Grade Management.
Run 4.4 RUN GRADE DEMO (Setup Grades for S001). (This registers a student and gives them an 'A' in course C101).
Run 4.2 Calculate Student GPA (Use Student ID S001).
Verify: The GPA should be 4.00.
Run 4.3 Generate Transcript (I/O Demo) (Use Student ID S001).
Verify: A new file named reports/S001_transcript.txt is created in the project root directory, containing the student's header and grade details.
5. Deliverables Status
Deliverable	Status	Completion Notes
Code Implementation	✅ Complete	All 3 modules and core Java concepts implemented.
README.md	✅ Complete	(This file)
UML Diagrams	❌ Missing	Must be generated and placed in the docs/diagrams folder.
Project Report	❌ Missing	Required 15-section PDF report detailing design and challenges.
JDBC/JPA Layer	⚠️ In-Memory	Data access uses Collections (in-memory) instead of persistent database.






