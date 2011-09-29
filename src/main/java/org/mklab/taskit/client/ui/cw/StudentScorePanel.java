/**
 * 
 */
package org.mklab.taskit.client.ui.cw;

import org.mklab.taskit.client.ui.EvaluationTableModel;
import org.mklab.taskit.client.ui.EvaluationTableModel.LectureScore;
import org.mklab.taskit.shared.AttendanceProxy;

import java.util.Arrays;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


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

  public void setModel(EvaluationTableModel model) {
    this.model = model;
    this.table.setRowData(model.asList());
  }

  private void initColumns() {
    final Column<LectureScore, Void> lectureNumberColumn = new Column<EvaluationTableModel.LectureScore, Void>(new AbstractCell<Void>() {

      @Override
      public void render(com.google.gwt.cell.client.Cell.Context context, Void value, SafeHtmlBuilder sb) {
        sb.appendHtmlConstant(String.valueOf(context.getIndex() + 1));
      }

    }) {

      @Override
      public Void getValue(@SuppressWarnings("unused") LectureScore object) {
        return null;
      }
    };

    final Column<LectureScore, String> attendanceColumn = new Column<EvaluationTableModel.LectureScore, String>(new SelectionCell(Arrays.asList("PRESENT", "ABSENT"))) {

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
