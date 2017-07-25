<%@ include file="includes/cabecera.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<div class="container" style="margin-top:2em">
<div class="row">
<div class="col-md-6">
	<form action="${applicationScope.rutaBase}/usuarioperfil" method="post">
		<fieldset class="form-group" style="display:none;">
			<label for="id">Id</label> 
			
			<input id="id" name="id" type="number" class="form-control"
			  required="required"  value="${sessionScope.usuario.id}" readonly="readonly">
		</fieldset>
		
		<fieldset class="form-group">
			<label for="nombre_completo">Nombre</label> 
			
			<input id="nombre" name="nombre" type="text" class="form-control"  
				placeholder="Nombre" value="${sessionScope.usuario.nombre}"/>
		</fieldset>
		<fieldset class="form-group">
			<label for="apellidos">Apellidos</label> 
			
			<input id="apellidos" name="apellidos" type="text" class="form-control"  
				placeholder="Apellidos" value="${sessionScope.usuario.apellidos}"/>
		</fieldset>
		<fieldset class="form-group">
			<label for="username">Nombre de usuario</label> 
			
			<input id="username" name="username" type="text" class="form-control"  
				required="required" placeholder="Username" value="${sessionScope.usuario.username}"/>
		</fieldset>
		
		<fieldset class="form-group">
			<label for="rawpassword">Contraseña</label> 
			
			<input id="rawpassword" name="rawpassword" type="password" class="form-control"  
				required="required" placeholder="Contraseña"/>
		</fieldset>
		<fieldset class="form-group">
			<label for="rawpassword2">Repita la contraseña</label> 
			
			<input id="rawpassword2" name="rawpassword2" type="password" class="form-control"  
				required="required" placeholder="Repita la contraseña"/>
		</fieldset>
		<fieldset class="form-group">
			<label for="documento">Documento de identidad</label> 
			
			<input id="documento" name="documento" type="text" class="form-control"  
				placeholder="Documento de identidad" value="${sessionScope.usuario.documento}"/>
		</fieldset>
		<fieldset class="form-group">
			<label for="telefono">Teléfono</label> 
			
			<input id="telefono" name="telefono" type="text" class="form-control"  
				placeholder="Teléfono" value="${sessionScope.usuario.telefono}"/>
		</fieldset>
		<fieldset class="form-group">
			<label for="direccion">Calle</label> 
			
			<input id="direccion" name="direccion" type="text" class="form-control"  
				placeholder="Calle" value="${sessionScope.usuario.direccion.calle}"/>
		</fieldset>
		<fieldset class="form-group">
			<label for="puerta">Puerta</label> 
			
			<input id="puerta" name="puerta" type="text" class="form-control"  
				placeholder="Puerta" value="${sessionScope.usuario.direccion.puerta}"/>
		</fieldset>
		<fieldset class="form-group">
			<label for="piso">Piso</label> 
			
			<input id="piso" name="piso" type="text" class="form-control"  
				placeholder="Piso" value="${sessionScope.usuario.direccion.piso}"/>
		</fieldset>
		<fieldset class="form-group">
			<label for="codigoPostal">Código postal</label> 
			
			<input id="codigoPostal" name="codigoPostal" type="text" class="form-control"  
				placeholder="Código postal" value="${sessionScope.usuario.codigoPostal}"/>
		</fieldset>
		<fieldset class="form-group">
			<label for="ciudad">Ciudad</label> 
			
			<input id="ciudad" name="ciudad" type="text" class="form-control"  
				placeholder="Ciudad" value="${sessionScope.usuario.direccion.ciudad}"/>
		</fieldset>
		<fieldset class="form-group">
			<label for="region">Región</label> 
			
			<input id="region" name="region" type="text" class="form-control"  
				placeholder="Región" value="${sessionScope.usuario.direccion.region}"/>
		</fieldset>
		<fieldset class="form-group">
			<label for="pais">País</label> 
			
			<input id="pais" name="pais" type="text" class="form-control"  
				placeholder="País" value="${sessionScope.usuario.direccion.pais}"/>
		</fieldset>
		<fieldset class="form-group">
			<label for="empresa">Empresa</label> 
			
			<input id="empresa" name="empresa" type="text" class="form-control"  
				placeholder="Empresa" value="${sessionScope.usuario.empresa.nombre}"/>
		</fieldset>
		
		<fieldset class="form-group">
			<input type="submit" class="btn btn-default" style="background:#ECC007; border:none;" value="MODIFICAR"  
				<c:if test="${param.op == null or param.op == ''}">
			  		style="display:none;"
			  	</c:if>/>
			
			
			<input type="hidden" name="opform" value="modificar" />
		</fieldset>
		
	</form>
	<div class="alert alert-danger" role="alert" <c:if test="${sessionScope.errorPerfil==null}">style="display:none"</c:if>><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> ${sessionScope.errorPerfil}</div>
	</div>

	</div>
	
</div>	

	
<%@ include file="includes/pie.jsp" %>