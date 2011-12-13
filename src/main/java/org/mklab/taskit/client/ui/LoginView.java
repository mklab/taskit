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
   * 自動ログインが有効でるかどうか調べます。
   * 
   * @return 自動ログインが有効かどうか
   */
  boolean isAutoLoginEnabled();

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

  /**
   * 状態を表示します。
   * <p>
   * 例えば、ログインに失敗した場合にその理由を表示します。
   * 
   * @param status ステータス文字列
   */
  void setStatusText(String status);

}
