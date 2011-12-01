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


/**
 * @author ishikura
 */
public interface ProfileView extends TaskitView {

  /**
   * 編集するユーザーデータを設定します。
   * 
   * @param user ユーザーデータ
   */
  void setUser(UserProxy user);

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  void setPresenter(Presenter presenter);

  /**
   * {@link ProfileView}で利用するプレゼンターです。
   * 
   * @author ishikura
   */
  public static interface Presenter {

    /**
     * パスワードを変更します。
     * 
     * @param currentPassword 現在のパスワード
     * @param newPassword 新しいパスワード
     */
    void changePassword(String currentPassword, String newPassword);

    /**
     * ユーザー名を設定します。
     * 
     * @param name ユーザー名
     */
    void changeUserName(String name);

  }

}
