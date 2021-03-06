package nl.projectsmile.api.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

@Data
public class SelfieUploadConfiguration {

	@JsonProperty
	@NotEmpty
	private String fileDirectory;
	@JsonProperty
	@NotEmpty
	private String baseUrl;

	@JsonProperty
	@Min(100)
	private Integer maxWidth;

	@JsonProperty
	@Min(100)
	private Integer maxHeight;

}
