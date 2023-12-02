package com.jtspringproject.JtSpringProject.controller;

import com.jtspringproject.JtSpringProject.models.User;
import com.jtspringproject.JtSpringProject.services.userService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AdminControllerTest {
    @Mock
    private Model model;
    @InjectMocks
    private AdminController adminController;
    @Mock
    private com.jtspringproject.JtSpringProject.services.userService userService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
        //tests what the app does if the admin is logged in (should redirect to admin home page/dashbaord )
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

    @AfterEach
    void tearDown() {
    }
}