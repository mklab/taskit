/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.client.ui.admin.UserEditView;
import org.mklab.taskit.client.ui.admin.UserEditorViewImpl;
import org.mklab.taskit.shared.AccountProxy;
import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.UserType;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * ユーザー編集を行う管理者用のアクティビティです。
 * 
 * @author Yuhi Ishikura
 */
public class UserEditActivity extends TaskitActivity implements UserEditView.Presenter {

  /**
   * {@link UserEditActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public UserEditActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    UserEditView view = new UserEditorViewImpl(clientFactory);
    view.setPresenter(this);
    return view;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onViewShown() {
    super.onViewShown();

    updateUserListAsync();
  }

  private void updateUserListAsync() {
    showInformationMessage(getClientFactory().getMessages().fetchingUserListMessage());
    getClientFactory().getRequestFactory().userRequest().getAllUsers().with("account").fire(new Receiver<List<UserProxy>>() { //$NON-NLS-1$

          @Override
          public void onSuccess(List<UserProxy> response) {
            showInformationMessage(getClientFactory().getMessages().fetchedUserListMessage());
            ((UserEditView)getTaskitView()).setUserList(response);
          }

          /**
           * {@inheritDoc}
           */
          @Override
          public void onFailure(ServerFailure error) {
            showErrorDialog(error.getMessage());
          }
        });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createUser(final String id, String password, UserType userType) {
    showInformationMessage(getClientFactory().getMessages().registeringUserMessage(id));
    getClientFactory().getRequestFactory().accountRequest().registerNewAccount(id, password, userType).fire(new Receiver<Void>() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        showInformationMessage(getClientFactory().getMessages().registeredUserMessage(id));
        updateUserListAsync();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        showErrorDialog(error.getMessage());
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void changePassword(String id, final String password) {
    showInformationMessage(getClientFactory().getMessages().changingPasswordMessage());
    getClientFactory().getRequestFactory().accountRequest().getAccountById(id).fire(new Receiver<AccountProxy>() {

      @Override
      public void onSuccess(AccountProxy response) {
        getClientFactory().getRequestFactory().accountRequest().changePassword(response, password).fire(new Receiver<Void>() {

          @SuppressWarnings({"hiding", "unused"})
          @Override
          public void onSuccess(Void response) {
            showInformationMessage(getClientFactory().getMessages().changedPasswordMessage());
          }

          /**
           * {@inheritDoc}
           */
          @Override
          public void onFailure(ServerFailure error) {
            showErrorDialog(error.getMessage());
          }
        });
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        showErrorDialog(error.getMessage());
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void changeUserName(String id, final String userName) {
    showInformationMessage(getClientFactory().getMessages().changingUsernameMessage());
    getClientFactory().getRequestFactory().userRequest().changeUserName(id, userName).fire(new Receiver<Void>() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onSuccess(@SuppressWarnings("unused") Void response) {
        showInformationMessage(getClientFactory().getMessages().changedUserNameMessage());
        updateUserListAsync();
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        showErrorDialog(error.getMessage());
      }
    });
  }

}
