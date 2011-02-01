/**
 * 
 */
package org.mklab.taskit.client.ui.admin;

import java.util.Date;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.Messages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
public class LectureEditorImpl extends Composite implements LectureEditor {

  private static LectureEditorUiBinder uiBinder = GWT.create(LectureEditorUiBinder.class);

  interface LectureEditorUiBinder extends UiBinder<Widget, LectureEditorImpl> {
    // no members
  }

  @UiField
  Label dateLabel;
  @UiField
  DateBox dateBox;
  @UiField
  Label titleLabel;
  @UiField
  TextBox titleBox;
  @UiField
  Button submitButton;

  /**
   * {@link LectureEditorImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public LectureEditorImpl(ClientFactory clientFactory) {
    initWidget(uiBinder.createAndBindUi(this));
    final Messages m = clientFactory.getMessages();
    this.dateLabel.setText(m.lectureDateLabel());
    this.titleLabel.setText(m.titleLabel());
    this.submitButton.setText(m.registerButton());
  }

  /**
   * @see org.mklab.taskit.client.ui.admin.LectureEditor#setLectureTitle(java.lang.String)
   */
  @Override
  public void setLectureTitle(String title) {
    this.titleBox.setText(title);
  }

  /**
   * @see org.mklab.taskit.client.ui.admin.LectureEditor#setLectureStartTime(long)
   */
  @Override
  public void setLectureStartTime(long timeInMillis) {
    this.dateBox.setValue(new Date(timeInMillis));
  }

  /**
   * @see org.mklab.taskit.client.ui.admin.LectureEditor#getLectureTitle()
   */
  @Override
  public String getLectureTitle() {
    return this.titleBox.getText();
  }

  /**
   * @see org.mklab.taskit.client.ui.admin.LectureEditor#getLectureStartTime()
   */
  @Override
  public long getLectureStartTime() {
    return this.dateBox.getValue().getTime();
  }

  /**
   * @see org.mklab.taskit.client.ui.admin.LectureEditor#getSubmitTrigger()
   */
  @Override
  public HasClickHandlers getSubmitTrigger() {
    return this.submitButton;
  }

}
