package com.jtspringproject.JtSpringProject.controller;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.jtspringproject.JtSpringProject.models.Category;
import com.jtspringproject.JtSpringProject.models.Product;
import com.jtspringproject.JtSpringProject.models.User;
import com.jtspringproject.JtSpringProject.services.categoryService;
import com.jtspringproject.JtSpringProject.services.productService;
import com.jtspringproject.JtSpringProject.services.userService;

import net.bytebuddy.asm.Advice.This;
import net.bytebuddy.asm.Advice.OffsetMapping.ForOrigin.Renderer.ForReturnTypeName;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private userService userService;
	@Autowired
	private categoryService categoryService;
	
	@Autowired
	private productService productService;
	
	int adminlogcheck = 0;
	String usernameforclass = "";
	@RequestMapping(value = {"/","/logout"})
	public String returnIndex() {
		return "adminlogin";
	}



	@GetMapping("/index")
	public String index(Model model) {
		if (usernameforclass == null || usernameforclass.isEmpty()) {
			return "userLogin";
		} else {
			model.addAttribute("username", usernameforclass);
			return "index";
		}
	}



	@GetMapping("login")
	public String adminlogin() {
		
		return "adminlogin";
	}
	@GetMapping("Dashboard")
	public String adminHome(Model model) {
		if(adminlogcheck==1)
			return "adminHome";
		else
			return "redirect:/admin/login";
	}
	@GetMapping("/loginvalidate")
	public String adminlog(Model model) {
		
		return "adminlogin";
	}
	@RequestMapping(value = "loginvalidate", method = RequestMethod.POST)
	public ModelAndView adminlogin( @RequestParam("username") String username, @RequestParam("password") String pass) {
		
		User user=this.userService.checkLogin(username, pass);
		
		if(user.getRole() != null && user.getRole().equals("ROLE_ADMIN")) {
			ModelAndView mv = new ModelAndView("adminHome");
			adminlogcheck=1;
			mv.addObject("admin", user);
			return mv;
		}
		else {
			ModelAndView mv = new ModelAndView("adminlogin");
			mv.addObject("msg", "Please enter correct username and password");
			return mv;
		}
	}
	@GetMapping("categories")
	public ModelAndView getcategory() {
		if(adminlogcheck==0){
			ModelAndView mView = new ModelAndView("adminlogin");
			return mView;
		}
		else {
			ModelAndView mView = new ModelAndView("categories");
			List<Category> categories = this.categoryService.getCategories();
			mView.addObject("categories", categories);
			return mView;
		}
	}
	@RequestMapping(value = "categories",method = RequestMethod.POST)
	public String addCategory(@RequestParam("categoryname") String category_name)
	{
		System.out.println(category_name);
		
		Category category =  this.categoryService.addCategory(category_name);
		if(category.getName().equals(category_name)) {
			return "redirect:categories";
		}else {
			return "redirect:categories";
		}
	}

	@GetMapping("categories/delete")
	public String removeCategoryDb(@RequestParam("id") int id) {
		categoryService.deleteCategory(id);
		return "redirect:/admin/categories";
	}


	@GetMapping("categories/update")
	public String updateCategory(@RequestParam("categoryid") int id, @RequestParam("categoryname") String categoryname)
	{
		Category category = this.categoryService.updateCategory(id, categoryname);
		return "redirect:/admin/categories";
	}

	
//	 --------------------------Remaining --------------------
	@GetMapping("products")
	public ModelAndView getproduct() {
		if(adminlogcheck==0){
			ModelAndView mView = new ModelAndView("adminlogin");
			return mView;
		}
		else {
			ModelAndView mView = new ModelAndView("products");

			List<Product> products = this.productService.getProducts();

			if (products.isEmpty()) {
				mView.addObject("msg", "No products are available");
			} else {
				mView.addObject("products", products);
			}
			return mView;
		}

	}
	@GetMapping("products/add")
	public ModelAndView addProduct() {
		ModelAndView mView = new ModelAndView("productsAdd");
		List<Category> categories = this.categoryService.getCategories();
		mView.addObject("categories",categories);
		return mView;
	}

	@RequestMapping(value = "products/add",method=RequestMethod.POST)
	public String addProduct(@RequestParam("name") String name,@RequestParam("categoryid") int categoryId ,@RequestParam("price") int price,@RequestParam("weight") int weight, @RequestParam("quantity")int quantity,@RequestParam("description") String description,@RequestParam("productImage") String productImage) {
		System.out.println(categoryId);
		Category category = this.categoryService.getCategory(categoryId);
		Product product = new Product();
		product.setId(categoryId);
		product.setName(name);
		product.setCategory(category);
		product.setDescription(description);
		product.setPrice(price);
		product.setImage(productImage);
		product.setWeight(weight);
		product.setQuantity(quantity);
		this.productService.addProduct(product);
		return "redirect:/admin/products";
	}

		@GetMapping("products/update/{id}")
	@ResponseBody
	public ModelAndView updateproduct(@PathVariable int id) {

		ModelAndView mView = new ModelAndView("productsUpdate");
		Product product = this.productService.getProduct(id);
		List<Category> categories = this.categoryService.getCategories();

		mView.addObject("categories",categories);
		mView.addObject("product", product);
		return mView;
	}

	
