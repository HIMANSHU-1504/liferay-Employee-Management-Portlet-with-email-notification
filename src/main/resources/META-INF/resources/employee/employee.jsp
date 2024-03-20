<%@ include file="init.jsp" %>


<portlet:actionURL  name = "addEmployee" var="submitURL" />
<portlet:actionURL  name = "editEmployee" var="editEmployeeURL" />


<head>
	<title>Employee Details</title>
</head>

	<h2>Employee Details Form</h2>

	<form action="${empty employeePrefill ? submitURL : editEmployeeURL}" method="post">
		<input value= "${employeePrefill.employeeId}" type="hidden" name = "<portlet:namespace />employeeId">
	
		<label for="firstName">First Name:</label>
        <input type="text" id="fname" value ='${employeePrefill.firstName}' name="<portlet:namespace />fname" required><br><br>

		<label for="lastName">Last Name:</label>
        <input type="text" id="lname" value ='${employeePrefill.lastName}' name="<portlet:namespace />lname" required><br><br>

        <label for="email">Email:</label>
        <input type="email" id="email" value ='${employeePrefill.email}' name="<portlet:namespace />email" required><br><br>
        
        <label for="mobileNumber">Mobile Number:</label>
        <input type="number" id="number" value ='${employeePrefill.mobile}' name="<portlet:namespace />number" required><br><br>
        
          <label for="departmentId">Select Department:</label>
    		<select id="departmentId" name="<portlet:namespace />departmentId">
        		<c:forEach var="department" items="${departments}">
            		<option value="${department.getPrimaryKey()}"
            		${department.getPrimaryKey() eq employeePrefill.departmetId ? 'selected' : ''}>
            		${department.getName()}</option>
        		</c:forEach>
    		</select><br><br>

        
        <label for="branchId">Select Branch:</label>
    		<select id="branchId" name="<portlet:namespace />branchId">
        		<c:forEach var="branch" items="${branches}">
            		<option value="${branch.getPrimaryKey()}"
            		${branch.getPrimaryKey() eq employeePrefill.branchId ? 'selected' : ''}>
            		${branch.getName()}</option>
        		</c:forEach>
    		</select><br><br>
        
        <label for="designationId">Designation Id:</label>
        <select id="designationId" name="<portlet:namespace />designationId">
        		<c:forEach var="designation" items="${designations}">
            		<option value="${designation.getPrimaryKey()}"
            		${designation.getPrimaryKey() eq employeePrefill.designationId ? 'selected' : ''}>
            		${designation.getName()}</option>
        		</c:forEach>
    		</select><br><br>
       
        <label for="address">Address:</label>
        <input type="text" id="address" value ='${employeePrefill.address}' name="<portlet:namespace />address" required><br><br>
        
        <button type="submit" value="Register">Click Here</button>
        
    </form>