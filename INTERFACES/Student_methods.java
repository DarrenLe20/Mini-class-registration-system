package INTERFACES;

import USERS.Student;

public interface Student_methods {
	// Methods to be implemented by Student class
	public abstract void viewCourses();
	public abstract void viewVacant();
	public abstract void register(Student a);
	public abstract void withdraw(Student a);
	public abstract void viewRegistered(Student a);
}
