package org.mklab.taskit.server.domain;

/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
public class UserLocator extends AbstractLocator<User, String> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<User> getDomainType() {
    return User.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<String> getIdType() {
    return String.class;
  }

}
