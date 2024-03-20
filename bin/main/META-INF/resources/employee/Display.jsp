<%@ include file="init.jsp"%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    
    <!-- Swal library -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<portlet:renderURL var="addEmployeeRenderURL">
	<portlet:param name="mvcPath" value="/employee/employee.jsp" />
</portlet:renderURL>

<portlet:actionURL name = "applyFilter" var="applySearchFilterURL">
	<portlet:param name="mvcPath" value="/employee/Display.jsp" />
</portlet:actionURL>



<h2>Employee List</h2>

<form action="${applySearchFilterURL}" method="post">
	<h3>Search by Keyword</h3>
	<div class="form-group">
		<label class="" for="0">KeyWord</label>
		<input id="0" placeholder="Enter Keyword" class="form-control" type="text"
			name="<portlet:namespace/>keyword" value="">
	</div>


	<h3>Fiter by dates</h3>
	<div class="form-group">
		<label for="fromdate">From Date:</label> 
		<input type="date" id="fromdate" name="<portlet:namespace/>fromDate">
		<label	for="todate">To Date:</label>
	 	<input type="date" id="todate" name="<portlet:namespace/>toDate"> 


		<div class="form-group">
			<button class="btn btn-primary" type="submit">Apply</button>
		</div>
</form>




<table class="table table-striped">
	<thead>
		<tr>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Email</th>
			<th>Mobile</th>
			<th>Department</th>
			<th>Branch</th>
			<th>Designation</th>
			<th>Address</th>
			<th>Delete</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="employee" items="${employeeList}">
		
		<!-- String firstName;
		String lastName;
		String email;
		String mobile;
		String department;
		String branch;
		String designation;
		String address;
		 -->
		<%-- <portlet:renderURL var="updateEmployeeRenderURL">
            <portlet:param name="mvcPath" value="/updateEmployee.jsp"/>
            <portlet:param name="firstName" value="${employee.firstName}"/>
            <portlet:param name="lastName" value="${employee.lastName}"/>
            <portlet:param name="email" value="${employee.email}"/>
            <portlet:param name="mobile" value="${employee.mobile}"/>
            <portlet:param name="department" value="${employee.department}"/>
            <portlet:param name="branch" value="${employee.branch}"/>
            <portlet:param name="designation" value="${employee.designation}"/>
            <portlet:param name="address" value="${employee.address}"/>
            <portlet:param name="employeeId" value="${employee.employeeId}"/>
        </portlet:renderURL> --%>
        
        	<portlet:renderURL var="updateEmployeeURL">
        				<portlet:param name="command" value="updateEmployee"/>
			            <portlet:param name="employeeId" value="${employee.getEmployeeId()}"/>
    			        <portlet:param name="mvcPath" value="/employee/employee.jsp"/>
			            
			</portlet:renderURL>
		
			<portlet:actionURL name="deleteEmployee" var="deleteEmployeeActionURL">
			            <portlet:param name="employeeId" value="${employee.getEmployeeId()}"/>
			</portlet:actionURL>
			
			<tr>
				<td>${employee.getFirstName()}</td>
				<td>${employee.getLastName()}</td>
				<td>${employee.getEmail()}</td>
				<td>${employee.getMobile()}</td>
				<td>${employee.getDepartment()}</td>
				<td>${employee.getBranch()}</td>
				<td>${employee.getDesignation()}</td>
				<td>${employee.getAddress()}</td>
				
				<td class="text-center" style="width:50px">
	                <a href="${updateEmployeeURL}"
	                    class="btn  btn-primary btn-default btn-sm px-2 py-1"
	                    class="btn btn-danger">Update</a>
            	</td>
                    
                <td class="text-center" style="width:50px">
	                <a href="${deleteEmployeeActionURL}"
	                    class="btn  btn-primary btn-default btn-sm px-2 py-1"
	                    onclick="return confirm('Are you sure you want to delete this item?');"class="btn btn-danger">Delete</a>
            	</td>
               
			</tr>
		</c:forEach>
	</tbody>
</table>

<a class="btn btn-primary" href="${addEmployeeRenderURL}">Add
	Employee</a>
	
	
<!-- <script>
function deleteEmployee(employeeId){
	let url = '${deleteURL }';
	url = url.replace('EMPLOYEE_ID', employeeId);
	Swal.fire({
		  title: "Are you sure?",
		  text: "You won't be able to revert this!",
		  icon: "warning",
		  showCancelButton: true,
		  confirmButtonColor: "#3085d6",
		  cancelButtonColor: "#d33",
		  confirmButtonText: "Yes, delete it!"
		}).then((result) => {
		  if (result.isConfirmed) {
		
		    setInterval(() =>{
		    	window.location.href = url;
		    },3000);
		    
		    Swal.fire({
			      title: "Deleted!",
			      text: "Your file has been deleted.",
			      icon: "success",
			      timer: 3000
			    });
		  }
		});
}
	
</script> -->
	