/**
 * 
 */
package org.mklab.taskit.server.dao;


/**
 * 課題の作成に失敗したときにスローされる例外です。
 * @author teshima
 * @version $Revision$, Jan 26, 2011
 */
public class ReportRegistrationException extends Exception{
  
  /** */
  private static final long serialVersionUID = 652378489741010L;

  /**
   * {@link ReportRegistrationException}オブジェクトを構築します。
   * @param message 例外メッセージ
   * 
   */
  public ReportRegistrationException(String message) {
    super(message);
  }

}
