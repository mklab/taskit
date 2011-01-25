/**
 * 
 */
package org.mklab.taskit.client.ui;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.IsWidget;


/**
 * ページ上部のヘッダを表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 24, 2011
 */
public interface HeaderView extends IsWidget {

  /**
   * ユーザーIDを設定します。
   * 
   * @param id ユーザーID
   */
  void setUserId(String id);

  /**
   * ユーザー種別を設定します。
   * 
   * @param type ユーザー種別
   */
  void setUserType(String type);

  /**
   * 管理者ビューのリンクを取得します。
   * 
   * @return 管理者ビューのリンク
   */
  HasClickHandlers getAdminLink();

  /**
   * ログアウトボタンを取得します。
   * 
   * @return ログアウトボタン
   */
  HasClickHandlers getLogoutTrigger();

}
