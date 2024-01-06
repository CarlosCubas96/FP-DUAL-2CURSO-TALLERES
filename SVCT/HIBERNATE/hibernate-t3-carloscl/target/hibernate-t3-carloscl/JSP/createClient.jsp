<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Create Client</title>

<!-- Enlace a los estilos de Bootstrap (Asegúrate de que estos enlaces sean correctos según la versión de Bootstrap que estés utilizando) -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
</head>
<body>
	<!-- Contenedor principal con margen superior -->
	<div class="container mt-5">
		<!-- Título de la página -->
		<h2>Create Client</h2>
		<!-- Formulario de creación de cliente -->
		<form action="CreateClientServlet" method="post">
			<!-- Campo de entrada para el primer nombre -->
			<div class="form-group">
				<label for="firstName">First Name:</label> <input type="text"
					class="form-control" id="firstName" name="firstName" required>
			</div>

			<!-- Campo de entrada para el apellido -->
			<div class="form-group">
				<label for="lastName">Last Name:</label> <input type="text"
					class="form-control" id="lastName" name="lastName" required>
			</div>

			<!-- Campo de entrada para el segundo apellido -->
			<div class="form-group">
				<label for="secondLastName">Second Last Name:</label> <input
					type="text" class="form-control" id="secondLastName"
					name="secondLastName">
			</div>

			<!-- Campo de entrada para el documento de identidad -->
			<div class="form-group">
				<label for="identityDocument">Identity Document:</label> <input
					type="text" class="form-control" id="identityDocument"
					name="identityDocument" required>
			</div>

			<!-- Botón de envío del formulario -->
			<button type="submit" class="btn btn-primary">Create Client</button>
		</form>
	</div>

	<!-- Enlace a los scripts de Bootstrap -->
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>
