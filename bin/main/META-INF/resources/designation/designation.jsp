<%@ include file="init.jsp" %>

<portlet:actionURL name="submit" var="submitURL" />

<head>
	<title>Designation Details</title>
</head>

	<h2>Designation Details Form</h2>

	<form action="${submitURL}" method="post">
		<label for="designationName">Designation Name:</label>
        <input type="text" id="name" name="<portlet:namespace />name" required><br><br>
        <button type="submit" value="Register">Click Here</button>
        
       <%--  <button><a href="<%= submitURL %>">Submit</a></button> --%>
    </form>