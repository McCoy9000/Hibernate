<%@ include file="includes/cabecera.jsp"%>

<div class="container" style="margin-top:2em">
<div class="row">
<div class="col-md-6">
<div class="jumbotron">
<h1>�Date ahora de alta!</h1>
<p>Tan solo te llevar� unos pocos segundos...</p>
<p><a class="btn btn-primary btn-lg" style="background:#ECC007; border:none; color:black" href="${applicationScope.rutaBase}/signup">ALTA</a>
</p>
</div>
</div>
<div class="col-md-6">
<form action="login" method="post">
	<fieldset class="form-group">
		<label for="username">Nombre de usuario</label>
		<input id="username" class="form-control" name="username" placeholder="Nombre de usuario" maxlength="20" required="required"/>
	</fieldset>
	<fieldset class="form-group">
		<label for="password">Contrase�a</label>
		<input id="password" class="form-control" name="password" type="password" placeholder="Contrase�a" maxlength="25" required="required"/>
		</fieldset>
	<fieldset class="form-group">
		<input type="submit" class="btn btn-default" value="LOGIN"/>
	</fieldset>
</form>
	<div class="alert alert-danger" role="alert" <c:if test="${sessionScope.errorLogin==null}">style="display:none"</c:if>><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> ${sessionScope.errorLogin}</div>
</div>

</div>
</div>



<%@ include file="includes/pie.jsp"%>