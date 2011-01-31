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
public interface AccountDao {

  /**
   * 与えられたIDに対応する、ハッシュ関数にかけられたパスワードを取得します。
   * 
   * @param id ID
   * @return ハッシュ関数にかけられたパスワード。IDが存在しない場合はnullを返す。
   */
  public Account getHashedPasswordIfExists(String id);

  /**
   * アカウントを新規作成します。
   * 
   * @param id ID
   * @param hashedPassword ハッシュ関数にかけられたパスワード
   * @param type アカウント種別
   * @throws AccountRegistrationException IDがすでに存在する、またはpasswordが不正で登録できない場合
   */
  public void registerAccount(String id, String hashedPassword, String type) throws AccountRegistrationException;

  /**
   * 生徒全員のIDを取得します。
   * 
   * @return 生徒全員のID
   */
  public List<String> getAllStudentIDs();

}
