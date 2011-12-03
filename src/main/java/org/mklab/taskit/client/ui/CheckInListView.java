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

import org.mklab.taskit.shared.CheckMapProxy;

import java.util.List;


/**
 * 誰がどの生徒を担当しているかどうかの一覧を表示するビューです。
 * 
 * @author Yuhi Ishikura
 */
public interface CheckInListView extends TaskitView {

  /**
   * チェックリストを設定します。
   * 
   * @param checks チェックリスト
   */
  void setCheckInList(List<CheckMapProxy> checks);

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  void setPresenter(Presenter presenter);

  /**
   * {@link CheckInListView}のプレゼンターです。
   * 
   * @author Yuhi Ishikura
   */
  public static interface Presenter {

    /**
     * 更新ボタンが押されたときに呼び出されます。
     */
    void reloadButtonPressed();
  }

}
