package org.mklab.taskit.server;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.mklab.taskit.client.HibernateSampleService;
import org.mklab.taskit.dao.TestDao;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 21, 2011
 */
public class HibernateSampleServiceImpl extends RemoteServiceServlet implements
		HibernateSampleService {

	/**
	 * @see org.mklab.taskit.client.HibernateSampleService#accessThroughHibernate()
	 */
	public String accessThroughHibernate() {
		final EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("Test"); //$NON-NLS-1$
		final EntityManager entityManager = factory.createEntityManager();
		final TestDao hogeDao = new TestDao(entityManager);
		return hogeDao.list().toString();
	}

}
