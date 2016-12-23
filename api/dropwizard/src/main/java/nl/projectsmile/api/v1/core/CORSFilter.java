package nl.projectsmile.api.v1.core;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

@Slf4j
public class CORSFilter implements ContainerResponseFilter {
	private final CORSConfiguration config;

	public CORSFilter(CORSConfiguration config) {
		this.config = config;
	}

	@Override
	public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
		final String origin = request.getHeaderString("Origin");


		if (config.getAllowedHostnames().contains(origin)) {
			response.getHeaders().add("Access-Control-Allow-Origin", origin);
			response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Accept, Accept-Language, User-Agent, Referer, DNT, " +
					"X-Requested-With");
			response.getHeaders().add("Access-Control-Allow-Credentials", "true");
			response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
			log.debug("Applied CORS for request headers: [%s]", request.getHeaders());
		} else {
			log.debug("Not applying CORS to Origin [{}] because it is not in the allowedHostnames [%s]", origin, config.getAllowedHostnames());
		}
	}
}
