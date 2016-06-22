package com.sephora.reporter;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/rest")
public class SephoraResourceConfig extends ResourceConfig {
	public SephoraResourceConfig() {
		register(MultiPartFeature.class);
		packages("com.sephora.reporter.resources");
	}
}
