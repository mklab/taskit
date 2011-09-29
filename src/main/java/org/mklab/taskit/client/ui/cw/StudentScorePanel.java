/**
 * 
 */
package org.mklab.taskit.client.ui.cw;

import org.mklab.taskit.client.ui.EvaluationTableModel;
import org.mklab.taskit.client.ui.EvaluationTableModel.LectureScore;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.ReportProxy;
import org.mklab.taskit.shared.SubmissionProxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;


/**
 * @author ishikura
 */
public class StudentScorePanel extends Composite {

  private CellTable<LectureScore> table;
  private EvaluationTableModel model;

  /**
   * {@link StudentScorePanel}オブジェクトを構築します。
   */
  public StudentScorePanel() {
    this.table = new CellTable<EvaluationTableModel.LectureScore>();

    initColumns();
    initWidget(this.table);
  }

  /**
   * 成績データをクリアします。
   */
  public void clearScoreData() {
    this.table.setRowCount(0);
  }

  /**
   * モデルを設定します。
   * 
   * @param model モデル
   */
  public void setModel(EvaluationTableModel model) {
    this.model = model;
    this.table.setRowData(model.asList());
    initColumns();
  }

  private void initColumns() {
    final int COMMON_COLUMN_COUNT = 2;
    if (this.table.getColumnCount() < COMMON_COLUMN_COUNT) initCommonColumns();

    if (this.model == null) return;

    final int currentReportColumnCount = this.table.getColumnCount() - COMMON_COLUMN_COUNT;
    final int newReportCount = this.model.getMaximumReportCount();

    if (newReportCount > currentReportColumnCount) {
      for (int i = currentReportColumnCount; i < newReportCount; i++) {
        addReportColumn(i - currentReportColumnCount);
      }
    } else {
      for (int i = newReportCount; i < currentReportColumnCount; i++) {
        this.table.removeColumn(this.table.getColumnCount() - 1);
      }
    }
  }

  @SuppressWarnings("nls")
  private void addReportColumn(final int reportIndex) {
    final List<String> options = Arrays.asList("○", "△", "×", "-", "");
    final Column<LectureScore, String> submissionColumn = new Column<EvaluationTableModel.LectureScore, String>(new SelectionCell(options)) {

      @Override
      public String getValue(LectureScore object) {
        if (object.getReportCount() <= reportIndex) {// そもそもそんな課題ない場合
          return options.get(4);
        }
        final ReportProxy report = object.getReport(reportIndex);
        final SubmissionProxy submission = object.getSubmission(report);
        if (submission == null) return options.get(3); // 未提出

        final int point = submission.getPoint();
        if (point >= 80) {
          return options.get(0);
        } else if (point >= 40) {
          return options.get(1);
        } else {
          return options.get(2);
        }
      }

    };
    this.table.addColumn(submissionColumn, String.valueOf(reportIndex + 1));
  }

  private void initCommonColumns() {
    final Column<LectureScore, Void> lectureNumberColumn = new Column<EvaluationTableModel.LectureScore, Void>(new AbstractCell<Void>() {

      public void onBrowserEvent(Context context, Element parent, Void value, NativeEvent event, ValueUpdater<Void> valueUpdater) {
        System.out.println(event);
      }

      @Override
      public void render(com.google.gwt.cell.client.Cell.Context context, @SuppressWarnings("unused") Void value, SafeHtmlBuilder sb) {
        sb.appendHtmlConstant(String.valueOf(context.getIndex() + 1));
      }

    }) {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onBrowserEvent(Context context, Element elem, LectureScore object, NativeEvent event) {
        System.out.println(event);
        super.onBrowserEvent(context, elem, object, event);
      }

      @Override
      public Void getValue(@SuppressWarnings("unused") LectureScore object) {
        return null;
      }
    };

    final List<String> attendanceTypes = new ArrayList<String>();
    for (AttendanceType type : AttendanceType.values()) {
      attendanceTypes.add(type.name());
    }
    final Column<LectureScore, String> attendanceColumn = new Column<EvaluationTableModel.LectureScore, String>(new SelectionCell(attendanceTypes)) {

      @Override
      public String getValue(LectureScore object) {
        if (object.getAttendance() == null) return null;
        return object.getAttendance().getType().name();
      }

    };

    this.table.addColumn(lectureNumberColumn, "No."); //$NON-NLS-1$
    this.table.addColumn(attendanceColumn, "Attendance"); //$NON-NLS-1$
  }
}
