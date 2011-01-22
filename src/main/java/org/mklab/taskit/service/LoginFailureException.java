/**
 * 
 */
package org.mklab.taskit.service;

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
   * {@link LoginFailureException}オブジェクトを構築します。
   */
  public LoginFailureException() {
    // empty
  }

  /**
   * {@link LoginFailureException}オブジェクトを構築します。
   * 
   * @param message メッセージ
   */
  public LoginFailureException(String message) {
    super(message);
  }

}
