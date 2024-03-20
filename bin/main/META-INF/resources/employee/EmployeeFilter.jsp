<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<portlet:defineObjects />

<portlet:actionURL name="employeeFilter" var="employeeFilterURL" />

<portlet:actionURL name="serachEmployee" var = "serachEmployeeURL" />

<head>
    <title>Employee Filter</title>
</head>

<body>
    <h2>Employee Filter Form</h2>

    <form action="${employeeFilterURL}" method="post">
        <label for="fromdate">From Date:</label>
        <input type="date" id="fromdate" name="<portlet:namespace/>fromDate">
        <label for="todate">To Date:</label>
        <input type="date" id="todate" name="<portlet:namespace/>toDate">
        <input type="submit" value="Filter">
    </form>

	</br></br>
	<form action="${serachEmployeeURL}" method="post">
		<label for="search">Search:</label>
        <input type="text" id="search" name="<portlet:namespace/>search">
		<input type="submit" value = "Search">
	</form>
    <table class="table">
  <thead>
    <tr>
      <th scope="col">First Name</th>
      <th scope="col">Last Name</th>
      <th scope="col">Email</th>
      <th scope="col">Mobile Number</th>
      <th scope="col">Department</th>
      <th scope="col">Branch</th>
      <th scope="col">Designation</th>
    </tr>
  </thead>
 <tbody>
 <c:forEach var="employee" items="${employeeList}">
    <tr>
      <td>${employee.getFirstName()}</td>
       <td>${employee.getLastName()}</td>
      <td>${employee.getEmail()}</td>
       <td>${employee.getMobileNumber()}</td>
      <td>${employee.getDepartmentId()}</td>
      <td>${employee.getBranchId()}</td>
      <td>${employee.getDesignationId()}</td>
    </tr>
    </c:forEach>
  </tbody>
</table></br>
</body>
