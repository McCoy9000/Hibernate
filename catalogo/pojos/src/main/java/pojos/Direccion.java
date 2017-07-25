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
@Table(name = "DIRECCIONES")
public class Direccion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_direccion", unique = true)
	private long id;
	@Column(name = "codigo_postal")
	private String codigoPostal;
	@Column(name = "pais")
	private String pais;
	@Column(name = "region")
	private String region;
	@Column(name = "ciudad")
	private String ciudad;
	@Column(name = "calle")
	private String calle;
	@Column(name = "puerta")
	private String puerta;
	@Column(name ="piso")
	private String piso;
	@OneToOne(mappedBy = "direccion", fetch = FetchType.LAZY)
	private Persona persona;
	@OneToOne(mappedBy = "direccion", fetch = FetchType.LAZY)
	private Empresa empresa;
	@OneToOne(mappedBy = "direccion", fetch = FetchType.LAZY)
	private Comprador comprador;
	
	
	public Direccion() {
		
	}

	public Direccion(String codigoPostal, String pais, String region,
			String ciudad, String calle, String puerta, String piso) {
		super();
		this.codigoPostal = codigoPostal;
		this.pais = pais;
		this.region = region;
		this.ciudad = ciudad;
		this.calle = calle;
		this.puerta = puerta;
		this.piso = piso;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getPuerta() {
		return puerta;
	}

	public void setPuerta(String puerta) {
		this.puerta = puerta;
	}

	public String getPiso() {
		return piso;
	}

	public void setPiso(String piso) {
		this.piso = piso;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
