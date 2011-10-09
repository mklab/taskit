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
   * メッセージダイアログを表示します。
   * <p>
   * このメソッドは、ユーザーによる入力を受けるまでスレッドをブロックします。
   * 
   * @param message メッセージ
   */
  void showInformationDialog(String message);

  /**
   * エラーダイアログを表示します。
   * <p>
   * このメソッドは、ユーザーによる入力を受けるまでスレッドをブロックします。
   * 
   * @param message エラーメッセージ
   */
  void showErrorDialog(String message);

  /**
   * 情報メッセージを表示します。
   * 
   * @param message 情報メッセージ
   */
  void showInformationMessage(String message);

  /**
   * エラーメッセージを表示します。
   * 
   * @param message エラーメッセージ
   */
  void showErrorMessage(String message);

}
