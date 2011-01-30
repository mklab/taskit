package org.mklab.taskit.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	/home/yuhi/workspace/taskit/src/main/resources/org/mklab/taskit/client/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Absentee".
   * 
   * @return translated "Absentee"
   */
  @DefaultMessage("Absentee")
  @Key("absentLabel")
  String absentLabel();

  /**
   * Translated "Registered a new account {0} successfully.".
   * 
   * @return translated "Registered a new account {0} successfully."
   */
  @DefaultMessage("Registered a new account {0} successfully.")
  @Key("accountRegistrationSuccessMessage")
  String accountRegistrationSuccessMessage(String arg0);

  /**
   * Translated "Account Type".
   * 
   * @return translated "Account Type"
   */
  @DefaultMessage("Account Type")
  @Key("accountTypeLabel")
  String accountTypeLabel();

  /**
   * Translated "Attendee".
   * 
   * @return translated "Attendee"
   */
  @DefaultMessage("Attendee")
  @Key("attendedLabel")
  String attendedLabel();

  /**
   * Translated "Attendence Type".
   * 
   * @return translated "Attendence Type"
   */
  @DefaultMessage("Attendence Type")
  @Key("attendenceTypeLabel")
  String attendenceTypeLabel();

  /**
   * Translated "Authorized Absentee".
   * 
   * @return translated "Authorized Absentee"
   */
  @DefaultMessage("Authorized Absentee")
  @Key("authorizedAbsenceLabel")
  String authorizedAbsenceLabel();

  /**
   * Translated "Login automatically".
   * 
   * @return translated "Login automatically"
   */
  @DefaultMessage("Login automatically")
  @Key("autoLoginCheck")
  String autoLoginCheck();

  /**
   * Translated "User ID equals to password.".
   * 
   * @return translated "User ID equals to password."
   */
  @DefaultMessage("User ID equals to password.")
  @Key("idEqualsPasswordMessage")
  String idEqualsPasswordMessage();

  /**
   * Translated "User ID".
   * 
   * @return translated "User ID"
   */
  @DefaultMessage("User ID")
  @Key("idLabel")
  String idLabel();

  /**
   * Translated "User ID is too short.".
   * 
   * @return translated "User ID is too short."
   */
  @DefaultMessage("User ID is too short.")
  @Key("idTooShortMessage")
  String idTooShortMessage();

  /**
   * Translated "Illness".
   * 
   * @return translated "Illness"
   */
  @DefaultMessage("Illness")
  @Key("illnessLabel")
  String illnessLabel();

  /**
   * Translated "{0} is blank.".
   * 
   * @return translated "{0} is blank."
   */
  @DefaultMessage("{0} is blank.")
  @Key("isBlankMessage")
  String isBlankMessage(String arg0);

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
   * Translated "Passwords are difference.".
   * 
   * @return translated "Passwords are difference."
   */
  @DefaultMessage("Passwords are difference.")
  @Key("passwordConfirmationFailMessage")
  String passwordConfirmationFailMessage();

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
   * Translated "Password is too short.".
   * 
   * @return translated "Password is too short."
   */
  @DefaultMessage("Password is too short.")
  @Key("passwordTooShortMessage")
  String passwordTooShortMessage();

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
   * Translated "Student No.".
   * 
   * @return translated "Student No."
   */
  @DefaultMessage("Student No.")
  @Key("studentNoLabel")
  String studentNoLabel();

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

  /**
   * Translated "Unexpected error occured.".
   * 
   * @return translated "Unexpected error occured."
   */
  @DefaultMessage("Unexpected error occured.")
  @Key("unexpectedErrorMessage")
  String unexpectedErrorMessage();

  /**
   * Translated "User name was already exists.".
   * 
   * @return translated "User name was already exists."
   */
  @DefaultMessage("User name was already exists.")
  @Key("userNameAlreadyExistsMessage")
  String userNameAlreadyExistsMessage();
}
