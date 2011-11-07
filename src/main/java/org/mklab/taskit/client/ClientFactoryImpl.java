/**
 * 
 */
package org.mklab.taskit.client;

import org.mklab.taskit.client.ui.AttendanceListView;
import org.mklab.taskit.client.ui.AttendanceListViewImpl;
import org.mklab.taskit.client.ui.HeaderView;
import org.mklab.taskit.client.ui.HeaderViewImpl;
import org.mklab.taskit.client.ui.HelpCallListView;
import org.mklab.taskit.client.ui.HelpCallListViewImpl;
import org.mklab.taskit.client.ui.LoginView;
import org.mklab.taskit.client.ui.LoginViewImpl;
import org.mklab.taskit.client.ui.PageLayout;
import org.mklab.taskit.client.ui.PageLayoutImpl;
import org.mklab.taskit.client.ui.ProfileView;
import org.mklab.taskit.client.ui.ProfileViewImpl;
import org.mklab.taskit.client.ui.StudentListView;
import org.mklab.taskit.client.ui.StudentListViewImpl;
import org.mklab.taskit.client.ui.StudentView;
import org.mklab.taskit.client.ui.StudentViewImpl;
import org.mklab.taskit.client.ui.admin.AdminView;
import org.mklab.taskit.client.ui.admin.AdminViewImpl;
import org.mklab.taskit.shared.TaskitRequestFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;


/**
 * 画面の大きさによる制約のないデスクトップ向けのビューを提供する{@link ClientFactory}の実装クラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class ClientFactoryImpl implements ClientFactory {

  private EventBus eventBus = new SimpleEventBus();
  private PlaceController placeController;
  private Messages messages;
  private TaskitRequestFactory requestFactory;
  private HelpCallWatcher helpCallWatcher;
  private LocalDatabase localDatabase;

  /**
   * {@link ClientFactoryImpl}オブジェクトを構築します。
   */
  public ClientFactoryImpl() {
    this.eventBus = new SimpleEventBus();
    this.placeController = new PlaceController(this.eventBus);
    this.messages = GWT.create(Messages.class);

    this.requestFactory = GWT.create(TaskitRequestFactory.class);
    this.requestFactory.initialize(this.eventBus);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskitRequestFactory getRequestFactory() {
    return this.requestFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EventBus getEventBus() {
    return this.eventBus;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PlaceController getPlaceController() {
    return this.placeController;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LocalDatabase getLocalDatabase() {
    if (this.localDatabase == null) {
      this.localDatabase = new LocalDatabase(getRequestFactory());
    }
    return this.localDatabase;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HelpCallWatcher getHelpCallWatcher() {
    if (this.helpCallWatcher == null) {
      this.helpCallWatcher = new HelpCallWatcher(getLocalDatabase());
    }
    return this.helpCallWatcher;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Messages getMessages() {
    return this.messages;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PageLayout getPageLayout() {
    return new PageLayoutImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LoginView getLoginView() {
    return new LoginViewImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HeaderView getHeaderView() {
    return new HeaderViewImpl();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StudentListView getStudentListView() {
    return new StudentListViewImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public StudentView getStudentView() {
    return new StudentViewImpl(this, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AttendanceListView getAttendanceListView() {
    return new AttendanceListViewImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProfileView getProfileView() {
    return new ProfileViewImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HelpCallListView getHelpCallListView() {
    return new HelpCallListViewImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AdminView getAdminView() {
    return new AdminViewImpl(this);
  }

}
