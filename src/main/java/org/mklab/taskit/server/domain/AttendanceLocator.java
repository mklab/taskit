package org.mklab.taskit.server.domain;

/**
 * @author ishikura
 * @version $Revision$, 2011/09/20
 */
public class AttendanceLocator extends AbstractLocator<Attendance, Integer> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Attendance> getDomainType() {
    return Attendance.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Integer> getIdType() {
    return Integer.class;
  }

}
