package nl.projectsmile.api.sparkjavaserver;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.sql.SQLQueryFactory;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import nl.projectsmile.api.sparkjavaserver.api.CardRegistration;
import nl.projectsmile.api.sparkjavaserver.domain.QCardMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import static nl.projectsmile.api.sparkjavaserver.domain.QCardRegistration.cardRegistration;
import static spark.Spark.before;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.port;
import static spark.Spark.post;

public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	private static final Config CONFIG = ConfigFactory.load();

	private static SQLQueryFactory db;

	public static void main(String[] args) {
		Database.init();
		db = Database.getSqlQueryFactory();

		java.util.List<String> fetch = db.select(QCardMessage.cardMessage.message).from(QCardMessage.cardMessage).fetch();
		fetch.forEach(System.out::println);

		port(4567);

		exception(Exception.class, (e, request, response) -> {
			LOGGER.error("Unexpected exception", e);
		});

		enableCORS("*", "POST, GET", "*");

		getCardRegistrationsEndpoint();
		imageRetrievalEndpoint();
		photoResizeEndpoint();
	}

	private static void getCardRegistrationsEndpoint() {
		get("/card/:cardId/registrations", (req, res) -> {
			final String cardId = req.params("cardId");

			return db.select(cardRegistration.id, cardRegistration.firstName, cardRegistration.location)
					.from(cardRegistration)
					.where(cardRegistration.cardid.eq(cardId))
					.fetch()
					.stream()
					.map((tuple) -> {
						return CardRegistration.builder()
								.cardId(tuple.get(Expressions.stringPath("id")))
								.build();
					})
					.collect(Collectors.toList());
		});
	}

	/**
	 * Note that this is only for DEV purposes. In PRD NGINX or a CDN takes care of this.
	 */
	private static void imageRetrievalEndpoint() {
		get("/images/:imageId", (req, res) -> {
			// TODO: check for invalid names
			final String imageId = req.params("imageId");
			final String imagePath = "/tmp/" + imageId;
			final BufferedImage image = ImageIO.read(new File(imagePath));

			if (BufferedImage.TYPE_4BYTE_ABGR != image.getType()) {
				res.status(400);
				LOGGER.error("Unexpected image type: {}", image.getType());
				return "Error";
			}

			HttpServletResponse raw = res.raw();
			ImageIO.write(image, "png", raw.getOutputStream());

			raw.getOutputStream().flush();
			raw.getOutputStream().close();
			return raw;
		});
	}


	private static void photoResizeEndpoint() {
		post("/resizePhoto", (request, res) -> {
			final MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/tmp");
			request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);

			try {
				final Collection<Part> parts = request.raw().getParts();

				for (Part part : parts) {
					System.out.println("Name:" + part.getName());
					System.out.println("Size: " + part.getSize());
					System.out.println("Filename:" + part.getSubmittedFileName());
				}
				final Part file = request.raw().getPart("image");
//				final String name = file.getSubmittedFileName();


				final BufferedImage originalImage = ImageIO.read(file.getInputStream());
				int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
				BufferedImage bufferedImage = resizeImage(originalImage, type);
				ImageIO.write(bufferedImage, "png", new File("/tmp/resized.png"));

			} catch (IOException | ServletException e2) {
				e2.printStackTrace();
			}

			return "all-ok";
		});
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

	private static void enableCORS(final String origin, final String methods, final String headers) {
		options("/*", (request, response) -> {

			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}

			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}

			return "OK";
		});

		before((request, response) -> {
			response.header("Access-Control-Allow-Origin", origin);
			response.header("Access-Control-Request-Method", methods);
			response.header("Access-Control-Allow-Headers", headers);
//			// Note: this may or may not be necessary in your particular application
//			response.type("application/json");
		});
	}


}
