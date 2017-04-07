package com.pgrabarczyk.proxy.provider.service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.pgrabarczyk.proxy.provider.service.exception.WebpageContentServiceException;

import lombok.NonNull;

@Service
public class WebpageContentService {

	@Value("${com.pgrabarczyk.proxy.provider.web.page.content.wait.milliseconds}")
	private long timeoutMillis = 30 * 1000;

	public HtmlPage getPageContent(@NonNull String endPointUrl, @NonNull Map<String, String> headers,
			Set<Cookie> cookies) throws WebpageContentServiceException {

		try {
			return getPage(endPointUrl, headers, cookies);
		} catch (FailingHttpStatusCodeException | IOException e) {
			throw new WebpageContentServiceException(e);
		}

	}

	private HtmlPage getPage(@NonNull String endPointUrl, @NonNull Map<String, String> headers, Set<Cookie> cookies)
			throws IOException {
		WebClient webClient = new WebClient();

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
		return webClient.getPage(endPointUrl);
	}

}
