# proxy-provider



### Maven - Import project
Add dependency:
```
<dependency>
	<groupId>com.pgrabarczyk</groupId>
	<artifactId>proxy-provider</artifactId>
	<version>2.0.0</version>
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
(recomended) synchronized methods of ProxyUpdatableContainer:
- getFirst()
- getProxies()
```
Return Optional(ProvidedProxy) or Set<ProvidedProxy> (could be empty). If proxies are too old then it will update set and return fresh one.
```
- remove(ProvidedProxy p);
```
Remove just remove proxy from set
```

(optional) ProxyProviderService.getProxies() will always try to download new Set of proxies.