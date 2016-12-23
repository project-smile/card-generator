package nl.projectsmile.api.v1.api;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class NewRegistration {
	private String firstname;
	private String location;
	private String selfieUploadId;
	private BigDecimal location_longitude;
	private BigDecimal location_latitude;
	private BigDecimal user_longitude;
	private BigDecimal user_latitude;

}
