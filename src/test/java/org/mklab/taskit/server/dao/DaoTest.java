/**
 * 
 */
package org.mklab.taskit.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
public abstract class DaoTest {

  private static EntityManagerFactory factory;

  static {
    factory = Persistence.createEntityManagerFactory("taskit-test"); //$NON-NLS-1$
  }

  protected EntityManager createEntityManager() {
    return factory.createEntityManager();
  }

}
