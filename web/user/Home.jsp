<%@page import="Model.Product"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="../Style/css/style.css">
    <link rel="stylesheet" href="../Style/css/User/home.css">
    <title>Home</title>
</head>
<body class="bg-light">

    <%@ include file="Header.jsp" %>
    
    <div class="container-sm shadow-sm rounded my-4 p-0 bg-white" id="main">

        <!--Hero-->
        <div class="rounded shadow-sm w-100" >
            <div id="hero" class="rounded shadow-sm">
                <div id="heroDetailsContainer" class="p-5 text-white rounded">
                    <h2 class="mb-5">Fashion & Apparel</h2>
                    <p>Welcome to <%= application.getInitParameter("companyName") %>, where style meets convenience!</p>
                    <p>We are your premier online destination for the latest fashion trends, offering a wide range of clothing, footwear, and accessories for men, women, and children.</p> 
                    <a href="Shop?categoryID=all" class="btn btn-sm rounded-pill shadow-sm mt-5 btn-dark px-4 py-1">Shop Now</a>
                </div>
            </div>
            
            <div>
                <h2 class="text-center py-2">About Us</h2>
                <p class="text-center p-5 pt-2">At <%= application.getInitParameter("companyName") %>, we believe fashion is more than just clothing — it's a powerful form of self-expression, confidence, and individuality. Born from a vision to create a fashion destination that empowers people to dress boldly and authentically, <%= application.getInitParameter("companyName") %> is your one-stop online store for contemporary, stylish, and versatile apparel for both men and women.

                                                From timeless wardrobe staples to the season’s latest trends, <%= application.getInitParameter("companyName") %> offers a thoughtfully curated collection of tops, bottoms, and essential fashion pieces that blend quality, comfort, and design. Whether you’re dressing up for a night out, putting together a casual everyday look, or refreshing your seasonal wardrobe, our collection is crafted to suit every occasion, every personality, and every body.

                                                We pride ourselves on being more than just another fashion store. <%= application.getInitParameter("companyName") %> is a community — a movement — that celebrates personal style, body positivity, and diversity. Our pieces are selected not just for their aesthetic appeal, but for how they make you feel when you wear them. We work with trusted suppliers and designers who share our commitment to premium fabrics, flattering fits, and responsible production practices.

                                                Customer satisfaction is at the heart of everything we do. From the moment you land on our website to the day your package arrives at your door, we aim to deliver a seamless, enjoyable, and inspiring shopping experience. With detailed product descriptions, helpful sizing guides, and fast, reliable delivery, we make online fashion shopping simple and stress-free.

                                                <%= application.getInitParameter("companyName") %> is driven by a mission to empower you to dress your best and feel your best — every single day. Whether you’re searching for a classic look or something bold and trend-setting, we’re here to help you make a statement without saying a word.</p>
            </div>
            <hr class="w-100">
            <div class="p-5 pt-2">
                <h2 class="text-center py-2">Top Sold</h2>
                <!--Search Bar-->
                <form class="searchBar container-sm bg-white shadow-sm mt-3 mb-1 mx-auto rounded-pill border" action="Shop" method="get">
                   <input type="text" placeholder="Product Name / ID" name="productName" value="${productName}">
                   <input type="submit" value="Search">
                </form>
                   <div class="row d-flex align-items-center justify-content-between mt-3">
                <%
                    List<Product> topSoldProducts = (List<Product>) request.getAttribute("topSoldProducts");
                    if(topSoldProducts.isEmpty()){
                %>
                        <p class="text-gray text-center"><i>(Currently has no product sold)</i></p>
                <%
                    }else{
                        int index = 0;
                        for(Product product : topSoldProducts){
                            if(index > 2) {break;} //Show only 3
                %>
                        <div class="col col-sm-3 rounded shadow-sm bg-light p-0">
                            <img src="<%=product.getImgpath()%>" class="topSoldProductImg w-100">
                            <div class="p-3">
                            <h3 class="text-center"><%=product.getName()%></h3>
                            <p class="text-center"><b>RM <%=product.getSellingprice()%></b></p>
                            <a href="ProductDetails?productID=<%=product.getId()%>" class="btn btn-sm btn-dark w-100 mt-3">More</a>
                            </div>
                        </div>
                <%          index++;
                        }
                    }%>
                   </div>
            </div>
    </div>
        
</body>
</html>
