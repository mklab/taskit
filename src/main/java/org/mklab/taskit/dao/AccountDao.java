/**
 * 
 */
package org.mklab.taskit.dao;

import org.mklab.taskit.model.Account;


/**
 * Account関連のDAOです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public interface AccountDao {

  /**
   * 与えられたID、パスワードのペアに該当するアカウントを取得します。
   * 
   * @param id ID
   * @param password パスワード
   * @return 該当するアカウント。存在しない場合にはnullを返す。
   */
  public Account getAccount(String id, String password);

  /**
   * アカウントを新規作成します。
   * 
   * @param id ID
   * @param password パスワード
   * @throws AccountRegistrationException IDがすでに存在する、またはpasswordが不正で登録できない場合
   */
  public void createAccount(String id, String password) throws AccountRegistrationException;

}
