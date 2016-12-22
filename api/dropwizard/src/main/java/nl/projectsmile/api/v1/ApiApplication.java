package nl.projectsmile.api.v1;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayBundle;
import io.dropwizard.flyway.FlywayFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import nl.projectsmile.api.v1.db.CardRegistration;
import nl.projectsmile.api.v1.db.UploadedSelfie;
import nl.projectsmile.api.v1.db.UploadedSelfieDAO;
import nl.projectsmile.api.v1.resources.CardRegistrationResource;
import nl.projectsmile.api.v1.resources.ImageResource;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.skife.jdbi.v2.DBI;

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
	public void run(final ServerConfiguration config,
					final Environment environment) {

		environment.jersey().register(MultiPartFeature.class);

//		final SimpleHealthCheck healthCheck = new SimpleHealthCheck(config.getTemplate());
//		environment.healthChecks().register("template", healthCheck);

		final DBIFactory factory = new DBIFactory();
		final DBI jdbi = factory.build(environment, config.getDataSourceFactory(), "h2");


//		final UserDAO dao = jdbi.onDemand(UserDAO.class);
//		environment.jersey().register(new UserResource(dao));

		final UploadedSelfieDAO uploadedSelfieDAO = new UploadedSelfieDAO(hibernate.getSessionFactory());
		environment.jersey().register(new CardRegistrationResource(config.getSelfieUploadConfig(), uploadedSelfieDAO));
		environment.jersey().register(new ImageResource(config.getSelfieUploadConfig().getFileDirectory()));
	}

}
