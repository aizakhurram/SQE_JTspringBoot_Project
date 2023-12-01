package com.jtspringproject.JtSpringProject;

import com.jtspringproject.JtSpringProject.controller.AdminController;
import com.jtspringproject.JtSpringProject.dao.productDao;
import com.jtspringproject.JtSpringProject.models.Category;
import com.jtspringproject.JtSpringProject.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class JtSpringProjectApplicationTests {

	/*@Test
	void contextLoads() {
	}*/
	@Mock
	private Model model;

	@InjectMocks
	private AdminController adminController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAdminHome_AdminLoggedIn() {
		adminController.adminlogcheck=1;
		when(model.addAttribute("username", "admin")).thenReturn(model); // Assuming admin is the username

		String result = adminController.adminHome(model);

		assertEquals("adminHome", result);
	}

	@Test
	void testAdminHome_AdminNotLoggedIn() {
		adminController.adminlogcheck=0;

		String result = adminController.adminHome(model);

		assertEquals("redirect:/admin/login", result);
	}

	@Test
	void testReturnIndex() {
		String result = adminController.returnIndex();
		assertEquals("userLogin", result);
	}
}
