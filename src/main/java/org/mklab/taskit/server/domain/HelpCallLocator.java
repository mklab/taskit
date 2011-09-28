/**
 * 
 */
package org.mklab.taskit.server.domain;

/**
 * @author ishikura
 */
public class HelpCallLocator extends AbstractLocator<Account, String> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<Account> getDomainType() {
    return Account.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<String> getIdType() {
    return String.class;
  }

}
