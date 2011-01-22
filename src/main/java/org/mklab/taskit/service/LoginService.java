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
@RemoteServiceRelativePath("account")
public interface LoginService extends RemoteService {

  /**
   * ログインします。
   * 
   * @param id ID
   * @param password パスワード
   */
  void login(String id, String password);

  /**
   * ログアウトします。
   */
  void logout();

}
