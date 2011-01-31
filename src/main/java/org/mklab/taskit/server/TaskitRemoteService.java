/**
 * 
 */
package org.mklab.taskit.server;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.jpa.HibernateJpaUtil;
import net.sf.gilead.gwt.GwtConfigurationHelper;
import net.sf.gilead.gwt.PersistentRemoteService;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
class TaskitRemoteService extends PersistentRemoteService {

  /** */
  private static final long serialVersionUID = -6742863973521111961L;

  private static EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("taskit"); //$NON-NLS-1$  
  }

  /**
   * {@link TaskitRemoteService}オブジェクトを構築します。
   */
  TaskitRemoteService() {
    final HibernateJpaUtil persistenceUtil = new HibernateJpaUtil(entityManagerFactory);
    final PersistentBeanManager persistentBeanManager = GwtConfigurationHelper.initGwtStatelessBeanManager(persistenceUtil);

    setBeanManager(persistentBeanManager);
  }

  protected final HttpSession getSession() {
    return getThreadLocalRequest().getSession(false);
  }

  protected final HttpSession getSessionNonNull() {
    return getThreadLocalRequest().getSession(true);
  }

  protected EntityManager createEntityManager() {
    return entityManagerFactory.createEntityManager();
  }

}
