# proxy-provider



### Maven - Import project
Add dependency:
```
<dependency>
	<groupId>com.pgrabarczyk</groupId>
	<artifactId>proxy-provider</artifactId>
	<version>1.0.1</version>
</dependency>
```
Add repository:
```
<repository>
	<id>proxy-provider</id>
	<name>proxy-provider</name>
	<url>https://github.com/pgrabarczyk/proxy-provider/raw/master/releases/</url>
</repository>
```

### Spring - Import configuration
For correct use spring configuration bean need to be added.
```
<bean id="proxyProviderAppConf" class="com.pgrabarczyk.proxy.provider.AppConf"/>
```

### Usage:
Use ProxyProviderService.getProxies() for Set of proxies.
Use UpdatedProxyProviderService for optimal usage of ProxyProviderService. It will automaticly update proxies or let you know when time has come for new set of proxies.
