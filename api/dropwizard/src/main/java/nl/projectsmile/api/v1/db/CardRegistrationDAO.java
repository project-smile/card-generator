package nl.projectsmile.api.v1.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class CardRegistrationDAO extends AbstractDAO<CardRegistration> {
	public CardRegistrationDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public CardRegistration findById(String id) {
		return get(id);
	}

	public void create(CardRegistration cardRegistration) {
		persist(cardRegistration);
	}

	public List<CardRegistration> getCardRegistrationsByCardId(String cardId) {
		return list(namedQuery("getCardRegistrationsByCardId").setParameter("cardId", cardId));
	}
}
