/**
 * 
 */
package org.mklab.taskit.shared.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * アカウントに関するサービスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 26, 2011
 */
@RemoteServiceRelativePath("account")
public interface AccountService extends RemoteService {

  /**
   * 新しいアカウントを作成します。
   * 
   * @param userId ユーザーID
   * @param password パスワード
   * @param accountType アカウント種別
   */
  void createNewAccount(String userId, String password, String accountType);

  /**
   * 全ての学生の学籍番号を取得します。
   * 
   * @return 学籍番号の配列
   */
  String[] getAllStudentIDs();

}
