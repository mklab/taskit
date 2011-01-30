/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.AttendenceListView;
import org.mklab.taskit.client.ui.AttendenceListViewImpl;
import org.mklab.taskit.client.ui.TaskitView;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
public class AttendenceListActivity extends TaskitActivity implements AttendenceListView.Presenter {

  /**
   * {@link AttendenceListActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AttendenceListActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * @see org.mklab.taskit.client.activity.TaskitActivity#createTaskitView(org.mklab.taskit.client.ClientFactory)
   */
  @Override
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    final AttendenceListView view = new AttendenceListViewImpl(clientFactory);
    return view;
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendenceListView.Presenter#setAttendenceType(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public void setAttendenceType(String studentNo, String type) {
    // TODO Auto-generated method stub

  }

}
