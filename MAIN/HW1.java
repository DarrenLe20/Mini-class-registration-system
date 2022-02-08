package MAIN;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import DATA.Course;
import USERS.*;;

public class HW1 implements Serializable{
	private static final long serialVersionUID = 1L;

	// Method to display user menus
	public static void displayMenu(int a) {
		// Display Admin menu if the parameter is 0
		if (a == 0) {
			System.out.println("--------- ADMIN MENU ---------");
			System.out.println("To pick an option, enter the category letter and the option number. For example, to add a new course, enter M1 or m1");
			System.out.println("-------- (M)ANAGEMENT --------");
			System.out.println("1. Add a new Course");
			System.out.println("2. Delete a Course");
			System.out.println("3. Edit a Course");
			System.out.println("4. Display information for a Course");
			System.out.println("5. Register a student");
			System.out.println("6. Exit");
			System.out.println();
			System.out.println("---------- (R)EPORTS ----------");
			System.out.println("1. View all Courses");
			System.out.println("2. View all FULL Courses");
			System.out.println("3. Write FULL Courses to file");
			System.out.println("4. View the student list for a Course");
			System.out.println("5. View a student's Course list");
			System.out.println("6. Sort Courses by number of students");
			System.out.println("7. View all Students");
			System.out.println("8. Exit");
			System.out.println();
			System.out.print("Your choice: ");
		} else { // Display Student menu if the parameter is not 0
			System.out.println("----------- STUDENT MENU -----------");
			System.out.println("To pick an option, enter the option number. For example, to view all courses, enter 1");
			System.out.println("--------- COURSE MANAGEMENT ---------");
			System.out.println("1. View all Courses");
			System.out.println("2. View all NOT FULL Courses");
			System.out.println("3. Register for a Course");
			System.out.println("4. Withdraw from a Course");
			System.out.println("5. View personal Course list");
			System.out.println("6. Exit");
			System.out.println();
			System.out.print("Your choice: ");
		}
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, EOFException {	
		System.out.println("------------------ COURSE MANAGEMENT SYSTEM ------------------");
		System.out.println();
		// Check if saved data for Courses and Students exists
		Course.loadCourses();
		Student.loadStudent();
		// Ask for user's role
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter A to log in as an Admin and S to log in as a Student (Case sensitive): ");
		String role = in.readLine(); 
		
		// Prompt Admin to enter user name and password
		if (role.equals("A")) {
			System.out.print("Welcome Admin, please enter your username (Case sensitive): ");
			String username = in.readLine();
			System.out.print("Enter your password (Case sensitive): ");
			String password = in.readLine();
			// Validate user name and password 
			while (username.equals("Admin") == false || password.equals("Admin001") == false) {
				System.out.println("Invalid password or username. Please try again");
				System.out.print("\nWelcome Admin, please enter your username (Case sensitive): ");
				username = in.readLine();
				System.out.print("Enter your password (Case sensitive): ");
				password = in.readLine();
			}
			// Initialize new Admin object 
			Admin admin = new Admin("System", "Admin", username, password);
			// AMDIN OPERATION
			// Output the operation menu for the Admin 
			System.out.println();
			displayMenu(0);
			// Prompt user to enter their choice 
			String choice = in.readLine();
			// Loop input prompt until user chooses to exit 
			while (choice.equalsIgnoreCase("M6") == false && choice.equalsIgnoreCase("R8") == false) {
				// Call different methods depending on user input
				if (choice.equalsIgnoreCase("M1")) {
					admin.createCourse();
					// Display menu and prompt user to enter a new choice after each operation 
					displayMenu(0);
					choice = in.readLine();
				}
				else if (choice.equalsIgnoreCase("M2")){
					admin.deleteCourse();
					displayMenu(0);
					choice = in.readLine();
				}
				else if (choice.equalsIgnoreCase("M3")){
					admin.editCourse();
					displayMenu(0);
					choice = in.readLine();
				}
				else if (choice.equalsIgnoreCase("M4")) {
					System.out.print("Enter the course ID: ");
					String ID = in.readLine();
					admin.displayCourseInfo(ID);
					displayMenu(0);
					choice = in.readLine();
				}
				else if (choice.equalsIgnoreCase("M5")){
					admin.registerStudent();
					displayMenu(0);
					choice = in.readLine();
				}
				else if (choice.equalsIgnoreCase("R1")){
					admin.viewCourses();
					displayMenu(0);
					choice = in.readLine();
				}
				else if (choice.equalsIgnoreCase("R2")){
					admin.viewFullCourses();
					displayMenu(0);
					choice = in.readLine();
				}
				else if (choice.equalsIgnoreCase("R3")){
					admin.recordFullCourses();
					displayMenu(0);
					choice = in.readLine();
				}
				else if (choice.equalsIgnoreCase("R4")){
					admin.viewCourseStudents();
					displayMenu(0);
					choice = in.readLine();
				}
				else if (choice.equalsIgnoreCase("R5")){
					admin.viewStudentCourses();
					displayMenu(0);
					choice = in.readLine();
				}
				else if (choice.equalsIgnoreCase("R6")){
					admin.sortCourses();
					displayMenu(0);
					choice = in.readLine();
				}
				else if (choice.equalsIgnoreCase("R7")){
					admin.viewStudents();;
					displayMenu(0);
					choice = in.readLine();
				}
			}
			// Serialize Course data upon exit
			Course.recordCourses();
			System.out.println("Data saved. Goodbye!");
			
		} else if (role.equals("S")) {
			// Student can only log in if there is at least 1 Student object  available
			if (Student.getStudentList().size() == 0) {
				System.out.println("Student database empty. Please contact a System Admin or come back later");
			} else {
				int count = 0;
				// Prompt Student to log in 
				System.out.print("Welcome Student, please enter your username(Case sensitive): ");
				String username = in.readLine();
				System.out.print("Enter your password (Case sensitive): ");
				String password = in.readLine();
				// Validate their input 
				for (Student student: Student.getStudentList()) {
					if (student.getUsername().equals(username) && student.getPassword().equals(password)) {
						System.out.println("Hello " + student.getFirstname() + " " + student.getLastname() + "!");
						System.out.println();
						// STUDENT OPERATION
						// Output the operation menu for the Student 
						displayMenu(1);
						String choice = in.readLine();
						// Loop input prompt until user chooses to exit 
						while (choice.equals("6") == false) {
							// Call different methods depending on user input
							if (choice.equals("1")) {
								student.viewCourses();
								displayMenu(1);
								choice = in.readLine();
							}
							else if (choice.equals("2")) {
								student.viewVacant();
								displayMenu(1);
								choice = in.readLine();
							}
							else if (choice.equals("3")) {
								student.register(student);
								displayMenu(1);
								choice = in.readLine();
							}
							else if (choice.equals("4")) {
								student.withdraw(student);
								displayMenu(1);
								choice = in.readLine();
							}
							else if (choice.equals("5")) {
								student.viewRegistered(student);
								displayMenu(1);
								choice = in.readLine();
							}
						} 
						System.out.println("Data saved. Goodbye!");
					} else {
						count ++;
					}
				} 
				// Notify user if no matching username and password are found
				if (count == Student.getStudentList().size()) {
					System.out.println("Invalid password or username");
				}
			}
		} else {
			// Notify user if they input anything besides "A" and "S" when choosing role
			System.out.println("Invalid input. Restart and try again.");
		}
	}
}
