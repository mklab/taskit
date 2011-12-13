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

import com.google.gwt.event.dom.client.ClickHandler;


/**
 * @author ishikura
 */
public interface ToolBarButton {

  /**
   * クリック処理を設定します。
   * 
   * @param h クリック処理
   */
  void setClickHandler(ClickHandler h);

  /**
   * ボタン名を設定します。
   * 
   * @param name ボタン名
   */
  void setName(String name);

  /**
   * アイコンを設定します。
   * 
   * @param icon アイコン
   */
  void setIcon(String icon);

}
