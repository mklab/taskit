package org.mklab.taskit.server.domain;

/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
public class ReportLocator extends AbstractLocator<Report, Integer> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Report> getDomainType() {
    return Report.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Integer> getIdType() {
    return Integer.class;
  }

}
