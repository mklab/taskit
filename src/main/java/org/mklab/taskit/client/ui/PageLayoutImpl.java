/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.Admin;
import org.mklab.taskit.client.place.AttendanceList;
import org.mklab.taskit.client.place.HelpCallList;
import org.mklab.taskit.client.place.Profile;
import org.mklab.taskit.client.place.Student;
import org.mklab.taskit.client.place.StudentList;
import org.mklab.taskit.shared.UserProxy;
import org.mklab.taskit.shared.UserType;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author ishikura
 */
public class PageLayoutImpl implements PageLayout {

  ClientFactory clientFactory;
  HeaderView header;
  Presenter presenter;
  UserProxy lastLoginUser;

  /**
   * {@link PageLayoutImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public PageLayoutImpl(ClientFactory clientFactory) {
    if (clientFactory == null) throw new NullPointerException();
    this.clientFactory = clientFactory;
    this.header = this.clientFactory.getHeaderView();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  ClientFactory getClientFactory() {
    return this.clientFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Widget layout(TaskitView taskitView, UserProxy user) {
    if (this.lastLoginUser == null || this.lastLoginUser.getType() != user.getType()) {
      initToolBarButtons(user);
    }

    final String name = user.getName() == null ? user.getAccount().getId() : user.getName();
    this.header.setUserId(name);
    this.header.setUserType(user.getType().name());

    final DockLayoutPanel pn = new DockLayoutPanel(Unit.PX);
    pn.addNorth(this.header, this.header.getHeight());
    pn.add(taskitView);

    this.lastLoginUser = user;

    return pn;
  }

  /**
   * 上部のツールバーに表示するボタン、及びそのアクションを設定します。
   * 
   * @param user ログインユーザー情報
   */
  @SuppressWarnings({"nls", "unused"})
  private void initToolBarButtons(UserProxy user) {
    final UserType userType = user.getType();

    if (userType == UserType.TA || userType == UserType.TEACHER) {
      final ToolBarButton studentListButton = this.header.addButton("student_list");
      studentListButton.setIcon("taskit/students64.png");
      studentListButton.setClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          getClientFactory().getPlaceController().goTo(StudentList.INSTANCE);
        }
      });

      final ToolBarButton attendanceListButton = this.header.addButton("attendance_list");
      attendanceListButton.setIcon("taskit/attendance64.png");
      attendanceListButton.setClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          getClientFactory().getPlaceController().goTo(AttendanceList.INSTANCE);
        }
      });

      final ToolBarButton helpCallListButton = this.header.addButton("call_list");
      helpCallListButton.setIcon("taskit/helplist64.png");
      helpCallListButton.setClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          getClientFactory().getPlaceController().goTo(HelpCallList.INSTANCE);
        }
      });
    }

    if (userType == UserType.TEACHER) {
      final ToolBarButton adminButton = this.header.addButton("admin");
      adminButton.setIcon("taskit/admin64.png");
      adminButton.setClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          getClientFactory().getPlaceController().goTo(Admin.INSTANCE);
        }
      });
    }

    if (userType == UserType.STUDENT) {
      final ToolBarButton studentButton = this.header.addButton("admin");
      studentButton.setIcon("taskit/student64.png");
      studentButton.setClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          getClientFactory().getPlaceController().goTo(Student.INSTANCE);
        }
      });
    }

    final ToolBarButton profileButton = this.header.addButton("profile");
    profileButton.setIcon("taskit/profile64.png");
    profileButton.setClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        getClientFactory().getPlaceController().goTo(Profile.INSTANCE);
      }
    });

    this.header.addSeparator();

    final ToolBarButton logoutButton = this.header.addButton("logout");
    logoutButton.setIcon("taskit/logout64.png");
    logoutButton.setClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        PageLayoutImpl.this.presenter.logout();
      }
    });
  }

}
