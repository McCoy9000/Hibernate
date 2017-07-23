<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Usuarios</title>
</head>
<body>
<jsp:useBean id="usuario" scope="application"
		class="pojos.Usuario" />
<table style="border: 1px solid;">
<thead>
	<tr>
	<th></th>
	<th>Nombre</th>
	<th>Apellidos</th>
	<th>Fecha de nacimiento</th>
	<th>Documento</th>
	<th>Telefono</th>
	<th>Email</th>
	<th>País</th>
	<th>Región</th>
	<th>Ciudad</th>
	<th>Empresa</th>
	<th>Username</th>
	<th>Password</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${applicationScope.usuarios}" var="usuario">
		<tr>
			<td>A</td>
			<td>${usuario.nombre}</td>
			<td>${usuario.apellidos}</td>
			<td>${usuario.fechaNacimiento}</td>
			<td>${usuario.documento}</td>
			<td>${usuario.telefono}</td>
			<td>${usuario.email}</td>
			<td>${usuario.direccion.pais}</td>
			<td>${usuario.direccion.region}</td>
			<td>${usuario.direccion.ciudad}</td>
			<td>${usuario.empresa.nombre}</td>
			<td>${usuario.username}</td>
			<td>${usuario.password}</td>
		</tr>
	</c:forEach>
</tbody>
</table>
</body>
</html>