package pojos;

import java.util.HashMap;

import org.apache.log4j.Logger;

public class Carrito {
	
	private static Logger log = Logger.getLogger(Carrito.class);

	private HashMap<Long, ArticuloCantidad> listaArticulos;

	public Carrito() {
		this.listaArticulos = new HashMap<>();
		log.info("Creado carrito con lista de productos vacía");
	}

	public Carrito(HashMap<Long, ArticuloCantidad> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}

	public HashMap<Long, ArticuloCantidad> getListaArticulos() {
		return listaArticulos;
	}

	public void setListaProductos(HashMap<Long, ArticuloCantidad> listaArticulos) {
		this.listaArticulos = listaArticulos;
	}


}
