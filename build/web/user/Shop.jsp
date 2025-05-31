<%@page import="java.util.List"%>
<%@page import="Model.Product"%>
<%@page import="Model.Category"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Shop</title>
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="../Style/css/style.css">
    <link rel="stylesheet" href="../Style/css/User/shop.css">
</head>
<body class="bg-light">

<%@ include file="Header.jsp" %>

<div class="container-sm bg-white shadow-sm rounded my-4 p-4" id="main">

    <form class="searchBar container-sm bg-white shadow-sm mt-3 mb-1 mx-auto rounded-pill border" action="Shop" method="get">
        <%
            String productName = (String) request.getAttribute("productName");
            if (productName == null) productName = "";
        %>
        <input type="text" placeholder="Product Name / ID" name="productName" value="<%= productName %>">
        <input type="submit" value="Search">
    </form>

    <div class="d-flex container-sm p-sm-0 py-2 ps-1 py-sm-3">
        <%
            String categoryID = request.getParameter("categoryID");
            boolean isAll = (categoryID == null || categoryID.equals("all"));
        %>
        <a href="Shop?categoryID=all" class="btn-<%= isAll ? "primary" : "secondary" %> btn btn-sm me-1 ms-2">All</a>

        <%
            List<Category> categories = (List<Category>) request.getAttribute("categories");
            if (categories != null) {
                for (Category category : categories) {
                    boolean isSelected = (categoryID != null && categoryID.equals(String.valueOf(category.getId())));
        %>
            <a href="Shop?categoryID=<%= category.getId() %>" class="btn-<%= isSelected ? "primary" : "secondary" %> btn btn-sm me-1 ms-2">
                <%= category.getText() %>
            </a>
        <%
                }
            }
        %>
    </div>

    <%
        List<Product> products = (List<Product>) request.getAttribute("products");
        if (products != null) {
            for (Product product : products) {
    %>
        <div class="container-sm mx-auto bg-white shadow-sm rounded row productContainer p-lg-2 mb-4 border">
            <div class="col-sm-5 d-flex justify-content-center align-items-center">
                <img class="productImg" src="<%= (product.getImgpath() == null) ? "../Img/Product/blank.jpg" : product.getImgpath() %>" alt="">
            </div>
            <div class="col-sm-7 d-flex flex-column justify-content-between">
                <div>
                    <h2><%= product.getName() %></h2>
                    <p><%= product.getDescription() %></p>
                    <div class="d-flex mt-2">
                        <%
                            List<Category> prodCategories = product.getCategories();
                            if (prodCategories != null) {
                                for (Category cat : prodCategories) {
                        %>
                            <p class="badge rounded-pill text-bg-<%= cat.getTagcolor() %> me-1 my-1"><%= cat.getText() %></p>
                        <%
                                }
                            }
                        %>
                    </div>
                </div>
                <div class="justify-content-between d-flex">
                    <div class="d-flex flex-column py-3">
                        <h6>RM <%= String.format("%.2f",product.getSellingprice()) %></h6>
                        <small><i class="text-gray">Date Released: <%= product.getDateRelease() %></i></small>
                    </div>
                    <div class="d-flex justify-content-center align-items-center">
                        <a class="btn btn-sm btn-outline-primary" href="ProductDetails?productID=<%= product.getId() %>">More</a>
                    </div>
                </div>
            </div>
        </div>
    <%
            }
        }
    %>

</div>
</body>
</html>
