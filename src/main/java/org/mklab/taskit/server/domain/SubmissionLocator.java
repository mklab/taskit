package org.mklab.taskit.server.domain;

/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
public class SubmissionLocator extends AbstractLocator<Submission, Integer> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Submission> getDomainType() {
    return Submission.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Integer> getIdType() {
    return Integer.class;
  }

}
