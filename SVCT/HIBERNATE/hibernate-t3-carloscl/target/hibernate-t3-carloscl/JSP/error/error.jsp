<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Error</title>
<!-- Link to Bootstrap CSS file -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
	<div class="container mt-5">
		<!-- Título principal para indicar el error -->
		<h1>Something Went Wrong</h1>

		<!-- Mensaje del error -->
		<p>We're sorry, but we encountered an error while processing your
			request.</p>

		<!-- Enlace para regresar a la página principal -->
		<a class="btn btn-success"
			href="<%=request.getServletContext().getContextPath()%>/ListClientServlet">Go
			Back</a>
	</div>

	<!-- Optional: Add jQuery and Popper.js para la funcionalidad de Bootstrap -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
	<!-- Include Bootstrap JS -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
