<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.*"%>
<%@page import ="java.io.FileOutputStream" %>
<%@page import=" java.io.ObjectOutputStream" %>

<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
	<meta charset="UTF-8">
	<meta name="viewport"
		  content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="stylesheet"
		  href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
		  integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
		  crossorigin="anonymous">

	<title>Document</title>
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<div class="container-fluid">
		<a class="navbar-brand" href="#"> <img
				th:src="@{/images/logo.png}" src="../static/images/logo.png"
				width="auto" height="40" class="d-inline-block align-top" alt="" />
		</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto"></ul>
			<ul class="navbar-nav">
				<li class="nav-item active">
					<a class="nav-link" href="/index">Home Page</a>
				</li>
				<li class="nav-item active"><a class="nav-link" href="/logout">Logout</a>
				</li>

			</ul>

		</div>
	</div>
</nav><br>
<div class="container-fluid">

	<a style="margin: 20px 0" class="btn btn-primary"
	   href="/user/products">Add Product</a><br>
	<table class="table">
		<tr>
			<th scope="col">Product ID</th>
			<th scope="col">Product Name</th>
			<th scope="col">Price</th>
			<th scope="col">Description</th>
			<th scope="col">Delete</th>
		</tr>
		<tbody>
		<%
			try {
				String url = "jdbc:mysql://localhost:3306/ecommjava";
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection(url, "root", "tayyab3037");
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("select * from Product_cart");
				while (rs.next()) {
		%>
		<tr>
			<td><%= rs.getInt("product_id") %></td>
			<td><%= rs.getString("name") %></td>
			<td><%= rs.getString("price") %></td>
			<td><%= rs.getString("description") %></td>
			<td>
				<form action="deleteCartItem" method="post">
					<input type="hidden" name="id" value="<%= rs.getInt("product_id") %>">
					<input type="submit" value="Delete" class="btn btn-danger">
				</form>

			</td>
		</tr>
		<%
				}
			} catch (Exception ex) {
				out.println("Exception Occurred:: " + ex.getMessage());
			}
		%>
		</tbody>
	</table>
</div>

<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
		integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBv
</script>
