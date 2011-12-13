/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
