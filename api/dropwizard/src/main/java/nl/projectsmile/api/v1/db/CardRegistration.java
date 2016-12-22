package nl.projectsmile.api.v1.db;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "card_registration")
@Data
@NamedQuery(name="getCardRegistrationsByCardId", query="select reg from nl.projectsmile.api.v1.db.CardRegistration reg where cardId = :cardId")
@NamedNativeQuery(name="getSimilarCardRegistrationsByCardId", resultClass = CardRegistration.class,
		query = "select * from card_registration reg where  reg.card_id in (select card.id from card card where card.message_id = (select message_id from card where id = :cardId))")
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

