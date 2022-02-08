package INTERFACES;

public interface Admin_methods {
	// Methods to be implemented by Admin class
	public abstract void createCourse();
	public abstract void deleteCourse();
	public abstract void editCourse();
	public abstract void displayCourseInfo(String a);
	public abstract void registerStudent();
	
	public abstract void viewCourses();
	public abstract void viewStudents(); // Extra method. Not in the requirements
	public abstract void viewFullCourses();
	public abstract void recordFullCourses();
	public abstract void viewCourseStudents();
	public abstract void viewStudentCourses();
	public abstract void sortCourses();

}
