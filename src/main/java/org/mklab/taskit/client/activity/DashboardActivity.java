/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.DashboardPlace;
import org.mklab.taskit.client.ui.DashboardViewImpl;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 23, 2011
 */
public class DashboardActivity extends AbstractActivity {

  /** このアクティビティの場所を表すオブジェクトです。 */
  public static final Place PLACE = new DashboardPlace("dashboard"); //$NON-NLS-1$
  private ClientFactory clientFactory;

  /**
   * {@link DashboardActivity}オブジェクトを構築します。
   * 
   * @param clientFactory
   */
  public DashboardActivity(ClientFactory clientFactory) {
    this.clientFactory = clientFactory;
  }

  /**
   * @see com.google.gwt.activity.shared.Activity#start(com.google.gwt.user.client.ui.AcceptsOneWidget,
   *      com.google.gwt.event.shared.EventBus)
   */
  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    panel.setWidget(new DashboardViewImpl(this.clientFactory));
  }
}
