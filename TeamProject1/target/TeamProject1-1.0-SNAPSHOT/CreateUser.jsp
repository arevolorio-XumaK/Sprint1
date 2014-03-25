<%-- 
    Document   : CreateUser
    Created on : 25/03/2014, 08:47:03 AM
    Author     : xumakgt5
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <form action="createNewUser">
             User: <input type="text" name="user" required="required"><br>
             Password: <input type="password" name="pass" required="required"><br>
             Confirm Password: <input type="password" name="confPass" required="required"><br>
            <input type="submit" value="Crear">
        </form>
    </body>
</html>
