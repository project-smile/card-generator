package nl.projectsmile.api.v1.api;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UploadedSelfie {
	private String id;
	private String url;
}
