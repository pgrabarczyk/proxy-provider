package com.pgrabarczyk.proxy.provider.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.pgrabarczyk.proxy.provider.service.exception.DownloadWebPageServiceException;

public class WebpageContentServiceTest {

	@Test
	public void shouldGetPageContent() throws DownloadWebPageServiceException {
		// given
		final String endPointUrl = "http://www.wp.pl/";
		Map<String, String> headers = new HashMap<>();
		Set<Cookie> cookies = new HashSet<>();
		DownloadWebPageService webpageContentService = new DownloadWebPageService();

		// when
		HtmlPage pageContent = webpageContentService.getPageContent(endPointUrl, headers, cookies);

		// then
		Assert.assertTrue(pageContent.asXml().contains("Sport"));
	}

	@Test(expected = DownloadWebPageServiceException.class)
	public void shouldNotFindSuchPage() throws DownloadWebPageServiceException {
		// given
		final String endPointUrl = "http://sdfdsfsddfsfdsfdsfsdfsdfsdfsdsfdsdffsdfsdfdssfsdfsfdsfd.sdfsdfsdfdffsd/";
		Map<String, String> headers = new HashMap<>();
		Set<Cookie> cookies = new HashSet<>();
		DownloadWebPageService webpageContentService = new DownloadWebPageService();

		// when
		webpageContentService.getPageContent(endPointUrl, headers, cookies);
	}
}
