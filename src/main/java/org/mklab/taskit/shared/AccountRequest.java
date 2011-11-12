package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Account;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @see Account
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
   * パスワードを変更します(管理者用)。
   * 
   * @param account 変更するアカウント
   * @param newPassword 新しいパスワード
   * @return リクエスト
   */
  Request<Void> changePassword(AccountProxy account, String newPassword);

  /**
   * 自分のパスワードを変更します。
   * 
   * @param oldPassword 古いパスワード
   * @param newPassword 新しいパスワード
   * @return リクエスト
   */
  Request<Void> changeMyPassword(String oldPassword, String newPassword);

}
