/**
 * 
 */
package org.mklab.taskit.server.dao;

/**
 * IPアドレスのデータベースへの登録に失敗時にスローされる例外です。
 * 
 * @author teshima
 * @version $Revision$, Mar 14, 2011
 */
public class IpAddressRegistrationException extends Exception {

  private static final long serialVersionUID = 48103922184901757L;

  /**
   * {@link IpAddressRegistrationException}オブジェクトを構築します。
   * 
   * @param message 例外メッセージ
   */
  public IpAddressRegistrationException(String message) {
    super(message);
  }
}
