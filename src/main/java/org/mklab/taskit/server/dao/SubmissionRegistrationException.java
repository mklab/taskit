/**
 * 
 */
package org.mklab.taskit.server.dao;

/**
 * 提出物の新規登録に失敗したときにスローされる例外です。
 * 
 * @author teshima
 * @version $Revision$, Feb 7, 2011
 */
public class SubmissionRegistrationException extends Exception {

  /** */
  private static final long serialVersionUID = 9187478402L;

  /**
   * {@link SubmissionRegistrationException}オブジェクトを構築します。
   * 
   * @param message 例外メッセージ
   */
  public SubmissionRegistrationException(String message) {
    super(message);
  }

}
