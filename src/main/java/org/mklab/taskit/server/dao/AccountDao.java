/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import org.mklab.taskit.shared.model.Account;
import org.mklab.taskit.shared.service.AccountRegistrationException;


/**
 * Account関連のDAOです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public interface AccountDao extends Dao {

  /**
   * 与えられたユーザー名に対応するアカウントデータを取得します。
   * 
   * @param userName ユーザー名
   * @return アカウントデータ。IDが存在しない場合はnullを返す。
   */
  public Account getAccountIfExists(String userName);

  /**
   * アカウントを新規作成します。
   * 
   * @param userName ユーザー名
   * @param hashedPassword ハッシュ関数にかけられたパスワード
   * @param type アカウント種別
   * @throws AccountRegistrationException IDがすでに存在する、またはpasswordが不正で登録できない場合
   */
  public void registerAccount(String userName, String hashedPassword, String type) throws AccountRegistrationException;

  /**
   * 生徒全員のIDを取得します。
   * 
   * @return 生徒全員のID
   */
  public List<String> getAllStudentUserNames();
}
