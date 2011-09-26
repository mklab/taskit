/**
 * 
 */
package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.domain.ServiceUtil.ServiceUtilImplementation;


/**
 * @author ishikura
 */
public class ServiceUtilImplementationForTest implements ServiceUtilImplementation {

  private User loginUser;

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isLoggedIn() {
    return this.loginUser != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public User getLoginUser() {
    return this.loginUser;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void login(User user) {
    this.loginUser = user;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void logout() {
    this.loginUser = null;
  }

}
