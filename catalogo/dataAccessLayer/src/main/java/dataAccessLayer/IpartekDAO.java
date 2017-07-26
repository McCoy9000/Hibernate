package dataAccessLayer;


public interface IpartekDAO {

	public void abrirManager();

	public void cerrarManager();

	public void iniciarTransaccion();

	public void terminarTransaccion();

}
