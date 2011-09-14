package org.mklab.taskit.server.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



/**
 * @author ishikura
 * @version $Revision$, 2011/09/14
 * @param <E> DAOの型
 */
public abstract class DaoFactory<E extends Dao> {

  private static EntityManagerFactory entityManagerFactory;

  static {
    entityManagerFactory = Persistence.createEntityManagerFactory("taskit"); //$NON-NLS-1$  
  }

  private EntityManager createEntityManager() {
    return entityManagerFactory.createEntityManager();
  }

  /**
   * DAOを生成します。
   * 
   * @return DAO
   */
  public E create() {
    return create(createEntityManager());
  }

  protected abstract E create(EntityManager entityManager);

}
