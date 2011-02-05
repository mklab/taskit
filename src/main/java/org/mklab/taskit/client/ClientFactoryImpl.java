/**
 * 
 */
package org.mklab.taskit.client;

import org.mklab.taskit.client.ui.HeaderView;
import org.mklab.taskit.client.ui.LoginView;
import org.mklab.taskit.client.ui.LoginViewImpl;
import org.mklab.taskit.client.ui.StudentListView;
import org.mklab.taskit.client.ui.smartgwt.SmartGwtHeaderView;
import org.mklab.taskit.client.ui.smartgwt.SmartGwtStudentListView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;


/**
 * {@link ClientFactory}の実装クラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class ClientFactoryImpl implements ClientFactory {

  private EventBus eventBus = new SimpleEventBus();
  private PlaceController placeController;
  private Messages messages;

  /**
   * {@link ClientFactoryImpl}オブジェクトを構築します。
   */
  public ClientFactoryImpl() {
    this.eventBus = new SimpleEventBus();
    this.placeController = new PlaceController(this.eventBus);
    this.messages = GWT.create(Messages.class);
  }

  /**
   * @see org.mklab.taskit.client.ClientFactory#getEventBus()
   */
  @Override
  public EventBus getEventBus() {
    return this.eventBus;
  }

  /**
   * @see org.mklab.taskit.client.ClientFactory#getPlaceController()
   */
  @Override
  public PlaceController getPlaceController() {
    return this.placeController;
  }

  /**
   * @see org.mklab.taskit.client.ClientFactory#getMessages()
   */
  @Override
  public Messages getMessages() {
    return this.messages;
  }

  /**
   * @see org.mklab.taskit.client.ClientFactory#getLoginView()
   */
  @Override
  public LoginView getLoginView() {
    return new LoginViewImpl(this);
  }

  /**
   * @see org.mklab.taskit.client.ClientFactory#getHeaderView()
   */
  @Override
  public HeaderView getHeaderView() {
    return new SmartGwtHeaderView();
  }

  /**
   * @see org.mklab.taskit.client.ClientFactory#getStudentListView()
   */
  @Override
  public StudentListView getStudentListView() {
    return new SmartGwtStudentListView(this);
  }

}
