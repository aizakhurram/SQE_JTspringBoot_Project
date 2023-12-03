package com.jtspringproject.JtSpringProject.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.jtspringproject.JtSpringProject.dao.userDao;
import com.jtspringproject.JtSpringProject.models.Category;
import com.jtspringproject.JtSpringProject.models.Product;
import com.jtspringproject.JtSpringProject.models.User;
import com.jtspringproject.JtSpringProject.services.productService;
import com.jtspringproject.JtSpringProject.services.userService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
public class UserControllerTest {
    @MockBean
    private productService productService;

    @Autowired
    private UserController userController;

    @MockBean
    private userService userService;

    /**
     * Method under test: {@link UserController#registerUser()}
     */
    @Test
    void testRegisterUser() {


        assertEquals("register", (new UserController()).registerUser());
    }

    /**
     * Method under test: {@link UserController#registerUser()}
     */
    @Test
    void testRegisterUser2() {

        userService userService = new userService();
        userService.userDao = mock(userDao.class);
        UserController userController = new UserController();
        userController.userService = userService;
        assertEquals("register", userController.registerUser());
    }

    /**
     * Method under test: {@link UserController#updateUser()}
     */
    @Test
    void testUpdateUser() {


        assertEquals("updateProfile", (new UserController()).updateUser());
    }

    /**
     * Method under test: {@link UserController#updateUser()}
     */
    @Test
    void testUpdateUser2() {

        userService userService = new userService();
        userService.userDao = mock(userDao.class);
        UserController userController = new UserController();
        userController.userService = userService;
        assertEquals("updateProfile", userController.updateUser());
    }

    /**
     * Method under test: {@link UserController#buy()}
     */
    @Test
    void testBuy() {

        assertEquals("buy", (new UserController()).buy());
    }

    /**
     * Method under test: {@link UserController#buy()}
     */
    @Test
    void testBuy2() {


        userService userService = new userService();
        userService.userDao = mock(userDao.class);
        UserController userController = new UserController();
        userController.userService = userService;
        assertEquals("buy", userController.buy());
    }

    /**
     * Method under test: {@link UserController#returnIndex()}
     */

