<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome to the Application</title>

<!-- Enlace al archivo CSS de Bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

<!-- Estilos para la página -->
<style>
body {
	/* Color de fondo */
	background-color: #f8f9fa;
	/* Tipo de fuente */
	font-family: 'Arial', sans-serif;
}

.container {
	/* Margen superior */
	margin-top: 50px;
	/* Alineación al centro */
	text-align: center;
}

h2 {
	/* Color del título */
	color: #007bff;
}

/* Inicialmente oculto */
.loading-container {
	display: none;
}

.loading-indicator {
	font-size: 24px;
}
</style>
</head>
<body>

	<div class="container">

		<!-- Título de bienvenida -->
		<h2>Welcome to the Application CRUD T3</h2>

		<!-- Descripción de la aplicación -->
		<p>This is a CRUD application.</p>

		<!-- Botón para iniciar la aplicación -->
		<button id="startAppBtn" class="btn btn-primary">Start
			Application</button>

		<!-- Modal de Carga -->
		<div class="modal fade" id="loadingModal" tabindex="-1" role="dialog"
			aria-labelledby="loadingModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="loadingModalLabel">Loading,
							please wait...</h5>
					</div>
					<div class="modal-body text-center">
						<div class="spinner-border text-primary" role="status">
							<span class="sr-only">Loading...</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Opcional: Agregar jQuery y Popper.js para la funcionalidad de Bootstrap -->

	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>

	<!-- Incluir Bootstrap JS -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

	<!-- Script para manejar el clic en el botón -->
	<script>
		$(document).ready(function() {
			// Click handler for the button
			$("#startAppBtn").click(function() {
				// Hide the button
				$(this).hide();
				// Show the loading modal
				$("#loadingModal").modal("show");

				setTimeout(function() {
					// Redirect to the servlet after 2 seconds (simulated)
					window.location.href = "ListClientServlet";
				}, 2000);
			});
		});
	</script>

</body>
</html>
