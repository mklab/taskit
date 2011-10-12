/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.model.StudentwiseRecordModel;
import org.mklab.taskit.client.model.StudentwiseRecordModel.LectureScore;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author ishikura
 */
public class StudentViewImpl extends AbstractTaskitView implements StudentView {

  private static final Binder binder = GWT.create(Binder.class);

  static interface Binder extends UiBinder<Widget, StudentViewImpl> {
    // do nothing
  }

  private Presenter presenter;
  @UiField(provided = true)
  StudentwiseRecordPanel scorePanel;
  @UiField
  ToggleButton helpCallButton;
  @UiField
  Label helpCallPosition;
  @UiField
  Label helpCallMessageLabel;
  @UiField
  TextBox helpCallMessage;
  @UiField
  VerticalPanel helpCallArea;
  private boolean editable;

  /**
   * {@link StudentViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   * @param editable 提出状況、出席状況を編集可能かどうか
   */
  public StudentViewImpl(ClientFactory clientFactory, boolean editable) {
    super(clientFactory);
    this.editable = editable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setModel(StudentwiseRecordModel model) {
    this.scorePanel.showUserPage(model);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCalling(boolean calling) {
    this.helpCallButton.setDown(calling);
    if (calling) {
      this.helpCallMessage.setText(null);
      this.helpCallMessage.setEnabled(false);
    } else {
      this.helpCallMessage.setEnabled(true);
      this.helpCallPosition.setText(""); //$NON-NLS-1$
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget initContent() {
    this.scorePanel = new StudentwiseRecordPanel(getClientFactory().getMessages(), this.editable);

    final Widget widget = binder.createAndBindUi(this);
    final Image helpImage = new Image("taskit/help128.png"); //$NON-NLS-1$
    final Image waitImage = new Image("taskit/calling128.png"); //$NON-NLS-1$
    this.helpCallButton.getUpFace().setImage(helpImage);
    this.helpCallButton.getDownFace().setImage(waitImage);
    this.helpCallButton.setSize("133px", "133px"); //$NON-NLS-1$ //$NON-NLS-2$
    this.helpCallArea.setCellHorizontalAlignment(this.helpCallButton, HasHorizontalAlignment.ALIGN_CENTER);
    this.helpCallMessageLabel.setText(getClientFactory().getMessages().messageLabel() + ":"); //$NON-NLS-1$
    return widget;
  }

  @UiHandler("helpCallButton")
  void callButtonClicked(@SuppressWarnings("unused") ClickEvent evt) {
    final boolean called = this.helpCallButton.isDown();
    if (called) {
      this.presenter.call(this.helpCallMessage.getText());
    } else {
      this.presenter.uncall();
    }
    setCalling(called);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void highlightRow(LectureScore rowData) {
    this.scorePanel.highlightRow(rowData);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHelpCallPosition(int position) {
    this.helpCallPosition.setText(getClientFactory().getMessages().helpCallPositionMessage(String.valueOf(position + 1)));
  }

}
