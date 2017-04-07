package com.pgrabarczyk.proxy.provider.service;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class UpdatedProxyProviderServiceTest {

	private UpdatedProxyProviderService updatedProxyProviderService = new UpdatedProxyProviderService();

	@Ignore("TODO")
	@Test
	public void testGetProxies() {
		fail("Not yet implemented");
	}

	@Ignore("TODO")
	@Test
	public void testUpdateProxies() {
		fail("Not yet implemented");
	}

	@Test
	public void testShouldBeUpdated() {
		// given
		// when
		final boolean shouldBeUpdated = updatedProxyProviderService.shouldBeUpdated();
		// then
		Assert.assertTrue(shouldBeUpdated);
	}

}
