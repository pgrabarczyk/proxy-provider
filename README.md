# proxy-provider

Provider of Anonymous and Elite proxies.

### Maven - Import project
Add dependency:
```
<dependency>
	<groupId>com.pgrabarczyk</groupId>
	<artifactId>proxy-provider</artifactId>
	<version>2.1.1</version>
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

### Usage:

#### ProxyUpdatableContainer
synchronized methods of ProxyUpdatableContainer:
- getFirst()
- getProxies()
```
Return Optional(ProvidedProxy) or Set<ProvidedProxy> (could be empty). If proxies are too old then it will update set and return fresh one.
```
- remove(ProvidedProxy p);
```
Remove just remove proxy from set
```

#### ProxyProviderService
```
getProxies()
```
methods will always try to download new Set of proxies.
Remember that it can take some time to download and parse much proxies.
