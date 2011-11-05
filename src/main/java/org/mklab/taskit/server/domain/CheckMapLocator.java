/**
 * 
 */
package org.mklab.taskit.server.domain;

/**
 * @author Yuhi Ishikura
 */
public class CheckMapLocator extends AbstractLocator<CheckMap, String> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<CheckMap> getDomainType() {
    return CheckMap.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<String> getIdType() {
    return String.class;
  }

}
