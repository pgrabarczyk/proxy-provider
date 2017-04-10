package com.pgrabarczyk.proxy.provider.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pgrabarczyk.proxy.provider.Constants;
import com.pgrabarczyk.proxy.provider.factory.ProvidedProxyFactory;
import com.pgrabarczyk.proxy.provider.model.ProvidedProxy;
import com.pgrabarczyk.proxy.provider.service.exception.DownloadWebPageServiceException;
import com.pgrabarczyk.proxy.provider.service.exception.ProxyProviderServiceException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Slf4j
public class ProxyProviderService {

	public ProxyProviderService(Set<String> urlsToParse) {
		super();
		this.urlsToParse = urlsToParse;
	}

	private Set<String> urlsToParse = Constants.DEFAULT_PAGES_TO_PARSE;
	private DownloadWebPageService webpageContentService = new DownloadWebPageService();
	private ProvidedProxyFactory providedProxyFactory = new ProvidedProxyFactory();

	/**
	 * Download and parse Anonymous and Elite proxy. Should not be used too
	 * often. Once per 5minutes should be ok.
	 * 
	 * @return Set of ProvidedProxy
	 * @throws ProxyProviderServiceException
	 */
	public Set<ProvidedProxy> getProxies() throws ProxyProviderServiceException {
		log.debug("Searching for proxies");
		Set<ProvidedProxy> result = new HashSet<>();

		for (String url : urlsToParse) {
			result.addAll(getProxies(url));
		}
		log.debug("Found {} proxies", result.size());
		return result;
	}

	private Set<ProvidedProxy> getProxies(String endPointUrl) throws ProxyProviderServiceException {
		Set<ProvidedProxy> result = new HashSet<>();
		HtmlPage htmlPage;
		try {
			htmlPage = webpageContentService.getPageContent(endPointUrl, Collections.emptyMap(),
					Collections.emptySet());
		} catch (DownloadWebPageServiceException ex) {
			throw new ProxyProviderServiceException(ex);
		}

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
