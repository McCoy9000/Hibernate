<%@ include file="includes/cabecera.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<nav class="container" style="margin-top:2em">
	<ul class="list-inline">
	<li><a class="btn btn-default" style="background:#ECC007; border:#ECC007;" href="${applicationScope.rutaBase}/checkout"><strong>CHECKOUT &#128722; (${sessionScope.numeroProductos})</strong></a></li>
	</ul>
</nav>
<div class="container">
<div class="table-responsive">
<table id="catalogo" class="table">
	<thead>
		<tr>
			
			<th style="text-align:center;">Nombre producto</th>
			<th style="text-align:center;">Imagen</th>
			<th style="text-align:center;">Precio</th>
			<th style="text-align:center;">Stock</th>
			<th></th>
			
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${applicationScope.catalogo}" var="articulo">
			<tr id="${articulo.id}">
				<td style="text-align:center; vertical-align: middle;"><a  onclick="window.open('${applicationScope.rutaBase}/catalogo?op=ver&id=${articulo.id}', '_blank', 'width=800,height=600')">${articulo.nombre}</a></td>
				<td style="text-align:center; vertical-align: middle;"><a  onclick="window.open('${applicationScope.rutaBase}/catalogo?op=ver&id=${articulo.id}', '_blank', 'width=800,height=600')"><object data="${applicationScope.rutaBase}/img/${articulo.imagen}.jpg" height="128" type="image/png">
					<img src="${applicationScope.rutaBase}/img/0.jpg" class="img-thumbnail" height="128" width="128"/></object></a></td>
				<td style="text-align:center; vertical-align: middle;">${articulo.precio} €</td>
				<td style="text-align:center; vertical-align: middle;">${articulo.cantidad}</td>
				<td id="anadir" style="text-align:center; vertical-align:middle;">
					<form action="${applicationScope.rutaBase}/catalogo#${articulo.id}" method="post">
						<input type="number" max="${articulo.cantidad}" min="1" id="cantidad" name="cantidad" value="1"/>
						<input type="hidden" id ="id" name="id" value="${articulo.id}"/>
						<input type="hidden" id="op" name="op" value="anadir"/>
						<input type="submit" class="btn btn-default" style="background:#ECC007; border:none;" value="AÑADIR"/>
					</form>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
</div>
<%@ include file="includes/pie.jsp"%>
