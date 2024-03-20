package employee.management.system.liferay.portlet;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import EmployeeManagementSystem2.service.DepartmentLocalService;
import employee.management.system.liferay.constants.EmployeeDepartmentConstants;


@Component(
    immediate = true,
    property = {
        "com.liferay.portlet.display-category=category.sample",
        "com.liferay.portlet.header-portlet-css=/css/main.css",
        "com.liferay.portlet.instanceable=true",
        "javax.portlet.display-name=DepartmentPortlet",
        "javax.portlet.init-param.template-path=/",
        "javax.portlet.init-param.view-template=/department/department.jsp",
        "javax.portlet.name=" + EmployeeDepartmentConstants.EMPLOYEEMANAGEMENTDEPARTMENT,
        "javax.portlet.resource-bundle=content.Language",
        "javax.portlet.security-role-ref=power-user,user"
    },
    service = Portlet.class
)

public class Department extends MVCPortlet {
    
    @Reference
    DepartmentLocalService departmentLocalService;
    
    
    
    @Override
    public void render(RenderRequest renderRequest, RenderResponse renderResponse)
            throws IOException, PortletException {
    	
    	Map<Long, String> userNameList = new HashMap<>();

		List<User> users = UserLocalServiceUtil.getUsers(-1, -1);

		for (User user : users) {
			userNameList.put(user.getUserId(), user.getFullName());
		}
		renderRequest.setAttribute("Users", userNameList);

        super.render(renderRequest, renderResponse);
    }
    
    @ProcessAction(name = "submitDepartment")
    @Override
    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse)
            throws IOException, PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

        long departmentId = CounterLocalServiceUtil.increment(Designation.class.getName());
        String departmentName = ParamUtil.getString(actionRequest, "name");
        String headId = ParamUtil.getString(actionRequest, "departmentHead");
        
        EmployeeManagementSystem2.model.Department department = departmentLocalService.createDepartment(departmentId);
        
        department.setCompanyId(themeDisplay.getCompanyId());
		department.setUserName(themeDisplay.getUser().getFullName());
		department.setCreateDate(new Date());
		department.setCreateDate(new Date());
		department.setUserId(themeDisplay.getUserId());
        department.setName(departmentName);
        department.setDepartmentHead(headId);
        
        departmentLocalService.addDepartment(department);
    }
}
