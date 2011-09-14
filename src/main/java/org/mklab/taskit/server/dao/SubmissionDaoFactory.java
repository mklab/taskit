package org.mklab.taskit.server.dao;

import javax.persistence.EntityManager;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/14
 */
public class SubmissionDaoFactory extends DaoFactory<SubmissionDao> {

  /**
   * {@inheritDoc}
   */
  @Override
  protected SubmissionDao create(EntityManager entityManager) {
    return new SubmissionDaoImpl(entityManager);
  }

}
