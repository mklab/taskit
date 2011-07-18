/**
 * 
 */
package org.mklab.taskit.server;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
class TaskitRemoteService extends RemoteServiceServlet {

  /** */
  private static final long serialVersionUID = -6742863973521111961L;

  private static EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("taskit"); //$NON-NLS-1$  
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
