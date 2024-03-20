<%@ include file="init.jsp" %>

<portlet:actionURL name="createBranch" var="createBranchURL" />
<portlet:resourceURL var="getCountryStatesCityURL" id="getState" />

<head>
	<title>Branch Details</title>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js" integrity="sha512-v2CJ7UaYy4JwqLDIrZUI/4hqeoQieOmAZNXBeQyjo21dadnwR+8ZaIJVT8EE2iyI61OV8e6M8PP2/4hpQINQ/g==" crossorigin="anonymous" referrerpolicy="no-referrer">
	</script>
</head>

	<h2>Branch Details Form</h2>

	<form action="${createBranchURL}" method="post">
		<label for="name">Name:</label>
        <input type="text" id="name" name="<portlet:namespace />name" required><br><br>
	    
	    <label for="country">Country:</label>
		<select id="country" name="<portlet:namespace />country">
		    <c:forEach var="country" items="${countryMap}">
		        <option value="${country.key}">${country.value}</option>
		    </c:forEach>
		</select>
	    <br><br>
	    
	    <label for="state">State:</label>
	    <select id="state" name="<portlet:namespace />state">
	        <option value="">Select State</option> <!-- Default option -->
	    </select>
	    <br><br>
	
	    <label for="city">City:</label>
	    <select id="city" name="<portlet:namespace />city">
	        <option value="">Select City</option> <!-- Default option -->
	    </select>
	    <br><br>
        
        <button type="submit" value="Register">Click Here</button>
    </form>
    
<script>
    $(document).ready(function() {
    	
        $('#state').on('change', function() {
            var selectedValue = $(this).val();
            $.ajax({
                url: '${getCountryStatesCityURL}',
                type: 'POST',
                data: {
                    '<portlet:namespace/>state': $(this).val()
                },
                success: function(data, textStatus, jqXHR) {
                    if (data) {
                        const resObj = JSON.parse(data);
                        if (resObj.citystatus == 'success') {
                            const cityName = resObj.cityNameList;
                            const cityId = resObj.cityIdList;
                            var cityEle = $('#city').empty();
                            cityEle.append('<option value="0">Select City</option>');
                            for (var i = 0; i < cityName.length; i++) {
                                var itemName = cityName[i];
                                var itemId = cityId[i];
                                cityEle.append('<option value="' + itemId + '">' + itemName + '</option>');
                            }

                        }
                    }
                },
                error: function(jqXHR, status, error) {
                    console.log("error in ajax");
                }
            });
        });
    	
    	
    	
    	
        $('#country').on('change', function() {
            var selectedValue = $(this).val();
            $.ajax({
                url: '${getCountryStatesCityURL}',
                type: 'POST',
                data: {
                    '<portlet:namespace/>country': $(this).val()
                },
                success: function(data, textStatus, jqXHR) {
                    if (data) {
                        const resObj = JSON.parse(data);
                        console.log(resObj);
                        if (resObj.statestatus == 'success') {
                            const statesName = resObj.stateNameList;
                            const statesId = resObj.stateIdList;
                            console.log(statesName);
                            var cityEle = $('#city').empty();
                            cityEle.append('<option value="1">Select City</option>');
                            var stateEle = $('#state').empty();
                            stateEle.append('<option value="1">Select State</option>');
                            for (var i = 0; i < statesName.length; i++) {
                                var itemName = statesName[i];
                                var itemId = statesId[i];
                                stateEle.append('<option value="' + itemId + '">' + itemName + '</option>');
                            }

                        }
                    }
                },
                error: function(jqXHR, status, error) {
                    console.log("error in ajax");
                }
            });
        });
    });
        
</script>


    
    