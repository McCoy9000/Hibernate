package factories;

import interfaces.ICarrito;
import pojos.CarritoHashMap;

public class CarritoFactory {
	
	public ICarrito getCarrito() {
		return new CarritoHashMap();
	}
	
}
