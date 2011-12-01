/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.Messages;
import org.mklab.taskit.client.model.StudentwiseRecordModel;
import org.mklab.taskit.client.model.StudentwiseRecordModel.LectureScore;
import org.mklab.taskit.client.ui.StudentListView.Presenter;
import org.mklab.taskit.client.ui.cell.SelectCell;
import org.mklab.taskit.client.ui.cell.TooltipCell;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.ReportProxy;
import org.mklab.taskit.shared.SubmissionProxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CompositeCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.HasCell;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * 単一学生の情報を表示するビューです。
 * <p>
 * 全講義の課題提出状況、出席状況を表示します。
 * 
 * @author Yuhi Ishikura
 */
public class StudentPanel extends Composite {

  private DataGrid<LectureScore> table;
  private StudentwiseRecordModel model;
  private Presenter presenter;
  private TableRowElement lastHighlightElement;
  private boolean editable;
  Messages messages;

  /**
   * {@link StudentPanel}オブジェクトを構築します。
   * 
   * @param messages メッセージ
   * @param editable 編集可能かどうか
   */
  public StudentPanel(Messages messages, boolean editable) {
    if (messages == null) throw new NullPointerException();

    this.table = new DataGrid<StudentwiseRecordModel.LectureScore>();
    this.editable = editable;
    this.messages = messages;
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
   * @param model ユーザーの成績情報
   */
  @SuppressWarnings("hiding")
  public void showUserPage(StudentwiseRecordModel model) {
    this.model = model;
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

  /**
   * 与えられた行をハイライトします。
   * 
   * @param rowData 行データ
   */
  public void highlightRow(StudentwiseRecordModel.LectureScore rowData) {
    if (this.lastHighlightElement != null) {
      this.lastHighlightElement.getStyle().clearBackgroundColor();
    }

    if (rowData == null) return;

    final TableRowElement rowElement = this.table.getRowElement(rowData.getIndex());
    rowElement.getStyle().setBackgroundColor("#FFFFAA"); //$NON-NLS-1$
    this.lastHighlightElement = rowElement;
  }

  private void initColumns() {
    final int COMMON_COLUMN_COUNT = 2;
    if (this.table.getColumnCount() < COMMON_COLUMN_COUNT) initCommonColumns();

    if (this.model == null) return;

    final int currentReportColumnCount = this.table.getColumnCount() - COMMON_COLUMN_COUNT;
    final int newReportCount = this.model.getMaximumReportCount();

    if (newReportCount > currentReportColumnCount) {
      for (int i = currentReportColumnCount; i < newReportCount; i++) {
        final int reportIndex = i - currentReportColumnCount;
        this.table.addColumn(createSubmissionColumn(reportIndex), String.valueOf(reportIndex + 1));
      }
    } else {
      for (int i = newReportCount; i < currentReportColumnCount; i++) {
        this.table.removeColumn(this.table.getColumnCount() - 1);
      }
    }
  }

  private void initCommonColumns() {
    final Column<LectureScore, LectureScore> lectureNumberColumn = createLectureNumberColumn();
    final Column<LectureScore, AttendanceType> attendanceColumn = createAttendanceColumn();

    this.table.addColumn(lectureNumberColumn, this.messages.numberColumnLabel());
    this.table.addColumn(attendanceColumn, this.messages.attendenceTypeLabel());

    this.table.setColumnWidth(lectureNumberColumn, "4em"); //$NON-NLS-1$
    this.table.setColumnWidth(attendanceColumn, "6em"); //$NON-NLS-1$
  }

  @SuppressWarnings("static-method")
  private Column<LectureScore, LectureScore> createLectureNumberColumn() {
    final List<HasCell<LectureScore, ?>> cells = new ArrayList<HasCell<LectureScore, ?>>();
    cells.add(new HasCell<StudentwiseRecordModel.LectureScore, Void>() {

      @Override
      public Cell<Void> getCell() {
        return new AbstractCell<Void>() {

          @Override
          public void render(com.google.gwt.cell.client.Cell.Context context, @SuppressWarnings("unused") Void value, SafeHtmlBuilder sb) {
            sb.appendHtmlConstant(String.valueOf(context.getIndex() + 1));
          }

        };
      }

      @Override
      public FieldUpdater<LectureScore, Void> getFieldUpdater() {
        return null;
      }

      @Override
      public Void getValue(@SuppressWarnings("unused") LectureScore object) {
        return null;
      }
    });
    cells.add(new HasCell<LectureScore, LectureScore>() {

      @Override
      public Cell<LectureScore> getCell() {
        return new TooltipCell<LectureScore>() {

          @Override
          protected Widget getTooltipOf(LectureScore value) {
            final LectureProxy lecture = value.getLecture();
            final Label title = new Label(lecture.getTitle());
            final Label date = new Label(DateTimeFormat.getFormat(PredefinedFormat.YEAR_MONTH_DAY).format(lecture.getDate()));
            final TextArea description = new TextArea();
            description.setReadOnly(true);
            description.setText(lecture.getDescription());

            final VerticalPanel vPanel = new VerticalPanel();
            vPanel.add(date);
            vPanel.add(title);
            vPanel.add(description);

            return vPanel;
          }
        };
      }

      @Override
      public FieldUpdater<LectureScore, LectureScore> getFieldUpdater() {
        return null;
      }

      @Override
      public LectureScore getValue(LectureScore object) {
        return object;
      }
    });

    final Column<LectureScore, LectureScore> lectureNumberColumn = new Column<LectureScore, LectureScore>(new CompositeCell<LectureScore>(cells)) {

      @Override
      public LectureScore getValue(LectureScore object) {
        return object;
      }

    };
    return lectureNumberColumn;
  }

  private Column<LectureScore, AttendanceType> createAttendanceColumn() {
    final List<AttendanceType> attendanceTypes = new ArrayList<AttendanceType>();
    for (AttendanceType type : AttendanceType.values()) {
      attendanceTypes.add(type);
    }
    attendanceTypes.add(null);
    final SelectCell<AttendanceType> selectionCell = new SelectCell<AttendanceType>(attendanceTypes, new SelectCell.Renderer<AttendanceType>() {

      @Override
      public String render(@SuppressWarnings("unused") int index, AttendanceType value) {
        return AttendanceListViewImpl.getLabelOfAttendanceType(StudentPanel.this.messages, value);
      }
    });
    selectionCell.setEditable(this.editable);

    final Column<LectureScore, AttendanceType> attendanceColumn = new Column<StudentwiseRecordModel.LectureScore, AttendanceType>(selectionCell) {

      @Override
      public AttendanceType getValue(LectureScore object) {
        if (object.getAttendance() == null) return null;
        return object.getAttendance().getType();
      }

    };
    attendanceColumn.setFieldUpdater(new FieldUpdater<StudentwiseRecordModel.LectureScore, AttendanceType>() {

      @SuppressWarnings({"unqualified-field-access", "synthetic-access", "unused"})
      @Override
      public void update(int index, LectureScore object, AttendanceType value) {
        if (value == null) {
          presenter.delete(object.getAttendance());
          return;
        }

        final LectureProxy lecture = object.getLecture();
        presenter.attend(lecture, value);
      }

    });
    return attendanceColumn;
  }

  private Column<LectureScore, LectureScore> createSubmissionColumn(final int reportIndex) {
    final List<HasCell<LectureScore, ?>> cells = new ArrayList<HasCell<LectureScore, ?>>();

    // 提出物の種別選択コンボボックス
    cells.add(new HasCell<LectureScore, String>() {

      @SuppressWarnings("nls")
      final List<String> options = Arrays.asList("○", "△", "×", "");

      @SuppressWarnings("synthetic-access")
      @Override
      public Cell<String> getCell() {
        final SubmissionCell submissionCell = new SubmissionCell(this.options);
        submissionCell.setEditable(StudentPanel.this.editable);
        return submissionCell;
      }

      @Override
      public FieldUpdater<LectureScore, String> getFieldUpdater() {
        return new FieldUpdater<StudentwiseRecordModel.LectureScore, String>() {

          @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
          @Override
          public void update(@SuppressWarnings("unused") int index, LectureScore object, String value) {
            if (object.getReportCount() <= reportIndex) {
              presenter.reloadUserPage();
              return;
            }
            final ReportProxy report = object.getReport(reportIndex);
            if (value.equals(options.get(0))) {
              presenter.submit(report, 100);
            } else if (value.equals(options.get(1))) {
              presenter.submit(report, 50);
            } else if (value.equals(options.get(2))) {
              presenter.submit(report, 0);
            } else if (value.equals(options.get(3))) {
              presenter.delete(object.getSubmission(report));
            } else {
              presenter.reloadUserPage();
              return;
            }
          }

        };
      }

      @Override
      public String getValue(LectureScore object) {
        if (object.getReportCount() <= reportIndex) {// そもそもそんな課題ない場合
          return null;
        }
        final ReportProxy report = object.getReport(reportIndex);
        final SubmissionProxy submission = object.getSubmission(report);
        if (submission == null) return this.options.get(3); // 未提出

        final int point = submission.getPoint();
        if (point >= 80) {
          return this.options.get(0);
        } else if (point >= 40) {
          return this.options.get(1);
        } else {
          return this.options.get(2);
        }
      }

    });
    // 課題の詳細ボタン
    cells.add(new HasCell<LectureScore, LectureScore>() {

      @Override
      public Cell<LectureScore> getCell() {
        return new TooltipCell<StudentwiseRecordModel.LectureScore>() {

          /**
           * {@inheritDoc}
           */
          @Override
          public void render(com.google.gwt.cell.client.Cell.Context context, LectureScore value, SafeHtmlBuilder sb) {
            // 課題の存在しない列の場合には詳細ボタンを非表示
            if (value.getReportCount() <= reportIndex) return;
            super.render(context, value, sb);
          }

          @Override
          protected Widget getTooltipOf(LectureScore value) {
            final ReportProxy report = value.getReport(reportIndex);
            final Label title = new Label(report.getTitle());
            final TextArea description = new TextArea();
            description.setReadOnly(true);
            description.setText(report.getDescription());

            final VerticalPanel vPanel = new VerticalPanel();
            vPanel.add(title);
            vPanel.add(description);

            return vPanel;
          }
        };
      }

      @Override
      public FieldUpdater<LectureScore, LectureScore> getFieldUpdater() {
        return null;
      }

      @Override
      public LectureScore getValue(LectureScore object) {
        return object;
      }
    });

    final Column<LectureScore, LectureScore> submissionColumn = new Column<LectureScore, LectureScore>(new CompositeCell<LectureScore>(cells)) {

      @Override
      public LectureScore getValue(LectureScore object) {
        return object;
      }

    };
    return submissionColumn;
  }

  /**
   * @author ishikura
   */
  final class SubmissionCell extends SelectCell<String> {

    /**
     * {@link SubmissionCell}オブジェクトを構築します。
     * 
     * @param options 選択可能なオプション
     */
    SubmissionCell(List<String> options) {
      super(options, new SelectCell.Renderer<String>() {

        @Override
        public String render(@SuppressWarnings("unused") int index, String value) {
          return value;
        }
      });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(com.google.gwt.cell.client.Cell.Context context, String value, SafeHtmlBuilder sb) {
      // nullの場合にはオプションすら表示しない
      if (value == null) return;
      super.render(context, value, sb);
    }
  }
}
