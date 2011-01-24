package org.mklab.taskit.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.jpa.HibernateJpaUtil;
import net.sf.gilead.gwt.GwtConfigurationHelper;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.mklab.taskit.server.dao.TestDao;
import org.mklab.taskit.shared.model.Test;
import org.mklab.taskit.shared.service.HibernateSampleService;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 21, 2011
 */
public class HibernateSampleServiceImpl extends PersistentRemoteService implements HibernateSampleService {

  /** for serialization. */
  private static final long serialVersionUID = -4392619702326579755L;
  private TestDao testDao;

  /**
   * {@link HibernateSampleServiceImpl}オブジェクトを構築します。
   */
  public HibernateSampleServiceImpl() {
    final EntityManagerFactory factory = Persistence.createEntityManagerFactory("taskit"); //$NON-NLS-1$
    final EntityManager entityManager = factory.createEntityManager();
    this.testDao = new TestDao(entityManager);

    final HibernateJpaUtil persistenceUtil = new HibernateJpaUtil(factory);
    final PersistentBeanManager persistentBeanManager = GwtConfigurationHelper.initGwtStatelessBeanManager(persistenceUtil);

    setBeanManager(persistentBeanManager);
  }

  /**
   * @see org.mklab.taskit.shared.service.HibernateSampleService#accessThroughHibernate()
   */
  @Override
  public List<Test> accessThroughHibernate() {
    return this.testDao.list();
  }

}
