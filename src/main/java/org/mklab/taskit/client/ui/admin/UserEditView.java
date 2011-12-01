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
package org.mklab.taskit.client.ui.admin;

import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.UserType;

import java.util.List;


/**
 * ユーザー編集を行うビューを表すインターフェースです。
 * 
 * @author Yuhi Ishikura
 */
public interface UserEditView extends TaskitView {

  /**
   * 編集可能なユーザーの一覧を設定します。
   * 
   * @param users 編集可能なユーザーの一覧
   */
  void setUserList(List<UserProxy> users);

  /**
   * 与えられたユーザーの編集を行います。
   * 
   * @param user ユーザー
   */
  void setEdittingUser(UserProxy user);

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  void setPresenter(Presenter presenter);

  /**
   * {@link UserEditView}で利用するプレゼンターです。
   * 
   * @author Yuhi Ishikura
   */
  public static interface Presenter {

    /**
     * 新たなユーザーを生成します。
     * 
     * @param id ユーザーのID
     * @param password パスワード
     * @param userType ユーザー種別
     */
    void createUser(String id, String password, UserType userType);

    /**
     * パスワードを変更します。
     * 
     * @param id 変更するユーザーのID
     * @param password パスワード
     */
    void changePassword(String id, String password);

    /**
     * ユーザー名を変更します。
     * 
     * @param id 変更するユーザーのID
     * @param userName ユーザー名
     */
    void changeUserName(String id, String userName);

  }

}
