package nl.projectsmile.api.sparkjavaserver.api;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
public class CardRegistration {
	private String id;
	private String cardId;

	private String firstName;

	private java.math.BigDecimal latitude;
	private java.math.BigDecimal longitude;

	private String location;
	private LocalDateTime registeredOn;

	private String selfieUri;

	@Builder
	public CardRegistration(String id, String cardId, String firstName, BigDecimal latitude, BigDecimal longitude, String location, LocalDateTime registeredOn, String selfieUri) {
		this.id = id;
		this.cardId = cardId;
		this.firstName = firstName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.location = location;
		this.registeredOn = registeredOn;
		this.selfieUri = selfieUri;
	}
}
