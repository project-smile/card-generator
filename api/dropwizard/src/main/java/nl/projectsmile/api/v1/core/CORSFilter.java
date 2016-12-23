package nl.projectsmile.api.v1.core;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
public class CORSFilter implements ContainerResponseFilter {
	private final CORSConfiguration config;
	private final String allowedHeaders;

	public CORSFilter(CORSConfiguration config) {
		this.config = config;
		this.allowedHeaders = config.getAllowedHeaders().stream().collect(Collectors.joining(", "));
	}

	@Override
	public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
		final String origin = request.getHeaderString("Origin");


		if (config.getAllowedHostnames().contains(origin)) {
			response.getHeaders().add("Access-Control-Allow-Origin", origin);
			response.getHeaders().add("Access-Control-Allow-Headers", allowedHeaders);
			response.getHeaders().add("Access-Control-Allow-Credentials", "true");
			response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
			log.debug("Applied CORS for request headers: [{}]", request.getHeaders());
		} else {
			log.debug("Not applying CORS to Origin [{}] because it is not in the allowedHostnames [{}]", origin, config.getAllowedHostnames());
		}
	}
}
