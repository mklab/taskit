/**
 * 
 */
package org.mklab.taskit.shared.service;

/**
 * サービスの実行中に問題が発生した場合にスローされる例外です。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 8, 2011
 */
public class ServiceException extends Exception {

  /** for serialization. */
  private static final long serialVersionUID = 457380039586670324L;

  /**
   * {@link ServiceException}オブジェクトを構築します。
   */
  public ServiceException() {
    super();
  }

  /**
   * {@link ServiceException}オブジェクトを構築します。
   * 
   * @param message メッセージ
   * @param cause 原因の例外
   */
  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * {@link ServiceException}オブジェクトを構築します。
   * 
   * @param message メッセージ
   */
  public ServiceException(String message) {
    super(message);
  }

  /**
   * {@link ServiceException}オブジェクトを構築します。
   * 
   * @param cause 原因の例外
   */
  public ServiceException(Throwable cause) {
    super(cause);
  }

}
