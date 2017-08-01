<%@ include file="includes/cabecera.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<div class="container" style="margin-top:2em">
<div class="row">
<div class="col-md-6">
	<form action="${applicationScope.rutaBase}/admin/articuloform" method="post">
		
		<fieldset class="form-group"
			<c:if test="${param.op == 'borrar' or param.op == 'alta'}">
				style="display:none"
			</c:if>>
			<label for="codigoArticulo">Codigo de articulo</label> 
			
			<input id="codigoArticulo" name="codigoArticulo" type="text" class="form-control"
			    <c:if test="${param.op == 'modificar'}">
				required="required" 
			</c:if> value="${articulo.codigoArticulo}" readonly="readonly">
		</fieldset>
		
		<fieldset class="form-group" 
			<c:if test="${param.op == 'borrar' or param.op == 'modificar'}">
				style="display:none"
			</c:if>>
			<label for="nuevoCodigoArticulo">Nuevo Código de Articulo</label> 
			
			<input id="nuevoCodigoArticulo" name="nuevoCodigoArticulo" type="text" class="form-control"  
				<c:if test="${param.op == 'alta'}">
				required="required" 
			</c:if> placeholder="Codigo Articulo"/>
		</fieldset>
		<fieldset class="form-group">
			<label for="nombre">Nombre</label> 
			
			<input <c:if test="${param.op == 'borrar'}">
					readonly="readonly"
					</c:if> id="nombre" name="nombre" type="text" class="form-control"  
				required="required" placeholder="Nombre" value="${articulo.nombre}"/>
		</fieldset>
		<fieldset class="form-group" 
			<c:if test="${param.op == 'borrar'}">
				style="display:none;"
			</c:if>>
			<label for="descripcion">Descripción</label> 
			
			<textarea rows="3" id="descripcion" name="descripcion" placeholder="Descripción"
				  class="form-control">${articulo.descripcion}</textarea>
		</fieldset>
		<fieldset class="form-group" 
			<c:if test="${param.op == 'borrar'}">
				style="display:none;"
			</c:if>>
			<label for="precio">Precio</label>
			<div class="input-group"> 
			<div class="input-group-addon">€</div>
			<input type="number" id="precio" name="precio" placeholder="Precio" value="${articulo.precio}"
				 min="0" class="form-control"/>
			</div>
		</fieldset>
		<fieldset class="form-group"
			<c:if test="${param.op == 'borrar'}">
				style="display:none;"
			</c:if>>
			<label for="cantidad">Cantidad</label>
			<div class="input-group">
			<input type="number" id="cantidad" name="cantidad" value="1"
				min="1" class="form-control"/>
			</div>
		</fieldset>
		
		<fieldset class="form-group">
			<input type="submit" class="btn btn-default" style="background:#ECC007; border:none;" value="${fn:toUpperCase(param.op)}"  
				<c:if test="${param.op == null or param.op == ''}">
			  		style="display:none;"
			  	</c:if>/>
			
			<input type="hidden" name="opform" value="${param.op}" />
			<input type="hidden" name="id" value="${articulo.id}" />
		</fieldset>
		
	</form>
	<div class="alert alert-danger" role="alert" <c:if test="${sessionScope.errorProducto==null}">style="display:none"</c:if>><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> ${sessionScope.errorProducto}</div>
	</div>
	</div>
	
</div>	

	
<%@ include file="includes/pie.jsp" %>