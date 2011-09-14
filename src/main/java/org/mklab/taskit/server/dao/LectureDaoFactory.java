package org.mklab.taskit.server.dao;

import javax.persistence.EntityManager;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/14
 */
public class LectureDaoFactory extends DaoFactory<LectureDao> {

  /**
   * {@inheritDoc}
   */
  @Override
  protected LectureDao create(EntityManager entityManager) {
    return new LectureDaoImpl(entityManager);
  }

}
