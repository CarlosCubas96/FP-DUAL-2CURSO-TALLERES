<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Update Client</title>
<!-- Link to Bootstrap CSS file -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
	<div class="container mt-5">
		<h1>Update Client</h1>

		<!-- Formulario para actualizar cliente -->
		<form action="EditClientServlet" method="post">
			<!-- Campo para mostrar y no editar el ID del cliente -->
			<div class="form-group">
				<label for="clientId">Client ID:</label> <input type="text"
					class="form-control" id="clientId" name="clientId"
					value="${client.clientId}" readonly>
			</div>
			<!-- Campo para actualizar el primer nombre -->
			<div class="form-group">
				<label for="firstName">First Name:</label> <input type="text"
					class="form-control" id="firstName" name="firstName"
					value="${client.firstName}" required>
			</div>
			<!-- Campo para actualizar el apellido -->
			<div class="form-group">
				<label for="lastName">Last Name:</label> <input type="text"
					class="form-control" id="lastName" name="lastName"
					value="${client.lastName}" required>
			</div>
			<!-- Campo para actualizar el segundo apellido -->
			<div class="form-group">
				<label for="secondLastName">Second Last Name:</label> <input
					type="text" class="form-control" id="secondLastName"
					name="secondLastName" value="${client.secondLastName}">
			</div>
			<!-- Campo para actualizar el documento de identidad -->
			<div class="form-group">
				<label for="identityDocument">Identity Document:</label> <input
					type="text" class="form-control" id="identityDocument"
					name="identityDocument" value="${client.identityDocument}" required>
			</div>
			<!-- Botón para enviar el formulario de actualización -->
			<button type="submit" class="btn btn-primary">Update</button>
		</form>
	</div>

	<!-- Optional: Agregar jQuery y Popper.js para la funcionalidad de Bootstrap -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
	<!-- Incluir Bootstrap JS -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
