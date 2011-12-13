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

import org.mklab.taskit.shared.UserProxy;

import com.google.gwt.user.client.ui.Widget;


/**
 * ページを整形するクラスです。
 * 
 * @author ishikura
 */
public interface PageLayout {

  /**
   * ページを構築します。
   * 
   * @param taskitView メインコンテンツ
   * @param loginUser ログインユーザー
   * @return ページ
   */
  Widget layout(TaskitView taskitView, UserProxy loginUser);

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  void setPresenter(Presenter presenter);

  /**
   * プレゼンターです。
   * 
   * @author ishikura
   */
  public static interface Presenter {

    /**
     * ログアウトします。
     */
    void logout();
  }

}
