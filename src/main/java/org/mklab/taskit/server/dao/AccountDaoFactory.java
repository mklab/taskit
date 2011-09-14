package org.mklab.taskit.server.dao;

import javax.persistence.EntityManager;

/**
 * @author ishikura
 * @version $Revision$, 2011/09/14
 */
public class AccountDaoFactory extends DaoFactory<AccountDao> {

  /**
   * {@inheritDoc}
   */
  @Override
  protected AccountDao create(EntityManager entityManager) {
    return new AccountDaoImpl(entityManager);
  }

}
