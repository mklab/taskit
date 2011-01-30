/**
 * 
 */
package org.mklab.taskit.shared.service;

/**
 * アカウントの作成に失敗したときにスローされる例外です。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class AccountRegistrationException extends Exception {

  /** for serialization. */
  private static final long serialVersionUID = 1420543210165710530L;

  /**
   * {@link AccountRegistrationException}オブジェクトを構築します。
   */
  AccountRegistrationException() {
    // for serialization
  }

  /**
   * {@link AccountRegistrationException}オブジェクトを構築します。
   * 
   * @param message メッセージ
   */
  public AccountRegistrationException(String message) {
    super(message);
  }

}
