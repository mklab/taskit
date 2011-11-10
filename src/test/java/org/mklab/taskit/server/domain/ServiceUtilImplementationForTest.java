/**
 * 
 */
package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.domain.ServiceUtil.ServiceUtilImplementation;

import de.novanic.eventservice.client.event.Event;
import de.novanic.eventservice.client.event.domain.Domain;


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

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unused")
  @Override
  public void registerClient(String clientId, User user) {
    // do nothing
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unused")
  @Override
  public void unregisterClient(String clientId) {
    // do nothing
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unused")
  @Override
  public void fireEvent(Domain domain, Event event, User user) {
    // do nothing
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unused")
  @Override
  public void fireEvent(Domain domain, Event event) {
    // do nothing
  }

}
