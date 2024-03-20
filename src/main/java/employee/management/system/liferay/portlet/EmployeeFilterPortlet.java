package employee.management.system.liferay.portlet;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import EmployeeManagementSystem2.service.EmployeeLocalService;
import employee.management.system.liferay.constants.EmployeeEmployeeConstants;

/**
 * @author himashu.jha
 */
@Component(immediate = true, property = { "com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css", "com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=EmployeeFilterPortlet", "javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/employee/EmployeeFilter.jsp",
		"javax.portlet.name=" + EmployeeEmployeeConstants.EMPLOYEEFILTER,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, service = Portlet.class)

public class EmployeeFilterPortlet extends MVCPortlet {

	@Reference
	EmployeeLocalService employeeLocalService;
	
	

	@ProcessAction(name = "employeeFilter")
	public void employeeFilter(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {

		System.out.println("process action callled");
		
		
		String startDateString = ParamUtil.getString(actionRequest, "fromDate") + " 00:00:00.000000";
		String endDateString = ParamUtil.getString(actionRequest, "toDate") + " 00:00:00.000000";
		System.out.println("Demo Date " + startDateString);
		System.out.println(endDateString);
															
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
		Date startDate = null;
		Date endDate = null;

		try {
			startDate = dateFormat.parse(startDateString);
			endDate = dateFormat.parse(endDateString);
			
			System.out.println("Demo Date " + startDate);
			System.out.println(endDate);
			
		} catch (Exception e) {
			e.printStackTrace();				
			System.out.println("--------------------------");
		}
		

		DynamicQuery dynamicQuery = employeeLocalService.dynamicQuery();

		
		dynamicQuery.add(RestrictionsFactoryUtil.between("createDate", startDate, endDate));
		
		

		List<EmployeeManagementSystem2.model.Employee> filteredEmployees = employeeLocalService.dynamicQuery(dynamicQuery);
		System.out.println("-------" + filteredEmployees);
		System.out.println("employees = " + filteredEmployees);

		System.out.println("Dynamic Query: " + dynamicQuery.toString());

		actionRequest.setAttribute("employeeList", filteredEmployees);
		
		
		
	}
	
						   
	@ProcessAction(name = "serachEmployee")
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {
		// TODO Auto-generated method stub
		
//		String searchQuery = ParamUtil.getString(actionRequest, "search");
//
//		 List<EmployeeManagementSystem2.model.Employee> employeeList = employeeLocalService.getAllEmployeeByDetails(searchQuery);
//	    
//	    actionRequest.setAttribute("employeeList", employeeList);
		System.out.println("dfgfggfegfgfdfgfdf");
	}

}
