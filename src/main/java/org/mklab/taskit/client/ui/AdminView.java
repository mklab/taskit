/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author yuhi
 */
public class AdminView extends AbstractTaskitView {

  private LectureEditor lectureEditor;
  private ReportEditor reportEditor;
  private Presenter presenter;

  /**
   * {@link AdminView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AdminView(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * lectureEditorを取得します。
   * 
   * @return lectureEditor
   */
  public LectureEditor getLectureEditor() {
    return this.lectureEditor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget initContent() {
    final TabLayoutPanel tabPanel = new TabLayoutPanel(1.5, Unit.EM);
    this.lectureEditor = new LectureEditor(getClientFactory().getMessages());
    tabPanel.add(this.lectureEditor, "Lecture");
    tabPanel.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {

      @Override
      public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
        presenter.selectedTabChanged();
      }
    });
    return tabPanel;
  }

  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
    this.lectureEditor.setPresenter(presenter);
  }

  public static interface Presenter extends LectureEditor.Presenter {

    void selectedTabChanged();

  }

}
