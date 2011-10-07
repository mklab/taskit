/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.ReportProxy;

import java.io.IOException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;


/**
 * @author yuhi
 */
public class ReportEditor extends Composite {

  private static final Binder binder = GWT.create(Binder.class);

  static interface Binder extends UiBinder<Widget, ReportEditor> {
    // empty
  }

  @UiField
  TextBox title;
  @UiField
  TextArea description;
  @UiField
  DateBox period;
  @UiField
  TextBox point;
  @UiField
  Button changeButton;
  @UiField(provided = true)
  ValueListBox<LectureProxy> lecture;
  private Presenter presenter;
  private ReportProxy report;

  /**
   * {@link ReportEditor}オブジェクトを構築します。
   */
  public ReportEditor() {
    this.lecture = new ValueListBox<LectureProxy>(new Renderer<LectureProxy>() {

      @Override
      public String render(LectureProxy object) {
        return object.getTitle();
      }

      @Override
      public void render(LectureProxy object, Appendable appendable) throws IOException {
        appendable.append(render(object));
      }
    });
    initWidget(binder.createAndBindUi(this));
  }

  /**
   * 編集するレポートデータを取得します。
   * 
   * @param report レポート
   */
  public void setEditData(ReportProxy report) {
    this.report = report;
    this.title.setText(report.getTitle());
    this.description.setText(report.getDescription());
    this.period.setValue(report.getPeriod());
    this.point.setText(String.valueOf(report.getPoint()));
    this.lecture.setValue(report.getLecture(), true);
  }

  @UiHandler("changeButton")
  void changeButtonPressed(@SuppressWarnings("unused") ClickEvent evt) {
    this.report.setTitle(this.title.getText());
    this.report.setDescription(this.description.getText());
    this.report.setPeriod(this.period.getValue());
    this.report.setPoint(Integer.parseInt(this.point.getValue()));
    this.report.setLecture(this.lecture.getValue());

    this.presenter.persist(this.report);
  }

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  /**
   * {@link ReportEditor}のプレゼンターです。
   * 
   * @author yuhi
   */
  public static interface Presenter {

    /**
     * 課題オブジェクトの編集を開始します。
     * 
     * @param report 変更する課題オブジェクト
     * @return 変更可能な課題オブジェクト
     */
    ReportProxy edit(ReportProxy report);

    /**
     * 与えられた課題を保存します。
     * <p>
     * すでに存在すればID以外の属性を保存します。
     * 
     * @param report 課題
     */
    void persist(ReportProxy report);

  }
}
