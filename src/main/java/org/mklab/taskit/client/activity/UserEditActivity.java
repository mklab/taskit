/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.LocalDatabase;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.client.ui.admin.ReportEditorView;
import org.mklab.taskit.client.ui.admin.UserEditView;
import org.mklab.taskit.client.ui.admin.UserEditorViewImpl;
import org.mklab.taskit.shared.UserProxy;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;


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

    getClientFactory().getLocalDatabase().getCacheOrExecute(LocalDatabase.STUDENT_LIST, new Receiver<List<UserProxy>>() {

      @Override
      public void onSuccess(List<UserProxy> response) {
        ((UserEditView)getTaskitView()).setUserList(response);
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void apply(UserProxy user) {
    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createUser(String id) {
    // TODO Auto-generated method stub

  }

}
