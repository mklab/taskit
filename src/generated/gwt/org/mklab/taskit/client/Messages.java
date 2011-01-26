package org.mklab.taskit.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/home/yuhi/workspace/taskit/src/main/resources/org/mklab/taskit/client/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Account Type".
   * 
   * @return translated "Account Type"
   */
  @DefaultMessage("Account Type")
  @Key("accountTypeLabel")
  String accountTypeLabel();

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
   * Translated "Logged in successfully.".
   * 
   * @return translated "Logged in successfully."
   */
  @DefaultMessage("Logged in successfully.")
  @Key("loginSuccessMessage")
  String loginSuccessMessage();

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

  /**
   * Translated "Password(for confirmation)".
   * 
   * @return translated "Password(for confirmation)"
   */
  @DefaultMessage("Password(for confirmation)")
  @Key("passwordLabelForConfirmation")
  String passwordLabelForConfirmation();

  /**
   * Translated "Register".
   * 
   * @return translated "Register"
   */
  @DefaultMessage("Register")
  @Key("registerButton")
  String registerButton();

  /**
   * Translated "Student".
   * 
   * @return translated "Student"
   */
  @DefaultMessage("Student")
  @Key("studentLabel")
  String studentLabel();

  /**
   * Translated "TA".
   * 
   * @return translated "TA"
   */
  @DefaultMessage("TA")
  @Key("taLabel")
  String taLabel();

  /**
   * Translated "Teacher".
   * 
   * @return translated "Teacher"
   */
  @DefaultMessage("Teacher")
  @Key("teacherLabel")
  String teacherLabel();
}
