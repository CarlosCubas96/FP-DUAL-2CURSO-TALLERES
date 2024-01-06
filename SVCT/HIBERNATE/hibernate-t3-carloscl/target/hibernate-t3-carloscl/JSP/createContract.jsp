<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="es">
<head>
<!-- Configuración del documento -->
<meta charset="UTF-8">
<title>Create Contract</title>

<!-- Enlace al archivo CSS de Bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
	<!-- Contenedor principal con margen superior -->
	<div class="container mt-5">
		<!-- Título principal -->
		<h1>Create Contract</h1>

		<!-- Formulario para la creación de contrato -->
		<form action="CreateContractServlet" method="post">
			<!-- Campo de entrada para el ID del cliente (solo lectura) -->
			<div class="form-group">
				<label for="clientId">Client ID:</label> <input type="text"
					class="form-control" id="clientId" name="clientId"
					value="${client.clientId}" readonly>
			</div>

			<!-- Campo de entrada para la fecha de inicio -->
			<div class="form-group">
				<label for="startDate">Start Date:</label> <input type="date"
					class="form-control" id="startDate" name="startDate" required>
			</div>

			<!-- Campo de entrada para la fecha de fin -->
			<div class="form-group">
				<label for="endDate">End Date:</label> <input type="date"
					class="form-control" id="endDate" name="endDate" required>
			</div>

			<!-- Campo de entrada para el precio mensual -->
			<div class="form-group">
				<label for="monthlyPrice">Monthly Price:</label> <input type="text"
					class="form-control" id="monthlyPrice" name="monthlyPrice" required>
			</div>

			<!-- Botón de envío del formulario -->
			<button type="submit" class="btn btn-primary">Create
				Contract</button>
		</form>
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
