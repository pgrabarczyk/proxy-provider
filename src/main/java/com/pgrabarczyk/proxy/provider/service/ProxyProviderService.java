package com.pgrabarczyk.proxy.provider.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pgrabarczyk.proxy.provider.Constants;
import com.pgrabarczyk.proxy.provider.ProxyAnonimityLevel;
import com.pgrabarczyk.proxy.provider.factory.ProvidedProxyFactory;
import com.pgrabarczyk.proxy.provider.factory.exception.ProvidedProxyFactoryException;
import com.pgrabarczyk.proxy.provider.model.ProvidedProxy;
import com.pgrabarczyk.proxy.provider.service.exception.DownloadWebPageServiceException;
import com.pgrabarczyk.proxy.provider.service.exception.ProxyProviderServiceException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Getter
@Slf4j
public class ProxyProviderService {

	private String urlFormat = "http://www.gatherproxy.com/proxylist/anonymity/?t=%s";
	private String bodyRequestFormat = "Type=%s&PageIdx=%d&Uptime=%d";
	@Setter
	private DownloadWebPageService webpageContentService = new DownloadWebPageService();
	private ProvidedProxyFactory providedProxyFactory = new ProvidedProxyFactory();
	@Setter
	private int maxResults = Constants.DEFAULT_MAX_PROXIES;

	/**
	 * Download and parse Anonymous and Elite proxies. Result should be max
	 * 1000. It can take much time. Consider to use second method with specified
	 * maxResults. Should not be used too often.
	 * 
	 * @return Set of ProvidedProxy
	 * @throws ProxyProviderServiceException
	 */
	public Set<ProvidedProxy> getProxies() throws ProxyProviderServiceException {	

		try {
			log.debug("Searching for proxies");

			Set<ProvidedProxy> result = new HashSet<>();

			int page = 1;
			boolean isEliteEmpty = false;
			boolean isAnonymousEmpty = false;
			while (result.size() < maxResults) {
				if (!isEliteEmpty) {
					isEliteEmpty = getAndAddProxiesByAnonimityLevel(maxResults, page, ProxyAnonimityLevel.ELITE,
							result);
				}
				if (!isAnonymousEmpty) {
					isAnonymousEmpty = getAndAddProxiesByAnonimityLevel(maxResults, page, ProxyAnonimityLevel.ANONYMOUS,
							result);
				}

				page++;
			}
			if (maxResults < result.size()) {
				final List<ProvidedProxy> subList = new ArrayList<>(result).subList(0, maxResults);
				result = new HashSet<>(subList);
			}

			log.debug("Found {} proxies", result.size());
			return result;
		} catch (Exception ex) {
			throw new ProxyProviderServiceException(ex);
		}

	}

	private boolean getAndAddProxiesByAnonimityLevel(int maxResults, int page, ProxyAnonimityLevel anonimityLevel,
			Set<ProvidedProxy> result) throws DownloadWebPageServiceException, MalformedURLException {
		Set<ProvidedProxy> proxies = getProxiesByAnonimityLevel(maxResults, page, anonimityLevel);
		result.addAll(proxies);
		log.trace("Got new {} proxies, total: {}", proxies.size(), result.size());
		return proxies.isEmpty();
	}

	private Set<ProvidedProxy> getProxiesByAnonimityLevel(int maxResults, int page, ProxyAnonimityLevel anonimityLevel)
			throws DownloadWebPageServiceException, MalformedURLException {
		String url = String.format(urlFormat, anonimityLevel.name());
		String bodyRequest = String.format(bodyRequestFormat, anonimityLevel.name(), page, 0);
		return getProxies(url, bodyRequest);
	}

	private Set<ProvidedProxy> getProxies(String endPointUrl, String bodyRequest)
			throws DownloadWebPageServiceException, MalformedURLException {
		Set<ProvidedProxy> result = new HashSet<>();

		URL url = new URL(endPointUrl);
		WebRequest webRequest = new WebRequest(url, HttpMethod.POST);
		webRequest.setRequestBody(bodyRequest);

		HtmlPage htmlPage = webpageContentService.getPageContent(webRequest, Collections.emptyMap(),
				Collections.emptySet());
		final DomElement table = htmlPage.getElementById("tblproxy");
		final DomNodeList<HtmlElement> tbodyElements = table.getElementsByTagName("tr");
		for (HtmlElement htmlElement : tbodyElements) {
			ProvidedProxy proxy;
			try {
				proxy = providedProxyFactory.create(htmlElement);
				result.add(proxy);
			} catch (ProvidedProxyFactoryException ex) {
				// dont care about parse exceptions
			}
		}

		return result;
	}



}
