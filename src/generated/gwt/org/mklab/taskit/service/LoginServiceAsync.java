package org.mklab.taskit.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


/**
 * {@link LoginService}の非同期実行インターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public interface LoginServiceAsync {

  /**
   * GWT-RPC service asynchronous (client-side) interface
   * 
   * @param id ID
   * @param password パスワード
   * @param callback コールバック
   * @see org.mklab.taskit.service.LoginService
   */
  void login(java.lang.String id, java.lang.String password, AsyncCallback<Void> callback);

  /**
   * GWT-RPC service asynchronous (client-side) interface
   * 
   * @param callback コールバック
   * @see org.mklab.taskit.service.LoginService
   */
  void logout(AsyncCallback<Void> callback);

  /**
   * Utility class to get the RPC Async interface from client-side code
   */
  public static final class Util {

    private static LoginServiceAsync instance;

    /**
     * サービスのインスタンスを取得します。
     * 
     * @return インスタンス
     */
    public static final LoginServiceAsync getInstance() {
      if (instance == null) {
        instance = (LoginServiceAsync)GWT.create(LoginService.class);
        ServiceDefTarget target = (ServiceDefTarget)instance;
        target.setServiceEntryPoint(GWT.getModuleBaseURL() + "LoginService"); //$NON-NLS-1$
      }
      return instance;
    }

    private Util() {
      // Utility class should not be instanciated
    }
  }
}
