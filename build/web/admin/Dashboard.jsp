<%-- 
    Document   : Dashboard
    Created on : 27 Apr 2025, 9:05:21 pm
    Author     : LIM CUAN HEI
--%>

<%@page import="Model.Product"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../Style/css/bootstrap/bootstrap.css">
        <link rel="stylesheet" href="../Style/css/style.css">
        <link href="../Style/css/Admin/dashboard.css" rel="stylesheet" type="text/css"/>
        <title>Dashboard</title>
    </head>
    <body class="bg-light adminBody">
        <%@ include file="SideMenu.jsp" %>
        <%@ include file="Header.jsp" %>
        <div class="page bg-light d-flex align-items-center justify-content-center">
            <div class="bg-white rounded shadow-sm maxPageHeight p-3 container-lg">
                <h2 class="text-center shadow-sm p-2 rounded bg-light mb-3">Dashboard</h2>
                <div class="d-flex justify-content-between">
                    <div class="d-flex align-items-center">
                       <a href="Dashboard?reportType=Yearly" class="btn btn-sm btn-${reportType.equals("Yearly")?"primary":"secondary"} me-1">Yearly</a>
                       <a href="Dashboard?reportType=Monthly" class="btn btn-sm btn-${reportType.equals("Monthly")?"primary":"secondary"} me-1">Monthly</a>
                       <a href="Dashboard?reportType=Daily" class="btn btn-sm btn-${reportType.equals("Daily")?"primary":"secondary"} me-1">Daily</a>
                    </div>
                    <button class="btn btn-outline-primary shadow-sm border" onclick="showContainer('topSoldContainer')">Top Product Report</button>
                    <div class="popUpContainer align-items-center justify-content-center" id="topSoldContainer">
                        <div class="popUpContent bg-light p-5 rounded bg-white w-80">
                             <h2 class="text-center shadow-sm p-2 rounded bg-light mb-3">Top Selling Product</h2>
                             <div>
                                <%
                                    List<Product> topProducts = (List<Product>) request.getAttribute("topSoldProducts");
                                    if (topProducts != null) {
                                        int i = 0;
                                        for (Product product : topProducts) {
                                        if(i>=9){break;} i++;
                                %>
                                <div class="shadow-sm rounded border d-flex align-items-center justify-content-between p-3 mb-2" >
                                    <div class="d-flex align-items-center">
                                        <img src="<%=product.getImgpath()==null?"../Img/Product/blank.jpg":product.getImgpath()%>" class="iconImg me-3"/>
                                        <p><b><%=product.getName()%></b></p>
                                    </div>
                                        <p>Product Sold : <b><%=product.getTotalSold()%></b><i> (units)</i></p>
                                    <div>
                                        <a href="ProductDetails?productID=<%=product.getId()%>" class="btn btn-sm btn-outline-primary">More</a>
                                    </div>
                                </div>
                                <%}
                                }%>
                             </div>
                             <button class="btn btn-sm w-100 btn-dark mt-3" onclick="toggleContainer('topSoldContainer')">Close</button>
                        </div>
                    </div>
                </div>
                <canvas id="statisticContainer"></canvas>
            </div>
    <script>
    const ctx = document.getElementById('statisticContainer').getContext('2d');
    const myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: [
                <% 
                    List<Object[]> result = (List<Object[]>) request.getAttribute("result");
                    if (result != null && !result.isEmpty()) {
                        for (int i = 0; i < result.size(); i++) {
                            Object[] row = result.get(i);
                            out.print("\"" + row[0] + "\"");
                            if (i < result.size() - 1) {
                                out.print(", ");
                            }
                        }
                    }
                %>
            ],
            datasets: [{
                label: '${reportType} Sales',
                data: [
                    <%
                        if (result != null && !result.isEmpty()) {
                            for (int i = 0; i < result.size(); i++) {
                                Object[] row = result.get(i);
                                out.print(row[1]);
                                if (i < result.size() - 1) {
                                    out.print(", ");
                                }
                            }
                        }
                    %>
                ],
                borderColor: 'rgba(75, 192, 192, 1)',
                fill: false
            }]
        },
        options: {
            responsive: true,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
</script>
<script src="../Style/js/js.js" type="text/javascript"></script>
    </body>
</html>
