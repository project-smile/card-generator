package nl.projectsmile.api.v1.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CORSConfiguration {
	@JsonProperty
	@NotNull
	private boolean enabled;

	@JsonProperty
	@NotEmpty
	private List<String> allowedHostnames;

	@JsonProperty
	@NotEmpty
	private List<String> allowedHeaders;


}
