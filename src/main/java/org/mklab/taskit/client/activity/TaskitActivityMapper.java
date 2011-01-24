/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.DashboardPlace;
import org.mklab.taskit.client.place.LoginPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;


/**
 * TASKitのアクティビティの{@link Place}からのマッピングを行うクラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class TaskitActivityMapper implements ActivityMapper {

  private ClientFactory clientFactory;

  /**
   * {@link TaskitActivityMapper}オブジェクトを構築します。
   * 
   * @param clientFactory {@link ClientFactory}インスタンス
   */
  public TaskitActivityMapper(ClientFactory clientFactory) {
    super();
    this.clientFactory = clientFactory;
  }

  /**
   * @see com.google.gwt.activity.shared.ActivityMapper#getActivity(com.google.gwt.place.shared.Place)
   */
  @Override
  public Activity getActivity(final Place place) {
    if (place instanceof LoginPlace) return new LoginActivity(this.clientFactory);
    if (place instanceof DashboardPlace) return new DashboardActivity(this.clientFactory);

    return null;
  }

}
