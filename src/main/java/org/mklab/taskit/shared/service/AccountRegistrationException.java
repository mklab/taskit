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
   * エラーコードを表す列挙型です。
   * 
   * @author Yuhi Ishikura
   * @version $Revision$, Jan 30, 2011
   */
  public static enum ErrorCode {
    /**
     * ユーザー名がすでに存在した場合の問題を表す定数です。
     */
    USER_NAME_ALREADY_EXISTS,
    /**
     * 想定していない問題を表す定数です。
     */
    UNEXPECTED
  }

  private ErrorCode errorCode;

  /**
   * {@link AccountRegistrationException}オブジェクトを構築します。
   */
  AccountRegistrationException() {
    // for serialization
  }

  /**
   * {@link AccountRegistrationException}オブジェクトを構築します。
   * 
   * @param errorCode エラーコード
   */
  public AccountRegistrationException(ErrorCode errorCode) {
    super("Registration error : " + errorCode.name()); //$NON-NLS-1$
    this.errorCode = errorCode;
  }

  /**
   * エラーコードを取得します。
   * 
   * @return errorCode
   */
  public ErrorCode getErrorCode() {
    return this.errorCode;
  }

}
