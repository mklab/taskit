/**
 * 
 */
package org.mklab.taskit.shared.service;

import org.mklab.taskit.shared.model.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * ログインサービスを提供するインターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {

  /**
   * ログインユーザーオブジェクトを取得します。
   * 
   * @return セッションにログイン情報があればそのユーザーを表すオブジェクト、ない場合はnull
   */
  User getLoginUser();

  /**
   * ログインします。
   * 
   * @param id ID
   * @param password パスワード
   * @return ログインに成功した場合はログインユーザーを表すモデル。失敗した場合は例外をスローするため返しません。
   * @throws LoginFailureException ログインに失敗した場合
   */
  User login(String id, String password) throws LoginFailureException;

  /**
   * ログアウトします。
   */
  void logout();

}
