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
import java.sql.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AdminControllerTest {
    @Mock
    private Model model;
    @Mock
    Connection mockCon;

    @Mock
    PreparedStatement mockStmt;

    @Mock
    private UserController userCon;

    @InjectMocks
    private AdminController adminController;
    @Mock
    private com.jtspringproject.JtSpringProject.services.userService userService;
    @Mock
    private com.jtspringproject.JtSpringProject.services.categoryService categoryS;
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

    @Test
    public void testDeleteCategory_success() {

        int categoryId = 123;
        when(categoryS.deleteCategory(categoryId)).thenReturn(true);


        String result = adminController.removeCategoryDb(categoryId);

        // Verify that the deleteCategory method was called with the correct ID
        verify(categoryS, times(1)).deleteCategory(categoryId);
        //success scenario
       // if (categoryS.deleteCategory(categoryId))
            assertEquals("redirect:/admin/categories", result);

    }

    @Test
    public void testDeleteCustomer_Success() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava","root","zodiac");
        String insertQuery = "INSERT INTO ecommjava.customer ( address, email, password, role, username) VALUES (\"146, Haven\", \"email@wtv\", 456, \"ROLE_NORMAL\", \"testUser\")";
        PreparedStatement stmt = con.prepareStatement(insertQuery);
        when(mockCon.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeUpdate()).thenReturn(1); // Simulating a successful deletion
        String result = adminController.deleteCustomer("testUser");

        assertEquals("redirect:/admin/customers", result);

    }

    @Test
    public void testDeleteCustomer_InvalidCust() throws SQLException {
        String username = "nonExistingUser";

        when(mockCon.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeUpdate()).thenReturn(0); // Simulating no customer found

        String result = adminController.deleteCustomer(username);

        assertEquals("redirect:/admin/customers", result);

    }

    @Test //should throw exception
    public void testDeleteCustomer_Failure() throws SQLException {
        String username = "testUser";

        when(mockCon.prepareStatement(anyString())).thenThrow(SQLException.class);

        String result = adminController.deleteCustomer(username);

        assertEquals("redirect:/admin/customers", result); // Or handle the exception case as needed
        verify(mockStmt, never()).setString(1, username);
        verify(mockStmt, never()).executeUpdate();
    }
    @Test
    void testAddCategory() throws Exception {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        Mockito.when(this.categoryS.addCategory((String)Mockito.any())).thenReturn(category);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/categories", new Object[0]).param("categoryname", new String[]{"foo"});
        MockMvcBuilders.standaloneSetup(new Object[]{this.adminController}).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isFound()).andExpect(MockMvcResultMatchers.model().size(0)).andExpect(MockMvcResultMatchers.view().name("redirect:categories")).andExpect(MockMvcResultMatchers.redirectedUrl("categories"));
    }
      @Test
    void testAdminHome() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/Dashboard", new Object[0]);
        MockMvcBuilders.standaloneSetup(new Object[]{this.adminController}).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isFound()).andExpect(MockMvcResultMatchers.model().size(0)).andExpect(MockMvcResultMatchers.view().name("redirect:/admin/login")).andExpect(MockMvcResultMatchers.redirectedUrl("/admin/login"));
    }
    @Test
    void testUpdateCategory() throws Exception {
        Category category = new Category();
        category.setId(1);
        category.setName("Name");
        Mockito.when(this.categoryS.updateCategory(Mockito.anyInt(), (String)Mockito.any())).thenReturn(category);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/categories/update", new Object[0]);
        MockHttpServletRequestBuilder requestBuilder = getResult.param("categoryid", new String[]{String.valueOf(1)}).param("categoryname", new String[]{"foo"});
        MockMvcBuilders.standaloneSetup(new Object[]{this.adminController}).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isFound()).andExpect(MockMvcResultMatchers.model().size(0)).andExpect(MockMvcResultMatchers.view().name("redirect:/admin/categories")).andExpect(MockMvcResultMatchers.redirectedUrl("/admin/categories"));
    }
    @Test
    void testAddProduct() throws Exception {
        Mockito.when(this.categoryS.getCategories()).thenReturn(new ArrayList());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/products/add", new Object[0]);
        MockMvcBuilders.standaloneSetup(new Object[]{this.adminController}).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().size(1)).andExpect(MockMvcResultMatchers.model().attributeExists(new String[]{"categories"})).andExpect(MockMvcResultMatchers.view().name("productsAdd")).andExpect(MockMvcResultMatchers.forwardedUrl("productsAdd"));
    }

    @Test
    void testAddProduct2() throws Exception {
        Mockito.when(this.categoryS.getCategories()).thenReturn(new ArrayList());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/products/add", new Object[0]);
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(new Object[]{this.adminController}).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().size(1)).andExpect(MockMvcResultMatchers.model().attributeExists(new String[]{"categories"})).andExpect(MockMvcResultMatchers.view().name("productsAdd")).andExpect(MockMvcResultMatchers.forwardedUrl("productsAdd"));
    }
    @Test
    void testGetCustomerDetail() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/customers", new Object[0]);
        MockMvcBuilders.standaloneSetup(new Object[]{this.adminController}).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().size(0)).andExpect(MockMvcResultMatchers.view().name("adminlogin")).andExpect(MockMvcResultMatchers.forwardedUrl("adminlogin"));
    }
      @Test
    void testGetcategory() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/categories", new Object[0]);
        MockMvcBuilders.standaloneSetup(new Object[]{this.adminController}).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().size(0)).andExpect(MockMvcResultMatchers.view().name("adminlogin")).andExpect(MockMvcResultMatchers.forwardedUrl("adminlogin"));
    }
     @Test
    void testGetproduct() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/products", new Object[0]);
        MockMvcBuilders.standaloneSetup(new Object[]{this.adminController}).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().size(0)).andExpect(MockMvcResultMatchers.view().name("adminlogin")).andExpect(MockMvcResultMatchers.forwardedUrl("adminlogin"));
    }
    @Test
    void testPostproduct() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/products", new Object[0]);
        MockMvcBuilders.standaloneSetup(new Object[]{this.adminController}).build().perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isFound()).andExpect(MockMvcResultMatchers.model().size(0)).andExpect(MockMvcResultMatchers.view().name("redirect:/admin/categories")).andExpect(MockMvcResultMatchers.redirectedUrl("/admin/categories"));
    }



}
