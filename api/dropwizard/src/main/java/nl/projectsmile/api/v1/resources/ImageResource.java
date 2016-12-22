package nl.projectsmile.api.v1.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

/**
 * This is only for dev purposes. On PRD we serve images using NGINX
 */
@Path("/image/{imageName}")
@Produces(MediaType.APPLICATION_JSON)
public class ImageResource {
	private final String imageBaseDir;

	public ImageResource(String imageBaseDir) {
		this.imageBaseDir = imageBaseDir;
	}

	@GET
	public Response sayHello(@PathParam("imageName") String imageName) {
		// TODO: validate path!
		return Response.ok(new File(imageBaseDir + "/" + imageName), "image/jpeg").build();
	}
}