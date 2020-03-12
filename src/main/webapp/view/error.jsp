<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto&display=swap">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Alfa+Slab+One|Sen&display=swap"> 


<style>
	<%@ include file="css/bootstrap.min.css"%>
	<%@ include file="css/styles.css"%>
</style>
 
</head>

<header>
	<nav class="navbar navbar-expand-lg navbar-light">
		<h4>Parser</h4>
	</nav>
</header>

<body>
		<div class="error-name">Error</div>
		<div class="error-code">
		${pageContext.errorData.statusCode}
		
		<c:if test="${pageContext.exception != null}">
		${pageContext.exception.message}
		</c:if>
	</div>
	<script>
	<%@ include file="js/bootstrap.min.js"%>
	<%@ include file="js/popper.min.js"%>
	<%@ include file="js/jquery.min.js"%>
	</script>
</body>
</html>
