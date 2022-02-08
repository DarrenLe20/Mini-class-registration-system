package USERS;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import DATA.Course;
import INTERFACES.Admin_methods;

public class Admin extends User implements Admin_methods, Serializable {
	private static final long serialVersionUID = 1L;
	// Path file to text file to write FULL courses
	private static final String fn = "/Users/ratmachine/Work/Projects/Mini-class-registration-system/SAVED_DATA/FULL_COURSES.txt";

	// Constructor
	public Admin(String firstname, String lastname, String username, String password) {
		super(firstname, lastname, username, password);
	}

	@Override
	public void createCourse() {
		// Use an empty String to hold new Course information
		String all_input = "";
		// Prompt user to enter necessary course information
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("Enter the course name: ");
			all_input += in.readLine() + ",";
			System.out.print("Enter the course ID: ");
			all_input += in.readLine() + ",";
			System.out.print("Enter the maximum students in the course: ");
			all_input += in.readLine() + ",0,NULL,";
			System.out.print("Enter the course instructor's name: ");
			all_input += in.readLine() + ",";
			System.out.print("Enter the course section number: ");
			all_input += in.readLine() + ",";
			System.out.print("Enter the course location: ");
			all_input += in.readLine();
			// Instantiate a new course object and add to ArrayList
			Course custom = new Course(all_input);
			Course.getCourseList().add(custom);
			// Notify user of the successful operation
			System.out.println("Course added. Changes will be saved upon choosing Exit");
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCourse() {
		// Create a new local Course ArrayList
		ArrayList<Course> draft = new ArrayList<>();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			// Prompt user to enter the ID of the course to be removed
			System.out.print("Enter the Course ID of the course(s) you want to delete: ");
			String ID = in.readLine();
			// Display courses with the input ID
			displayCourseInfo(ID);
			// Add them to the new Course ArrayList and increase Course counter accordingly
			for (Course a : Course.getCourseList()) {
				if (a.getID().equalsIgnoreCase(ID)) {
					draft.add(a);
				}
			}
			int count = draft.size();
			// If there is at least 1 Course with the input ID
			if (count != 0) {
				// Prompt user to choose which section to delete
				System.out.print("Enter the section number of the course you want to delete, or enter \"end\" to end: ");
				String choice = in.readLine();
				// Loop until player inputs "end" or Course count reaches 0
				while (choice.equals("end") == false && count != 0) {
					// Loop through the draft ArrayList
					for (Course a : draft) {
						// Remove chosen Course section from the main Course ArrayList and decrease
						// counter by 1
						if (a.getSection().equals(choice)) {
							Course.getCourseList().remove(a);
							count--;
							System.out.println("Course deleted. Changes will be saved upon choosing Exit");
							System.out.println("\nRemaining Courses with the same ID:");
						}
					}
					displayCourseInfo(ID);
					if (count != 0) {
						System.out.print("Enter the section number of the course you want to delete, or enter \"end\" to end: ");
						choice = in.readLine();
					}
				}
				// Notify user if they have removed all sections of the same Course
				if (count == 0) {
					System.out.println("You have removed all Courses with that ID. Changes will be saved upon choosing Exit");
					System.out.println();
				} else {
					System.out.println("Done!");
					System.out.println();
				}
				// Notify user if there are no courses with the input ID
			} else {
				System.out.println("There are no courses with this ID. Please try again");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editCourse() {
		// Create a dummy Course object using default constructor
		Course d = new Course();
		int count = 0;
		ArrayList<Course> draft = new ArrayList<>();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			// Prompt the user to enter the course ID
			System.out.print("Enter the course ID: ");
			String ID = in.readLine();
			// Iterate through each Course in the Course ArrayList and find Courses with the
			// same ID
			for (Course a : Course.getCourseList()) {
				if (a.getID().equalsIgnoreCase(ID)) {
					draft.add(a);
					// Add to counter if Course ID is valid
					count++;
					Course.displayIndividualCourse(a);
				}
			} // Start operation if there is at least 1 Course with the input ID
			if (count != 0) {
				System.out.print("Enter the number of the section you want to edit or enter \"end\" to end: ");
				String choice = in.readLine();
				// Loop until user inputs "end"
				while (choice.equalsIgnoreCase("end") == false) {
					// Display the course being edited
					for (Course b : draft) {
						if (b.getSection().equals(choice)) {
							d = b;
							System.out.println("\nYou are editing this course:");
							Course.displayIndividualCourse(b);
						}
					}
					System.out.println();
					// Display configurable info
					System.out.println("You can edit:");
					System.out.println("(1) Maximum Students capacity");
					System.out.println("(2) Number of Enrolled Students");
					System.out.println("(3) Instructor");
					System.out.println("(4) Course Section Number");
					System.out.println("(5) Location");
					System.out.print("Enter a number to indicate what you would like to edit: ");
					String edit = in.readLine();
					// Edit different parameters depending of the input case
					if (edit.equals("1")) {
						System.out.print("Enter the new maximum student capacity for the Course: ");
						String x = in.readLine();
						for (Course a : Course.getCourseList()) {
							if (a.equals(d)) {
								a.setMax(Integer.parseInt(x));
								System.out.println("Updated info:");
								Course.displayIndividualCourse(a);
							}
						}
						System.out.print("Enter the number of the section you want to edit or enter \"end\" to end: ");
						choice = in.readLine();

					} else if (edit.equals("2")) {
						System.out.print("Enter the number of student to be enrolled in the Course: ");
						String y = in.readLine();
						for (Course a : Course.getCourseList()) {
							if (a.equals(d)) {
								if (a.getMax() < Integer.parseInt(y)) {
									System.out.println("New number of enrolled students exceed the course's capacity. Please try again");
								} else {
									a.setEnrolled(Integer.parseInt(y));
									System.out.println("Updated info:");
									Course.displayIndividualCourse(a);
								}
							}
						}
						System.out.print("Enter the number of the section you want to edit or enter \"end\" to end: ");
						choice = in.readLine();

					} else if (edit.equals("3")) {
						System.out.print("Enter the new instructor for the Course: ");
						String z = in.readLine();
						for (Course a : Course.getCourseList()) {
							if (a.equals(d)) {
								a.setInstructor(z);
								System.out.println("Updated info:");
								Course.displayIndividualCourse(a);
							}
						}
						System.out.print("Enter the number of the section you want to edit or enter \"end\" to end: ");
						choice = in.readLine();

					} else if (edit.equals("4")) {
						System.out.print("Enter the new section number of the Course: ");
						String w = in.readLine();
						for (Course a : Course.getCourseList()) {
							if (a.equals(d)) {
								a.setSection(w);
								System.out.println("Updated info:");
								Course.displayIndividualCourse(a);
							}
						}
						System.out.print("Enter the number of the section you want to edit or enter \"end\" to end: ");
						choice = in.readLine();

					} else if (edit.equals("5")) {
						System.out.print("Enter the new Course location: ");
						String u = in.readLine();
						for (Course a : Course.getCourseList()) {
							if (a.equals(d)) {
								a.setLocation(u);
								System.out.println("Updated info:");
								Course.displayIndividualCourse(a);
							}
						}
						System.out.print("Enter the number of the section you want to edit or enter \"end\" to end: ");
						choice = in.readLine();
					}
				}
				System.out.println("Course edited! Changes will be saved upon choosing Exit");
				System.out.println();
			} else {
				System.out.println("There are no courses with that ID. Please try again");
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void displayCourseInfo(String ID) {
		int count = 0;
		for (Course a : Course.getCourseList()) {
			if (a.getID().equalsIgnoreCase(ID)) {
				count++;
				// Display courses
				Course.displayIndividualCourse(a);
			}
		}
		// Notify user if input ID is invalid
		if (count == 0) {
			System.out.println("There is no course with that ID. Please try again");
		}
		System.out.println();
	}

	@Override
	public void registerStudent() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			// Prompt user to input all necessary information for Student constructor
			System.out.print("Enter new student's first name: ");
			String firstname = in.readLine();
			System.out.print("Enter new student's last name: ");
			String lastname = in.readLine();
			System.out.print("Enter new student's username: ");
			String username = in.readLine();
			System.out.print("Enter new student's password: ");
			String password = in.readLine();
			// Instantiate a new Student object and add to Student ArrayList
			Student custom = new Student(firstname, lastname, username, password);
			Student.getStudentList().add(custom);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Save changes to serialized file
		Student.recordStudent();
		System.out.println("New student added. Changes have been saved");
		System.out.println();
	}

	@Override
	public void viewCourses() {
		// Iterate through each Course in the Course ArrayList
		for (Course a : Course.getCourseList()) {
			// Display courses
			Course.displayIndividualCourse(a);
		}
		System.out.println();

	}

	@Override
	public void viewFullCourses() {
		System.out.println("FULL Courses:");
		int count = 0;
		// Iterate through each Course in the Course ArrayList
		for (Course a : Course.getCourseList()) {
			// Find FULL Courses and print out their information
			if (a.getMax() == a.getEnrolled()) {
				count++;
				// Display Course information
				Course.displayIndividualCourse(a);
			}
		}
		// Notify users if there are no FULL courses
		if (count == 0) {
			System.out.println("There are no FULL Courses");
		}
		System.out.println();

	}

	@Override
	public void recordFullCourses() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fn));
			// Search for full Courses
			for (Course a : Course.getCourseList()) {
				if (a.getMax() == a.getEnrolled()) {
					// Format and write full Courses to a text file
					bw.write(String.format("%-45s", a.getName()) + "ID: " + a.getID() + " \tCapacity: "
							+ Integer.toString(a.getMax()) + " \tStudent number: " +
							Integer.toString(a.getEnrolled()) + String.format("%-40s", " \tInstructor: " + a.getInstructor())
							+ " \tSection number: " + a.getSection() + " \tLocation: " + a.getLocation() + "\n");
				}
			}
			bw.close();
			// Notify user
			System.out.println("All FULL Coures written to FULL_COURSES.txt");
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void viewCourseStudents() {
		int count = 0;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("Enter the course ID: ");
			String ID = in.readLine();
			displayCourseInfo(ID);
			System.out.print("Enter section number: ");
			String section = in.readLine();
			// Find the course
			for (Course a : Course.getCourseList()) {
				if (a.getSection().equals(section) && a.getID().equalsIgnoreCase(ID) && a.getStudents().size() != 0) {
					// Print out enrolled students' names
					System.out.println("Students currently enrolled in this course:");
					for (int i = 0; i < a.getStudents().size(); i++) {
						if (i == 0) {
							continue;
						} else {
							System.out.println("(" + i + ") " + a.getStudents().get(i));
							count++;
						}
					}
				}
			}
			// Notify user if the course has no students
			if (count == 0) {
				System.out.println("This course currently has no students");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println();
	}

	@Override
	public void viewStudentCourses() {
		// Counters
		int count = 0;
		int personalCount = 0;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			// Prompt user to enter the student's first and last name
			System.out.print("Enter the student's first name: ");
			String first = in.readLine();
			System.out.print("Enter the student's last name: ");
			String last = in.readLine();
			System.out.println();
			// Find the student
			for (Student s : Student.getStudentList()) {
				if (s.getFirstname().equalsIgnoreCase(first) && s.getLastname().equalsIgnoreCase(last)) {
					count++;
					// Create full name String
					String full = s.getFirstname() + " " + s.getLastname();
					System.out.println(full + " is currently enrolled in:");
					// Loop through each name in each Course's Student ArrayList
					for (Course c : Course.getCourseList()) {
						for (String n : c.getStudents()) {
							if (n.equals(full)) {
								personalCount++;
								// Display the Course's info if the name is found
								Course.displayIndividualCourse(c);
							}
						}
					}
					// Notify user if student's name can't be found in any courses
					if (personalCount == 0) {
						System.out.println("Student is currently not enrolled in any courses");
					}
				}
			}
			System.out.println();
			// Notify user if there is no student with the input name
			if (count == 0) {
				System.out.println("There are no students with this name. Please try again");
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Sort courses by number of students enrolled
	@SuppressWarnings("unchecked")
	@Override
	public void sortCourses() {
		// sort method from Collection class
		Collections.sort(Course.getCourseList());
		// Notify user
		System.out.println("Courses sorted. Changes will be saved upon choosing Exit");
		System.out.println();
	}

	// Method to view all Students registered in the system
	@Override
	public void viewStudents() {
		String full = "";
		// Iterate through each Student in the Student ArrayList
		for (Student a : Student.getStudentList()) {
			// Create full name String
			full = a.getFirstname() + " " + a.getLastname();
			// Format and print Student information
			System.out.printf("%-20s %-20s %-10s \n", full, "Username: " + a.getUsername(), "Password: " + a.getPassword());
		}

	}

	// Getter Setters
	public static String getFn() {
		return fn;
	}

}
