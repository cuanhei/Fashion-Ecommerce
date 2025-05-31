<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="../Style/css/style.css">
    <link rel="stylesheet" href="../Style/css/Admin/productDetails.css">
    <title>Product Details</title>
</head>
<body class="adminBody bg-light">
    
    <%@ include file="SideMenu.jsp" %>
    <%@ include file="Header.jsp" %>
    
    <div class="page bg-light d-flex align-items-center justify-content-center">
        <div class="bg-white rounded shadow-sm container-xl maxPageHeight p-3">
            <h2 class="text-center shadow-sm p-2 rounded bg-light mb-3">Product Details</h2>
            <form class="row p-4" method="post" action="ProductDetails" enctype="multipart/form-data" onsubmit="return confirmSubmission('Are you sure you want to make changes on this product?')">
                <input type="hidden" name="purpose" value="update">
                <input type="hidden" name="productID" value="${product.id}">
                <div class="col-sm-5 d-flex flex-column justify-content-between">
                    <div class="d-flex align-items-center justify-content-center mb-3">
                        <input type="file" name="productPicInput" id="productPicInput" accept="image/*" onchange="previewPhoto(event,'productImage')" style="display: none;">
                        <img src="${product.imgpath==null?"../Img/Product/blank.jpg":product.imgpath}" alt="" 
                             class="reviewImg shadow cursor-pointer" 
                             onclick="triggerFileInput('productPicInput')"
                             id="productImage">
                    </div>

                    <!-- <button class="btn btn-primary btn-sm w-100">Add 3D File</button> -->
<!--                    <div class="border rounded d-flex p-2 align-items-center w-100 shadow-sm">
                        <img src="../../Img/Icon/customer.png" alt="" class="iconImg">
                        <p class="w-100 text-center"><b>3D model 5.gltx</b></p>
                        <button class="btn btn-sm btn-outline-danger">Remove</button>
                    </div>-->
                </div>
                <div class="col-sm-7 d-flex flex-column justify-content-between mt-5 mt-sm-0">
                    <input type="text" name="name" value="${product.name}" placeholder="Product Name">
                    <textarea name="details" id="" placeholder="Details" class="border mt-3">${product.description}</textarea>
                    <div class="d-flex flex-sm-row flex-column align-items-sm-center border rounded py-3 px-3 mt-3">
                        <c:forEach var="category" items="${categories}">
                            <div class="d-flex w-100">
                                <input type="checkbox" class="ui-checkbox" name="category" value="${category.id}" ${product.getCategories().contains(category)?"checked":""}>
                                <label class="ms-1 text-gray"><p>${category.text}</p></label>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="w-100 mt-3">
                        <h6 class="text-gray">RM</h6>
                        <input type="number" min="0" step="any" name="sellingPrice" value="${String.format("%.2f",product.sellingprice)}" id="" class="w-100 border">
                    </div>
                    
                    <div class="rounded border bg-light shadow-sm mt-3">
                        <p class="p-2 text-gray"><b><i>Supplier Purchasing Price</i></b></p>
                        <hr>
                        <div class="d-flex px-2 py-3 align-items-center">
                            <h6 class="text-gray pe-3 my-auto">RM</h6>
                            <input type="number" min="0" step="any" name="supplyPrice" value="${String.format("%.2f",product.supplierprice)}" id="" class="w-100 bg-light me-2 border">
                        </div>
                    </div>
                    <input type="submit" class="btn btn-primary btn-sm w-100 mt-2" name="update" value="Update">
                </div>
            </form>   

            <div class="border rounded px-2 py-4 row mx-4 d-flex justify-content-between">
                <div class="col-sm-6 d-flex flex-column justify-content-between">
                    <div class="d-flex">
                        <c:forEach var="productSize" items="${sizes}">
                            <button class="btn btn-light shadow-sm btn-sm me-1 sizeBtn border" onclick="showPurchaseForm('${productSize.id}')">${productSize.text}</button>
                        </c:forEach>
                    </div>
                    <div class="purcahseForm">
                        <p class="text-gray"><i>Please select the size to purchase.</i></p>
                    </div>
                    <c:forEach var="stockQty" items="${stockQuantities}">
                        <form method="post" action="ProductDetails" id="${stockQty.key.id}" class="purcahseForm" style="display: none">
                            <input type="hidden" name="purpose" value="purchaseStock">
                            <input type="hidden" name="sizeID" value="${stockQty.key.id}">
                            <input type="hidden" name="productID" value="${product.id}">
                            <p class="text-gray my-2"><i>(Size <b>${stockQty.key.text}</b> currently has <b>${stockQty.value}</b> units in stock)</i></p>
                            <p>Number of <b>${stockQty.key.text}</b> size unit(s) to order : </p>
                            <input type="number" name="quantity" min="0" class="border rounded w-100">
                            <input type="submit" class="btn btn-outline-primary btn-sm w-100 mt-2" value="Purchase">
                        </form>
                    </c:forEach>
                </div>
                <div class="col-sm-5 d-flex justify-content-between flex-column mt-4 mt-sm-0">
                    <table class="w-100 border shadow-sm">
                        <tr class="text-center bg-light">
                            <th><p>Size</p></th>
                            <th><p>Quantity</p></th>
                        </tr>
                        <tr>
                            <td colspan="2"><hr></td>
                        </tr>
                        <c:forEach var="stockQty" items="${stockQuantities}">
                            <tr class="text-center text-gray">
                                <td><p>${stockQty.key.text}</p></td>
                                <td><p>${stockQty.value}</p></td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="2"><hr></td>
                        </tr>
                        <tr class="text-center">
                            <th><p>Total</p></th>
                            <th><p>${product.totalQty}</p></th>
                        </tr>
                    </table>
                </div>
            </div>

            <form class="mt-4 mx-4" method="post" action="ProductDetails" onsubmit="return confirmSubmission('Are you sure you want to DELETE this product?')">
                <input type="hidden" name="purpose" value="delete">
                <input type="hidden" name="productID" value="${product.id}">
                <a href="Product?categoryID=all" class="btn btn-dark btn-sm w-100 mb-2">Cancel</a>
                <%if(loggedAcc.isManager()){%>
                <input type="submit" class="btn btn-outline-danger btn-sm w-100 " value="Delete">
                <%}%>
            </form>

        </div>
    </div>
    <script src="../Style/js/Admin/productDetails.js" type="text/javascript"></script>
    <script src="../Style/js/js.js" type="text/javascript"></script>
</body>
</html>