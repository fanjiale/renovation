package com.weixin.webservice.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.log4j.Logger;

/**
 * <p>Java class for vsopServiceRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vsopServiceRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="request" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vsopServiceRequest", propOrder = { "request" })
public class VsopServiceRequest {
	private static Logger log = Logger.getLogger(VsopServiceRequest.class);

	@XmlElement(required = true)
	protected String request;

	/**
	 * Gets the value of the request property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getRequest() {
		log.debug("����" + this.request);
		request = request.replaceAll("<Request>", "");
		request = request.replaceAll("</Request>", "");
		request = request.replaceAll("<SessionBody>", "");
		request = request.replaceAll("</SessionBody>", "");
		request = request.replaceAll("<\\?.*\\?>", "");
		return request;
	}

	/**
	 * Sets the value of the request property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setRequest(String value) {
		this.request = "<Request><SessionBody>" + value + "</SessionBody></Request>";
	}

}
