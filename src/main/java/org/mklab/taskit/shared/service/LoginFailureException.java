/**
 * 
 */
package org.mklab.taskit.shared.service;

/**
 * ログインに失敗したときに発生する例外です。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class LoginFailureException extends Exception {

  /** for serialization. */
  private static final long serialVersionUID = 6508320452347972829L;

  /**
   * この例外の原因の種別を表す列挙型です。
   * 
   * @author Yuhi Ishikura
   * @version $Revision$, Jan 22, 2011
   */
  public static enum ErrorCode {
    /**
     * IDが存在しないことを表す定数です。
     */
    ID_NOT_EXISTS,
    /**
     * パスワードが誤っていることを表す定数です。
     */
    WRONG_PASSWORD,
    /**
     * 不正なIDであることを表す定数です。
     */
    INVALID_ID,
    /**
     * 不正なパスワードであることを表す定数です。
     */
    INVALID_PASSWORD
  }

  private ErrorCode errorCode;

  /**
   * {@link LoginFailureException}オブジェクトを構築します。
   */
  public LoginFailureException() {
    // empty
  }

  /**
   * {@link LoginFailureException}オブジェクトを構築します。
   * 
   * @param errorCode エラーコード
   */
  public LoginFailureException(ErrorCode errorCode) {
    super("Failed to login. : " + errorCode); //$NON-NLS-1$
    this.errorCode = errorCode;
  }

  /**
   * エラーの種別を取得します。
   * 
   * @return errorCode
   */
  public ErrorCode getErrorCode() {
    return this.errorCode;
  }

}
