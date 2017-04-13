package com.pgrabarczyk.proxy.provider.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.pgrabarczyk.proxy.provider.service.exception.DownloadWebPageServiceException;

public class WebpageContentServiceTest {

	@Test
	public void shouldGetPageContent() throws DownloadWebPageServiceException, MalformedURLException {
		// given
		final String endPointUrl = "http://www.wp.pl/";
		URL url = new URL(endPointUrl);
		WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);
		
		Map<String, String> headers = new HashMap<>();
		Set<Cookie> cookies = new HashSet<>();
		DownloadWebPageService webpageContentService = new DownloadWebPageService();

		// when
		HtmlPage pageContent = webpageContentService.getPageContent(requestSettings, headers, cookies);

		// then
		Assert.assertTrue(pageContent.asXml().contains("Sport"));
	}

	@Test(expected = DownloadWebPageServiceException.class)
	public void shouldNotFindSuchPage() throws DownloadWebPageServiceException, MalformedURLException {
		// given
		final String endPointUrl = "http://sdfdsfsddfsfdsfdsfsdfsdfsdfsdsfdsdffsdfsdfdssfsdfsfdsfd.sdfsdfsdfdffsd/";
		URL url = new URL(endPointUrl);
		WebRequest requestSettings = new WebRequest(url, HttpMethod.GET);
		Map<String, String> headers = new HashMap<>();
		Set<Cookie> cookies = new HashSet<>();
		DownloadWebPageService webpageContentService = new DownloadWebPageService();

		// when
		webpageContentService.getPageContent(requestSettings, headers, cookies);
	}
}
