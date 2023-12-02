package com.jtspringproject.JtSpringProject.controller;

import com.jtspringproject.JtSpringProject.dao.userDao;
import com.jtspringproject.JtSpringProject.models.User;
import com.jtspringproject.JtSpringProject.services.userService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class UserControllerTest {

    @Mock
    private Model model;
    @Mock
    private userDao DAOaccess;


    @Mock
    private com.jtspringproject.JtSpringProject.services.userService userService; // Assuming UserService is your actual service class

    @InjectMocks
    private AdminController adminController;

    @InjectMocks
    private UserController userCont;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }





    @Test
    void testUserLogout() {
        //checks logout feature on the basis of the return string of the returnIndex() method
        String result = adminController.returnIndex();
        assertEquals("userLogin", result);
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