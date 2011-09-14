package org.mklab.taskit.server.dao;

import javax.persistence.EntityManager;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/14
 */
public class AttendanceDaoFactory extends DaoFactory<AttendanceDao> {

  /**
   * {@inheritDoc}
   */
  @Override
  protected AttendanceDao create(EntityManager entityManager) {
    return new AttendanceDaoImpl(entityManager);
  }

}
