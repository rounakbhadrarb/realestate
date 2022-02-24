package com.thbs.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thbs.RealestateApplication;
import com.thbs.constantProperties.Constants;
import com.thbs.models.House;
import com.thbs.models.Purchase;
import com.thbs.models.SoldHouses;
import com.thbs.models.User;
import com.thbs.repository.PurchaseRepository;
import com.thbs.services.HouseService;
import com.thbs.services.PurchaseService;
import com.thbs.services.UserService;
import com.thbs.util.util;

/**
 * @author Darshan and Rounak
 */
@Controller
public class UserController {
	static int pid;
	@Autowired
	UserService userService;
	static String n;
	@Autowired
	HouseService houseService;
	@Autowired
	PurchaseRepository purchaseRepository;

	private static final Log LOGGER = LogFactory.getLog(RealestateApplication.class);

	@Autowired
	PurchaseService purchaseservice;

	/**
	 * user registration and validation
	 * 
	 * @param user
	 * @return
	 */

	@PostMapping(value = Constants.USER_RGISTERATION)
	public String registerUser(@ModelAttribute("user") User user,Model model) {
		// TODO Auto-generated method stub
		Optional<User> searchUser = userService.getUser(user.getUsername());
		if (searchUser.isPresent()) {
			User u1=searchUser.get();
			model.addAttribute("username",u1.getUsername());
			return "sameusername";

		} else {
			userService.userSave(user);
			return "index";
		}
	}

	/**
	 * user login validation
	 * 
	 * @param u
	 * @param model
	 * @return
	 */
	@PostMapping(value = Constants.USER_LOGIN_VALIDATION)
	public String loginUser(@ModelAttribute("user") User u, Model model) {
		Optional<User> searchUser = userService.getUser(u.getUsername());
		if (searchUser.isPresent()) {
			User userFromDb = searchUser.get();
			if (u.getPassword().equals(userFromDb.getPassword())) {
				List<House> listProducts = houseService.getAllProperties();
				model.addAttribute("listProducts", listProducts);
				n = u.getUsername();
				model.addAttribute("n", n);
				model.addAttribute("util", new util());
				return "userportl";
			} else {
				return "invalid";
			}

		} else
			return "invalid";
	}

	/**
	 * property search validation
	 * 
	 * @param house
	 * @param model
	 * @return
	 */
	@RequestMapping(value = Constants.USER_SEARCH_OPTIONS)
	public String serchProperty(@ModelAttribute("house") House house, Model model) {
		Optional<com.thbs.models.House> listProducts = houseService.getProperty(house.getPid());
		if (listProducts.isPresent()) {
			model.addAttribute("listProducts", listProducts.get());
		}
		return "userget";
	}

	/**
	 * searching a particular property by propertyId
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@GetMapping(value = Constants.USER_OPERATION_TESTING_PAGE)
	public String userPortal(@ModelAttribute("user") User user, Model model) {
		List<House> listProducts = houseService.getAllProperties();
		model.addAttribute("listProducts", listProducts);
		model.addAttribute("n", n);
		return "userportl";
	}

	/**
	 * this API will used to purchase a property
	 * 
	 * @param purchase
	 * @param model
	 * @return
	 */
	@PostMapping(value = Constants.USER_CONFIRM_PURCHASE)
	public String confirm_purchase(@ModelAttribute("purchase") Purchase purchase, Model model) {
		pid = purchase.getPid();
		String tid1 = getTid();
		model.addAttribute("tid", tid1);
		purchase.setTransactionId(tid1);
		/*
		 * LocalDateTime now = LocalDateTime.now(); DateTimeFormatter format =
		 * DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); String formatDateTime =
		 * now.format(format);
		 */
		Date date = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss z");
		formatDate.setTimeZone(TimeZone.getTimeZone("IST"));
		System.out.println(formatDate.format(date));
		purchase.setDateandtime(formatDate.format(date));
		LOGGER.info(formatDate.format(date));
		String confirm = purchaseservice.savepurchase(purchase);
		if (confirm.equals("true")) {
			model.addAttribute("pid", pid);
			model.addAttribute("username", n);
			model.addAttribute("price", MainController.price);
			return "success";
		}
		return "Payment1";
	}

