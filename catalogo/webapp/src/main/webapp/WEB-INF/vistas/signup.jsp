<%@ include file="includes/cabecera.jsp"%>
<!-- <div class="container">
	<h2>Alta</h2>
</div> -->
<div class="container" style="margin-top:2em">
<div class="row">
<div class="col-md-6">
<form action="signup" method="post">
<fieldset class="form-group">
<label for="username">Nombre de usuario <span style="color:red">*</span></label>
<input id="username" class="form-control" name="username" placeholder="Nombre de usuario" maxlength="16" required="required"/>
</fieldset>
<fieldset class="form-group">
<label for="password">Contraseņa <span style="color:red">*</span></label>
<input id="password" class="form-control" name="password" type="password" placeholder="Contraseņa" maxlength="16" required="required"/>
</fieldset>
<fieldset class="form-group">
<label for="password2">Repita la contraseņa <span style="color:red">*</span></label>
<input id="password2" class="form-control" name="password2" type="password" placeholder="Contraseņa" maxlength="16" required="required"/>
</fieldset>
<fieldset class="form-group">
<label for="email">Email <span style="color:red">*</span></label>
<input id="email" class="form-control" name="email" type="text" placeholder="email"/>
</fieldset>
<fieldset class="form-group">
<label for="nombre">Nombre</label>
<input id="nombre" class="form-control" name="nombre" placeholder="Nombre" maxlength="25"/>
</fieldset>
<fieldset class="form-group">
<label for="apellidos">Apellidos</label>
<input id="apellidos" class="form-control" name="apellidos" placeholder="Apellidos" maxlength="50"/>
</fieldset>
<fieldset class="form-group">
<input type="submit" class="btn btn-default" style="background:#ECC007; border:none;"  value="ALTA">
</fieldset>
</form>
<div id="error" class="alert alert-danger" role="alert" <c:if test="${sessionScope.errorSignup==null}">style="display:none"</c:if>><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> ${sessionScope.errorSignup}</div>
</div>
</div>
</div>


<%@ include file="includes/pie.jsp"%>