/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.admin.LectureEditor;
import org.mklab.taskit.client.ui.admin.LectureEditorImpl;
import org.mklab.taskit.client.ui.admin.NewAccountView;
import org.mklab.taskit.client.ui.admin.NewAccountViewImpl;
import org.mklab.taskit.client.ui.admin.ReportEditor;
import org.mklab.taskit.client.ui.admin.ReportEditorImpl;
import org.mklab.taskit.shared.model.Lecture;
import org.mklab.taskit.shared.model.Report;

import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 26, 2011
 */
public class AdminViewImpl extends AbstractTaskitView implements AdminView {

  private NewAccountViewImpl accountView;
  private LectureEditor lectureEditor;
  private ReportEditor reportEditor;

  /**
   * {@link AdminViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AdminViewImpl(ClientFactory clientFactory) {
    super(clientFactory);
    init();
  }

  /**
   * @see org.mklab.taskit.client.ui.AbstractTaskitView#initContent()
   */
  @Override
  protected Widget initContent() {
    final TabPanel tabPanel = new TabPanel();
    this.accountView = new NewAccountViewImpl(getClientFactory());
    this.lectureEditor = new LectureEditorImpl(getClientFactory());
    this.reportEditor = new ReportEditorImpl(getClientFactory());

    tabPanel.add(this.accountView, "Account"); //$NON-NLS-1$
    tabPanel.add(this.lectureEditor, "Lecture"); //$NON-NLS-1$
    tabPanel.add(this.reportEditor, "Report");
    tabPanel.selectTab(0);

    Lecture l = new Lecture("title1", 10000);
    Report report = new Report(1, "report1", "detail", 0, l.getLectureId());
    this.reportEditor.setLectures(new Lecture[] {l});
    //    this.reportEditor.setReports(l, new Report[] {report});

    return tabPanel;
  }

  /**
   * @see org.mklab.taskit.client.ui.AdminView#getNewAccountView()
   */
  @Override
  public NewAccountView getNewAccountView() {
    return this.accountView;
  }

  /**
   * @see org.mklab.taskit.client.ui.AdminView#getLectureEditor()
   */
  @Override
  public LectureEditor getLectureEditor() {
    return this.lectureEditor;
  }

  /**
   * @see org.mklab.taskit.client.ui.AdminView#getReportEditor()
   */
  @Override
  public ReportEditor getReportEditor() {
    // TODO Auto-generated method stub
    return null;
  }

}
