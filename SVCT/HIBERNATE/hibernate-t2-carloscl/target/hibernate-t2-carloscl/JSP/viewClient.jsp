<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Client Details</title>
    <!-- Link to Bootstrap CSS file -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <!-- Título principal -->
        <h1>Client Details</h1>

        <!-- Tarjeta para mostrar información del cliente -->
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">Client Information</h5>
                <p>ID: ${client.clientId}</p>
                <p>First Name: ${client.firstName}</p>
                <p>Last Name: ${client.lastName}</p>
                <p>Second Last Name: ${client.secondLastName}</p>
                <p>Identity Document: ${client.identityDocument}</p>
            </div>
        </div>

        <!-- Sección para mostrar contratos asociados al cliente -->
        <div class="mt-4">
            <h3>Contracts</h3>
            <ul class="list-group">
                <!-- Iteración sobre la lista de contratos utilizando c:forEach -->
                <c:forEach var="contract" items="${contracts}">
                    <li class="list-group-item">
                        <p>Start Date: ${contract.startDate}</p>
                        <p>End Date: ${contract.endDate}</p>
                        <p>Monthly Price: ${contract.monthlyPrice}</p>
                        
                        <!-- Botones para modificar y eliminar contrato -->
                        <form action="EditContractServlet" method="get" style="display: inline;">
                            <input type="hidden" name="contractId" value="${contract.contractId}" />
                            <input type="hidden" name="clientId" value="${client.clientId}" />
                            <button type="submit" class="btn btn-warning btn-sm">Modify Contract</button>
                        </form>
                        
                        <form action="DeleteContractServlet" method="post" style="display: inline;">
                            <input type="hidden" name="contractId" value="${contract.contractId}" />
                            <input type="hidden" name="clientId" value="${client.clientId}" />
                            <button type="submit" class="btn btn-danger btn-sm">Delete Contract</button>
                        </form>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <!-- Sección para agregar un nuevo contrato -->
        <div class="mt-4">
            <h3>Add New Contract</h3>
            <form action="CreateContractServlet" method="get">
                <input type="hidden" name="clientId" value="${client.clientId}" />
                <button type="submit" class="btn btn-primary mb-4">Add Contract</button>
            </form>
        </div>

        <!-- Enlace para regresar a la lista de clientes -->
        <a class="btn btn-success"
            href="<%=request.getServletContext().getContextPath()%>/ListClientServlet">Go Back</a>
    </div>

    <!-- Opcional: Agregar jQuery y Popper.js para la funcionalidad de Bootstrap -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.0.7/dist/umd/popper.min.js"></script>
    <!-- Incluir Bootstrap JS -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
