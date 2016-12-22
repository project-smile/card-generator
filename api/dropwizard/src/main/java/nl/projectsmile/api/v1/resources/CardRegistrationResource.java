package nl.projectsmile.api.v1.resources;

import com.codahale.metrics.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import nl.projectsmile.api.v1.SelfieUploadConfiguration;
import nl.projectsmile.api.v1.api.CardRegistration;
import nl.projectsmile.api.v1.api.NewRegistration;
import nl.projectsmile.api.v1.api.UploadedSelfie;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@Path("/card/{cardId}/registration")
@Produces(MediaType.APPLICATION_JSON)
public class CardRegistrationResource {

	private final SelfieUploadConfiguration config;

	public CardRegistrationResource(SelfieUploadConfiguration config) {
		this.config = config;
	}

	@POST
	public void registerCard(@PathParam("cardId") String cardId, NewRegistration newRegistration) {

	}

	@GET
	public java.util.List<CardRegistration> getCardRegistrations(@PathParam("cardId") String cardId) {
		return Collections.emptyList();
	}


	/**
	 * TODO: upload to database
	 */
	@POST
	@Path("/selfie")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Timed
	public UploadedSelfie uploadSelfie(@FormDataParam("selfie") InputStream uploadedInputStream,
									   @FormDataParam("selfie") final FormDataBodyPart body,
									   @FormDataParam("selfie") FormDataContentDisposition fileDetail,
									   @PathParam("cardId") String cardId) throws IOException {


		final MediaType mediaType = body.getMediaType();
		if (!MediaType.valueOf("image/jpeg").equals(mediaType)) {
			throw new IllegalArgumentException("Invalid picture, only supports JPEG provided with [" + mediaType + "]");
		}

		final String imageId = UUID.randomUUID().toString();

		final BufferedImage originalImage = ImageIO.read(uploadedInputStream);
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

		BufferedImage bufferedImage = resizeImage(originalImage, type);
		ImageIO.write(bufferedImage, "png", new File(config.getFileDirectory() + "/" + imageId));


		return UploadedSelfie.builder()
				.id(imageId)
				.url(config.getBaseUrl() + "/" + imageId)
				.build();
	}

	private static final int IMG_WIDTH = 100;
	private static final int IMG_HEIGHT = 100;

	private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();

		return resizedImage;
	}
}