    /**
     * Method under test: {@link UserController#deleteCartItem(int)}
     */
    @Test
    void testDeleteCartItem() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/deleteCartItem");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("id", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/cartproduct"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/cartproduct"));
    }

    /**
     * Method under test:  {@link UserController#getproduct()}
     */
    @Test
    void testGetproduct() throws Exception {
        when(productService.getProducts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/products");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("msg"))
                .andExpect(MockMvcResultMatchers.view().name("uproduct"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("uproduct"));
    }

    /**
     * Method under test: {@link UserController#getproduct()}
     */
    @Test
    void testGetproduct2() throws Exception {
        Category category = new Category();
        category.setId(1);
        category.setName("?");

        Product product = new Product();
        product.setCategory(category);
        product.setDescription("The characteristics of someone or something");
        product.setId(1);
        product.setImage("?");
        product.setName("?");
        product.setPrice(1);
        product.setQuantity(1);
        product.setWeight(3);

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productService.getProducts()).thenReturn(productList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/products");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.view().name("uproduct"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("uproduct"));
    }

    /**
     * Method under test: {@link UserController#index(Model)}
     */
    @Test
    void testIndex() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/index");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("userLogin"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userLogin"));
    }

    /**
     * Method under test: {@link UserController#index(Model)}
     */
    @Test
    void testIndex2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/index");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("userLogin"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userLogin"));
    }

    /**
     * Method under test: {@link UserController#profileDisplay(Model)}
     */
    @Test
    void testProfileDisplay() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/profileDisplay");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("profile"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("profile"));
    }

    /**
     * Method under test: {@link UserController#profileDisplay(Model)}
     */
    @Test
    void testProfileDisplay2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/profileDisplay");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("profile"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("profile"));
    }

    /**
     * Method under test: {@link UserController#Test(Model)}
     */
    @Test
    void testTest() {

        UserController userController = new UserController();
        ConcurrentModel model = new ConcurrentModel();
        String actualTestResult = userController.Test(model);
        Object getResult = model.get("f");
        assertEquals("abc", ((List<String>) getResult).get(1));
        assertEquals("test", actualTestResult);
        assertEquals("xyz", ((List<String>) getResult).get(0));
        assertEquals(2, ((Collection<String>) getResult).size());
    }

    /**
     * Method under test: {@link UserController#Test(Model)}
     */
    @Test
    void testTest2() {


        userService userService = new userService();
        userService.userDao = mock(userDao.class);
        UserController userController = new UserController();
        userController.userService = userService;
        ConcurrentModel model = new ConcurrentModel();
        String actualTestResult = userController.Test(model);
        Object getResult = model.get("f");
        assertEquals("abc", ((List<String>) getResult).get(1));
        assertEquals("test", actualTestResult);
        assertEquals("xyz", ((List<String>) getResult).get(0));
        assertEquals(2, ((Collection<String>) getResult).size());
    }

    /**
     * Method under test: {@link UserController#Test2()}
     */
    @Test
    void testTest22() {


        ModelAndView actualTest2Result = (new UserController()).Test2();
        Map<String, Object> model = actualTest2Result.getModel();
        Object getResult = model.get("marks");
        assertEquals(10, ((List<Integer>) getResult).get(0));
        assertEquals(2, ((Collection<Integer>) getResult).size());
        assertEquals(25, ((List<Integer>) getResult).get(1));
        assertTrue(actualTest2Result.isReference());
        assertSame(model, actualTest2Result.getModelMap());
    }

    /**
     * Method under test: {@link UserController#Test2()}
     */
    @Test
    void testTest23() {


        userService userService = new userService();
        userService.userDao = mock(userDao.class);
        UserController userController = new UserController();
        userController.userService = userService;
        ModelAndView actualTest2Result = userController.Test2();
        Map<String, Object> model = actualTest2Result.getModel();
        Object getResult = model.get("marks");
        assertEquals(10, ((List<Integer>) getResult).get(0));
        assertEquals(2, ((Collection<Integer>) getResult).size());
        assertEquals(25, ((List<Integer>) getResult).get(1));
        assertTrue(actualTest2Result.isReference());
        assertSame(model, actualTest2Result.getModelMap());
    }

    /**
     * Method under test: {@link UserController#addToCart(int)}
     */
    @Test
    void testAddToCart() {


        assertThrows(RuntimeException.class, () -> (new UserController()).addToCart(1));
        assertThrows(RuntimeException.class, () -> (new UserController()).addToCart("42"));
    }

    /**
     * Method under test: {@link UserController#viewCartProduct()}
     */
    @Test
    void testViewCartProduct() {

        assertEquals("cartproduct", (new UserController()).viewCartProduct());
    }

    /**
     * Method under test: {@link UserController#viewCartProduct()}
     */
    @Test
    void testViewCartProduct2() {


        userService userService = new userService();
        userService.userDao = mock(userDao.class);
        UserController userController = new UserController();
        userController.userService = userService;
        assertEquals("cartproduct", userController.viewCartProduct());
    }

    /**
     * Method under test:  {@link UserController#updateUserProfile(String, String, String, String, String)}
     */
    @Test
    void testUpdateUserProfile() throws Exception {
        when(productService.getProducts()).thenReturn(new ArrayList<>());

        User user = new User();
        user.setAddress("42 Main St");
        user.setEmail("jane.doe@example.org");
        user.setId(1);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUsername("janedoe");
        when(userService.checkLogin(Mockito.<String>any(), Mockito.<String>any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/updateuser")
                .param("address", "foo")
                .param("email", "foo")
                .param("password", "foo")
                .param("userid", "foo")
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("msg", "user"))
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("index"));
    }

    /**
     * Method under test:
     * {@link UserController#updateUserProfile(String, String, String, String, String)}
     */
    @Test
    void testUpdateUserProfile2() throws Exception {
        Category category = new Category();
        category.setId(1);
        category.setName("?");

        Product product = new Product();
        product.setCategory(category);
        product.setDescription("The characteristics of someone or something");
        product.setId(1);
        product.setImage("?");
        product.setName("?");
        product.setPrice(2);
        product.setQuantity(2);
        product.setWeight(3);

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productService.getProducts()).thenReturn(productList);

        User user = new User();
        user.setAddress("42 Main St");
        user.setEmail("jane.doe@example.org");
        user.setId(1);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUsername("janedoe");
        when(userService.checkLogin(Mockito.<String>any(), Mockito.<String>any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/updateuser")
                .param("address", "foo")
                .param("email", "foo")
                .param("password", "foo")
                .param("userid", "foo")
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products", "user"))
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("index"));
    }

    /**
     * Method under test:
     * {@link UserController#userlogin(String, String, Model, HttpServletResponse)}
     */
    @Test
    void testUserlogin() throws Exception {
        User user = new User();
        user.setAddress("42 Main St");
        user.setEmail("jane.doe@example.org");
        user.setId(1);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUsername("janedoe");
        when(userService.checkLogin(Mockito.<String>any(), Mockito.<String>any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/userloginvalidate")
                .param("password", "foo")
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("msg"))
                .andExpect(MockMvcResultMatchers.view().name("userLogin"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userLogin"));
    }

    /**
     * Method under test:  {@link UserController#userlogin(String, String, Model, HttpServletResponse)}
     */
    @Test
    void testUserlogin2() throws Exception {
        when(productService.getProducts()).thenReturn(new ArrayList<>());

        User user = new User();
        user.setAddress("42 Main St");
        user.setEmail("jane.doe@example.org");
        user.setId(1);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUsername("foo");
        when(userService.checkLogin(Mockito.<String>any(), Mockito.<String>any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/userloginvalidate")
                .param("password", "foo")
                .param("username", "foo");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("msg", "user"))
                .andExpect(MockMvcResultMatchers.view().name("index"));
        ResultActions resultActions2 = resultActions.andExpect(MockMvcResultMatchers.cookie().value("username", "foo"));
        ResultActions resultActions3 = resultActions2.andExpect(MockMvcResultMatchers.cookie().secure("username", false));
        ResultActions resultActions4 = resultActions3.andExpect(MockMvcResultMatchers.cookie().httpOnly("username", false));
        resultActions4.andExpect(MockMvcResultMatchers.cookie().maxAge("username", -1))
                .andExpect(MockMvcResultMatchers.forwardedUrl("index"));
    }

    /**
     * Method under test:
     * {@link UserController#userlogin(String, String, Model, HttpServletResponse)}
     */
    @Test
    void testUserlogin3() throws Exception {
        Category category = new Category();
        category.setId(1);
        category.setName("?");

        Product product = new Product();
        product.setCategory(category);
        product.setDescription("The characteristics of someone or something");
        product.setId(1);
        product.setImage("?");
        product.setName("?");
        product.setPrice(2);
        product.setQuantity(2);
        product.setWeight(3);

        ArrayList<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productService.getProducts()).thenReturn(productList);

        User user = new User();
        user.setAddress("42 Main St");
        user.setEmail("jane.doe@example.org");
        user.setId(1);
        user.setPassword("iloveyou");
        user.setRole("Role");
        user.setUsername("foo");
        when(userService.checkLogin(Mockito.<String>any(), Mockito.<String>any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/userloginvalidate")
                .param("password", "foo")
                .param("username", "foo");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products", "user"))
                .andExpect(MockMvcResultMatchers.view().name("index"));
        ResultActions resultActions2 = resultActions.andExpect(MockMvcResultMatchers.cookie().value("username", "foo"));
        ResultActions resultActions3 = resultActions2.andExpect(MockMvcResultMatchers.cookie().secure("username", false));
        ResultActions resultActions4 = resultActions3.andExpect(MockMvcResultMatchers.cookie().httpOnly("username", false));
        resultActions4.andExpect(MockMvcResultMatchers.cookie().maxAge("username", -1))
                .andExpect(MockMvcResultMatchers.forwardedUrl("index"));
    }

    /**
     * Method under test: {@link UserController#viewCart(Model)}
     */
    @Test
    void testViewCart() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cart");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("cartproduct"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("cartproduct"));
    }

    /**
     * Method under test: {@link UserController#viewCart(Model)}
     */
    @Test
    void testViewCart2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/cart");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("cartproduct"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("cartproduct"));
    }
}
