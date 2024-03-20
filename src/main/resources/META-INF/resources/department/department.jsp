<%@ include file="init.jsp" %>

<portlet:actionURL name="submitDepartment" var="submitDepartmentURL" />
<head>
	<title>Department Details</title>
</head>

	<h2>Department Details Form</h2>

	<form action="${submitDepartmentURL}" method="post">
		<label for="name">Name:</label>
        <input type="text" id="name" name="<portlet:namespace />name" required><br><br>

		<label for="departmentHead">Select Department Head:</label>
	    <select id="departmentHead" name="<portlet:namespace />departmentHead">
	    	<c:forEach var="user" items="${Users}">
            <option value="${user.key}">${user.value}</option>
        </c:forEach>
	    </select>
	    <br><br>
        
        <button type="submit" value="Register">Click Here</button>
        
       <%--  <button><a href="<%= submitURL %>">Submit</a></button> --%>
    </form>