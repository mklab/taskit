/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.StudentScore;
import org.mklab.taskit.client.ui.StudentListView;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.UserProxy;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Receiver;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 23, 2011
 */
public class StudentListActivity extends TaskitActivity implements StudentListView.Presenter {

  private String[] userNames;

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
    final StudentListView list = clientFactory.getStudentListView();
    list.setPresenter(this);

    getClientFactory().getRequestFactory().userRequest().getAllStudents().fire(new Receiver<List<UserProxy>>() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onSuccess(List<UserProxy> arg0) {
        final String[] names = new String[arg0.size()];
        for (int i = 0; i < names.length; i++) {
          names[i] = arg0.get(i).getId();
        }
        StudentListActivity.this.userNames = names;
        list.setListData(names);
      }

    });
    return list;
  }

  /**
   * @see org.mklab.taskit.client.ui.StudentListView.Presenter#listDataClicked(int)
   */
  @Override
  public void listDataClicked(int index) {
    getClientFactory().getPlaceController().goTo(new StudentScore(this.userNames[index]));
  }

}
