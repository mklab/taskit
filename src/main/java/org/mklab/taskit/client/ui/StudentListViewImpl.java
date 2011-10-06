/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.model.StudentScoreModel;
import org.mklab.taskit.client.model.StudentScoreModel.LectureScore;
import org.mklab.taskit.shared.UserProxy;

import java.io.IOException;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author ishikura
 */
public class StudentListViewImpl extends AbstractTaskitView implements StudentListView {

  @UiField(provided = true)
  ValueListBox<UserProxy> userList;
  @UiField(provided = true)
  StudentScorePanel panel;
  @UiField
  Label userName;
  @UiField
  Label userId;
  private Presenter presenter;
  private static final Binder binder = GWT.create(Binder.class);

  interface Binder extends UiBinder<Widget, StudentListViewImpl> {
    // empty
  }

  /**
   * {@link StudentListViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public StudentListViewImpl(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
    this.panel.setPresenter(presenter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectedListData(UserProxy user) {
    this.userList.setValue(user, true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setListData(List<UserProxy> listData) {
    listData.add(0, null);
    this.userList.setAcceptableValues(listData);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clearUserPage() {
    this.panel.clearScoreData();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void highlightRow(LectureScore rowData) {
    this.panel.highlightRow(rowData);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget initContent() {
    this.panel = new StudentScorePanel(getClientFactory().getMessages(), true);

    this.userList = new ValueListBox<UserProxy>(new Renderer<UserProxy>() {

      @Override
      public String render(UserProxy object) {
        if (object == null) return "Select ID"; //$NON-NLS-1$
        StringBuilder sb = new StringBuilder(object.getAccount().getId());
        if (object.getName() != null) {
          sb.append(" " + object.getName()); //$NON-NLS-1$
        }
        return sb.toString();
      }

      @Override
      public void render(UserProxy object, Appendable appendable) throws IOException {
        appendable.append(render(object));
      }
    });
    this.userList.addValueChangeHandler(new ValueChangeHandler<UserProxy>() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onValueChange(ValueChangeEvent<UserProxy> event) {
        StudentListViewImpl.this.presenter.listSelectionChanged(event.getValue());
      }
    });

    return binder.createAndBindUi(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showUserPage(UserProxy user, StudentScoreModel model) {
    this.userId.setText(user.getAccount().getId());
    this.userName.setText(user.getName() != null ? user.getName() : "<< Not set >>"); //$NON-NLS-1$
    this.panel.showUserPage(model);
  }

  @UiHandler("uncallButton")
  void uncallButtonPressed(@SuppressWarnings("unused") ClickEvent evt) {
    this.presenter.uncall(getSelectedUser().getAccount());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserProxy getSelectedUser() {
    return this.userList.getValue();
  }

}
