package com.pgrabarczyk.proxy.provider;

import java.util.HashSet;
import java.util.Set;

public class Constants {
	
	public static final int DEFAULT_WEB_PAGE_CONTENT_WAIT_MILLISECONDS = 30 * 1000;
	public static final int DEFAULT_UPDATE_PROXY_TIME_MILLISECONDS = 5 * 60 * 1000;
	public static final Set<String> DEFAULT_PAGES_TO_PARSE = getDefaultPagesToParse();
	
	private static Set<String> getDefaultPagesToParse() {
		Set<String> set = new HashSet<>();
		set.add("http://www.gatherproxy.com/proxylist/anonymity/?t=Elite");
		set.add("http://www.gatherproxy.com/proxylist/anonymity/?t=Anonymous");
		return set;
	}
			
			
}
