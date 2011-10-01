/**
 * 
 */
package org.mklab.taskit.client.ui.cw;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.model.StudentScoreModel;
import org.mklab.taskit.client.model.StudentScoreModel.LectureScore;
import org.mklab.taskit.client.ui.AbstractTaskitView;
import org.mklab.taskit.client.ui.StudentView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author ishikura
 */
public class CwStudentView extends AbstractTaskitView implements StudentView {

  private static final Binder binder = GWT.create(Binder.class);

  static interface Binder extends UiBinder<Widget, CwStudentView> {
    // do nothing
  }

  private Presenter presenter;
  @UiField
  StudentScorePanel scorePanel;
  @UiField
  ToggleButton helpCallButton;

  /**
   * {@link CwStudentView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public CwStudentView(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setModel(StudentScoreModel model) {
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
  protected Widget initContent() {
    final Widget widget = binder.createAndBindUi(this);
    final Image helpImage = new Image("taskit/help128.png"); //$NON-NLS-1$
    final Image waitImage = new Image("taskit/wait128.png"); //$NON-NLS-1$
    this.helpCallButton.getUpFace().setImage(helpImage);
    this.helpCallButton.getDownFace().setImage(waitImage);
    this.helpCallButton.setSize("133px", "133px"); //$NON-NLS-1$ //$NON-NLS-2$
    return widget;
  }

  @UiHandler("helpCallButton")
  void callButtonClicked(ClickEvent evt) {
    System.out.println(evt);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void highlightRow(LectureScore rowData) {
    this.scorePanel.highlightRow(rowData);
  }

}
