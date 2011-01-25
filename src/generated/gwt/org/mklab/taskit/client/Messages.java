package org.mklab.taskit.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/home/yuhi/workspace/taskit/src/main/resources/org/mklab/taskit/client/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "User ID".
   * 
   * @return translated "User ID"
   */
  @DefaultMessage("User ID")
  @Key("idLabel")
  String idLabel();

  /**
   * Translated "Login".
   * 
   * @return translated "Login"
   */
  @DefaultMessage("Login")
  @Key("loginButton")
  String loginButton();

  /**
   * Translated "Logout".
   * 
   * @return translated "Logout"
   */
  @DefaultMessage("Logout")
  @Key("logoutButton")
  String logoutButton();

  /**
   * Translated "Password".
   * 
   * @return translated "Password"
   */
  @DefaultMessage("Password")
  @Key("passwordLabel")
  String passwordLabel();
}
