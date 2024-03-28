package employee.management.system.liferay.portlet;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import EmployeeManagementSystem2.model.Branch;
import EmployeeManagementSystem2.model.Department;
import EmployeeManagementSystem2.model.Designation;
import EmployeeManagementSystem2.model.Employee;
import EmployeeManagementSystem2.service.BranchLocalService;
import EmployeeManagementSystem2.service.DepartmentLocalService;
import EmployeeManagementSystem2.service.DesignationLocalService;
import EmployeeManagementSystem2.service.EmployeeLocalService;
import employee.management.system.liferay.constants.EmployeeEmployeeConstants;
import employee.management.system.liferay.model.EmployeeModel;;

/**
 * @author himashu.jha
 */
@Component(immediate = true, property = { "com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css", "com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=Employee Portlet",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/employee/Display.jsp",
		"javax.portlet.name=" + EmployeeEmployeeConstants.EMPLOYEEMANAGEMENTEMPLOYEE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user" }, service = Portlet.class)

public class EmployeePortlet extends MVCPortlet {
	
	private Log log = LogFactoryUtil.getLog(this.getClass().getName());

	@Reference
	EmployeeLocalService employeeLocalService;
	@Reference
	DepartmentLocalService departmentLocalService;
	@Reference
	DesignationLocalService designationLocalService;
	@Reference
	BranchLocalService branchLocalService;

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {

		
		String commandValue = ParamUtil.getString(renderRequest,"command","");
		
		
		if (commandValue.equals("updateEmployee")) {
			try {
			System.out.println("=========================");
			long employeeId = ParamUtil.getLong(renderRequest, "employeeId");

			Employee employee = employeeLocalService.getEmployee(employeeId);
			
			EmployeeModel employeeWebModel = new EmployeeModel(employee, branchLocalService,
					designationLocalService, departmentLocalService);
			renderRequest.setAttribute("employeePrefill", employeeWebModel);
			} catch (Exception e) { 
				System.out.println("not alllllllllllllllllowed");
				e.printStackTrace(); }
		}
		
		
		List<Branch> branchList = branchLocalService.getBranches(-1, -1);
		List<Department> departmentList = departmentLocalService.getDepartments(-1, -1);
		List<Designation> designationList = designationLocalService.getDesignations(-1, -1);
		renderRequest.setAttribute("branches", branchList);
		renderRequest.setAttribute("departments", departmentList);
		renderRequest.setAttribute("designations", designationList);

//		accessing dates
		String startDateString = ParamUtil.getString(renderRequest, "fromDate");
		String endDateString = ParamUtil.getString(renderRequest, "toDate");
		String keyWord = ParamUtil.getString(renderRequest, "keyword");

		if (!keyWord.equals(StringPool.BLANK)) {
			List<Object> resultObjList = employeeLocalService.getAllEmployeeByDetails(keyWord);

			List<EmployeeModel> filteredList = new ArrayList<EmployeeModel>();
			for (int i = 0; i < resultObjList.size(); i++) {
				EmployeeModel employeeWebModel = new EmployeeModel(resultObjList.get(i));
				filteredList.add(employeeWebModel);
			}
			renderRequest.setAttribute("employeeList", filteredList);

		}
//		validating dates are not empty
		else if (!(endDateString.equals(StringPool.BLANK)) && !(startDateString.equals(StringPool.BLANK))) {

//			typecasting dates to sql supported format
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
			Date startDate = null;
			Date endDate = null;
			try {

				startDate = dateFormat.parse(startDateString + " 00:00:00.000000");
				endDate = dateFormat.parse(endDateString + " 00:00:00.000000");
			} catch (Exception e) {
				System.out.println("exceptionnnnnnnnn");
				e.printStackTrace();
			}

//	        creating query
			DynamicQuery dynamicQuery = employeeLocalService.dynamicQuery();
			System.out.println(startDate + "   " + endDate);
			dynamicQuery.add(RestrictionsFactoryUtil.between("createDate", startDate, endDate));
			List<Employee> employeeList = employeeLocalService.dynamicQuery(dynamicQuery);

			List<EmployeeModel> filteredList = new ArrayList<EmployeeModel>();

			for (Employee employee : employeeList) {
				EmployeeModel employeeWebModel = new EmployeeModel(employee, branchLocalService,
						designationLocalService, departmentLocalService);
				filteredList.add(employeeWebModel);
			}

			renderRequest.setAttribute("employeeList", filteredList);

		} else {
			List<Employee> employeeList = employeeLocalService.getEmployees(-1, -1);
			List<EmployeeModel> filteredList = new ArrayList<EmployeeModel>();

			for (Employee employee : employeeList) {
				EmployeeModel employeeWebModel = new EmployeeModel(employee, branchLocalService,
						designationLocalService, departmentLocalService);
				filteredList.add(employeeWebModel);
			}
//			System.out.println(employeeList);
			renderRequest.setAttribute("employeeList", filteredList);
		}

		
		super.render(renderRequest, renderResponse);

	}

	public void applyFilter(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {
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

		List<EmployeeManagementSystem2.model.Employee> filteredEmployees = employeeLocalService
				.dynamicQuery(dynamicQuery);

//		System.out.println("employees = " + filteredEmployees);
//		System.out.println();
//		System.out.println("Dynamic Query: " + dynamicQuery.toString());

		actionRequest.setAttribute("employeeList", filteredEmployees);


	}
	public void addEmployee(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		String fname = ParamUtil.getString(actionRequest, "fname");
		String lname = ParamUtil.getString(actionRequest, "lname");
		String email = ParamUtil.getString(actionRequest, "email");
		long number = ParamUtil.getLong(actionRequest, "number");
		
		long departmentId = ParamUtil.getLong(actionRequest, "departmentId");
		long branchId = ParamUtil.getLong(actionRequest, "branchId");
		long designationId = ParamUtil.getLong(actionRequest, "designationId");
		String address = ParamUtil.getString(actionRequest, "address");
		long employeeId = CounterLocalServiceUtil.increment(Employee.class.getName());
		EmployeeManagementSystem2.model.Employee employee = employeeLocalService.createEmployee(employeeId);

		employee.setCompanyId(themeDisplay.getCompanyId());
		employee.setUserName(themeDisplay.getUser().getFullName());
		employee.setCreateDate(new Date());
		employee.setCreateDate(new Date());
		employee.setUserId(themeDisplay.getUserId());
		employee.setFirstName(fname);
		employee.setLastName(lname);
		employee.setEmail(email);
		employee.setMobileNumber(number);
		employee.setDepartmentId(departmentId);
		employee.setBranchId(branchId);
		employee.setDesignationId(designationId);
		employee.setAddress(address);
		
		employeeLocalService.addEmployee(employee);
		
		try {
	        sendEmailToAllUsers(new InternetAddress(email), employee);
	    } catch (Exception e) {
	    	System.out.println("Error sending email to all users++++++++++++");
	    }
	}

	
	private void sendEmailToAllUsers(InternetAddress emailAddress, Employee employee) {
	    try {
	        // Retrieve all users
	    	List<User> users = UserLocalServiceUtil.getUsers(-1, -1);

	        // Iterate through the list of users and send email
	        for (User user : users) {
	        	if(user != null && !user.getEmailAddress().endsWith("@liferay.com")){
		        	sendEmailToUser(user.getEmailAddress(), employee);
		        	System.out.println("EMail sent to "+user.getEmailAddress());
	            }
	            
	        }
	    } catch (Exception e) {
	    	System.out.println("Error retrieving users===============");
	    }
	}

	private void sendEmailToUser(String emailAddress, Employee employee) throws UnsupportedEncodingException, AddressException {
		System.out.println("sending mail");
		MailMessage mailMessage = new MailMessage();
		mailMessage.setHTMLFormat(true);
		mailMessage.setBody("Hello Everyone we have a new Joiner "+employee.getFirstName());
		
		mailMessage.setFrom(new InternetAddress("himanshuprincejha2001@gmail.com","Himanshu Jha"));
		
		mailMessage.setSubject("Announcement for new Joining");
		mailMessage.setTo(new InternetAddress(emailAddress));
		MailServiceUtil.sendEmail(mailMessage);	}

    public void deleteEmployee(ActionRequest actionRequest, ActionResponse actionResponse){
   		
		long employeeId = ParamUtil.getLong(actionRequest, "employeeId");
        try {
        	
        	employeeLocalService.deleteEmployee(employeeId);
            
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
    
    public void editEmployee(ActionRequest actionRequest, ActionResponse actionResponse) throws PortalException {
    	
    	long employeeId = ParamUtil.getLong(actionRequest, "employeeId");
    	
    	Employee employee = employeeLocalService.getEmployee(employeeId);
    	
    	String fname = ParamUtil.getString(actionRequest, "fname");
		String lname = ParamUtil.getString(actionRequest, "lname");
		String email = ParamUtil.getString(actionRequest, "email");
		long number = ParamUtil.getLong(actionRequest, "number");
		long departmentId = ParamUtil.getLong(actionRequest, "departmentId");
		long branchId = ParamUtil.getLong(actionRequest, "branchId");
		long designationId = ParamUtil.getLong(actionRequest, "designationId");
		String address = ParamUtil.getString(actionRequest, "address");
    	
    	employee.setFirstName(fname);
		employee.setLastName(lname);
		employee.setEmail(email);
		employee.setMobileNumber(number);
		employee.setDepartmentId(departmentId);
		employee.setBranchId(branchId);
		employee.setDesignationId(designationId);
		employee.setAddress(address);
    	
    	try {
    		
    		employeeLocalService.updateEmployee(employee);
    		
    	}catch (Exception e) {
    		e.printStackTrace();
		}
    }
    
//    XLSX
    
    public void exportXLSX(ActionRequest actionRequest, ActionResponse actionResponse)
            throws IOException, PortletException {

        System.out.println("Inside export Action Method===========");
        log.info("Inside export Action Method");

//        try {
            List<Employee> employeesList = employeeLocalService.getEmployees(0, employeeLocalService.getEmployeesCount());

            System.out.println("Inside try Block =========");
            // Create Excel workbook and sheet
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Employee Data");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] columns = { "First Name", "Last Name", "Email", "Mobile", "Department", "Branch", "Designation", "Address" };
            for (int i = 0; i < columns.length; i++) {
                System.out.println("Inside loop of cell making Block =========");

                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Fill data rows
            int rowNum = 1;
            for (Employee employee : employeesList) {
                System.out.println("Inside loop of row making Block =========");

            	
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(employee.getFirstName());
                row.createCell(1).setCellValue(employee.getLastName());
                row.createCell(2).setCellValue(employee.getEmail());
                row.createCell(3).setCellValue(employee.getMobileNumber());
                row.createCell(4).setCellValue(employee.getDepartmentId());
                row.createCell(5).setCellValue(employee.getBranchId());
                row.createCell(6).setCellValue(employee.getDesignationId());
                row.createCell(7).setCellValue(employee.getAddress());
            }
            
           
            try {
            	FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\EmployeeData.xlsx"));
            	System.out.println("Inside try Block or fileOut =========");

            	workbook.write(fileOutputStream);
            	fileOutputStream.close();
            }
            
            
        catch (Exception e) {
            System.out.println("Can't fetch Excel file");
            log.error("Exception while exporting Excel file : ", e);
        }
    }
    
    //PDF

    public void exportPDF(ActionRequest actionRequest, ActionResponse actionResponse) {
    	
    	System.out.println("Checking PDF Functionality");
    	
    	List<Employee> employeeList = employeeLocalService.getEmployees(0, employeeLocalService.getEmployeesCount());
    	
    	Document document = new Document();
    	
    	try {
    		System.out.println("Inside 1st Try Block ");
    		
    		PdfWriter.getInstance(document, new FileOutputStream("D:\\EmployeeDataPDF.pdf"));
    		document.open();
    		
    		PdfPTable table = new PdfPTable(8); // 8 columns for employee data
	          table.setWidthPercentage(100); // Width 100%
	          table.setSpacingBefore(10f); // Space before table
	          table.setSpacingAfter(10f); // Space after table
	
	          float[] columnWidths = {1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};
	          table.setWidths(columnWidths);
	
	          String[] columns = { "First Name", "Last Name", "Email", "Mobile", "Department", "Branch", "Designation", "Address" };
	          for (String column : columns) {
	        	  
	        	  System.out.println("Creating Columns");
	        	  
	              PdfPCell cell = new PdfPCell(new Paragraph(column));
	              table.addCell(cell);
	          }
	
	          for (Employee employee : employeeList) {
	        	  
	        	  System.out.println("Fill row Data");
	        	  
	              table.addCell(new PdfPCell(new Paragraph(employee.getFirstName())));
	              table.addCell(new PdfPCell(new Paragraph(employee.getLastName())));
	              table.addCell(new PdfPCell(new Paragraph(employee.getEmail())));
	              table.addCell(new PdfPCell(new Paragraph(employee.getMobileNumber())));
	              table.addCell(new PdfPCell(new Paragraph(String.valueOf(employee.getDepartmentId()))));
	              table.addCell(new PdfPCell(new Paragraph(String.valueOf(employee.getBranchId()))));
	              table.addCell(new PdfPCell(new Paragraph(String.valueOf(employee.getDesignationId()))));
	              table.addCell(new PdfPCell(new Paragraph(employee.getAddress())));
	          }
	
	          document.add(table);
	
	          document.close();
    		
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		System.out.println("Cant fetch the PDF");
		}
    }

}
