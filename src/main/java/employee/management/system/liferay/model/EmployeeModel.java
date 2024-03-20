package employee.management.system.liferay.model;




import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

import EmployeeManagementSystem2.model.Department;
import EmployeeManagementSystem2.model.Employee;
import EmployeeManagementSystem2.service.BranchLocalService;
import EmployeeManagementSystem2.service.DepartmentLocalService;
import EmployeeManagementSystem2.service.DesignationLocalService;
	public class EmployeeModel {		
		
		String firstName;
		String lastName;
		String email;
		String mobile;
		String department;
		String branch;
		String designation;
		String address;
		
		
		long employeeId;
		long departmetId;
		long designationId;
		long branchId;

		@Override
		public String toString() {
			return "EmployeeModel [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", mobile="
					+ mobile + ", department=" + department + ", branch=" + branch + ", designation=" + designation
					+ ", address=" + address + "]";
		}


		public EmployeeModel() {
			super();
		}




		public EmployeeModel(String firstName, String lastName, String email, String mobile, String department,
				String branch, String designation, String address, long employeeId, long departmetId,
				long designationId, long branchId) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
			this.mobile = mobile;
			this.department = department;
			this.branch = branch;
			this.designation = designation;
			this.address = address;
			this.employeeId = employeeId;
			this.departmetId = departmetId;
			this.designationId = designationId;
			this.branchId = branchId;
		}


		public String getFirstName() {
			return firstName;
		}


		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}


		public String getLastName() {
			return lastName;
		}


		public void setLastName(String lastName) {
			this.lastName = lastName;
		}


		public String getEmail() {
			return email;
		}


		public void setEmail(String email) {
			this.email = email;
		}


		public String getMobile() {
			return mobile;
		}


		public void setMobile(String mobile) {
			this.mobile = mobile;
		}


		public String getDepartment() {
			return department;
		}


		public void setDepartment(String department) {
			this.department = department;
		}


		public String getBranch() {
			return branch;
		}


		public void setBranch(String branch) {
			this.branch = branch;
		}


		public String getDesignation() {
			return designation;
		}


		public long getDepartmetId() {
			return departmetId;
		}


		public void setDepartmetId(long departmetId) {
			this.departmetId = departmetId;
		}


		public long getDesignationId() {
			return designationId;
		}


		public void setDesignationId(long designationId) {
			this.designationId = designationId;
		}


		public long getBranchId() {
			return branchId;
		}


		public void setBranchId(long branchId) {
			this.branchId = branchId;
		}


		public void setDesignation(String designation) {
			this.designation = designation;
		}


		public String getAddress() {
			return address;
		}


		public void setAddress(String address) {
			this.address = address;
		}
		
		
//		Adding Employee Id
		public long getEmployeeId() {
			return employeeId;
		}
		
		public void setEmployeeId(long employeeId) {
			this.employeeId = employeeId;
		}
	
		
		
		public 
		EmployeeModel(Employee employee,BranchLocalService bls,DesignationLocalService dels,DepartmentLocalService dls){
			firstName = employee.getFirstName();
			lastName = employee.getLastName();
			email = employee.getEmail();
			
			employeeId = (int) employee.getEmployeeId();
			
			departmetId = employee.getDepartmentId();
			designationId = employee.getDesignationId();
			branchId = employee.getBranchId();
			
			mobile = StringPool.BLANK + employee.getMobileNumber();
			try {
				department =  dls.getDepartment(employee.getDepartmentId()).getName();
				branch =  bls.getBranch(employee.getBranchId()).getName();
				designation =  dels.getDesignation(employee.getDesignationId()).getName();
			} catch (PortalException e) {
				e.printStackTrace();
			}

			address = StringPool.BLANK + employee.getAddress();
			
		}

		public EmployeeModel(Object object){

			Object[] current = (Object[])object;
//			int i =0;
//			for(Object obj : current) {
//				System.out.println(obj+"  "+i++);
//				
//			}
			
			firstName = StringPool.BLANK + current[8];
			lastName = StringPool.BLANK + current[9];
			email = StringPool.BLANK + current[10];
			mobile = StringPool.BLANK + current[11];
			department = StringPool.BLANK + current[17];
			branch = StringPool.BLANK + current[16];
			designation = StringPool.BLANK + current[18];
			address = StringPool.BLANK + current[15];
			
			employeeId = Long.parseLong(StringPool.BLANK + current[1]);
			
			departmetId = Long.parseLong(StringPool.BLANK + current[12]);
			branchId = Long.parseLong(StringPool.BLANK + current[13]);
			designationId = Long.parseLong(StringPool.BLANK + current[14]);
			
		}
	}


