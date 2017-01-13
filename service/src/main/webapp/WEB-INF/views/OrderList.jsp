<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <div align="center">
            <h1>Order List</h1>
            <h2><a href="">New User</a></h2>
             
            <table border="1">
            	<tr>
	                <th>No</th>
	                <th>Address</th>
	                <th>Notes</th>
	                <th>Created Date</th>
	                <th>Ordered Date</th>
	                <th>Actions</th>
                </tr>
                 
                <c:forEach var="order" items="${orderList}" varStatus="status">
	                <tr>
	                    <td>${status.index + 1}</td>
	                    <td>${order.address}</td>
	                    <td>${order.notes}</td>
	                    <td>${order.created}</td>
	                    <td>${order.orderDate}</td>
	                    <td>
	                        <a href="">Edit</a>
	                        &nbsp;&nbsp;&nbsp;&nbsp;
	                        <a href="">Delete</a>
	                    </td>
	                </tr>
                </c:forEach>             
            </table>
        </div>
    </body>
</html>