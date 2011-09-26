/**
 * 
 */
package org.mklab.taskit.server.domain;

/**
 * @author ishikura
 */
public class HelpCallLocator extends AbstractLocator<HelpCall, String> {

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
  public Class<String> getIdType() {
    return String.class;
  }

}
