<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- <link rel="stylesheet" -->
<!-- 	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"> -->
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
	<table class="table table-bordered text-center">
		<thead>
			<tr>
				<th scope="col">Flower</th>
				<th scope="col">Quantity</th>
				<th scope="col">Country</th>
				<th scope="col">Soil</th>
				<th scope="col">Multiplying</th>
				<th scope="col">Date</th>
				<th scope="col">Growing tip
					<table class="table inner-table text-center table-bordered">
						<tr>
							<th scope="col" class="inner-left-column">Name</th>
							<th scope="col" class="inner-right-column">Value</th>
						</tr>
					</table>
				</th>
				<th scope="col">Visual Parameters
					<table class="table inner-table text-center table-bordered">
						<tr>
							<th scope="col" class="inner-left-column">Parameter</th>
							<th scope="col" class="inner-right-column">Value</th>
						</tr>
					</table>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="flower" items="${flowers }">
				<td><c:out value="${flower.name }" /></td>
				<td><c:out value="${flower.quantity }" /></td>
				<td><c:out value="${flower.origin }" /></td>
				<td><c:out value="${flower.soil }" /></td>
				<td><c:out value="${flower.multiplying }" /></td>
				<td><fmt:formatDate value="${flower.plantingDate.time}"
						pattern="dd/MM/yyyy" /></td>


				<td><c:forEach var="tip" items="${flower.tips }">
						<table class="table inner-table text-center table-bordered">
							<tr>
								<td class="inner-left-column"><c:out value="${tip.name }" /></td>

								<td class="inner-right-column"><c:out value="${tip.value }" /></td>

							</tr>
						</table>
					</c:forEach></td>

				<td><c:forEach var="parameter" items="${flower.parameters }">
						<table class="table inner-table text-center table-bordered">
							<tr>
								<td class="inner-left-column"><c:out value="${parameter.parameter }" /></td>

								<td class="inner-right-column"><c:out value="${parameter.value }" /></td>

							</tr>
						</table>
					</c:forEach></td>
			</c:forEach>
		</tbody>
	</table>
	
		<script>
	<%@ include file="js/jquery-3.3.1.min.js"%>
		
	<%@ include file="js/bootstrap.min.js"%>
		
	<%@ include file="js/popper.min.js"%>
	
	<%@ include file="js/main.js"%>
	
	</script>

<!-- 	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script> -->
</body>
</html>
