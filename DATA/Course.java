package DATA;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class Course implements Serializable, Comparable {
	private static final long serialVersionUID = 1L;
	// Path to serialized Course data file
	private static String fn = "/Users/ratmachine/Work/Projects/Mini-class-registration-system/SAVED_DATA/ALL_COURSES";
	// Class members
	private static ArrayList<Course> courseList = new ArrayList<>();
	private String[] info;
	private String name;
	private String ID;
	private int max;
	private int enrolled;
	private String instructor;
	private String location;
	private String section;
	private ArrayList<String> students;

	// Default constructor
	public Course() {
	}

	// Constructors
	public Course(String a) {
		students = new ArrayList<>();
		// Split the String by commas and add elements to the Array
		this.info = a.split(",");
		// Assign array elements to variables
		this.name = this.info[0];
		this.ID = this.info[1];
		this.max = Integer.parseInt(this.info[2]);
		this.enrolled = Integer.parseInt(this.info[3]);
		this.students.add(this.info[4]);
		this.instructor = this.info[5];
		this.section = this.info[6];
		this.location = this.info[7];

	}

	// Compare method
	@Override
	public int compareTo(Object o) {
		int compareNum = ((Course) o).getEnrolled();
		return this.enrolled - compareNum;
	}

	// Method to serialize all course data
	public static void recordCourses() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fn, false));
			// Iterate through each Course in the Course ArrayList and write them to file
			for (Course a : Course.getCourseList()) {
				out.writeObject(a);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method to display a course's information
	public static void displayIndividualCourse(Course a) {
		// Format and print information about the Course
		System.out.printf("%-45s", a.getName());
		System.out.print("ID: " + a.getID() + " \tCapacity: " + Integer.toString(a.getMax()) + " \tStudent number: " +
				Integer.toString(a.getEnrolled()));
		System.out.printf("%-40s", " \tInstructor: " + a.getInstructor());
		System.out.print(" \tSection number: " + a.getSection() + " \tLocation: " + a.getLocation() + "\n");
	}

	// Method to load data
	public static void loadCourses() throws IOException, ClassNotFoundException {
		// Create a file object to the Course file and check if it is empty
		File coursecheck = new File(Course.getFn());
		// Read from the CSV file if the Course file is empty
		// Only consider file non-empty if its length is greater than 4 to avoid
		// counting null
		if (coursecheck.length() == 0) {
			System.out.println("Course database empty. Reading from CSV file");
			// File path to the csv file
			String csv_fn = "/Users/ratmachine/Work/Projects/Mini-class-registration-system/FILES/MyUniversityCourses (4).csv";
			// Read csv file
			BufferedReader br = new BufferedReader(new FileReader(csv_fn));
			String line;
			int line_count = 0;
			// Read every line of the csv file except for the first line
			while ((line = br.readLine()) != null) {
				if (line_count == 0) {
					line_count++;
				} else { // Create new Course object from each line and add it to the ArrayList
					Course b = new Course(line);
					Course.getCourseList().add(b);
				}
			}
			br.close();
			// If the Course file is not empty, read it into the Course ArrayList
		} else {
			System.out.println("Saved Course data found! Loading data");
			ObjectInputStream readcourse = new ObjectInputStream(new FileInputStream(Course.getFn()));
			Course c;
			try {
				// read all non-null objects
				while ((c = (Course) readcourse.readObject()) != null) {
					Course.getCourseList().add(c);
				}
				readcourse.close();
			} catch (EOFException e) {
				readcourse.close();
			}
		}
	}

	// Getters Setters
	public static ArrayList<Course> getCourseList() {
		return courseList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(int enrolled) {
		this.enrolled = enrolled;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ArrayList<String> getStudents() {
		return students;
	}

	public void setStudents(ArrayList<String> students) {
		this.students = students;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public static String getFn() {
		return fn;
	}

}
