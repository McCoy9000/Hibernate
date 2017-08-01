<%@ include file="includes/cabecera.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<div id="perfil" class="container">
<div class="bg-info" style="margin:1em">
<div class="row">
	<div class="col-xs-3 col-md-3" style="margin:1em"><label for="username">Username</label><p id="username">${sessionScope.usuario.username}</p>
	</div>
	<div class="col-xs-3 col-md-3" style="margin:1em"><label for="password">Password</label><p id="password">${sessionScope.usuario.password}</p>
	</div>
</div>
</div>
<div  class="bg-info" style="margin:1em">
<div class="row">
	 
	<div class="col-xs-3 col-md-3" style="margin:1em"><label for="nombre">Nombre</label><p id="nombre">${sessionScope.usuario.nombre}</p>
	</div>
	<div class="col-xs-3 col-md-3" style="margin:1em"><label for="apellidos">Apellidos</label><p id="apellidos">${sessionScope.usuario.apellidos}</p>
	</div>
</div>
<div class="row">
	<div class="col-xs-3 col-md-3" style="margin:1em"><label for="telefono">Telefono</label><p id="telefono">${sessionScope.usuario.telefono}</p>
	</div>
	<div class="col-xs-3 col-md-3" style="margin:1em"><label for="documento">Documento</label><p id="documento">${sessionScope.usuario.documento}</p>
	</div>
</div>
</div>
<div  class="bg-info" style="margin:1em">
<div class="row">
	<div class="col-xs-3 col-md-3" style="margin:1em"><label for="direccion">Direccion</label><p id="direccion">${sessionScope.usuario.direccion.calle} ${sessionScope.usuario.direccion.puerta} ${sessionScope.usuario.direccion.piso}</p>
	</div>
	<div class="col-xs-3 col-md-3" style="margin:1em"><label for="codigoPostal">Código postal</label><p id="codigoPostal">${sessionScope.usuario.direccion.codigoPostal}</p>
	</div>
</div>
<div class ="row">
	<div class="col-xs-3 col-md-3" style="margin:1em"><label for="ciudad">Ciudad</label><p id="ciudad">${sessionScope.usuario.direccion.ciudad}</p>
	</div>
	<div class="col-xs-3 col-md-3" style="margin:1em"><label for="region">Región</label><p id="region">${sessionScope.usuario.direccion.region}</p>
	</div>
	<div class="col-xs-3 col-md-3" style="margin:1em"><label for="pais">País</label><p id="pais">${sessionScope.usuario.direccion.pais}</p>
	</div>
</div>
</div>
<div  class="bg-info" style="margin:1em">
<div class="row">
	<div class="col-xs-3 col-md-3" style="margin:1em"><label for="empresa">Empresa</label><p id="empresa">${sessionScope.usuario.empresa.nombre}</p>
	</div>
</div>
</div>
<div class="row" style="margin-left:1em">
	<div><a class="btn btn-default" style="background:#ECC007; border:none;" href="${applicationScope.rutaBase}/usuarioperfil?opform=formulario">MODIFICAR</a>
</div>
</div>	
		

</div>

	
<%@ include file="includes/pie.jsp" %>