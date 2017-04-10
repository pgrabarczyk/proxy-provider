package com.pgrabarczyk.proxy.provider.service;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.pgrabarczyk.proxy.provider.model.ProvidedProxy;
import com.pgrabarczyk.proxy.provider.service.exception.DownloadWebPageServiceException;
import com.pgrabarczyk.proxy.provider.service.exception.ProxyProviderServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyProviderServiceTest {

	private ProxyProviderService proxyProviderService = new ProxyProviderService();

	@Test
	public void checkSplitOfUrlsToParseTest() {
		// given
		// when
		final Set<String> urlsToParse = proxyProviderService.getUrlsToParse();
		// then
		Assert.assertNotNull(urlsToParse);
		Assert.assertFalse(urlsToParse.isEmpty());
	}

	@Test
	public void getProxiesTest() throws DownloadWebPageServiceException, ProxyProviderServiceException {
		// given
		// when
		final Set<ProvidedProxy> proxies = proxyProviderService.getProxies();
		// then
		log.debug(proxies.toString());
		Assert.assertNotNull(proxies);
		Assert.assertFalse(proxies.isEmpty());
	}

}
