/**
 * 
 */
package org.mklab.taskit.client.event;

import com.google.gwt.event.shared.EventHandler;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public interface LoginHandler extends EventHandler {

  /**
   * ログインしたときに呼び出されます。
   * 
   * @param evt イベントオブジェクト
   */
  void onLogin(LoginEvent evt);

}
