//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.07.04 at 04:19:28 PM CEST 
//

package icaro.infraestructura.entidadesBasicas.descEntidadesOrganizacion.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for TipoComponente.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="TipoComponente">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="AgenteAplicacion"/>
 *     &lt;enumeration value="Gestor"/>
 *     &lt;enumeration value="RecursoAplicacion"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TipoComponente")
@XmlEnum
public enum TipoComponente {

	@XmlEnumValue("AgenteAplicacion")
	AGENTE_APLICACION("AgenteAplicacion"), @XmlEnumValue("Gestor")
	GESTOR("Gestor"), @XmlEnumValue("RecursoAplicacion")
	RECURSO_APLICACION("RecursoAplicacion");
	private final String value;

	TipoComponente(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static TipoComponente fromValue(String v) {
		for (TipoComponente c : TipoComponente.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
