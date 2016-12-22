package nl.projectsmile.api.v1.db;

import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "card_registration")
@Data
public class CardRegistration {
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "card_id")
	private String cardId;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "location")
	private String location;

	@Column(name = "selfie_uri")
	private String selfieUri;


	@Column(name = "longitude")
	private BigDecimal longitude;

	@Column(name = "latitude")
	private BigDecimal latitude;


	@Column(name = "registered_on")
	private LocalDateTime uploadedOn;

	public CardRegistration() {

	}

	@Builder
	public CardRegistration(String id, String cardId, String firstName, String location, String selfieUri, BigDecimal longitude, BigDecimal latitude, LocalDateTime uploadedOn) {
		this.id = id;
		this.cardId = cardId;
		this.firstName = firstName;
		this.location = location;
		this.selfieUri = selfieUri;
		this.longitude = longitude;

		this.latitude = latitude;
		this.uploadedOn = uploadedOn;
	}

}

