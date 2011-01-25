/**
 * 
 */
package org.mklab.taskit.client.ui;

import com.google.gwt.event.dom.client.HasClickHandlers;


/**
 * 新しくアカウントを作成するビューを表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 25, 2011
 */
public interface NewAccountView {

  /**
   * 入力されているユーザーIDを取得します。
   * 
   * @return 入力されているユーザーID
   */
  String getUserId();

  /**
   * 入力されているパスワードを取得します。
   * 
   * @return 入力されているパスワード
   */
  String getPassword();

  /**
   * 入力されている確認パスワードを取得します。
   * 
   * @return 入力されているパスワード
   */
  String getPasswordForComfimation();

  /**
   * 入力されているアカウント種別を取得します。
   * 
   * @return アカウント種別
   */
  String getAccountType();

  /**
   * 決定ボタンを取得します。
   * 
   * @return 決定ボタン
   */
  HasClickHandlers getSubmitTrigger();

}
