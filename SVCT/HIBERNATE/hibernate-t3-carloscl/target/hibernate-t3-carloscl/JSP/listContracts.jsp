<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="es">
<head>
<!-- Configuración del documento -->
<meta charset="UTF-8">
<title>List of Contracts</title>

<!-- Enlace al archivo CSS de Bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

	<!-- Contenedor principal con margen superior -->
	<div class="container mt-5">

		<!-- Título principal -->
		<h2>List of Contracts</h2>

		<!-- Formulario de búsqueda por DNI -->
		<form action="SearchContractServlet" method="get" class="mb-3">
			<div class="input-group">
				<input type="text" class="form-control" placeholder="Search by DNI"
					name="searchDNI">
				<div class="input-group-append">
					<button class="btn btn-outline-secondary" type="submit">Search</button>
				</div>
			</div>
		</form>

		<!-- Botones para volver y resetear contratos -->
		<div class="mt-3">
			<!-- Formulario para volver atrás -->
			<form action="ListClientServlet" method="get"
				style="display: inline;">
				<button class="btn btn-info float-left" type="submit">Go
					Back</button>
			</form>

			<form action="ListContractServlet" method="get"
				style="display: inline;">
				<button class="btn btn-secondary float-right" type="submit">Reset
					and Show All Contracts</button>
			</form>
		</div>

		<!-- Verifica si hay contratos disponibles -->
		<c:if test="${not empty contracts}">

			<!-- Tabla para mostrar la información de los contratos -->
			<table class="table">

				<!-- Descripción de la tabla -->
				<caption>List of Contracts</caption>

				<thead>
					<!-- Encabezados de la tabla -->
					<tr>
						<th>Contract ID</th>
						<th>Client ID</th>
						<th>Start Date</th>
						<th>Expiration Date</th>
						<th>Monthly Price</th>
					</tr>
				</thead>

				<!-- Itera sobre la lista de contratos y muestra la información en la tabla -->
				<tbody>
					<c:forEach var="contract" items="${contracts}">
						<tr>
							<!-- Datos de cada contrato -->
							<td>${contract.contractId}</td>
							<td>${contract.client.identityDocument}</td>
							<td>${contract.startDate}</td>
							<td>${contract.endDate}</td>
							<td>${contract.monthlyPrice}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>

		<!-- Mensaje para lista vacía -->
		<div class="mt-3">
			<c:if test="${empty contracts}">
				<p class="alert alert-info">No contracts available.</p>
			</c:if>
		</div>
	</div>

	<!-- Opcional: Agregar jQuery y Popper.js para la funcionalidad de Bootstrap -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
	<!-- Incluir Bootstrap JS -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>