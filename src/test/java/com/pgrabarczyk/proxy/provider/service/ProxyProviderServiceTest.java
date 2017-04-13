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

	@Test
	public void getProxiesTest() throws DownloadWebPageServiceException, ProxyProviderServiceException {
		// given
		final int maxResults = 33;
		ProxyProviderService proxyProviderService = new ProxyProviderService();
		proxyProviderService.setMaxResults(maxResults);
		// when
		final Set<ProvidedProxy> proxies = proxyProviderService.getProxies();
		// then
		log.debug(proxies.toString());
		Assert.assertNotNull(proxies);
		Assert.assertFalse(proxies.isEmpty());
		Assert.assertTrue(proxies.size() <= maxResults);
	}

}
