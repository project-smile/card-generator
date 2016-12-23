package nl.projectsmile.api.v1.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import nl.projectsmile.api.v1.SelfieUploadConfiguration;
import nl.projectsmile.api.v1.api.NewRegistration;
import nl.projectsmile.api.v1.db.CardRegistration;
import nl.projectsmile.api.v1.db.CardRegistrationDAO;
import nl.projectsmile.api.v1.db.UploadedSelfie;
import nl.projectsmile.api.v1.db.UploadedSelfieDAO;
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
import java.util.List;
import java.util.UUID;

@Slf4j
@Path("/card/{cardId}/registration")
@Produces(MediaType.APPLICATION_JSON)
public class CardRegistrationResource {

	private final SelfieUploadConfiguration config;
	private final UploadedSelfieDAO uploadedSelfieDAO;
	private final CardRegistrationDAO cardRegistrationDAO;

	public CardRegistrationResource(SelfieUploadConfiguration config, UploadedSelfieDAO uploadedSelfieDAO, CardRegistrationDAO cardRegistrationDAO) {
		this.config = config;
		this.uploadedSelfieDAO = uploadedSelfieDAO;
		this.cardRegistrationDAO = cardRegistrationDAO;
	}

	@POST
	@UnitOfWork
	public String registerCard(@PathParam("cardId") String cardId, NewRegistration newRegistration) {
		final String registrationId = UUID.randomUUID().toString();

		String selfieUri = null;
		if (newRegistration.getSelfieUploadId() != null) {
			final UploadedSelfie uploadedSelfie = uploadedSelfieDAO.findById(newRegistration.getSelfieUploadId());
			selfieUri = uploadedSelfie.getUri();
		}

		final CardRegistration registration = CardRegistration.builder()
				.cardId(cardId)
				.id(registrationId)
				.firstName(newRegistration.getFirstname())
				.user_latitude(newRegistration.getUser_latitude())
				.user_longitude(newRegistration.getUser_longitude())
				.location_latitude(newRegistration.getLocation_latitude())
				.location_longitude(newRegistration.getLocation_longitude())
				.location(newRegistration.getLocation())
				.selfieUri(selfieUri)
				.build();

		cardRegistrationDAO.create(registration);
		return registrationId;
	}

	@GET
	@UnitOfWork
	public List<CardRegistration> getCardRegistrations(@PathParam("cardId") String cardId) {
		return cardRegistrationDAO.getCardRegistrationsByCardId(cardId);
	}


	/**
	 * Gets the registrations for cards with the same template
	 */
	@GET
	@Path("/similar")
	@UnitOfWork
	public List<CardRegistration> getSimilarCardRegistrations(@PathParam("cardId") String cardId) {
		return cardRegistrationDAO.getSimilarCardRegistrationsByCardId(cardId);
	}


	@POST
	@Path("/selfie")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Timed
	@UnitOfWork
	public UploadedSelfie uploadSelfie(@FormDataParam("selfie") InputStream uploadedInputStream,
									   @FormDataParam("selfie") final FormDataBodyPart body,
									   @FormDataParam("selfie") FormDataContentDisposition fileDetail,
									   @PathParam("cardId") String cardId) throws IOException {


		final MediaType mediaType = body.getMediaType();
		if (!MediaType.valueOf("image/jpeg").equals(mediaType)) {
			throw new IllegalArgumentException("Invalid picture, only supports JPEG provided with [" + mediaType + "]");
		}

		final String imageId = UUID.randomUUID().toString();

		// first save the uploadedSelfie as this will help with transactionality
		final UploadedSelfie uploadedSelfie = UploadedSelfie.builder()
				.id(imageId)
				.cardId(cardId)
				.mimeType("image/jpeg")
				.url(config.getBaseUrl() + "/" + imageId)
				.build();
		// TODO: handle referential contraint error
		uploadedSelfieDAO.create(uploadedSelfie);


		// then actually resize and save the image
		final BufferedImage originalImage = ImageIO.read(uploadedInputStream);
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

		BufferedImage bufferedImage = resizeImage(originalImage, type);
		ImageIO.write(bufferedImage, "jpeg", new File(config.getFileDirectory() + "/" + imageId));

		return uploadedSelfie;
	}

	private static final int IMG_WIDTH = 100;
	private static final int IMG_HEIGHT = 100;

	private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
		final BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		final Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
		return resizedImage;
	}
}