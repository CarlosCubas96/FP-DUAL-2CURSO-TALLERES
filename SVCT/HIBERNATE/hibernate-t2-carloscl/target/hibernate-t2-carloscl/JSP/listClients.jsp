<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html lang="es">
<head>
<!-- Configuración del documento -->
<meta charset="UTF-8">
<title>Client List</title>

<!-- Enlace al archivo CSS de Bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
	<!-- Contenedor principal con margen superior -->
	<div class="container mt-5">
		<!-- Título principal -->
		<h1>Client List</h1>

		<!-- Formulario de búsqueda -->
		<form action="SearchClientServlet" method="get" class="mb-3">
			<div class="input-group">
				<input type="text" class="form-control" placeholder="Search by name"
					name="searchKeyword">
				<div class="input-group-append">
					<button class="btn btn-outline-secondary" type="submit">Search</button>
				</div>
			</div>
		</form>

		<!-- Botón para volver a la lista -->
		<a class="btn btn-info mb-3" href="ListClientServlet">Back to List</a>

		<!-- Tabla de clientes -->
		<table class="table">
			<!-- Descripción de la tabla -->
			<caption>List of Clients</caption>
			<thead>
				<tr>
					<th>ID</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Second Last Name</th>
					<th>Identity Document</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>

				<!-- Iteración sobre la lista de clientes -->
				<c:forEach var="client" items="${clients}">
					<tr>
						<td>${client.clientId}</td>
						<td>${client.firstName}</td>
						<td>${client.lastName}</td>
						<td>${client.secondLastName}</td>
						<td>${client.identityDocument}</td>
						<td>
							<!-- Formulario para editar cliente -->
							<form action="EditClientServlet" method="get"
								style="display: inline;">
								<input type="hidden" name="clientId" value="${client.clientId}" />
								<button class="btn btn-primary btn-sm" type="submit">Edit</button>
							</form> <!-- Formulario para ver detalles del cliente -->
							<form action="ViewClientServlet" method="get"
								style="display: inline;">
								<input type="hidden" name="clientId" value="${client.clientId}" />
								<button class="btn btn-success btn-sm" type="submit">View
									Client</button>
							</form> <!-- Formulario para eliminar cliente -->
							<form action="DeleteClientServlet" method="post"
								style="display: inline;">
								<input type="hidden" name="clientId" value="${client.clientId}" />
								<button class="btn btn-danger btn-sm" type="submit">Delete</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<!-- Enlace para crear un nuevo cliente -->
		<a class="btn btn-success" href="CreateClientServlet">Create
			Client</a>
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
