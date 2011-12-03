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
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.LocalDatabase;
import org.mklab.taskit.client.Messages;
import org.mklab.taskit.client.ui.ProfileView;
import org.mklab.taskit.client.ui.TaskitView;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * プロフィールの表示・変更を行うアクティビティです。
 * 
 * @author Yuhi Ishikura
 */
public class ProfileActivity extends TaskitActivity implements ProfileView.Presenter {

  /**
   * {@link ProfileActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public ProfileActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    final ProfileView profileView = clientFactory.getProfileView();
    profileView.setPresenter(this);

    return profileView;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onViewShown() {
    super.onViewShown();
    ((ProfileView)getTaskitView()).setUser(getLoginUser());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void changePassword(String currentPassword, String password) {
    if (currentPassword.equals(password)) {
      showInformationDialog("Password not changed."); //$NON-NLS-1$
      return;
    }
    final Messages messages = getClientFactory().getMessages();
    getClientFactory().getRequestFactory().accountRequest().changeMyPassword(currentPassword, password).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        showInformationDialog(messages.changedPasswordMessage());
        clearLoginUserCache();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        showErrorMessage(messages.changedPasswordFailMessage() + ":" + error.getMessage()); //$NON-NLS-1$
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void changeUserName(String name) {
    final Messages messages = getClientFactory().getMessages();
    getClientFactory().getRequestFactory().userRequest().changeMyUserName(name).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        showInformationDialog(messages.changedUserNameMessage());
        clearLoginUserCache();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        showErrorMessage(messages.changedUserNameFailMessage() + ":" + error.getMessage()); //$NON-NLS-1$
      }
    });
  }

  /**
   * キャッシュしているログインユーザー情報をクリアします。
   * <p>
   * すなわち、次のアクティビティ遷移でユーザー情報を取得しなおすように要求します。
   */
  void clearLoginUserCache() {
    getClientFactory().getLocalDatabase().clearCache(LocalDatabase.LOGIN_USER);
  }

}
