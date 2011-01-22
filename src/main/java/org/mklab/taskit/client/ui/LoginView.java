/**
 * 
 */
package org.mklab.taskit.client.ui;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;


/**
 * ログインビューを表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public interface LoginView extends IsWidget {

  /**
   * IDを取得します。
   * 
   * @return ID
   */
  String getId();

  /**
   * パスワードを取得します。
   * 
   * @return パスワード
   */
  String getPassword();

  /**
   * submitボタンを取得します。
   * 
   * @return サブミットボタン
   */
  HasClickHandlers getSubmitButton();

  /**
   * このログインビューにフォーカスします。
   */
  void requestFocus();

}
