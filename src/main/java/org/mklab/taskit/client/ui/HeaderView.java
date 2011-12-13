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
   * ボタンを追加します。
   * 
   * @param name ボタン名
   * @return 生成したボタン
   */
  ToolBarButton addButton(String name);

  /**
   * ボタンの間にセパレータを追加します。
   */
  void addSeparator();

  /**
   * 高さをptで取得します。
   * 
   * @return 高さ
   */
  int getHeight();

}
