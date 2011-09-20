/**
 * 
 */
package org.mklab.taskit.server.domain;

/**
 * @author ishikura
 */
public class HelpCallLocator extends AbstractLocator<HelpCall, Integer> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<HelpCall> getDomainType() {
    return HelpCall.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Integer> getIdType() {
    return Integer.class;
  }

}
