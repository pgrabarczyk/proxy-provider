package com.pgrabarczyk.proxy.provider.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pgrabarczyk.proxy.provider.factory.ProvidedProxyFactory;
import com.pgrabarczyk.proxy.provider.model.ProvidedProxy;
import com.pgrabarczyk.proxy.provider.service.exception.WebpageContentServiceException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Getter
@Slf4j
public class ProxyProviderService {

	@Value("#{'${com.pgrabarczyk.proxy.provider.pages.to.parse}'.split(',')}")
	private Set<String> urlsToParse;

	@Autowired
	private WebpageContentService webpageContentService;

	@Autowired
	private ProvidedProxyFactory providedProxyFactory;

	/**
	 * Download and parse Anonymous and Elite proxy. Should not be used too
	 * often. Once per 5minutes should be ok.
	 * 
	 * @return
	 * @throws WebpageContentServiceException
	 */
	public Set<ProvidedProxy> getProxies() throws WebpageContentServiceException {

		Set<ProvidedProxy> result = new HashSet<>();

		for (String url : urlsToParse) {
			result.addAll(getProxies(url));
		}

		return result;
	}

	private Set<ProvidedProxy> getProxies(String endPointUrl) throws WebpageContentServiceException {
		Set<ProvidedProxy> result = new HashSet<>();
		HtmlPage htmlPage = webpageContentService.getPageContent(endPointUrl, Collections.emptyMap(),
				Collections.emptySet());

		final DomElement table = htmlPage.getElementById("tblproxy");
		final DomNodeList<HtmlElement> tbodyElements = table.getElementsByTagName("tr");
		for (HtmlElement htmlElement : tbodyElements) {
			if (htmlElement.hasAttribute("class")) {
				final String classAttribute = htmlElement.getAttribute("class");
				if (classAttribute.contains("proxy")) {
					ProvidedProxy proxy = providedProxyFactory.create(htmlElement);
					result.add(proxy);
				}
			}
		}

		return result;
	}

}
