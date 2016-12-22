package nl.projectsmile.api.v1;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayBundle;
import io.dropwizard.flyway.FlywayFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import nl.projectsmile.api.v1.core.CORSFilter;
import nl.projectsmile.api.v1.db.CardRegistration;
import nl.projectsmile.api.v1.db.CardRegistrationDAO;
import nl.projectsmile.api.v1.db.UploadedSelfie;
import nl.projectsmile.api.v1.db.UploadedSelfieDAO;
import nl.projectsmile.api.v1.resources.CardRegistrationResource;
import nl.projectsmile.api.v1.resources.ImageResource;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

public class ApiApplication extends Application<ServerConfiguration> {

	public static void main(final String[] args) throws Exception {
		new ApiApplication().run(args);
	}

	private final HibernateBundle<ServerConfiguration> hibernate = new HibernateBundle<ServerConfiguration>(
			CardRegistration.class, UploadedSelfie.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(ServerConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public String getName() {
		return "projectsmile";
	}

	@Override
	public void initialize(final Bootstrap<ServerConfiguration> bootstrap) {
		bootstrap.addBundle(hibernate);
		bootstrap.addBundle(new FlywayBundle<ServerConfiguration>() {
			@Override
			public DataSourceFactory getDataSourceFactory(ServerConfiguration configuration) {
				return configuration.getDataSourceFactory();
			}

			@Override
			public FlywayFactory getFlywayFactory(ServerConfiguration configuration) {
				return new FlywayFactory();
			}
		});
	}

	@Override
	public void run(final ServerConfiguration config, final Environment environment) {
		final UploadedSelfieDAO uploadedSelfieDAO = new UploadedSelfieDAO(hibernate.getSessionFactory());
		final CardRegistrationDAO cardRegistrationDAO = new CardRegistrationDAO(hibernate.getSessionFactory());

		environment.jersey().register(new CORSFilter(config.getCorsConfig()));
		environment.jersey().register(new CardRegistrationResource(config.getSelfieUploadConfig(), uploadedSelfieDAO, cardRegistrationDAO));
		environment.jersey().register(new ImageResource(config.getSelfieUploadConfig().getFileDirectory()));
		environment.jersey().register(MultiPartFeature.class);
	}

}
