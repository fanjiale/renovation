package com.weixin.webservice.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import com.weixin.webservice.request.VsopServiceRequest;
import com.weixin.webservice.response.VsopServiceResponse;

/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the cn.com.mbossvsop.vsop package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

	private final static QName _SubInstQryFromVSOPReq_QNAME = new QName(
			"http://www.mbossvsop.com.cn/vsop", "SubInstQryFromVSOPReq");
	private final static QName _SubInstQryFromVSOPResp_QNAME = new QName(
			"http://www.mbossvsop.com.cn/vsop", "SubInstQryFromVSOPResp");

	/**
	 * Create a new ObjectFactory that can be used to create new instances of
	 * schema derived classes for package: cn.com.mbossvsop.vsop
	 * 
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link VsopServiceRequest }
	 * 
	 */
	public VsopServiceRequest createVsopServiceRequest() {
		return new VsopServiceRequest();
	}

	/**
	 * Create an instance of {@link VsopServiceResponse }
	 * 
	 */
	public VsopServiceResponse createVsopServiceResponse() {
		return new VsopServiceResponse();
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link VsopServiceRequest }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.mbossvsop.com.cn/vsop", name = "SubInstQryFromVSOPReq")
	public JAXBElement<VsopServiceRequest> createSubInstQryFromVSOPReq(
			VsopServiceRequest value) {
		return new JAXBElement<VsopServiceRequest>(
				_SubInstQryFromVSOPReq_QNAME, VsopServiceRequest.class, null,
				value);
	}

	/**
	 * Create an instance of {@link JAXBElement }{@code <}
	 * {@link VsopServiceResponse }{@code >}
	 * 
	 */
	@XmlElementDecl(namespace = "http://www.mbossvsop.com.cn/vsop", name = "SubInstQryFromVSOPResp")
	public JAXBElement<VsopServiceResponse> createSubInstQryFromVSOPResp(
			VsopServiceResponse value) {
		return new JAXBElement<VsopServiceResponse>(
				_SubInstQryFromVSOPResp_QNAME, VsopServiceResponse.class, null,
				value);
	}

}
