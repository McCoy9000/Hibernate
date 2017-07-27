package pojos;

import java.io.Serializable;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class Carrito implements Serializable {

	private static final long serialVersionUID = -1977919344319664515L;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listaArticulos == null) ? 0 : listaArticulos.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carrito other = (Carrito) obj;
		if (listaArticulos == null) {
			if (other.listaArticulos != null)
				return false;
		} else if (!listaArticulos.equals(other.listaArticulos))
			return false;
		return true;
	}

}
