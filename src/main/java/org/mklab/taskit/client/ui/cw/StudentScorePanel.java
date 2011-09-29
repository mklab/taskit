/**
 * 
 */
package org.mklab.taskit.client.ui.cw;

import org.mklab.taskit.client.ui.StudentScoreModel;
import org.mklab.taskit.client.ui.StudentScoreModel.LectureScore;
import org.mklab.taskit.client.ui.StudentListView.Presenter;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.ReportProxy;
import org.mklab.taskit.shared.SubmissionProxy;
import org.mklab.taskit.shared.UserProxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;


/**
 * @author ishikura
 */
public class StudentScorePanel extends Composite {

  private CellTable<LectureScore> table;
  private UserProxy user;
  private StudentScoreModel model;
  private Presenter presenter;

  /**
   * {@link StudentScorePanel}オブジェクトを構築します。
   */
  public StudentScorePanel() {
    this.table = new CellTable<StudentScoreModel.LectureScore>();

    initColumns();
    initWidget(this.table);

    this.table.setLoadingIndicator(null);
  }

  /**
   * presenterを設定します。
   * 
   * @param presenter presenter
   */
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  /**
   * presenterを取得します。
   * 
   * @return presenter
   */
  public Presenter getPresenter() {
    return this.presenter;
  }

  /**
   * 成績データをクリアします。
   */
  public void clearScoreData() {
    this.table.setRowData(new ArrayList<LectureScore>());
  }

  /**
   * モデルを設定します。
   * 
   * @param user 表示するユーザー
   * @param model ユーザーの成績情報
   */
  @SuppressWarnings("hiding")
  public void showUserPage(UserProxy user, StudentScoreModel model) {
    this.model = model;
    this.user = user;
    this.table.setRowData(this.model.asList());
    initColumns();
  }

  /**
   * テーブルデータを更新します。
   */
  public void updateTable() {
    this.table.setRowData(this.model.asList());
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
    final SelectionCell submissionCell = new SelectionCell(options);
    final Column<LectureScore, String> submissionColumn = new Column<StudentScoreModel.LectureScore, String>(submissionCell) {

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
    submissionColumn.setFieldUpdater(new FieldUpdater<StudentScoreModel.LectureScore, String>() {

      @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
      @Override
      public void update(@SuppressWarnings("unused") int index, LectureScore object, String value) {
        if (object.getReportCount() <= reportIndex) {
          presenter.reloadUserPage();
          return;
        }
        final ReportProxy report = object.getReport(reportIndex);
        int n;
        if (value.equals(options.get(0))) {
          n = 100;
        } else if (value.equals(options.get(1))) {
          n = 50;
        } else if (value.equals(options.get(2))) {
          n = 0;
        } else {
          presenter.reloadUserPage();
          return;
        }

        presenter.submit(report, n);
      }

    });
    this.table.addColumn(submissionColumn, String.valueOf(reportIndex + 1));
  }

  private void initCommonColumns() {
    final Column<LectureScore, Void> lectureNumberColumn = new Column<StudentScoreModel.LectureScore, Void>(new AbstractCell<Void>() {

      @Override
      public void render(com.google.gwt.cell.client.Cell.Context context, @SuppressWarnings("unused") Void value, SafeHtmlBuilder sb) {
        sb.appendHtmlConstant(String.valueOf(context.getIndex() + 1));
      }

    }) {

      @Override
      public Void getValue(@SuppressWarnings("unused") LectureScore object) {
        return null;
      }
    };

    final List<String> attendanceTypes = new ArrayList<String>();
    for (AttendanceType type : AttendanceType.values()) {
      attendanceTypes.add(type.name());
    }
    final Column<LectureScore, String> attendanceColumn = new Column<StudentScoreModel.LectureScore, String>(new SelectionCell(attendanceTypes)) {

      @Override
      public String getValue(LectureScore object) {
        if (object.getAttendance() == null) return null;
        return object.getAttendance().getType().name();
      }

    };
    attendanceColumn.setFieldUpdater(new FieldUpdater<StudentScoreModel.LectureScore, String>() {

      @SuppressWarnings({"unqualified-field-access", "synthetic-access", "unused"})
      @Override
      public void update(int index, LectureScore object, String value) {
        final LectureProxy lecture = object.getLecture();
        final AttendanceType type = AttendanceType.valueOf(value);
        presenter.attend(lecture, type);
      }

    });

    this.table.addColumn(lectureNumberColumn, "No."); //$NON-NLS-1$
    this.table.addColumn(attendanceColumn, "Attendance"); //$NON-NLS-1$
  }
}
