<%@page import="Model.Category"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="../Style/css/style.css">
    <link rel="stylesheet" href="../Style/css/Admin/product.css">
    <title>Product</title>
</head>
<body class="adminBody">
        
    <%@ include file="SideMenu.jsp" %>
    <%@ include file="Header.jsp" %>

    <div class="page bg-light d-flex align-items-center justify-content-center">
        <div class="bg-white rounded shadow-sm container-xl maxPageHeight p-3">
            
            <h2 class="text-center shadow-sm p-2 rounded bg-light mb-3">Product</h2>
            
            <div class="d-flex justify-content-between align-items-center row">
                <div class=" mb-3 d-flex p-sm-0 order-2 order-sm-1 col-md-10 flex-wrap justify-content-between justify-content-sm-start">
                    <a href="Product?categoryID=all" 
                        class="btn-${ (param.categoryID == 'all' || empty param.categoryID) ? 'primary' : 'secondary' } btn btn-sm me-1 ms-2">
                        All
                     </a>

                     <c:forEach var="category" items="${categories}">
                         <a href="Product?categoryID=${category.id}" 
                            class="btn-${ (param.categoryID == category.id) ? 'primary' : 'secondary' } btn btn-sm me-1 ms-2">
                            ${category.text}
                         </a>
                     </c:forEach>

                </div>
                <div class="mb-3 order-1 order-sm-2 col-md-2 py-0 my-1">
                    <button class="btn btn-sm btn-outline-primary shadow-sm w-100 border shadow-sm" onclick="showContainer('addProductContainer')">Add</button>
                        <div class="popUpContainer align-items-center justify-content-center" id="addProductContainer">
                            <div class="popUpContent bg-light p-5 pt-2 rounded">
                                <h2 class="w-100 text-center">Add Product</h2>
                                <form class="shadow bg-white border rounded p-3 row" method="POST" action="Product" enctype="multipart/form-data">
                                    <div class="col-12 col-md-5 mb-4">
                                        <p class="text-center mb-2"><b>Product Image</b></p>
                                        <input type="file" name="productPicInput" id="productPicInput" accept="image/*" onchange="previewPhoto(event,'productImage')" style="display: none;">
                                        <img class="w-100 d-flex mx-auto cursor-pointer shadow-sm border" id="productImage"
                                             src="../Img/Product/blank.jpg" 
                                                alt="Product Image" 
                                                onclick="triggerFileInput('productPicInput')">
                                        
                                    </div>
                                    <div class="col-12 col-md-7 d-flex flex-column justify-content-between">
                                        <div>
                                            <p><b>Product Name</b></p>
                                            <input class="w-100" type="text" name="name" required>
                                            <small class="text-gray w-100"><i>This field is for the product showing name.</i></small><br>
                                            <!--<small><i class="text-danger"><?php echo isset($error['details'])?$error['details']:""?></i></small>-->
                                            <p class="mt-4"><b>Product Details</b></p>
                                            <textarea class="border w-100 mt-2" name="details" required></textarea>
                                            <small class="text-gray w-100"><i>This field is for the describing the details of the product.</i></small><br>
                                            
                                            <p class="mt-4"><b>Product Category</b></p>
                                            <div class="d-flex flex-wrap align-items-center justify-content-between border rounded p-2 mt-2">
                                                <c:forEach var="category" items="${categories}">
                                                    <label class="d-flex align-items-center">
                                                       <input type="checkbox" name="categories" value="${category.id}" class="ui-checkbox">
                                                       ${category.text}
                                                    </label>
                                                </c:forEach>
                                                
                                            </div>
                                            <small class="text-gray w-100"><i>Please select the related categories for the product.</i></small><br>
                                            
                                            <p class="mt-4"><b>Product Selling Price</b></p>
                                            <input class="w-100" type="text" name="sellingPrice" required>
                                            <small class="text-gray w-100"><i>This field will be the selling price for the product.</i></small><br>
                                            
                                            <p class="mt-4"><b>Product Supply Price</b></p>
                                            <input class="w-100" type="text" name="supplyPrice" required> 
                                            <small class="text-gray w-100"><i>The price that supplier sells.</i></small><br>
  
                                        </div>
                                        
                                        <div class="mt-4">
                                            <input  class="btn btn-sm btn-primary w-100" type="submit" name="add" value="Add Product">
                                            <a href="Product?categoryID=all" class="btn btn-sm btn-dark w-100 mt-2">Cancel</a>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                </div>
            </div>
            

            <!--Search Bar-->
            <form class="searchBar container-sm bg-white shadow-sm mb-1 mx-auto rounded-pill border" action="" method="get">
                <input type="text" placeholder="Product Name" name="productName" value="<%= (request.getAttribute("productName")==null?"":request.getAttribute("productName"))%>">
                <input type="submit" value="Search">
            </form>
            
            <p class="text-gray text-center my-2"><small><i>(${products.size()} Records Found)</i></small></p>

            <div id="productMainContainer">
                <c:forEach var="product" items="${products}">
                    <div class="row p-2 shadow-sm rounded border mx-1 mb-2">

                        <div class="col-sm-3 d-flex">
                            <div class="d-flex align-items-center ">
                                <img src="${product.imgpath==null?"../Img/Product/blank.jpg":product.imgpath}" alt="" class="iconImg">
                            </div>

                            <div class="d-flex flex-column justify-content-center ms-3 text-start text-gray">
                                <h6 class="m-0">${product.name}</h6>
                                <p><small>${product.getDateRelease()}</small></p>
                            </div>
                        </div>

                        <div class="col-md-3 d-flex align-items-center flex-wrap">
                            <c:forEach var="category" items="${product.getCategories()}">
                                <p class="badge rounded-pill text-bg-${category.tagcolor} me-1 my-1">${category.text}</p>
                            </c:forEach>
                        </div>

                        <div class="col-md-2 d-flex align-items-center">
                            <h6 class="m-0 ms-1">RM ${product.sellingprice}</h6>
                            <p class="ms-1 d-md-none d-lg-block"><small><i>(per unit)</i></small></p>
                        </div>

                        <div class="col-md-2 d-flex align-items-center">
                            <p class="ms-1">Stock :</p>
                            <h6 class="m-0 ms-1">${product.totalQty}</h6>
                            <p class="ms-1 d-md-none d-lg-block"><small><i>(unit)</i></small></p>
                        </div>

                        <div class="col-md-1 d-flex align-items-center justify-content-between">
                            <a href='Review?productID=${product.id}' class="btn btn-sm btn-outline-primary w-100 my-1 my-sm-0">Review</a>
                        </div>
                        <div class="col-md-1 d-flex align-items-center justify-content-between">
                            <a href='ProductDetails?productID=${product.id}' class="btn btn-sm btn-outline-primary w-100 my-1 my-sm-0">More</a>
                        </div>

                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <script src="../Style/js/js.js" type="text/javascript"></script>
</body>
</html>