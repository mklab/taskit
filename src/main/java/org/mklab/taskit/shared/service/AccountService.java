/**
 * 
 */
package org.mklab.taskit.shared.service;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import com.google.gwt.user.client.rpc.RemoteService;


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

}
