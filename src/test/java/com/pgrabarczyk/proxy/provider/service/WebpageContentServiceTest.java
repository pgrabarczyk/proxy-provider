package com.pgrabarczyk.proxy.provider.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.pgrabarczyk.proxy.provider.service.exception.WebpageContentServiceException;

public class WebpageContentServiceTest {

	@Test
	public void shouldGetPageContent() throws WebpageContentServiceException {
		// given
		final String endPointUrl = "http://www.wp.pl/";
		Map<String, String> headers = new HashMap<>();
		Set<Cookie> cookies = new HashSet<>();
		WebpageContentService webpageContentService = new WebpageContentService();

		// when
		HtmlPage pageContent = webpageContentService.getPageContent(endPointUrl, headers, cookies);

		// then
		Assert.assertTrue(pageContent.asXml().contains("Sport"));
	}

	@Test(expected = WebpageContentServiceException.class)
	public void shouldNotFindSuchPage() throws WebpageContentServiceException {
		// given
		final String endPointUrl = "http://sdfdsfsddfsfdsfdsfsdfsdfsdfsdsfdsdffsdfsdfdssfsdfsfdsfd.sdfsdfsdfdffsd/";
		Map<String, String> headers = new HashMap<>();
		Set<Cookie> cookies = new HashSet<>();
		WebpageContentService webpageContentService = new WebpageContentService();

		// when
		webpageContentService.getPageContent(endPointUrl, headers, cookies);
	}
}
