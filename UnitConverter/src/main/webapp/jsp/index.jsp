<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Unit Converter</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/unitStyles.css" rel="stylesheet">
        <!-- Custom styles for this template -->

        <!-- SWC Icon -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/icon.png">


    </head>
    <body>
        <div class="container">
            <h1>Unit Converter</h1>
            <hr/>

            <sf:form action="convert" method="POST" modelAttribute="conversion">
                <div class="warning-text"><sf:errors path="startingValue" cssClass="error"></sf:errors></div>
                    <div class="row">
                        <div class="col-sm-4 col-sm-offset-4">
                            <select class="form-control" name="unitType" id="unitOptions">
                                <option selected value="no-selection">Pick a Measurement Type</option>
                                <option value="Time">Time</option>
                                <option value="Volume">Volume</option>
                                <option value="Mass">Mass</option>
                            </select>
                        </div>
                    </div>
                    <div id="conversionOptions">
                        <div class="form-group col-sm-3 col-sm-offset-2">
                        <sf:select class="form-control" id="startingUnits" path="startingUnits">
                        </sf:select>

                    </div>
                    <div class="col-sm-2">
                        <span class="glyphicon glyphicon-chevron-right"></span>
                        <span class="glyphicon glyphicon-chevron-right"></span>
                        <span class="glyphicon glyphicon-chevron-right"></span>
                    </div>
                    <div class="col-sm-3 form-group">
                        <sf:select class="form-control" id="endingUnits" path="endingUnits">
                        </sf:select>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-4 col-sm-offset-4">
                            <sf:input type="text" class="form-control" id="inputBox" path="startingValue"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-2 col-sm-offset-5">
                            <input type="submit" class="btn btn-primary form-control" value="Calculate">
                        </div>
                    </div>
                </div>
            </sf:form>


        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/unitConverter.js"></script>

    </body>
</html>

