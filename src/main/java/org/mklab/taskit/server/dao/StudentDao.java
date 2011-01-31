/**
 * 
 */
package org.mklab.taskit.server.dao;

/**
 * Student関連のDaoです。
 * 
 * @author teshima
 * @version $Revision$, Jan 28, 2011
 */
public interface StudentDao {

  /**
   * アカウントのIDから学籍番号を取得します。
   * 
   * @param accountId アカウントのID
   * @return 学籍番号を返す。
   */
  public String getStudentNo(String accountId);

}
