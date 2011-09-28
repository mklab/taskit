package org.mklab.taskit.server.domain;

/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
public class UserLocator extends AbstractLocator<Account, String> {

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
