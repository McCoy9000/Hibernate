package dataAccessLayer;

public interface DAOManager {
	
	public void abrir() ;
	
	public void cerrar();
	
	public void iniciarTransaccion();
	
	public void terminarTransaccion();
	
	public void abortarTransaccion();
	
	public ArticuloStockDAO getArticuloStockDAO();
	
	public ArticuloVendidoDAO getArticuloVendidoDAO();
	
	public CompradorDAO getCompradorDAO();
	
	public DireccionDAO getDireccionDAO();
	
	public EmpresaDAO getEmpresaDAO();
	
	public FacturaDAO getFacturaDAO();
	
	public ImagenDAO getImagenDAO();
	
	public RolDAO getRolDAO();
	
	public UsuarioDAO getUsuarioDAO();
}
