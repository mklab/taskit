/**
 * 
 */
package org.mklab.taskit.service;


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
   * ログインします。
   * 
   * @param id ID
   * @param password パスワード
   * @throws LoginFailureException ログインに失敗した場合
   */
  void login(String id, String password) throws LoginFailureException;

  /**
   * ログアウトします。
   */
  void logout();

}
