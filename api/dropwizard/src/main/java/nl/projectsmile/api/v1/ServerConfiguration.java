package nl.projectsmile.api.v1;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import lombok.Data;
import org.hibernate.validator.constraints.*;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
public class ServerConfiguration extends Configuration {


	@Valid
	@NotNull
	@JsonProperty("database")
	private DataSourceFactory dataSourceFactory = new DataSourceFactory();


	@Valid
	@NotNull
	@JsonProperty("selfies")
	private SelfieUploadConfiguration selfieUploadConfig;

}
