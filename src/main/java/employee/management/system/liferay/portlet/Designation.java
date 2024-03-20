package employee.management.system.liferay.portlet;


import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import EmployeeManagementSystem2.service.DesignationLocalService;
import employee.management.system.liferay.constants.EmployeeDesignationConstants;

/**
 * @author himashu.jha
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=DesignationPortlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/designation/designation.jsp",
		"javax.portlet.name=" + EmployeeDesignationConstants.EMPLOYEEMANAGEMENTDESIGNATION,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)

public class Designation extends MVCPortlet {
	
	@Reference
	DesignationLocalService designationLocalService;
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		super.render(renderRequest, renderResponse);
	}
	
	@ProcessAction(name = "submit")
	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {

			ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			String designationName = ParamUtil.getString(actionRequest, "name");
			long designationId = CounterLocalServiceUtil.increment(Designation.class.getName());
			EmployeeManagementSystem2.model.Designation designation = designationLocalService.createDesignation(designationId);
			
			designation.setCompanyId(themeDisplay.getCompanyId());
			designation.setUserName(themeDisplay.getUser().getFullName());
			designation.setCreateDate(new Date());
			designation.setCreateDate(new Date());
			designation.setUserId(themeDisplay.getUserId());
			designation.setName(designationName);
			designation.setDesignationId(designationId);
			
			
			designationLocalService.addDesignation(designation);
		}
	}