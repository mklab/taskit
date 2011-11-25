/**
 * 
 */
package org.mklab.taskit.client.event;

/**
 * タイムアウトから復帰できなかった場合にスローされるチェック例外です。
 * 
 * @author Yuhi Ishikura
 */
public class TimeoutRecoveryFailureException extends Exception {

  /** for serialization. */
  private static final long serialVersionUID = -7870510666563269293L;

  /**
   * {@link TimeoutRecoveryFailureException}オブジェクトを構築します。
   * 
   * @param message メッセージ
   * @param cause 原因の例外
   */
  public TimeoutRecoveryFailureException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * {@link TimeoutRecoveryFailureException}オブジェクトを構築します。
   * 
   * @param message メッセージ
   */
  public TimeoutRecoveryFailureException(String message) {
    super(message);
  }

}
