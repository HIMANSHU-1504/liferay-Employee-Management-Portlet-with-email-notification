package employee.management.system.liferay.portlet;

import employee.management.system.liferay.constants.EmployeeManagementSystemLiferayPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author himashu.jha
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=EmployeeManagementSystemLiferay",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/employee/employee.jsp",
		"javax.portlet.name=" + EmployeeManagementSystemLiferayPortletKeys.EMPLOYEEMANAGEMENTSYSTEMLIFERAY,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class EmployeeManagementSystemLiferayPortlet extends MVCPortlet {
}