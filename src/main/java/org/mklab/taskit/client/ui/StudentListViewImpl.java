/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.Messages;
import org.mklab.taskit.client.model.StudentwiseRecordModel;
import org.mklab.taskit.client.model.StudentwiseRecordModel.LectureScore;
import org.mklab.taskit.shared.UserProxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
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
  ValueListBox<SortType> sortTypeList;
  @UiField(provided = true)
  StudentwiseRecordPanel panel;
  @UiField
  Label userName;
  @UiField
  Label userId;

  @UiField
  CaptionPanel profileCaption;
  @UiField
  Label userIdLabel;
  @UiField
  Label userNameLabel;
  @UiField
  Label userListLabel;
  @UiField
  Button uncallButton;
  @UiField
  Button reloadButton;

  private Presenter presenter;
  private static final Binder binder = GWT.create(Binder.class);

  interface Binder extends UiBinder<Widget, StudentListViewImpl> {
    // empty
  }

  static enum SortType {
    SCORE_ASCENDING, SCORE_DESCENDING, ID_ASCENDING, ID_DESCENDING
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
    this.userList.setValue(user, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setListData(List<UserProxy> listData) {
    final List<UserProxy> acceptableValues = new ArrayList<UserProxy>(listData);
    acceptableValues.add(0, null);
    this.userList.setAcceptableValues(acceptableValues);
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
    final Messages messages = getClientFactory().getMessages();
    this.panel = new StudentwiseRecordPanel(messages, true);

    initUserList(messages);
    this.sortTypeList = new ValueListBox<StudentListViewImpl.SortType>(new Renderer<SortType>() {

      @Override
      public String render(SortType object) {
        if (object == null) return ""; //$NON-NLS-1$
        return object.name();
      }

      @Override
      public void render(SortType object, Appendable appendable) throws IOException {
        appendable.append(render(object));
      }
    });
    this.sortTypeList.setAcceptableValues(Arrays.asList(SortType.values()));
    final Widget widget = binder.createAndBindUi(this);

    localizeMessages(messages);

    return widget;
  }

  private void initUserList(final Messages messages) {
    this.userList = new ValueListBox<UserProxy>(new Renderer<UserProxy>() {

      @Override
      public String render(UserProxy object) {
        if (object == null) return messages.selectIdLabel();
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
  }

  private void localizeMessages(final Messages messages) {
    this.userIdLabel.setText(messages.userIdLabel() + ": "); //$NON-NLS-1$
    this.userNameLabel.setText(messages.userNameLabel() + ": "); //$NON-NLS-1$
    this.userListLabel.setText(messages.userListLabel() + ": "); //$NON-NLS-1$
    this.profileCaption.setCaptionText(messages.profileLabel());
    this.uncallButton.setText(messages.uncallLabel());
    this.reloadButton.setText(messages.reloadLabel());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showUserPage(UserProxy user, StudentwiseRecordModel model) {
    this.userId.setText(user.getAccount().getId());
    this.userName.setText(user.getName() != null ? user.getName() : "<< " + getClientFactory().getMessages().unsetLabel() + " >>"); //$NON-NLS-1$ //$NON-NLS-2$
    this.panel.showUserPage(model);
  }

  @UiHandler("uncallButton")
  void uncallButtonPressed(@SuppressWarnings("unused") ClickEvent evt) {
    this.presenter.uncall(getSelectedUser().getAccount());
  }

  @UiHandler("reloadButton")
  void reloadUserPage(@SuppressWarnings("unused") ClickEvent evt) {
    this.presenter.reloadUserPage();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserProxy getSelectedUser() {
    return this.userList.getValue();
  }

}
