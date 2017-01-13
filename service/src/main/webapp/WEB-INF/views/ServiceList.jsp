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
            <h1>Service List</h1>
            <h2><a href="new">New User</a></h2>
             
            <table border="1">
            	<tr>
	                <th>No</th>
	                <th>Name</th>
	                <th>Main Question</th>
					<th>Extra Question</th>
					<th>Main Selections</th>
					<th>Extra Selections</th>
					<th>Action</th>
                </tr>
                <c:forEach var="serviceItem" items="${serviceList}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${serviceItem.name}</td>
                    <td>${serviceItem.mainQuestion}</td>
                    <td>${serviceItem.extraQuestion}</td>     
                    <td>
	                   	<c:forEach var="mainSelection" items="${serviceItem.mainSelections}" varStatus="status">
	                    	<p>${mainSelection.name}</p>
	                    </c:forEach>
                    </td>
					
					<td>
	                   	<c:forEach var="extraSelection" items="${serviceItem.extraSelections}" varStatus="status">
	                    	<p>${extraSelection.name}</p>
	                    </c:forEach>
                    </td>           
                    <td>
                        <a href="edit?id=${user.id}">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="delete?id=${user.id}">Delete</a>
                    </td>
                </tr>
                </c:forEach>             
            </table>
        </div>
    </body>
</html>