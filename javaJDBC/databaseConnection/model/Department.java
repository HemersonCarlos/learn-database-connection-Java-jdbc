package model;

import java.util.List;

public class Department {

	private int departamentId;
	private String departmentName;
	private List<Person> departmentEmployee;
	
	public Department() { }
	
	public Department(int informedDepartmentId, String informedName, List<Person> informedListEmployee) {		
		this.departamentId = informedDepartmentId;
		this.departmentName = informedName;
		this.departmentEmployee = informedListEmployee;
	}

	public int getDepartmentId() {
		return this.departamentId;
	}

	public void setDepartmentId(int informedDepartmentId) {
		this.departamentId = informedDepartmentId;
	}

	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String informedDepartmentName) {
		this.departmentName = informedDepartmentName;
	}

	public List<Person> getListDepartmentEmployee() {
		return this.departmentEmployee;
	}

	public void setListDepartmentEmployee(List<Person> informedDepartmentEmployee) {
		this.departmentEmployee = informedDepartmentEmployee;
	}

	public String toString() {
		return "Department [id = " + this.departamentId + ", name = " + this.departmentName + "]";
	}
	
}
