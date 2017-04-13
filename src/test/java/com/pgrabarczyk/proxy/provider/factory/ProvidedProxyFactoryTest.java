package com.pgrabarczyk.proxy.provider.factory;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.pgrabarczyk.proxy.provider.factory.exception.ProvidedProxyFactoryException;
import com.pgrabarczyk.proxy.provider.model.ProvidedProxy;

@RunWith(MockitoJUnitRunner.class)
public class ProvidedProxyFactoryTest {

	private ProvidedProxyFactory providedProxyFactory = new ProvidedProxyFactory();

	@Mock
	private HtmlElement htmlElement;

	@Test
	public void createTest() throws IOException, ProvidedProxyFactoryException {
		// given
		final String toParse = "4m 53s ago\t97.77.104.22\t3128\tAnonymous\tUnited States\t\t16205/1290\t30ms\t";
		Mockito.when(htmlElement.asText()).thenReturn(toParse);

		// when
		final ProvidedProxy proxy = providedProxyFactory.create(htmlElement);

		// then
		Assert.assertEquals("97.77.104.22", proxy.getIp());
		Assert.assertEquals(3128, proxy.getPort());
		Assert.assertEquals("Anonymous", proxy.getType());
	}

	@Test(expected = ProvidedProxyFactoryException.class)
	public void shouldThrowException() throws IOException, ProvidedProxyFactoryException {
		// given
		final String toParse = "TROLOLO\t\t\t\t\tTROLOLO";
		Mockito.when(htmlElement.asText()).thenReturn(toParse);

		// when
		providedProxyFactory.create(htmlElement);

		// then

	}

}
