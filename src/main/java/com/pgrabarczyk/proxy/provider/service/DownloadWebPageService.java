package com.pgrabarczyk.proxy.provider.service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.pgrabarczyk.proxy.provider.Constants;
import com.pgrabarczyk.proxy.provider.service.exception.DownloadWebPageServiceException;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
public class DownloadWebPageService {

	private long timeoutMillis = Constants.DEFAULT_WEB_PAGE_CONTENT_WAIT_MILLISECONDS;

	public HtmlPage getPageContent(@NonNull WebRequest webRequest, @NonNull Map<String, String> headers,
			Set<Cookie> cookies) throws DownloadWebPageServiceException {

		try {
			return getPage(webRequest, headers, cookies);
		} catch (FailingHttpStatusCodeException | IOException e) {
			throw new DownloadWebPageServiceException(e);
		}

	}

	private HtmlPage getPage(@NonNull WebRequest webRequest, @NonNull Map<String, String> headers, Set<Cookie> cookies)
			throws IOException {
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getCookieManager().setCookiesEnabled(true);
		
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				webClient.getCookieManager().addCookie(cookie);
			}
		}

		for (Map.Entry<String, String> header : headers.entrySet()) {
			webClient.addRequestHeader(header.getKey(), header.getValue());
		}

		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);

		webClient.waitForBackgroundJavaScript(timeoutMillis);
		webClient.waitForBackgroundJavaScriptStartingBefore(timeoutMillis);
		
		return webClient.getPage(webRequest);
	}

}
