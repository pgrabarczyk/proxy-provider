package com.pgrabarczyk.proxy.provider.factory;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.pgrabarczyk.proxy.provider.model.ProvidedProxy;

@RunWith(MockitoJUnitRunner.class)
public class ProvidedProxyFactoryTest {

	private ProvidedProxyFactory providedProxyFactory = new ProvidedProxyFactory();

	@Mock
	private HtmlElement htmlElement;

	@Test
	public void createTest() throws IOException {
		// given
		final String prx = "122.142.77.84:9999";
		Mockito.when(htmlElement.getAttribute("prx")).thenReturn(prx);

		final String time = "2017-04-07T13:07:30Z";
		Mockito.when(htmlElement.getAttribute("time")).thenReturn(time);

		final String type = "Anonymous";
		Mockito.when(htmlElement.getAttribute("type")).thenReturn(type);

		final String tmres = "77";
		Mockito.when(htmlElement.getAttribute("tmres")).thenReturn(tmres);

		// when
		final ProvidedProxy proxy = providedProxyFactory.create(htmlElement);

		// then
		Assert.assertEquals("122.142.77.84", proxy.getIp());
		Assert.assertEquals(9999, proxy.getPort());
		Assert.assertEquals(type, proxy.getType());
		Assert.assertEquals(77, proxy.getResponseTime());
		final LocalDateTime dateTime = LocalDateTime.of(2017, 4, 7, 13, 7, 30);
		Assert.assertEquals(dateTime, proxy.getDateTime());
	}

}
