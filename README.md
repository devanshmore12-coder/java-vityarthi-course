Intelligent University Management System (UMS) MVP
Project Overview
This project implements a Minimum Viable Product (MVP) for a console-based University Management System (UMS) using pure Java. It is designed to demonstrate proficiency in fundamental and advanced Java concepts, including OOP principles (Inheritance, Polymorphism), Multithreading with Synchronization, Custom Exception Handling, and File I/O.
The system integrates three core academic modules, providing essential management functions for students, courses, and grades.

Key Features
The UMS MVP delivers the following functional and conceptual features:
Core Modules
Student Management (Module 1): Full CRUD (Create, Read, Update, Delete) cycle for student profiles, supporting inheritance for different student types (UndergraduateStudent, GraduateStudent).
Course & Enrollment (Module 2): Manages course capacities and implements a thread-safe enrollment system.
Grade Management (Module 3): Handles grade entry, GPA computation, and academic transcript generation.
Conceptual Demos
Multithreading: Features a Concurrent Enrollment Demo (Option 3.4) utilizing the synchronized keyword to prevent race conditions and ensure thread-safe resource management.
OOP: Uses an abstract Student class with polymorphic calculateTuitionFee() implementations.
I/O Streams: Generates comprehensive academic transcripts to a local file using java.io.BufferedWriter.
Collections: Extensive use of ArrayList, HashMap, and TreeMap (for sorted grade reports).
Custom Exceptions: Implements business logic exceptions like StudentNotFoundException and CourseFullException.

Technologies/Tools Used
CategoryTechnology/Tool Notes Language Java Core development language (JDK 8+).Architecture Layered (MVC-style)Separates concerns into Model, DAO, Service, and Controller layers.Persistence In-Memory Collections Data stored in ArrayList and HashMap for fast MVP testing (can be switched to JDBC/JPA easily).Interface Console (Command Line) Simple, text-based interactive menu system. 

Steps to Install & Run the Project
The project is designed to run directly using the Java Development Kit (JDK) from the command line.

Prerequisites
Java Development Kit (JDK) 8 or newer installed on your system.

Running Instructions
1. Clone the Repository:
Bash
git clone [YOUR_REPOSITORY_URL]
cd UniversityManagementSystem
2. Navigate to Source: Move into the directory containing your package structure:
Bash
cd src/main/java
3. Compile the Code: Compile all necessary Java files. (Ensure you run this command successfully with no errors.)
Bash
javac com/university/Main.java com/university/controller/*.java com/university/service/
4. Execute the Application: Run the compiled Main class:
Bash
java -cp . com.university.Main

Instructions for Testing
Use the following test cases to demonstrate the core functionality and technical requirements.

Test 1: Multithreading & Synchronization Demo
This is the primary test for Module 2.

From the Main Menu, choose 3. Enrollment Management.

Choose 3.4 RUN CONCURRENT ENROLLMENT DEMO.

Expected Result: The system will attempt to enroll 10 students into a course with limited capacity. The output must show that only the available spots were filled, with the remaining attempts failing with a CourseFullException. This verifies the thread-safe synchronized block is working.

Test 2: I/O Streams & GPA Calculation Demo
This demonstrates the file writing and complex calculation requirements.

From the Main Menu, choose 4. Grade Management.

Choose 4.4 RUN GRADE DEMO (Setup Grades for S001). (This automatically registers student S001, enrolls them, and records a high grade.)

Choose 4.2 Calculate Student GPA (Enter ID S001).

Choose 4.3 Generate Transcript (I/O Demo) (Enter ID S001).

Expected Result:

4.2 should display a high GPA (e.g., 4.00).

4.3 should confirm "Transcript generated successfully". Check the project root for the reports/S001_transcript.txt file.

Test 3: OOP/Polymorphism Demo
From the Main Menu, choose 1. Student Management.

Run 1.1 Register New Student: Register one Undergraduate and one Graduate student.

Run 1.4 List All Students.

Expected Result: The FEE (Demo) column will show different values for the Undergraduate and Graduate students, demonstrating the polymorphic calculateTuitionFee() method.
