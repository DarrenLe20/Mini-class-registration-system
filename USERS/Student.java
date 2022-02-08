package USERS;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import DATA.Course;
import INTERFACES.Student_methods;

public class Student extends User implements Student_methods, Serializable {
	private static final long serialVersionUID = 1L;
	// Path to Serialized Student data file
	private static final String fn = "/Users/ratmachine/Work/Projects/Mini-class-registration-system/SAVED_DATA/ALL_STUDENTS";
	// Initialize Student ArrayList
	private static ArrayList<Student> studentList = new ArrayList<>();

	// Constructor
	public Student(String firstname, String lastname, String username, String password) {
		super(firstname, lastname, username, password);
	}

	@Override
	public void viewCourses() {
		for (Course a : Course.getCourseList()) {
			// Print each Course
			Course.displayIndividualCourse(a);
		}
		System.out.println();
	}

	@Override
	public void viewVacant() {
		System.out.println("--- Vacant Courses:");
		// Iterate through each Course in the ArrayList and find vacant Courses
		for (Course a : Course.getCourseList()) {
			if (a.getMax() > a.getEnrolled()) {
				// print each vacant Course
				Course.displayIndividualCourse(a);
			}
		}
		System.out.println();
	}

	@Override
	public void register(Student student) {
		// Prompt user to input their first and last name
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			// Prompt user to input their first and last name
			System.out.print("Enter your first name: ");
			String first = in.readLine();
			System.out.print("Enter your last name: ");
			String last = in.readLine();
			String full = "";
			// Validate the user name and create a full name String
			if (student.getFirstname().equalsIgnoreCase(first) && student.getLastname().equalsIgnoreCase(last)) {
				System.out.println("You are registering for username: " + student.getUsername());
				// Create a full name String
				full = student.getFirstname() + " " + student.getLastname();
				// Allow student to add courses if they successfully log in
				System.out.print("Enter the Course ID: ");
				String ID = in.readLine();
				int count = 0;
				for (Course a : Course.getCourseList()) {
					// Only display courses with the same ID that are not FULL
					if (a.getID().equalsIgnoreCase(ID) && a.getMax() > a.getEnrolled()) {
						count++;
						// Formatting
						Course.displayIndividualCourse(a);
						// Prompt user to pick the course to register
					}
				}
				// Notify user if there is no available course
				if (count == 0) {
					System.out.println("There are no available courses (Invalid ID or no vacancies)");
					System.out.println();
					// Prompt user to choose a section to enroll in if there is at least 1 available
				} else {
					System.out.println();
					System.out.print("Enter the number of the section you want to enroll in: ");
					String section = in.readLine();
					// Find the course and add to students ArrayList and increase enrolled student
					// number
					for (Course b : Course.getCourseList()) {
						if (b.getID().equalsIgnoreCase(ID) && b.getSection().equals(section)) {
							// Add student's name to ArrayList
							b.getStudents().add(full);
							// Edit Course's enrolled number
							b.setEnrolled(b.getEnrolled() + 1);
							// Print success notice
							System.out.println("Student has been registered to the course. Changes have been saved");
							System.out.println();
						}
					}
				}
			} else {
				// Notify user if there is no available Student object
				System.out.println("This account is not associated with that name. Please try again");
				System.out.println();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		// Save changes to serialized files
		Course.recordCourses();
		Student.recordStudent();
	}

	@Override
	public void withdraw(Student student) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			// Prompt user to input their first and last name
			System.out.print("Enter your first name: ");
			String first = in.readLine();
			System.out.print("Enter your last name: ");
			String last = in.readLine();
			String full = "";
			// Validate the user name and create a full name String
			if (student.getFirstname().equalsIgnoreCase(first) && student.getLastname().equalsIgnoreCase(last)) {
				System.out.println("You are withdrawing for username: " + student.getUsername());
				// Create a full name string
				full = student.getFirstname() + " " + student.getLastname();
				// Allow student to add courses if they successfully log in
				System.out.print("Enter the Course ID: ");
				String ID = in.readLine();
				int count = 0;
				for (Course a : Course.getCourseList()) {
					// Find course with the same ID in student's Course ArrayList
					if (a.getID().equalsIgnoreCase(ID)) {
						for (String n : a.getStudents()) {
							if (n.equals(full)) {
								count++;
								// Remove name from Course's Students' names ArrayList
								a.getStudents().remove(full);
								// Reduce Enrolled number by 1
								a.setEnrolled(a.getEnrolled() - 1);
								// Notify user of the change and display changed course info
								System.out.println("You have been withdrawn from:");
								Course.displayIndividualCourse(a);
								System.out.println();
								break; // Break loop when corresponding Course is found
							}
						}
					}
				} // Notify user if corresponding Course can't be found
				if (count == 0) {
					System.out.println("You are not registered to any courses with this ID");
					System.out.println();
				}

			} else {
				// Notify user if there is no available Student object
				System.out.println("This account is not associated with that name. Please try again");
				System.out.println();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		// Save changes to serialized files
		Course.recordCourses();
		Student.recordStudent();
		System.out.println("Changes have been saved");
		System.out.println();
	}

	@Override
	public void viewRegistered(Student a) {
		System.out.println("You are currently enrolled in:");
		int count = 0;
		// Create full name String
		String full = a.getFirstname() + " " + a.getLastname();
		// Iterate through each Course in Course ArrayList
		for (Course c : Course.getCourseList()) {
			// Iterate through each element of the student's name ArrayList in each Course
			// object
			for (String n : c.getStudents()) {
				// Display the Course information if an element matches the student's name
				if (n.equals(full)) {
					count++;
					Course.displayIndividualCourse(c);
				}
			}
		}
		System.out.println();
		// Notify user if they are not registered for any courses
		if (count == 0) {
			System.out.println("You are currently NOT enrolled in any courses");
			System.out.println();
		}

	}

	// Method to serialize student data to a file
	public static void recordStudent() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fn, false));
			// Iterate through each Student in the Student ArrayList and write them into a
			// file
			for (Student a : Student.getStudentList()) {
				out.writeObject(a);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to load student data
	public static void loadStudent() throws IOException, ClassNotFoundException {
		// Create a file object to the Course file and check if it is empty
		File studentcheck = new File(Student.getFn());
		// Read from the CSV file if the Course file is empty
		// Only consider file non-empty if its length is greater than 4 to avoid
		// counting null
		if (studentcheck.length() == 0) {
			System.out.println("Student database empty. Recommended action: Add new Students ");
			// If the Course file is not empty, read it into the Course ArrayList
		} else {
			System.out.println("Saved Student data found! Loading data");
			ObjectInputStream readstudent = new ObjectInputStream(new FileInputStream(Student.getFn()));
			Student c;
			try {
				while ((c = (Student) readstudent.readObject()) != null) {
					Student.getStudentList().add(c);
				}
				readstudent.close();
			} catch (EOFException e) {
				readstudent.close();
			}
		}
	}

	// Getters Setters
	public static ArrayList<Student> getStudentList() {
		return studentList;
	}

	public static void setStudentList(ArrayList<Student> studentList) {
		Student.studentList = studentList;
	}

	public static String getFn() {
		return fn;
	}
}
