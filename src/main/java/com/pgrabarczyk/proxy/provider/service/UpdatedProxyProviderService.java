package com.pgrabarczyk.proxy.provider.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pgrabarczyk.proxy.provider.model.ProvidedProxy;
import com.pgrabarczyk.proxy.provider.service.exception.DownloadWebPageServiceException;

@Service
public class UpdatedProxyProviderService {

	@Autowired
	private ProxyProviderService proxyProviderService;

	@Value("${com.pgrabarczyk.proxy.provider.proxy.refresh.milliseconds}")
	private int refreshDelayInMilliseconds;

	private Set<ProvidedProxy> proxies;

	private LocalDateTime lastDownloaded = LocalDateTime.of(1970, 1, 1, 1, 1);

	public synchronized Set<ProvidedProxy> getProxies() throws DownloadWebPageServiceException {
		if (null == proxies) {
			updateProxies();
			return proxies;
		}

		if (shouldBeUpdated()) {
			updateProxies();
			return proxies;
		}

		return proxies;
	}

	public synchronized void updateProxies() throws DownloadWebPageServiceException {
		proxies = proxyProviderService.getProxies();
		lastDownloaded = LocalDateTime.now();
	}

	public synchronized boolean shouldBeUpdated() {
		LocalDateTime dateTimeMillisAgo = LocalDateTime.now().minus(refreshDelayInMilliseconds, ChronoUnit.MILLIS);
		return dateTimeMillisAgo.isAfter(lastDownloaded);
	}

}
