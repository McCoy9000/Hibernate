package pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "IMAGENES")
public class Imagen {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true)
	private long id;
	@Column(name = "nombre")
	private String nombre;
	@OneToOne(mappedBy = "imagen", fetch = FetchType.LAZY)
	private Persona persona;
	
	
	public Imagen() {
		
	}
	
	public Imagen(String nombre) {
		this.nombre = nombre; 		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
