package org.mklab.taskit.server.domain;

/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
public class LectureLocator extends AbstractLocator<Lecture, Integer> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Lecture> getDomainType() {
    return Lecture.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Integer> getIdType() {
    return Integer.class;
  }

}
