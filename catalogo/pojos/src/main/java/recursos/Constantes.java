package recursos;

import java.math.BigDecimal;

public class Constantes {

	public static final BigDecimal IVA = new BigDecimal("0.21");
	
	public static final long ID_USUARIO_DEVOLUCION = 1L;
	
	public static final String RUTA_LOGIN = "/WEB-INF/vistas/login.jsp";
	public static final String RUTA_SERVLET_LOGIN = "/login";
	public static final String RUTA_SIGNUP = "/WEB-INF/vistas/signup.jsp";
	
	public static final String RUTA_CATALOGO = "/WEB-INF/vistas/catalogo.jsp";
	public static final String RUTA_SERVLET_CATALOGO = "/catalogo";
	public static final String RUTA_ARTICULO = "/WEB-INF/vistas/articulo.jsp";
	public static final String RUTA_CHECKOUT = "/WEB-INF/vistas/checkout.jsp";
	public static final String RUTA_SERVLET_CHECKOUT = "/checkout";
	public static final String RUTA_DEVOLUCION = "/WEB-INF/vistas/devolucionform.jsp";
	public static final String RUTA_AGRADECIMIENTO = "/WEB-INF/vistas/agradecimiento.jsp";
	
	public static final String RUTA_FORMULARIO_USUARIO = "/WEB-INF/vistas/usuarioform.jsp";
	public static final String RUTA_LISTADO_USUARIO = "/WEB-INF/vistas/usuariocrud.jsp";
	public static final String RUTA_SERVLET_LISTADO_USUARIO = "/admin/usuariocrud";
	public static final String RUTA_PERFIL_USUARIO = "/WEB-INF/vistas/usuarioperfil.jsp";
	public static final String RUTA_FORMULARIO_PERFIL_USUARIO = "/WEB-INF/vistas/usuarioperfilform.jsp";
	public static final String RUTA_SERVLET_PERFIL_USUARIO = "/WEB-INF/vistas/usuarioperfil";
	
	public static final String RUTA_FORMULARIO_PRODUCTO = "/WEB-INF/vistas/productoform.jsp";
	public static final String RUTA_LISTADO_PRODUCTO = "/WEB-INF/vistas/productocrud.jsp";
	public static final String RUTA_SERVLET_LISTADO_PRODUCTO = "/admin/articulocrud";
	public static final String RUTA_FORMULARIO_IMAGEN_PRODUCTOS = "/WEB-INF/vistas/imagenproductoform.jsp";
	
	public static final String RUTA_FORMULARIO_FACTURA = "/WEB-INF/vistas/facturaform.jsp";
	public static final String RUTA_LISTADO_FACTURA = "/WEB-INF/vistas/facturacrud.jsp";
	public static final String RUTA_SERVLET_LISTADO_FACTURA = "/admin/facturacrud";
	public static final String RUTA_FACTURA_FACTURA = "/WEB-INF/vistas/factura.jsp";
	public static final String RUTA_ERROR_FACTURA = "/WEB-INF/errorfactura.jsp";
	public static final String RUTA_LISTADO_FACTURA_USUARIOS = "/WEB-INF/vistas/facturasusuario.jsp";
}
