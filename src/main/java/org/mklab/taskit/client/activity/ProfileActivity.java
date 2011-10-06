/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.ProfileView;
import org.mklab.taskit.client.ui.TaskitView;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * @author ishikura
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

    profileView.setUser(getLoginUser());

    return profileView;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void changePassword(String currentPassword, String password) {
    if (currentPassword.equals(password)) {
      showInformationMessage("Password not changed."); //$NON-NLS-1$
      return;
    }
    getClientFactory().getRequestFactory().accountRequest().changeMyPassword(currentPassword, password).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        showInformationMessage("Password was changed successfully!"); //$NON-NLS-1$
        clearLoginUserCache();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        showErrorMessage(error.getMessage());
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void changeUserName(String name) {
    getClientFactory().getRequestFactory().userRequest().changeUserName(name).fire(new Receiver<Void>() {

      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        showInformationMessage("User name was changed successfully!"); //$NON-NLS-1$
        clearLoginUserCache();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        showErrorMessage(error.getMessage());
      }
    });
  }

}
