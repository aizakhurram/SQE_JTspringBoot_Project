package com.jtspringproject.JtSpringProject.controller;

import com.jtspringproject.JtSpringProject.models.Cart;
import com.jtspringproject.JtSpringProject.models.Product;
import com.jtspringproject.JtSpringProject.models.User;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.jtspringproject.JtSpringProject.services.cartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.jtspringproject.JtSpringProject.services.userService;
import com.jtspringproject.JtSpringProject.services.productService;
import com.jtspringproject.JtSpringProject.services.cartService;



@Controller
public class UserController{
	
	@Autowired
	public userService userService;

	@Autowired
	private productService productService;
	String usernameforclass = "";
	int useridforclass;
	@GetMapping("/register")
	public String registerUser()
	{
		return "register";
	}
	@GetMapping("/updateProfile")
	public String updateUser() {
		return "updateProfile";
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
	@GetMapping("/buy")
	public String buy()
	{
		return "buy";
	}
	@RequestMapping(value = {"/","/logout"})
	public String returnIndex() throws SQLException {
		//change password to your own database password
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava", "root", "tayyab3037");
		String deleteProductCartQuery = "DELETE FROM Product_cart";

		try (PreparedStatement deleteStmt = con.prepareStatement(deleteProductCartQuery)) {
			// Execute the delete statement
			int rowsAffected = deleteStmt.executeUpdate();
		}
		return "userLogin";
	}

//	@GetMapping("/")
//	public String userlogin(Model model) {
//
//		return "userLogin";
//	}
	@RequestMapping(value = "userloginvalidate", method = RequestMethod.POST)
	public ModelAndView userlogin( @RequestParam("username") String username, @RequestParam("password") String pass,Model model,HttpServletResponse res) {
		
		System.out.println(pass);
		User u = this.userService.checkLogin(username, pass);
		System.out.println(u.getUsername());
		if(u.getRole() != null) {	
			if (u.getUsername().equals("admin")){
				return new ModelAndView("redirect:/admin/login");
			}
			res.addCookie(new Cookie("username", u.getUsername()));

			usernameforclass=u.getUsername();
			useridforclass=u.getId();

			ModelAndView mView  = new ModelAndView("index");	
			mView.addObject("user", u);
			List<Product> products = this.productService.getProducts();

			if (products.isEmpty()) {
				mView.addObject("msg", "No products are available");
			} else {
				mView.addObject("products", products);
			}
			return mView;

		}else {
			ModelAndView mView = new ModelAndView("userLogin");
			mView.addObject("msg", "Please enter correct email and password");
			return mView;
		}
		
	}
	
	
	@GetMapping("/user/products")
	public ModelAndView getproduct() {

		ModelAndView mView = new ModelAndView("uproduct");

		List<Product> products = this.productService.getProducts();

		if(products.isEmpty()) {
			mView.addObject("msg","No products are available");
		}else {
			mView.addObject("products",products);
		}

		return mView;
	}
	@RequestMapping(value = "newuserregister", method = RequestMethod.POST)
	public ModelAndView newUseRegister(@ModelAttribute User user) {

		boolean exists = this.userService.checkUserExists(user.getUsername());

		if(!exists) {
			System.out.println(user.getEmail());
			user.setRole("ROLE_NORMAL");
			this.userService.addUser(user);

			System.out.println("New user created: " + user.getUsername());
			ModelAndView mView = new ModelAndView("userLogin");
			return mView;
		} else {
			System.out.println("New user not created - username taken: " + user.getUsername());
			ModelAndView mView = new ModelAndView("register");
			mView.addObject("msg", user.getUsername() + " is taken. Please choose a different username.");
			return mView;
		}
	}

	@GetMapping("/profileDisplay")
	public String profileDisplay(Model model) {
		String displayusername,displaypassword,displayemail,displayaddress;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava","root","zodiac");
			PreparedStatement stmt = con.prepareStatement("select * from CUSTOMER where username = ?"+";");
			stmt.setString(1, usernameforclass);
			ResultSet rst = stmt.executeQuery();

			if(rst.next())
			{
				int userid = rst.getInt(1);

				displayusername = rst.getString(6);
				displayemail = rst.getString(3);
				displaypassword = rst.getString(4);
				displayaddress = rst.getString(2);
				System.out.println("Hello user "+displayusername+" "+displayemail+" "+displaypassword+" "+displayaddress);
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
		return "profile";
	}

	@RequestMapping(value = "updateuser",method=RequestMethod.POST)
	public ModelAndView updateUserProfile(@RequestParam("userid") String userid,@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("address") String address)

	{
		try
		{
			//System.out.println(username+" "+email+" "+password+" "+address+" "+useridforclass);

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava","root","hello123");

			PreparedStatement pst = con.prepareStatement("update CUSTOMER set username= ?,email = ?,password= ?, address= ? where id = ?;");
			System.out.println(username+" "+email+" "+password+" "+address+" "+userid);
			pst.setString(1, username);
			pst.setString(2, email);
			pst.setString(3, password);
			pst.setString(4, address);
			pst.setInt(5, useridforclass);
			//System.out.println("After setting useridforclass: " + useridforclass);
			int i = pst.executeUpdate();
			usernameforclass = username;

		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		ModelAndView mv= new ModelAndView("index");
		return mv;
	}




	//for Learning purpose of model
		@GetMapping("/test")
		public String Test(Model model)
		{
			System.out.println("test page");
			model.addAttribute("author","jay gajera");
			model.addAttribute("id",40);
			
			List<String> friends = new ArrayList<String>();
			model.addAttribute("f",friends);
			friends.add("xyz");
			friends.add("abc");
			
			return "test";
		}
		
		// for learning purpose of model and view ( how data is pass to view)
		
		@GetMapping("/test2")
		public ModelAndView Test2()
		{
			System.out.println("test page");
			//create modelandview object
			ModelAndView mv=new ModelAndView();
			mv.addObject("name","jay gajera 17");
			mv.addObject("id",40);
			mv.setViewName("test2");
			
			List<Integer> list=new ArrayList<Integer>();
			list.add(10);
			list.add(25);
			mv.addObject("marks",list);
			return mv;
			
			
		}

	//Add to Cart Button 
		@RequestMapping(value = "index", method = RequestMethod.POST)
	public String addToCart(@RequestParam("product_id") String productId) {
		System.out.println("Product ID added to cart: " + productId);

		try {
			// Change password to your own database password
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava", "root", "tayyab3037");
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM product WHERE product_id = ?");

			stmt.setString(1, productId);
			ResultSet rst = stmt.executeQuery();

			// Data retreival
			if (rst.next()) {
				int productIdFromDB = rst.getInt("product_id");
				String productName = rst.getString("name");
				String description = rst.getString("description");
				double price = rst.getDouble("price");
				String imageFromDB = rst.getString("image");
				int quantityFromDB = rst.getInt("quantity");
				int weightFromDB = rst.getInt("weight");
				int categoryIdFromDB = rst.getInt("category_id");
				int customerIdFromDB = rst.getInt("customer_id");


				System.out.println("Product ID: " + productIdFromDB);
				System.out.println("Product Name: " + productName);
				System.out.println("Description: " + description);
				System.out.println("Price: " + price);

				// Data insertion
				String insertQuery = "INSERT INTO Product_cart (product_id, description, name, price, image, quantity, weight, category_id, customer_id) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

				try (PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {
					insertStmt.setInt(1, productIdFromDB);
					insertStmt.setString(2, description);
					insertStmt.setString(3, productName);
					insertStmt.setDouble(4, price);
					insertStmt.setString(5, imageFromDB);
					insertStmt.setInt(6, quantityFromDB);
					insertStmt.setInt(7, weightFromDB);
					insertStmt.setInt(8, categoryIdFromDB);
					insertStmt.setInt(9, customerIdFromDB);

					// Execute the insert statement
					int rowsAffected = insertStmt.executeUpdate();


				} catch (SQLException ex) {
					throw new RuntimeException(ex);
				}
			}
			

		} catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "cartproduct";
		}

	//Delete Button implementation on cartproduct.jsp page
		@PostMapping("/deleteCartItem")
		public String deleteCartItem(@RequestParam("id") int cartItemId) {
			try {
				//Change password to your own database password
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommjava","root","tayyab3037");
				String deleteQuery = "DELETE FROM product_cart WHERE product_id = ?";
				PreparedStatement stmt = con.prepareStatement(deleteQuery);
				stmt.setInt(1, cartItemId);
				int rowsAffected = stmt.executeUpdate();
				System.out.println("Item deleted from cart. Cart Item ID: " + cartItemId);

				// Redirect back to the cartproduct page
				return "redirect:/cartproduct";
			} catch (Exception e) {
				
				System.out.println("Error deleting item from cart: " + e.getMessage());
				return "redirect:/cartproduct"; // Redirect back to the cartproduct page in case of an error
			}
		}


	@GetMapping("/cart")
	public String viewCart(Model model) {
		// Fetch products by IDs from the cartProductIds list


		// Add the cartProducts to the model


		// Return the cartproduct.jsp page
		return "cartproduct";
	}





	@GetMapping("/cartproduct")
	public String viewCartProduct() {
		return "cartproduct";
	}


//	@GetMapping("carts")
//	public ModelAndView  getCartDetail()
//	{
//		ModelAndView mv= new ModelAndView();
//		List<Cart>carts = cartService.getCarts();
//	}
	  
}
