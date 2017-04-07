package com.pgrabarczyk.proxy.provider.model;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(of = { "ip", "port" })
@ToString
public class ProvidedProxy {

	private String ip;
	private int port;
	private ZonedDateTime dateTime;
	private String type;
	private int responseTime;

}
