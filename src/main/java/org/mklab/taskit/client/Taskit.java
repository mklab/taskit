package org.mklab.taskit.client;

import org.mklab.taskit.client.activity.TaskitActivityMapper;
import org.mklab.taskit.client.place.Login;
import org.mklab.taskit.client.place.TaskitPlaceHistoryMapper;
import org.mklab.taskit.shared.TaskitRequestFactory;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Taskit implements EntryPoint {

  private Place defaultPlace = Login.INSTANCE;
  private SimplePanel appWidget = new SimplePanel();

  /**
   * This is the entry point method.
   */
  @Override
  public void onModuleLoad() {
    final ClientFactory clientFactory = GWT.create(ClientFactory.class);
    final EventBus eventBus = clientFactory.getEventBus();
    final PlaceController placeController = clientFactory.getPlaceController();

    final ActivityMapper activityMapper = new TaskitActivityMapper(clientFactory);
    final ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
    activityManager.setDisplay(this.appWidget);

    final PlaceHistoryMapper historyMapper = GWT.create(TaskitPlaceHistoryMapper.class);
    final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
    historyHandler.register(placeController, eventBus, this.defaultPlace);

    final RootPanel rootPanel = RootPanel.get();
    rootPanel.add(this.appWidget);
    historyHandler.handleCurrentHistory();

    TaskitRequestFactory factory = GWT.create(TaskitRequestFactory.class);
    factory.initialize(eventBus);
  }
}
