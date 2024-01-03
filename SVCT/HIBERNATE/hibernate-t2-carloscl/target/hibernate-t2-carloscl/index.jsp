<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bienvenido a la Aplicación</title>
    <!-- Link to Bootstrap CSS file -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa; /* Color de fondo */
            font-family: 'Arial', sans-serif; /* Tipo de fuente */
        }

        .container {
            margin-top: 50px; /* Espacio superior */
            text-align: center; /* Alineación del texto al centro */
        }

        h2 {
            color: #007bff; /* Color del título */
        }

        .loading-container {
            display: none; /* Inicialmente oculto */
        }

        .loading-indicator {
            font-size: 24px;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Bienvenido a la Aplicación</h2>
        <p>Esta es una aplicación CRUD.</p>
        <button id="startAppBtn" class="btn btn-primary">Iniciar Aplicación</button>

        <!-- Contenedor de carga -->
        <div class="loading-container">
            <p class="loading-indicator">Iniciando espere ...</p>
        </div>
    </div>

    <!-- Optional: Add jQuery and Popper.js for Bootstrap functionality -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
    <!-- Include Bootstrap JS -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <script>
        $(document).ready(function () {
            // Handler para el clic en el botón
            $("#startAppBtn").click(function () {
                // Ocultar el botón
                $(this).hide();
                // Mostrar el indicador de carga
                $(".loading-container").show();
                
                setTimeout(function () {
                    // Redireccionar al servlet después de 2 segundos (simulado)
                    window.location.href = "ListClientServlet";
                }, 2000);
            });
        });
    </script>
</body>
</html>
