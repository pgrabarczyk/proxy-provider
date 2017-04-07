package com.pgrabarczyk.proxy.provider.service;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pgrabarczyk.proxy.provider.AppConf;
import com.pgrabarczyk.proxy.provider.model.ProvidedProxy;
import com.pgrabarczyk.proxy.provider.service.exception.WebpageContentServiceException;

@ContextConfiguration(classes = AppConf.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ProxyProviderServiceIT {

	@Autowired
	private ProxyProviderService proxyProviderService;

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
	public void getProxiesTest() throws WebpageContentServiceException {
		// given
		// when
		final Set<ProvidedProxy> proxies = proxyProviderService.getProxies();
		// then
		Assert.assertNotNull(proxies);
		Assert.assertFalse(proxies.isEmpty());
	}

}
