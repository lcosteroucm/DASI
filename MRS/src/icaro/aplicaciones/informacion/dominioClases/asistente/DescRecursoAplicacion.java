//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.11.26 at 01:54:43 AM CET 
//

package icaro.aplicaciones.informacion.dominioClases.asistente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for DescRecursoAplicacion complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="DescRecursoAplicacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="nombre" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="localizacionClaseGeneradora" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescRecursoAplicacion")
public class DescRecursoAplicacion {

	@XmlAttribute(required = true)
	protected String nombre;
	@XmlAttribute
	protected String localizacionClaseGeneradora;

	/**
	 * Gets the value of the nombre property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the value of the nombre property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setNombre(String value) {
		this.nombre = value;
	}

	/**
	 * Gets the value of the localizacionClaseGeneradora property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getLocalizacionClaseGeneradora() {
		return localizacionClaseGeneradora;
	}

	/**
	 * Sets the value of the localizacionClaseGeneradora property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setLocalizacionClaseGeneradora(String value) {
		this.localizacionClaseGeneradora = value;
	}

}
