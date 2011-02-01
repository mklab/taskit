/**
 * 
 */
package org.mklab.taskit.client.ui.admin;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.shared.model.Lecture;
import org.mklab.taskit.shared.model.Report;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
public class ReportEditorImpl extends Composite implements ReportEditor {

  private LectureReportChooser lrChooser;

  /**
   * {@link ReportEditorImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public ReportEditorImpl(ClientFactory clientFactory) {
    final VerticalPanel vPanel = new VerticalPanel();
    this.lrChooser = new LectureReportChooser();

    vPanel.add(this.lrChooser);

    initWidget(vPanel);
  }

  /**
   * @see org.mklab.taskit.client.ui.admin.ReportEditor#getLectureIndex()
   */
  @Override
  public int getLectureIndex() {
    return 0;
  }

  /**
   * @see org.mklab.taskit.client.ui.admin.ReportEditor#getReportNumber()
   */
  @Override
  public int getReportNumber() {
    return 0;
  }

  /**
   * @see org.mklab.taskit.client.ui.admin.ReportEditor#getReportTitle()
   */
  @Override
  public String getReportTitle() {
    return null;
  }

  /**
   * @see org.mklab.taskit.client.ui.admin.ReportEditor#getReportDetail()
   */
  @Override
  public String getReportDetail() {
    return null;
  }

  /**
   * @see org.mklab.taskit.client.ui.admin.ReportEditor#getSubmitTrigger()
   */
  @Override
  public HasClickHandlers getSubmitTrigger() {
    return null;
  }

  /**
   * @see org.mklab.taskit.client.ui.admin.ReportEditor#setLectures(org.mklab.taskit.shared.model.Lecture[])
   */
  @Override
  public void setLectures(Lecture[] lectures) {
    this.lrChooser.setLectures(lectures);
  }

  /**
   * @see org.mklab.taskit.client.ui.admin.ReportEditor#setReports(org.mklab.taskit.shared.model.Lecture,
   *      org.mklab.taskit.shared.model.Report[])
   */
  @Override
  public void setReports(Lecture l, Report[] reports) {
    this.lrChooser.setReports(l, reports);
  }

}
