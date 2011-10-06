/**
 * 
 */
package org.mklab.taskit.client.ui;

import com.google.gwt.user.client.ui.IsWidget;


/**
 * TASKitのベースのビューを表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 24, 2011
 */
public interface TaskitView extends IsWidget {

  /**
   * メッセージを表示します。
   * 
   * @param message メッセージ
   */
  void showInformationMessage(String message);

  /**
   * エラーメッセージを表示します。
   * 
   * @param message エラーメッセージ
   */
  void showErrorMessage(String message);

}
