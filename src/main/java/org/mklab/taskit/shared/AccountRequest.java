package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Account;
import org.mklab.taskit.shared.model.UserType;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/18
 */
@Service(value = Account.class)
public interface AccountRequest extends RequestContext {

  /**
   * 新規アカウントを作成します。
   * 
   * @param id アカウントID
   * @param password パスワード
   * @param userType ユーザー種別
   * @return リクエスト
   */
  Request<Void> registerNewAccount(String id, String password, UserType userType);

  /**
   * 与えられたIDのアカウントの登録を抹消します。
   * 
   * @param id アカウントID
   * @return リクエスト
   */
  Request<Void> unregisterAccount(String id);

  /**
   * IDからアカウントを取得します。
   * 
   * @param id アカウントID
   * @return アカウント。存在しなければnull
   */
  Request<AccountProxy> getAccountById(String id);

  /**
   * ログインを行います。
   * 
   * @param id アカウントID
   * @param password パスワード
   * @return ログインユーザーデータ
   */
  Request<UserProxy> login(String id, String password);

  /**
   * ログアウトを行います。
   * 
   * @return リクエスト
   */
  Request<Void> logout();

  /**
   * パスワードを変更します。
   * 
   * @param newPassword 新しいパスワード
   * @return リクエスト
   */
  InstanceRequest<AccountProxy, Void> changePassword(String newPassword);

}
