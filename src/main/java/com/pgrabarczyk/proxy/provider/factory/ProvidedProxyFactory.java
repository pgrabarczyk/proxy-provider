package com.pgrabarczyk.proxy.provider.factory;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.pgrabarczyk.proxy.provider.factory.exception.ProvidedProxyFactoryException;
import com.pgrabarczyk.proxy.provider.model.ProvidedProxy;

public class ProvidedProxyFactory {

	public ProvidedProxy create(HtmlElement htmlElement) throws ProvidedProxyFactoryException {
		try {
			String[] splitted = htmlElement.asText().split("\t");
			return ProvidedProxy.builder()//
					.ip(splitted[1])
					.port(Integer.parseInt(splitted[2]))
					.type(splitted[3])
					.build();
		} catch (Exception e) {
			throw new ProvidedProxyFactoryException();
		}

	}
}
