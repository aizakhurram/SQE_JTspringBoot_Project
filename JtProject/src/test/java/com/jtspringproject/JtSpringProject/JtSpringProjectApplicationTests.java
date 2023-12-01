package com.jtspringproject.JtSpringProject;

import com.jtspringproject.JtSpringProject.controller.AdminController;
import com.jtspringproject.JtSpringProject.controller.UserController;
import com.jtspringproject.JtSpringProject.dao.productDao;
import com.jtspringproject.JtSpringProject.dao.userDao;
import com.jtspringproject.JtSpringProject.models.Category;
import com.jtspringproject.JtSpringProject.models.Product;
import com.jtspringproject.JtSpringProject.models.User;
import com.jtspringproject.JtSpringProject.services.userService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class JtSpringProjectApplicationTests {

	/*@Test
	void contextLoads() {
	}*/
	@Mock
	private Model model;
	@Mock
	private userDao DAOaccess;


	@Mock
	private userService userService; // Assuming UserService is your actual service class

	@InjectMocks
	private AdminController adminController;

	@InjectMocks
	private UserController userCont;


	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test //tests what the app does if the admin is logged in (should redirect to admin home page/dashbaord )
	void testAdminHome_AdminLoggedIn() {
		adminController.adminlogcheck=1;
		when(model.addAttribute("username", "admin")).thenReturn(model); // Assuming admin is the username

		String result = adminController.adminHome(model);

		assertEquals("adminHome", result);
	}

	@Test //check what the app does if admin is not logged in (it should redirect to login page and not dashboard)
	void testAdminHome_AdminNotLoggedIn() {
		adminController.adminlogcheck=0;

		String result = adminController.adminHome(model);

		assertEquals("redirect:/admin/login", result);
	}

	@Test
	void testUserLogout() {
		//checks logout feature on the basis of the return string of the returnIndex() method
		String result = adminController.returnIndex();
		assertEquals("userLogin", result);
	}
	@Test
	void testAdminLogin() {//
		String result = adminController.adminlogin();

		assertEquals("adminlogin", result);
	}
	@Test  //testing login feature in case of success
	void AdminLoginSuccess() {
		String username = "admin";
		String password = "password";

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setRole("ROLE_ADMIN");

		when(userService.checkLogin(username, password)).thenReturn(user);

		ModelAndView modelAndView = adminController.adminlogin(username, password);

		assertEquals("adminHome", modelAndView.getViewName());
		assertEquals(user, modelAndView.getModel().get("admin"));
		assertEquals(1, adminController.adminlogcheck);
	}

	@Test //testing login feature in case of failure
	void AdminLoginFailure() {
		String username = "user";
		String password = "incorrectPass";

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setRole("ROLE_USER");

		when(userService.checkLogin(username, password)).thenReturn(user);

		ModelAndView modelAndView = adminController.adminlogin(username, password);

		assertEquals("adminlogin", modelAndView.getViewName());
		assertEquals("Please enter correct username and password", modelAndView.getModel().get("msg"));
		assertEquals(0, adminController.adminlogcheck);
	}

	@Test
	void testNewUserRegisterSuccess() {
		User user = new User();
		user.setUsername("testUser");
		user.setPassword("userPass");
		user.setAddress("146 Lahore, Valencia");
		user.setEmail("testuser@gmail.com");
		when(userService.checkUserExists("testUser")).thenReturn(false);
		when(userService.addUser(user)).thenReturn(user);

		ModelAndView modelAndView =userCont.newUseRegister(user);

		assertEquals("userLogin", modelAndView.getViewName());
		verify(userService, times(1)).checkUserExists("testUser");
		verify(userService, times(1)).addUser(user);
	}

	@Test //tests to make sure that a user with the same name as an existing user is not being registered
	void testRegisterRedundantUser() {
		User user = new User();
		user.setUsername("lisa");
		user.setPassword("userPass");
		user.setAddress("146 Lahore, Valencia");
		user.setEmail("testuser@gmail.com");
		when(userService.checkUserExists("lisa")).thenReturn(true);
		when(userService.addUser(user)).thenReturn(user);

		ModelAndView modelAndView =userCont.newUseRegister(user);

		assertEquals("register", modelAndView.getViewName());
		verify(userService, times(1)).checkUserExists("lisa");

	}



}


