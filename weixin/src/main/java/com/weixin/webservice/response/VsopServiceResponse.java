package com.weixin.webservice.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.log4j.Logger;

/**
 * <p>Java class for vsopServiceResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="vsopServiceResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="response" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vsopServiceResponse", propOrder = { "response" })
public class VsopServiceResponse {
	private static Logger log = Logger.getLogger(VsopServiceResponse.class);

	@XmlElement(required = true)
	protected String response;

	/**
	 * Gets the value of the response property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public String getResponse() {
		response = response.replaceAll("<Response>", "");
		response = response.replaceAll("</Response>", "");
		response = response.replaceAll("<\\?.*\\?>", "");
		return response;
	}

	/**
	 * Sets the value of the response property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setResponse(String value) {
		this.response = "<Response>" + value.replaceAll("<\\?.*\\?>", "") + "</Response>";
		log.debug("��ִ:" + this.response);
	}
}
