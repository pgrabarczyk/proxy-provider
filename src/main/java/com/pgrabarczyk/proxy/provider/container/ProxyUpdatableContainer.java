package com.pgrabarczyk.proxy.provider.container;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.pgrabarczyk.proxy.provider.Constants;
import com.pgrabarczyk.proxy.provider.model.ProvidedProxy;
import com.pgrabarczyk.proxy.provider.service.ProxyProviderService;
import com.pgrabarczyk.proxy.provider.service.exception.ProxyProviderServiceException;

import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public class ProxyUpdatableContainer {

	public ProxyUpdatableContainer(int updateProxyTimeMilliseconds) {
		super();
		this.updateProxyTimeMilliseconds = updateProxyTimeMilliseconds;
	}

	public ProxyUpdatableContainer(int updateProxyTimeMilliseconds, ProxyProviderService proxyProviderService) {
		this(updateProxyTimeMilliseconds);
		this.proxyProviderService = proxyProviderService;
	}

	private int updateProxyTimeMilliseconds = Constants.DEFAULT_UPDATE_PROXY_TIME_MILLISECONDS;

	private ProxyProviderService proxyProviderService = new ProxyProviderService();

	private Set<ProvidedProxy> proxies = new HashSet<>();

	private LocalDateTime lastDownloaded = LocalDateTime.of(1970, 1, 1, 1, 1);

	public synchronized Set<ProvidedProxy> getProxies() throws ProxyProviderServiceException {
		if (this.shouldBeUpdated()) {
			this.updateProxies();
		}

		return proxies;
	}

	public synchronized Optional<ProvidedProxy> getFirst() throws ProxyProviderServiceException {
		if (this.shouldBeUpdated()) {
			this.updateProxies();
		}
		return proxies.stream().findFirst();
	}

	public synchronized boolean remove(@NonNull ProvidedProxy providedProxy) {
		return this.proxies.remove(providedProxy);
	}

	private void updateProxies() throws ProxyProviderServiceException {
		this.proxies = proxyProviderService.getProxies();
		this.lastDownloaded = LocalDateTime.now();
	}

	private boolean shouldBeUpdated() {
		if (proxies.isEmpty())
			return true;
		LocalDateTime dateTimeMillisAgo = LocalDateTime.now().minus(updateProxyTimeMilliseconds, ChronoUnit.MILLIS);
		return dateTimeMillisAgo.isAfter(lastDownloaded);
	}

}
