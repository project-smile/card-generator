package nl.projectsmile.api.v1.db;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "selfie_upload")
@Data
public class UploadedSelfie {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "card_id")
	private String cardId;
	@Column(name = "image_uri")
	private String uri;
	@Column(name = "uploaded_on")
	private LocalDateTime uploadedOn;

	@Column(name = "image_mime_type")
	private String mimeType;

	public UploadedSelfie() {
	}

	@Builder
	public UploadedSelfie(String id, String url, LocalDateTime uploadedOn, String cardId, String mimeType) {
		this.id = id;
		this.uri = url;
		this.uploadedOn = uploadedOn;
		this.cardId = cardId;
		this.mimeType = mimeType;
	}
}