	/**
	 * to get receipt
	 * 
	 * @param soldhouse
	 * @param model
	 * @return
	 */
	@RequestMapping(value = Constants.USER_RECEIPT)
	public String getReceipt(@ModelAttribute("soldhouse") SoldHouses soldhouse, Model model,
			@ModelAttribute("purchase") Purchase purchase) {

		Optional<SoldHouses> listProducts = purchaseservice.getASoldHouse(pid);
		Optional<Purchase> p = purchaseservice.getAPurchaseDetails(pid);
		purchase = p.get();
		if (listProducts.isPresent()) {
			model.addAttribute("listProducts", listProducts.get());
			model.addAttribute("purchase", purchase);
			model.addAttribute("tid", purchase.getTransactionId());
			return "receipt";
		}
		return "receipt";
	}

	/**
	 * this API will help the user to reset the password
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/forgotPassword")
	public String forgotPassword(@ModelAttribute("user") User user, Model model) {
		Optional<User> user1 = userService.getUser(user.getUsername());
		if (user1.isPresent()) {
			User u = user1.get();
			if(user.getContactnumber().equals(u.getContactnumber()))
			{String username = u.getUsername();
			String password = u.getPassword();
			model.addAttribute("username", username);
			model.addAttribute("password", password);
			return "gettingOldPasswordPage";
			}
			return "invalidforgotpassword";
		}
		return "invalidforgotpassword";
	}

	/**
	 * This API will help the user to update the password...
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/updatePassword")
	public String updatePassword(@ModelAttribute("user") User user, Model model) {
		Optional<User> user1 = userService.getUser(user.getUsername());
		User u = user1.get();
		String oldPassword = u.getPassword();
		String newPassword = user.getPassword();
		u.setPassword(user.getPassword());
		model.addAttribute("oldPassword", oldPassword);
		model.addAttribute("newPassword", newPassword);
		userService.userSave(u);
		return "passwordSuccess";
	}

	/**
	 * This will use User to see his profile details...
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/userProfile")
	public String profile(Model model) {
		Optional<User> user = userService.getUser(n);
		User u = user.get();
		model.addAttribute("username", UserController.n);
		model.addAttribute("emailid", u.getEmailid());
		model.addAttribute("name", u.getName());
		model.addAttribute("contactnumber", u.getContactnumber());
		return "profile";
	}

	/**
	 * This API will helps to display the user purchase history.
	 * 
	 * @param purchase
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/purchaseHistory")
	public String history(@ModelAttribute("purchase") Purchase purchase, Model model) {
		List<Purchase> plist = purchaseRepository.findByUsername(n);
		model.addAttribute("purchaseList", plist);
		model.addAttribute("username", n);
		LOGGER.info(plist);
		return "purchaseHistory";
	}

	@GetMapping(value = "/getImage1/{pid}")
	public String getHouseImage(@PathVariable(value = "pid") int pid, @ModelAttribute("house") House house,
			Model model) {
		House house1 = houseService.getPropertyByPid(pid);
		model.addAttribute("house1", house1);
		util u1 = new util();
		model.addAttribute("ext", u1.getImgData(house1.getImage()));

		return "userviewimg";
	}

	/**
	 * this method helps to generate the random transaction id.
	 * 
	 * @return
	 */
	public String getTid() {
		Random rand = new Random();
		int count = (rand.nextInt(100) + 100);
		String s3 = Integer.toString(count);
		char ch = n.charAt(0);
		String s1 = Character.toString(ch);
		int pid1 = pid;
		String s2 = Integer.toString(pid1);
		String s = (s1.toUpperCase()) + s2 + s3;
		return s;
	}

}
