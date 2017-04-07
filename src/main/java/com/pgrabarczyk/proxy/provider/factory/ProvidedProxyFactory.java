package com.pgrabarczyk.proxy.provider.factory;

import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.pgrabarczyk.proxy.provider.model.ProvidedProxy;

@Component
public class ProvidedProxyFactory {
	
	public ProvidedProxy create(HtmlElement htmlElement) {
		String prx = htmlElement.getAttribute("prx");

		String ip = prx.split(":")[0];
		int port = Integer.parseInt(prx.split(":")[1]);

		// String time = htmlElement.getAttribute("time");
		// ZonedDateTime dateTime; TODO

		String type = htmlElement.getAttribute("type");
		int responseTime = Integer.parseInt(htmlElement.getAttribute("tmres"));

		return ProvidedProxy.builder()
				.ip(ip)
				.port(port)
				.dateTime(null) // TODO
				.type(type)
				.responseTime(responseTime)
				.build();
	}
}
