package com.pgrabarczyk.proxy.provider.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pgrabarczyk.proxy.provider.model.ProvidedProxy;
import com.pgrabarczyk.proxy.provider.service.exception.WebpageContentServiceException;

@Service
public class UpdatedProxyProviderService {

	@Autowired
	private ProxyProviderService proxyProviderService;

	@Value("${com.pgrabarczyk.proxy.provider.proxy.refresh.milliseconds}")
	private int refreshDelayInMilliseconds;

	private Set<ProvidedProxy> proxies;

	private LocalDateTime lastDownloaded;

	public synchronized Set<ProvidedProxy> getProxies() throws WebpageContentServiceException {
		if (null == lastDownloaded || null == proxies) {
			updateProxies();
			return proxies;
		}

		LocalDateTime dateTimeMillisAgo = LocalDateTime.now().minus(refreshDelayInMilliseconds, ChronoUnit.MILLIS);
		if (dateTimeMillisAgo.isBefore(dateTimeMillisAgo)) {
			updateProxies();
			return proxies;
		}

		return proxies;
	}

	private synchronized void updateProxies() throws WebpageContentServiceException {
		proxies = proxyProviderService.getProxies();
		lastDownloaded = LocalDateTime.now();
	}

}