//
//, @ModelAttribute("product") Product product
	@PostMapping (value = "products/updated/{id}")
	public String updateProduct(@PathVariable("id") int id, @RequestParam("name") String name,@RequestParam("categoryid") int categoryId ,@RequestParam("price") int price,@RequestParam("weight") int weight, @RequestParam("quantity")int quantity,@RequestParam("description") String description,@RequestParam("productImage") String productImage)
	{

		
		System.out.println(categoryId);
		Category category = this.categoryService.getCategory(categoryId);
		Product prod = this.productService.getProduct(id);

		prod.setId(id);
		prod.setName(name);
		prod.setCategory(category);
		prod.setDescription(description);
		prod.setPrice(price);
		prod.setImage(productImage);
		prod.setWeight(weight);
		prod.setQuantity(quantity);

		try {
			this.productService.updateProduct(id, prod); // Update the product
		
		} catch (Exception ex) {
			ex.printStackTrace(); // Log any exceptions
			// Handle exception or return an error view/page
//			return "errorPage"; // Redirect to an error page if an exception occurs
		}

		return "redirect:/admin/products";
	}
	
	@GetMapping("products/delete")
	public String removeProduct(@RequestParam("id") int id)
	{
		this.productService.deleteProduct(id);
		return "redirect:/admin/products";
	}
	
	@PostMapping("products")
	public String postproduct() {
		return "redirect:/admin/categories";
	}
	
	@GetMapping("customers")
	public ModelAndView getCustomerDetail() {
		if(adminlogcheck==0){
			ModelAndView mView = new ModelAndView("adminlogin");
			return mView;
		}
		else {
			ModelAndView mView = new ModelAndView("displayCustomers");
			List<User> users = this.userService.getUsers();
			mView.addObject("customers", users);
			return mView;
		}
	}

	// delete button for customer/user
	@RequestMapping(value = "customers/delete", method = RequestMethod.POST)
	public String deleteCustomer(@RequestParam("usernamee") String username) {
		System.out.println("customer name= " + username);
		try {
			//change password for your database in AdminController.java line #233
			//in place of tayyab3037 enter your database password on line # 233
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava","root","tayyab3037");
			String deleteQuery = "DELETE FROM customer WHERE username = ?";
			PreparedStatement stmt = con.prepareStatement(deleteQuery);
			stmt.setString(1, username);
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Customer deleted successfully");
			} else {
				System.out.println("No customer found with the given username");
			}

		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}

		return "redirect:/admin/customers";
	}
	
	@GetMapping("profileDisplay")
	public String profileDisplay(Model model) {
		String displayusername,displaypassword,displayemail,displayaddress;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava","root","");
			PreparedStatement stmt = con.prepareStatement("select * from users where username = ?"+usernameforclass+";");
			stmt.setString(1, usernameforclass);
			ResultSet rst = stmt.executeQuery();
			
			if(rst.next())
			{
			int userid = rst.getInt(1);
			displayusername = rst.getString(2);
			displayemail = rst.getString(3);
			displaypassword = rst.getString(4);
			displayaddress = rst.getString(5);
			model.addAttribute("userid",userid);
			model.addAttribute("username",displayusername);
			model.addAttribute("email",displayemail);
			model.addAttribute("password",displaypassword);
			model.addAttribute("address",displayaddress);
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		System.out.println("Hello");
		return "updateProfile";
	}
	
	@RequestMapping(value = "updateuser",method=RequestMethod.POST)
	public String updateUserProfile(@RequestParam("userid") int userid,@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("address") String address) 
	
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava","root","");
			
			PreparedStatement pst = con.prepareStatement("update users set username= ?,email = ?,password= ?, address= ? where uid = ?;");
			pst.setString(1, username);
			pst.setString(2, email);
			pst.setString(3, password);
			pst.setString(4, address);
			pst.setInt(5, userid);
			int i = pst.executeUpdate();	
			usernameforclass = username;
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		return "redirect:/index";
	}

}
