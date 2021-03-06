package com.rab3tech.customer.ui.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.rab3tech.customer.service.CustomerService;
import com.rab3tech.customer.service.CustomerTransactionService;
import com.rab3tech.customer.service.LocationService;
import com.rab3tech.customer.service.LoginService;
import com.rab3tech.customer.service.impl.CustomerEnquiryService;
import com.rab3tech.customer.service.impl.SecurityQuestionService;
import com.rab3tech.email.service.EmailService;
import com.rab3tech.vo.ChangePasswordVO;
import com.rab3tech.vo.CustomerAccountInfoVO;
import com.rab3tech.vo.CustomerSavingVO;
import com.rab3tech.vo.CustomerSecurityQueAnsVO;
import com.rab3tech.vo.CustomerTransactionVO;
import com.rab3tech.vo.CustomerVO;
import com.rab3tech.vo.EmailVO;
import com.rab3tech.vo.FundTransferVO;
import com.rab3tech.vo.LoginVO;
import com.rab3tech.vo.PayeeInfoVO;

/**
 * 
 * @author nagendra
 * This class for customer GUI
 *
 */
@Controller
//@RequestMapping("/customer")
public class CustomerUIController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerUIController.class);

	@Autowired
	private CustomerEnquiryService customerEnquiryService;

	
	@Autowired
	private SecurityQuestionService securityQuestionService;
	
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
   private LoginService loginService;	
	
	@Autowired
   private LocationService locationService;
	
	
	@Autowired
   private CustomerTransactionService customerTransactionService;
	
	@PostMapping("/customer/profile/update")
	public String updateCustomer(int cid, String name, String jobTitle) throws IOException {
		
		customerService.updateCustomerProfile(cid, name, jobTitle);
		return "redirect:/customer/profile";
	}
	
	@GetMapping("/customer/edit")
	public String showEditAccount(Model model, HttpSession session) {
		LoginVO loginVO2 =  (LoginVO) session.getAttribute("userSessionVO");
		
		String currentLoggedInUserName = loginVO2.getUsername();
		CustomerVO customerVO = customerService.findCustomerByUsername(currentLoggedInUserName);
		model.addAttribute("customerVO", customerVO);
		
		return "/customer/eProfile";
	}
	
	@PostMapping("/customer/profile/photo")
	public String changeCustomerPhoto(@RequestParam int cid,@RequestParam("pppppphoto") MultipartFile photo) throws IOException {
		byte[] bphoto=photo.getBytes();
		customerService.updatePhoto(cid, bphoto);
		return "redirect:/customer/profile";// I will refresh your page
	}

	@GetMapping("/customer/profile/photo")
	public void findCustomerPhoto(@RequestParam int cid,HttpServletResponse response) throws IOException { 
	   byte[] photo=customerService.findPhotoByid(cid);
	   response.setContentType("image/png");
	   ServletOutputStream outputStream=response.getOutputStream();
	   if(photo!=null) {
		   outputStream.write(photo);
	   }else {
		   outputStream.write(new byte[] {});
	   }
	   outputStream.flush();
	   outputStream.close();
	}

	
	@GetMapping("/customer/profile")
	public String showProfile(Model model,HttpSession session){
		LoginVO  loginVO2=(LoginVO)session.getAttribute("userSessionVO");
		//userid,username,loginid,emailid
		String currentLoggedInUserName=loginVO2.getUsername();
		CustomerVO customerVO=customerService.findCustomerByUsername(currentLoggedInUserName);
		model.addAttribute("customerVO", customerVO);
		return "/customer/profile";//profile.html
	}
	
	@GetMapping("/customer/sendAccountStmt")
	public String customerSendAccountStmt(Model model,HttpSession session) {
		//Here we have to write logic to fetch data
		//This is coming from session
				
				//This is coming from session
			LoginVO  loginVO2=(LoginVO)session.getAttribute("userSessionVO");
			String currentLoggedInUserName=loginVO2.getUsername();
			
            //This I need for email
			EmailVO mail = new EmailVO(currentLoggedInUserName, "javahunk2020@gmail.com",
					"Not setting", "", currentLoggedInUserName);
			mail.setUsername(currentLoggedInUserName);
			//mail.setName(loginVO2.getName());
			emailService.sendAccountStatement(mail);
			
			//This I need for showing all the transaction details of customer again from,
			//database!
			List<CustomerTransactionVO>  customerTransactionVOs=customerTransactionService.findCustomerTransaction(currentLoggedInUserName);
			model.addAttribute("customerTransactionVOs", customerTransactionVOs);
			//customer/accountSummary - view name
			return "customer/customerTransaction"; // thyme leaf
		
	}
	
	//action = /customer/accountSummary
	@GetMapping("/customer/accountSummary")
	public String customerAccountSummary(Model model,HttpSession session) {
		//Here we have to write logic to fetch data
		//This is coming from session
		LoginVO  loginVO2=(LoginVO)session.getAttribute("userSessionVO");
		String currentLoggedInUserName=loginVO2.getUsername();
		CustomerAccountInfoVO customerAccountInfoVO=customerService.findCustomerAccountInfo(currentLoggedInUserName);
		model.addAttribute("customerAccount", customerAccountInfoVO);
		//customer/accountSummary - view name
		return "customer/accountSummary"; // thyme leaf
		
	}
	
	@GetMapping("/customer/customerTransaction")
	public String showCustomerTransaction(Model model,HttpSession session) {
		//Here we have to write logic to fetch data
		//This is coming from session
		LoginVO  loginVO2=(LoginVO)session.getAttribute("userSessionVO");
		String currentLoggedInUserName=loginVO2.getUsername();
		List<CustomerTransactionVO>  customerTransactionVOs=customerTransactionService.findCustomerTransaction(currentLoggedInUserName);
		model.addAttribute("customerTransactionVOs", customerTransactionVOs);
		//customer/accountSummary - view name
		return "customer/customerTransaction"; // thyme leaf
		
	}
	

	
	@PostMapping("/customer/changePassword")
	public String saveCustomerQuestions(@ModelAttribute ChangePasswordVO changePasswordVO, Model model,HttpSession session) {
		LoginVO  loginVO2=(LoginVO)session.getAttribute("userSessionVO");
		String loginid=loginVO2.getUsername();
		changePasswordVO.setLoginid(loginid);
		String viewName ="customer/dashboard";
		boolean status=loginService.checkPasswordValid(loginid,changePasswordVO.getCurrentPassword());
		if(status) {
			if(changePasswordVO.getNewPassword().equals(changePasswordVO.getConfirmPassword())) {
				 viewName ="customer/dashboard";
				 loginService.changePassword(changePasswordVO);
			}else {
				model.addAttribute("error","Sorry , your new password and confirm passwords are not same!");
				return "customer/login";	//login.html	
			}
		}else {
			model.addAttribute("error","Sorry , your username and password are not valid!");
			return "customer/login";	//login.html	
		}
		return viewName;
	}
	
	@PostMapping("/customer/securityQuestion")
	public String saveCustomerQuestions(@ModelAttribute("customerSecurityQueAnsVO") CustomerSecurityQueAnsVO customerSecurityQueAnsVO, Model model,HttpSession session) {
		LoginVO  loginVO2=(LoginVO)session.getAttribute("userSessionVO");
		String loginid=loginVO2.getUsername();
		customerSecurityQueAnsVO.setLoginid(loginid);
		securityQuestionService.save(customerSecurityQueAnsVO);
		//
		return "customer/chagePassword";
	}

	// http://localhost:444/customer/account/registration?cuid=1585a34b5277-dab2-475a-b7b4-042e032e8121603186515
	@GetMapping("/customer/account/registration")
	public String showCustomerRegistrationPage(@RequestParam String cuid, Model model) {

		logger.debug("cuid = " + cuid);
		Optional<CustomerSavingVO> optional = customerEnquiryService.findCustomerEnquiryByUuid(cuid);
		CustomerVO customerVO = new CustomerVO();

		if (!optional.isPresent()) {
			return "customer/error";
		} else {
			// model is used to carry data from controller to the view =- JSP/
			CustomerSavingVO customerSavingVO = optional.get();
			customerVO.setEmail(customerSavingVO.getEmail());
			customerVO.setName(customerSavingVO.getName());
			customerVO.setMobile(customerSavingVO.getMobile());
			customerVO.setAddress(customerSavingVO.getLocation());
			customerVO.setToken(cuid);
			logger.debug(customerSavingVO.toString());
			// model - is hash map which is used to carry data from controller to thyme
			// leaf!!!!!
			// model is similar to request scope in jsp and servlet
			model.addAttribute("customerVO", customerVO);
			return "customer/customerRegistration"; // thyme leaf
		}
	}

	@PostMapping("/customer/account/registration")
	public String createCustomer(@ModelAttribute CustomerVO customerVO, Model model) {
		// saving customer into database
		logger.debug(customerVO.toString());
		customerVO = customerService.createAccount(customerVO);
		// Write code to send email

		EmailVO mail = new EmailVO(customerVO.getEmail(), "javahunk2020@gmail.com",
				"Regarding Customer " + customerVO.getName() + "  userid and password", "", customerVO.getName());
		mail.setUsername(customerVO.getUserid());
		mail.setPassword(customerVO.getPassword());
		emailService.sendUsernamePasswordEmail(mail);
		System.out.println(customerVO);
		model.addAttribute("loginVO", new LoginVO());
		model.addAttribute("message", "Your account has been setup successfully , please check your email.");
		return "customer/login";
	}
	
	@GetMapping("/customers/acphoto")
	public void findCustomerPhotoByAc(@RequestParam String accNumber,HttpServletResponse response) throws IOException {
	   byte[] photo=customerService.findPhotoByAC(accNumber);
	   response.setContentType("image/png");
	   ServletOutputStream outputStream=response.getOutputStream();
	   if(photo!=null) {
		   outputStream.write(photo);
	   }else {
		   outputStream.write(new byte[] {});
	   }
	   outputStream.flush();
	   outputStream.close();
	}

  /*
	@GetMapping(value = { "/customer/account/enquiry", "/", "/mocha", "/welcome" })
	public String showCustomerEnquiryPage(Model model) {
		CustomerSavingVO customerSavingVO = new CustomerSavingVO();
		// model is map which is used to carry object from controller to view
		model.addAttribute("customerSavingVO", customerSavingVO);
		return "customer/customerEnquiry"; // customerEnquiry.html
	}
*/
    
    @GetMapping(value = { "/customer/account/enquiry", "/", "/mocha", "/welcome" })
	public String showCustomerEnquiryPage(Model model) {
		//LoadLocationAndAccountVO loadLocationAndAccountVOs = new LoadLocationAndAccountVO();
		CustomerSavingVO customerSavingVO = new CustomerSavingVO();
		
		model.addAttribute("customerSavingVO", customerSavingVO);
		return "customer/customerEnquiry"; // customerEnquiry.html
	}
	
	@PostMapping("/customer/account/enquiry")
	public String submitEnquiryData(@Valid @ModelAttribute CustomerSavingVO customerSavingVO,
			BindingResult result, Model model) {
		
		if (result.hasErrors()) {
	        return "customer/customerEnquiry";
	    }
		
		boolean status = customerEnquiryService.emailNotExist(customerSavingVO.getEmail());
		logger.info("Executing submitEnquiryData");
		if (status) {
			CustomerSavingVO response = customerEnquiryService.save(customerSavingVO);
			logger.debug("Hey Customer , your enquiry form has been submitted successfully!!! and appref "
					+ response.getAppref());
			model.addAttribute("message",
					"Hey Customer , your enquiry form has been submitted successfully!!! and appref "
							+ response.getAppref());
		} else {
			model.addAttribute("message", "Sorry , this email is already in use " + customerSavingVO.getEmail());
		}
		return "customer/success"; // customerEnquiry.html

	}

	@GetMapping("/customer/app/status")
	public String customerAppStatus() {
		
		return "customer/appstatus";
	}
	
	@GetMapping("/customer/customerSearch")
	public String customerSearch(Model model) {
		List<CustomerVO> customerVOs=customerService.findCustomers();
		model.addAttribute("customerVOs", customerVOs);
		return "customer/customerSearch";
	}
	
	@GetMapping("/customer/addPayee")
	public String customerAddPayee(Model model) {
		PayeeInfoVO payeeInfoVO=new PayeeInfoVO();
		model.addAttribute("payeeInfoVO",payeeInfoVO);
		return "customer/addPayee";
	}
	
	@PostMapping("/customer/account/addPayee")
	public String newPayee(@Valid @ModelAttribute("payeeInfoVO") PayeeInfoVO payeeInfoVO,BindingResult bindingResult,HttpSession session, Model model) {
		
		if(payeeInfoVO.getAccNumberConfirm()!=null && !payeeInfoVO.getAccNumberConfirm().equalsIgnoreCase(payeeInfoVO.getPayeeAccountNo())){
			//ObjectError objectError=new ObjectError("accNumberConfirm", "Hey!, your account and confirm account are not same");
			bindingResult.rejectValue("accNumberConfirm", "account.msg", "An account already exists for this email.");
			//bindingResult.addError(objectError);
	    }
		
	    if (bindingResult.hasErrors()) {
	    	return "customer/addPayee";	
		}
	    
		LoginVO loginVO=(LoginVO)session.getAttribute("userSessionVO");
		payeeInfoVO.setCustomerId(loginVO.getUsername());
		System.out.println("MY CUSTOMER USERID ========================================="+payeeInfoVO.getCustomerId());
		//String loginId = loginService.findUserByName(payeeInfoVO.getPayeeName());
		//payeeInfoVO.setCustomerId(loginId);
		customerService.addPayee(payeeInfoVO);
		model.addAttribute("successMessage", "Payee added successfully");
		return "redirect:/customer/dashboard";
	}
	
	

	@GetMapping("/customer/pendingPayee")
	public String pendinPayeeList(Model model) {
		List<PayeeInfoVO> payeeInfoList = customerService.pendingPayeeList();
		model.addAttribute("payeeInfoList", payeeInfoList);
		return "customer/pendingPayee";
		
	}
	
	@GetMapping("/customer/registeredPayee")
	public String registeredPayeeList(Model model) {
		List<PayeeInfoVO> payeeInfoList = customerService.registeredPayeeList("technohunk100@gmail.com");
		model.addAttribute("payeeInfoList", payeeInfoList);
		return "customer/registeredPayee";
		
	}
	
	@GetMapping("/customer/fundTransfer")
	public String fundTransfer(Model model) {
		FundTransferVO fundTransferVO=new FundTransferVO();
		model.addAttribute("fundTransferVO",fundTransferVO);
		return "customer/fundTransfer";
	}
	
	
	@PostMapping("/customer/fundTransfer")
	public String fundTransferPost(@ModelAttribute("fundTransferVO") FundTransferVO fundTransferVO, Model model) {
		return "customer/fundTransferReview";
	}

	
	@PostMapping("/customer/fundTransferSubmit")
	public String fundTransferSubmit(@ModelAttribute("fundTransferVO") FundTransferVO fundTransferVO, Model model) {
		//validate OTP 
		//deduct money from sender and credit to account
		//Make a transaction history
		//make a su9mmary etc
		return "customer/fundSummary";
	}
	
	
	
	

	private  byte[] generatedCreditCard(String cardNumber,String exp,String name) {
		byte[] photo = new byte[]{};
		
		Resource resource = new ClassPathResource("images/credit-card-front-template.jpg");
		
		try {
			InputStream resourceInputStream = resource.getInputStream();
			
			Image src = ImageIO.read(resourceInputStream);

			int wideth = src.getWidth(null);
			int height = src.getHeight(null);

			BufferedImage tag = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = tag.createGraphics();

			g.setBackground(new Color(200, 250, 200));
			g.clearRect(0, 0, wideth, height);
			g.setColor(Color.WHITE);
			g.drawImage(src, 0, 0, wideth, height, null);
			
			// credit card number
			g.setFont(new Font("Lucida Console", Font.BOLD, 36));
			g.drawString(cardNumber.substring(0, 4), 40, 207);
			g.drawString(cardNumber.substring(4, 8), 150, 207);
			g.drawString(cardNumber.substring(8, 12), 260, 207);
			g.drawString(cardNumber.substring(12, 16), 370, 207);
			
			// exp date
			g.setFont(new Font("Lucida Console", Font.PLAIN, 24));
			g.drawString(exp, 65, 250);

			// customer name
			g.setFont(new Font("Tahoma", Font.PLAIN, 28));
			g.drawString(name.toUpperCase(), 30, 290);
			
			//cardType
			g.setFont(new Font("Lucida Console",Font.ITALIC,20));
			g.drawString("VISA", 120, 20);
			
			//load new image
			Resource simage = new ClassPathResource("images/logo.png");
			InputStream simageInputStream = simage.getInputStream();
			Image img = ImageIO.read(simageInputStream);
			//Draw image on given card
			g.drawImage(img, 304, 255, 91, 45, null);

			g.dispose();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(tag, "jpg", baos);
			baos.flush();
			photo= baos.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return photo;
	}
	
	private  String generateCreditCardNumber() {
		Random random = new Random();
		StringBuilder number = new StringBuilder();
		number.append(String.format("%04d", random.nextInt(10000)));
		number.append(String.format("%04d", random.nextInt(10000)));
		number.append(String.format("%04d", random.nextInt(10000)));
		number.append(String.format("%04d", random.nextInt(10000)));
		return number.toString();
	}

	private String generateCreditCardExpireDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
		LocalDate date = LocalDate.now();
		date = date.plusYears(3);
		return formatter.format(date);
	}
	
	private String generateCCVNumber() {
		Random random = new Random();
		StringBuilder number = new StringBuilder();
		number.append(String.format("%03d", random.nextInt(1000)));
		return number.toString();
	}
	
	
	@GetMapping("/customers/credit/card")
	public void findCustomerCreditCard(@RequestParam String name,@RequestParam String email,HttpServletResponse response) throws IOException {
	   byte[] card=generatedCreditCard(generateCreditCardNumber(),generateCreditCardExpireDate(),name); 
	   response.setContentType("image/png");
	   ServletOutputStream outputStream=response.getOutputStream();
	   if(card!=null) {
		   outputStream.write(card);
	   }else {
		   outputStream.write(new byte[] {});
	   }
	   outputStream.flush();
	   outputStream.close();
	}
}
