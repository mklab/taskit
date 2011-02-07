/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ui.event.ClickHandler;

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
   * 学生一覧ビューのリンクを監視します。
   * 
   * @param h リスナ
   */
  void addStudentListLinkClickHandler(ClickHandler h);

  /**
   * 出席一覧ビューのリンクを監視します。
   * 
   * @param h リスナ
   */
  void addAttendanceListLinkClickHandler(ClickHandler h);

  /**
   * 管理者ビューのリンクを監視します。
   * 
   * @param h リスナ
   */
  void addAdminLinkClickHandler(ClickHandler h);

  /**
   * ログアウトボタンの監視をします。
   * 
   * @param h リスナ
   */
  void addLogoutLinkClickHandler(ClickHandler h);

}
