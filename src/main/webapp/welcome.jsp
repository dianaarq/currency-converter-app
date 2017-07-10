<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Create an account</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>
<body>
<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>

    </c:if>

    <div>
        <c:if test="${not empty rates}">
            <table>
                <tr class="tabletitle">

                    <td>Base</td>
                    <td>Currency</td>
                    <td>Change</td>
                    <td>Date</td>

                </tr>
                <c:forEach var="listValue" items="${rates}">
                    <tr>

                        <td>${listValue.base}</td>
                        <td>${listValue.currency}</td>
                        <td>${listValue.exchange}</td>
                        <td>${listValue.date}</td>

                    </tr>
            </c:forEach>

            </table>
        </c:if>
    </div>

</div>
<!-- /container -->

<div class="container">

<form:form method="POST" modelAttribute="currencyForm" class="form-signin">

       <h2 class="form-heading">Select currency and date</h2>
    <spring:bind path="base">
        <div class="form-group ${error != null ? 'has-error' : ''}">

            <span>${message}</span>
            <form:select type="base" path="base" class="form-control" placeholder="Currency base">
                <option value="CAD">Canadian Dollar</option>
                <option value="USD">Dollar</option>
                <option value="EUR">Euro</option>
                <option value="AUD">Australian Dollar</option>
                <option value="PLN">Zloty</option>
                <option value="MXN">Mexico Peso</option>
                <option value="GBP">British Pound</option>
                <option value="NZD">New Zealand Dollar</option>
                <option value="JPY">Japanese Yen</option>
                <option value="HUF">Hungarian Forint</option>
            </form:select>
        </div>
    </spring:bind>

    <spring:bind path="currency">
        <div class="form-group ${error != null ? 'has-error' : ''}">

            <span>${message}</span>
            <form:select type="currency" path="currency" class="form-control" placeholder="Currency">
                <option value="EUR">Euro</option>
                <option value="AUD">Australian Dollar</option>
                <option value="CAD">Canadian Dollar</option>
                <option value="PLN">Zloty</option>
                <option value="USD">Dollar</option>
                <option value="MXN">Mexico Peso</option>
                <option value="GBP">British Pound</option>
                <option value="NZD">New Zealand Dollar</option>
                <option value="JPY">Japanese Yen</option>
                <option value="HUF">Hungarian Forint</option>
            </form:select>
            <form:errors path="currency"></form:errors>
        </div>
    </spring:bind>

    <spring:bind path="date">
        <div class="form-group ${status.error ? 'has-error' : ''}">
            <form:input type="text" path="date" class="form-control" placeholder="Date: YYYY-MM-DD"
                        autofocus="true"></form:input>
            <form:errors path="date"></form:errors>
        </div>
    </spring:bind>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button class="btn btn-lg btn-primary btn-block" type="submit">OK</button>


</form:form>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
