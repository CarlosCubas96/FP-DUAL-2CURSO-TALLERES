<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Update Contract</title>
    <!-- Link to Bootstrap CSS file -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1>Update Contract</h1>

        <!-- Formulario para la actualización del contrato -->
        <form action="EditContractServlet" method="post">
            <!-- Campos de entrada ocultos para clientId y contractId -->
            <input type="hidden" name="clientId" value="${clientId}" />
            <input type="hidden" name="contractId" value="${contract.contractId}" />

            <div class="form-group">
                <label for="startDate">Start Date:</label>
                <input type="text" class="form-control" id="startDate" name="startDate" value="${empty contract.startDate ? '' : contract.startDate}" required>
            </div>

            <div class="form-group">
                <label for="endDate">End Date:</label>
                <input type="text" class="form-control" id="endDate" name="endDate" value="${empty contract.endDate ? '' : contract.endDate}" required>
            </div>

            <div class="form-group">
                <label for="monthlyPrice">Monthly Price:</label>
                <input type="text" class="form-control" id="monthlyPrice" name="monthlyPrice" value="${empty contract.monthlyPrice ? '' : contract.monthlyPrice}" required>
            </div>

            <!-- Botón para enviar el formulario de actualización -->
            <button type="submit" class="btn btn-primary">Update Contract</button>
        </form>
    </div>

    <!-- Opcional: Agregar jQuery y Popper.js para la funcionalidad de Bootstrap -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
    <!-- Include Bootstrap JS -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
