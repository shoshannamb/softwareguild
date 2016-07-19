<%-- 
    Document   : inputError
    Created on : Jul 10, 2016, 2:17:55 PM
    Author     : apprentice
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Input Error</title>
        <style>
            body {
                text-align: center;
                margin: auto;
            }
        </style>
    </head>
    <body>
        <h1>You entered something wrong!</h1>
        <image src="${pageContext.request.contextPath}/img/notAcceptable.jpg" width="600" height="480" />
        <h2><a href="${pageContext.request.contextPath}">Go Back to Unit Converter?</a></h2>
    </body>
</html>
