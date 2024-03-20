package employee.management.system.liferay.portlet;


import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import EmployeeManagementSystem2.model.City;
import EmployeeManagementSystem2.model.State;
import EmployeeManagementSystem2.service.BranchLocalService;
import EmployeeManagementSystem2.service.CityLocalService;
import EmployeeManagementSystem2.service.StateLocalService;
import employee.management.system.liferay.constants.EmployeeBranchConstants;

/**
 * @author himashu.jha
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=BranchPortlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/branch/branch.jsp",
		"javax.portlet.name=" + EmployeeBranchConstants.EMPLOYEEMANAGEMENTBRANCH,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)

public class Branch extends MVCPortlet {
	
	@Reference
	BranchLocalService branchLocalService;
	@Reference
	CountryLocalService countryLocalService;
	
	@Reference
	StateLocalService stateLocalService;
	
	@Reference
	CityLocalService cityLocalService;
	
	private static final Log log = LogFactoryUtil.getLog(Branch.class);
	
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		
		try {
            List<Country> countries = countryLocalService.getCountries(-1, -1);

            Map<Long, String> countryMap = new HashMap<>();
            for (Country country : countries) {
                countryMap.put(country.getCountryId(), country.getName());
            }

            renderRequest.setAttribute("countryMap", countryMap);

            List<State> states = stateLocalService.getStates(-1, -1);
            
            Map<Long, String> stateMap = new HashMap<>();
            for(State state : states) {
            	stateMap.put(state.getStateId(), state.getStateName());
            }
            
            renderRequest.setAttribute("stateMap", stateMap);
            
            List<City> cities = cityLocalService.getCities(-1, -1);
            
            Map<Long, String> cityMap = new HashMap<>();
            for(City city : cities) {
            	stateMap.put(city.getCityId(), city.getCityName());
            }
            
            
            renderRequest.setAttribute("cityMap", cityMap);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.render(renderRequest, renderResponse);
    }
	
	@ProcessAction(name = "createBranch")
	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {
		
		System.out.println("Branch Action Called");

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		long branchId = CounterLocalServiceUtil.increment(Designation.class.getName());
		String branchName = ParamUtil.getString(actionRequest, "name");
		long stateId = ParamUtil.getLong(actionRequest, "state");
		long cityId = ParamUtil.getLong(actionRequest, "city");
		long countryId = ParamUtil.getLong(actionRequest, "country");
		
		EmployeeManagementSystem2.model.Branch branch = branchLocalService.createBranch(branchId);
		
		branch.setCompanyId(themeDisplay.getCompanyId());
		branch.setUserName(themeDisplay.getUser().getFullName());
		branch.setCreateDate(new Date());
		branch.setCreateDate(new Date());
		branch.setUserId(themeDisplay.getUserId());
		branch.setName(branchName);
		branch.setBranchId(branchId);
		branch.setStateId(stateId);
		branch.setCityId(cityId);
		branch.setCountryId(countryId);
		
		branchLocalService.addBranch(branch);
		
	}
	
	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException {
			long country = ParamUtil.getLong(resourceRequest, "country");
			long state = ParamUtil.getLong(resourceRequest, "state");

			JSONObject responseObj = JSONFactoryUtil.createJSONObject();

			PrintWriter writer = null;
			try {
				writer = resourceResponse.getWriter();
				List<City> cityList = new ArrayList<City>();
				List<State> stateList = new ArrayList<State>();

				if (state != 1 && state != 0) {
					cityList = cityLocalService.findBystateId(state);
				}
				else if(country != 1 && country != 0){
					stateList = stateLocalService.findBycountryId(country);
				}

//				City
				List<String> cityNameList = new ArrayList<String>();
				List<Long> cityIdList = new ArrayList<Long>();
				for(City element : cityList)
				{
					cityNameList.add(element.getCityName());
					cityIdList.add(element.getPrimaryKey());
				}
				
				responseObj.put("citystatus", "success");
				responseObj.put("cityIdList", cityIdList);
				responseObj.put("cityNameList",cityNameList);
				
//				State
				List<String> stateNameList = new ArrayList<String>();
				List<Long> stateIdList = new ArrayList<Long>();
				
				for(State element : stateList)
				{
					stateNameList.add(element.getStateName());
					stateIdList.add(element.getPrimaryKey());
				}
				responseObj.put("stateNameList",stateNameList);
				responseObj.put("stateIdList", stateIdList);
				responseObj.put("statestatus", "success");
	
				log.info("retrieved successfully");
			} catch (IOException e) {
				log.error("Error occured while fetching states from country -> " + e.getMessage());
				responseObj.put("status", "error");
				responseObj.put("error", "Error occured while fetching states from country.");
			} finally {
				writer.write(responseObj.toString());
				writer.close();
			}
			super.serveResource(resourceRequest, resourceResponse);
		}
	

	
}
