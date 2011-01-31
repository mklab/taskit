/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.StudentScore;
import org.mklab.taskit.client.ui.StudentScoreView;
import org.mklab.taskit.client.ui.StudentScoreViewImpl;
import org.mklab.taskit.client.ui.TaskitView;

import com.google.gwt.place.shared.Place;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 31, 2011
 */
public class StudentScoreActivity extends TaskitActivity {

  /**
   * {@link StudentScoreActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public StudentScoreActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * @see org.mklab.taskit.client.activity.TaskitActivity#createTaskitView(org.mklab.taskit.client.ClientFactory)
   */
  @Override
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    final Place place = clientFactory.getPlaceController().getWhere();
    if (place instanceof StudentScore == false) throw new IllegalStateException();

    final int accountId = ((StudentScore)place).getAccountId();
    final StudentScoreView view = new StudentScoreViewImpl(clientFactory);
    for (int i = 0; i < 5; i++) {
      view.setTitle(i, String.valueOf(i + 1));
      for (int j = 0; j < 3; j++) {
        view.setEvaluation(i, j, (int)(Math.random() * 100));
      }
    }
    return view;
  }

}
