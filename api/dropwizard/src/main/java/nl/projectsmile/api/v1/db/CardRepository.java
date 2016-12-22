package nl.projectsmile.api.v1.db;

public interface CardRepository {


	/**
	 *
	 * @param cardId
	 * @param url
	 * @param type
	 * @return the selfieID
	 */
	String insertUploadedSelfie(String cardId, String url, String type);

}
