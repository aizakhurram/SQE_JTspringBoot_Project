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

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class UserControllerTest {

    @Mock
    private Model model;
    @Mock
    private userDao DAOaccess;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @Mock
    private com.jtspringproject.JtSpringProject.services.userService userService; // Assuming UserService is your actual service class
     @Mock
    private com.jtspringproject.JtSpringProject.services.cartService cartS;
    @Mock
    private com.jtspringproject.JtSpringProject.services.productService productS;

    @InjectMocks
    private AdminController adminController;

    @InjectMocks
    private UserController userCont;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserLogout() throws SQLException {
        //checks logout feature on the basis of the return string of the returnIndex() method
        String result =userCont.returnIndex();
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

    @Test
    void testProfileDisplay_success() throws Exception {
        String usernameForClass = "lisa";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava","root","zodiac");
        PreparedStatement stmt = con.prepareStatement("select * from CUSTOMER where username = ?"+";");
        stmt.setString(1, usernameForClass);
        ResultSet rst = stmt.executeQuery();
            String viewName = userCont.profileDisplay(model);
        assertEquals("profile", viewName);
    }

    @Test
    void testAddToCart() {
        String productId = "1";

        try {
            Connection mockConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava", "root", "zodiac");
            PreparedStatement mockStmt = mockConnection.prepareStatement("SELECT * FROM product WHERE product_id = ?");
            ResultSet mockResultSet = mock(ResultSet.class);

            when(mockStmt.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("product_id")).thenReturn(1);
            when(mockResultSet.getString("name")).thenReturn("Test Product");
            when(mockResultSet.getString("description")).thenReturn("Test Description");
            when(mockResultSet.getDouble("price")).thenReturn(99.99);
            when(mockResultSet.getString("image")).thenReturn("testimage.jpg");
            when(mockResultSet.getInt("quantity")).thenReturn(10);
            when(mockResultSet.getInt("weight")).thenReturn(100);
            when(mockResultSet.getInt("category_id")).thenReturn(1);
            when(mockResultSet.getInt("customer_id")).thenReturn(1);

            when(model.addAttribute("productId", 123)).thenReturn(model);
            when(model.addAttribute("productName", "Test Product")).thenReturn(model);
            when(model.addAttribute("description", "Test Description")).thenReturn(model);
            when(model.addAttribute("price", 99.99)).thenReturn(model);

            String viewName = userCont.addToCart(productId);

            assertEquals("cartproduct", viewName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     @Test
    void testProfileDisplay() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/profileDisplay");
        MockMvcBuilders.standaloneSetup(userCont)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("profile"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("profile"));
    }
     @Test
    void testDeleteCartItem() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/deleteCartItem");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("id", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(userCont)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/cartproduct"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/cartproduct"));
    }
    @Test
    void testBuy() {

        assertEquals("buy", (new UserController()).buy());
    }
    @Test
    void testViewCartProduct() {

        assertEquals("cartproduct", (new UserController()).viewCartProduct());
    }
     @Test
    void testGetproduct() throws Exception {
        when(productS.getProducts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/products");
        MockMvcBuilders.standaloneSetup(userCont)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("msg"))
                .andExpect(MockMvcResultMatchers.view().name("uproduct"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("uproduct"));
    }
    @Test
    void testReturnIndex() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");
        MockMvcBuilders.standaloneSetup(userCont)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("userLogin"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userLogin"));
    }
    @Test
    void testViewCart() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cart");
        MockMvcBuilders.standaloneSetup(userCont)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("cartproduct"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("cartproduct"));
    }
    @Test
    void testViewCartSecond() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cart");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userCont)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("cartproduct"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("cartproduct"));
    }
    


}
