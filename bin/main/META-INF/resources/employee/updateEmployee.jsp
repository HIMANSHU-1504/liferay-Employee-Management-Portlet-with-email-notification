<%@include file = "init.jsp" %>

<portlet:defineObjects/>
<portlet:actionURL name="updateEmployee" var="updateEmployeeActionURL"/>



<form action="${updateEmployeeActionURL}" method="post">

		<lable for="employeeId">Employee Id:</lable>
		
		<label for="firstName">First Name:</label>
        <input type="text" id="fname" name="<portlet:namespace />fname" required><br><br>

		<label for="lastName">Last Name:</label>
        <input type="text" id="lname" name="<portlet:namespace />lname" required><br><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="<portlet:namespace />email" required><br><br>
        
        <label for="mobileNumber">Mobile Number:</label>
        <input type="number" id="number" name="<portlet:namespace />number" required><br><br>
        
          <label for="departmentId">Select Department:</label>
    		<select id="departmentId" name="<portlet:namespace />departmentId">
        		<c:forEach var="department" items="${departments}">
            		<option value="${department.getPrimaryKey()}">${department.getName()}</option>
        		</c:forEach>
    		</select><br><br>

        
        <label for="branchId">Select Branch:</label>
    		<select id="branchId" name="<portlet:namespace />branchId">
        		<c:forEach var="branch" items="${branches}">
            		<option value="${branch.getPrimaryKey()}">${branch.getName()}</option>
        		</c:forEach>
    		</select><br><br>
        
        <label for="designationId">Designation Id:</label>
        <select id="designationId" name="<portlet:namespace />designationId">
        		<c:forEach var="designation" items="${designations}">
            		<option value="${designation.getPrimaryKey()}">${designation.getName()}</option>
        		</c:forEach>
    		</select><br><br>
       
        <label for="address">Address:</label>
        <input type="text" id="address" name="<portlet:namespace />address" required><br><br>
        
        <button type="submit" value="Register">Click Here</button>
        
    </form>