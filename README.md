# proxy-provider
Proivider of proxy

For correct use spring configuration bean need to be added.
<bean id="proxyProviderAppConf" class="com.pgrabarczyk.proxy.provider.AppConf"/>

Use ProxyProviderService.getProxies() for Set of proxies.
Use UpdatedProxyProviderService for optimal usage of ProxyProviderService. It will automaticly update proxies or let you know when time has come for new set of proxies.
