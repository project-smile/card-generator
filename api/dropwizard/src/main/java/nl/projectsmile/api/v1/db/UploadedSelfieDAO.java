package nl.projectsmile.api.v1.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

public class UploadedSelfieDAO extends AbstractDAO<UploadedSelfie> {
	public UploadedSelfieDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public UploadedSelfie findById(Long id) {
		return get(id);
	}

	public void create(UploadedSelfie person) {
		persist(person);
	}
}
