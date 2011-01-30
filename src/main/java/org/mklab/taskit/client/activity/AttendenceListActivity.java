/**
 * 
 */
package org.mklab.taskit.client.activity;

import java.util.Iterator;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.AttendenceListView;
import org.mklab.taskit.client.ui.AttendenceListViewImpl;
import org.mklab.taskit.client.ui.TaskitView;

import com.google.gwt.user.client.Window;


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
    view.setPresenter(this);

    final String[] sample = new String[100];
    for (int i = 0; i < sample.length; i++) {
      sample[i] = String.valueOf(i + 10675001);
    }

    for (int i = 0; i < 100; i++) {
      view.setStudentNumber(i, String.valueOf(i + 10675003));
      view.setAttendenceType(i, (int)(Math.random() * 4));
    }

    return view;
  }

  /**
   * @see org.mklab.taskit.client.ui.AttendenceListView.Presenter#setAttendenceType(java.lang.String,
   *      java.lang.String)
   */
  @Override
  public void setAttendenceType(String studentNo, String type) {

  }

  /**
   * @see org.mklab.taskit.client.ui.AttendenceListView.Presenter#getChoosableAttendenceTypes()
   */
  @Override
  public String[] getChoosableAttendenceTypes() {
    return new String[] {"出席", "欠席", "病欠", "公欠"};
  }

}
