package org.mklab.taskit.server.dao;

import javax.persistence.EntityManager;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/14
 */
public class ReportDaoFactory extends DaoFactory<ReportDao> {

  /**
   * {@inheritDoc}
   */
  @Override
  protected ReportDao create(EntityManager entityManager) {
    return new ReportDaoImpl(entityManager);
  }

}
