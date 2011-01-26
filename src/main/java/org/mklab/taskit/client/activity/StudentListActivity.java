/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.StudentListView;
import org.mklab.taskit.client.ui.StudentListViewImpl;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.service.AccountService;
import org.mklab.taskit.shared.service.AccountServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 23, 2011
 */
public class StudentListActivity extends TaskitActivity implements StudentListView.Presenter {

  /**
   * {@link StudentListActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public StudentListActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * @see org.mklab.taskit.client.activity.TaskitActivity#createTaskitView(org.mklab.taskit.client.ClientFactory)
   */
  @Override
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    final StudentListView list = new StudentListViewImpl(clientFactory);
    list.setPresenter(this);

    final AccountServiceAsync service = GWT.create(AccountService.class);
    service.getAllStudentIDs(new AsyncCallback<String[]>() {

      @Override
      public void onSuccess(String[] result) {
        list.setListData(result);
      }

      @Override
      public void onFailure(Throwable caught) {
        list.setListData(new String[] {"Could'nt fetch list data."}); //$NON-NLS-1$
        showErrorMessage(caught.getMessage());
      }
    });
    return list;
  }

  /**
   * @see org.mklab.taskit.client.ui.StudentListView.Presenter#listDataClicked(java.lang.String)
   */
  @Override
  public void listDataClicked(String text) {
    Window.alert("clicked : " + text);
  }
}
