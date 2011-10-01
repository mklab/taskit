/**
 * 
 */
package org.mklab.taskit.client.ui.cw;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.model.StudentScoreModel;
import org.mklab.taskit.client.ui.AbstractTaskitView;
import org.mklab.taskit.client.ui.StudentView;
import org.mklab.taskit.shared.UserProxy;

import com.google.gwt.user.client.ui.Widget;


/**
 * @author ishikura
 */
public class CwStudentView extends AbstractTaskitView implements StudentView {

  private Presenter presenter;
  private StudentScorePanel scorePanel;

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
    this.scorePanel = new StudentScorePanel();
    return this.scorePanel;
  }

}
