/**
 * 
 */
package org.mklab.taskit.client.ui.smartgwt;

import org.mklab.taskit.client.ui.EvaluationTable;
import org.mklab.taskit.client.ui.smartgwt.SmartGwtStudentScoreView.ScoreSignature;

import com.google.gwt.user.client.ui.Composite;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.grid.CellEditValueFormatter;
import com.smartgwt.client.widgets.grid.CellEditValueParser;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 10, 2011
 */
public class SmartGwtEvaluationTable extends Composite implements EvaluationTable {

  private ListGrid listGrid;
  private ScoreValueHandler valueHandler = new ScoreValueHandler();

  /**
   * {@link SmartGwtEvaluationTable}オブジェクトを構築します。
   */
  public SmartGwtEvaluationTable() {
    this.listGrid = new ListGrid();
    this.listGrid.setEditOnFocus(Boolean.TRUE);
    this.listGrid.setEditEvent(ListGridEditEvent.CLICK);
    this.listGrid.setCellHeight(30);
    this.listGrid.setHeight100();
    this.listGrid.freezeField(1);
    initWidget(this.listGrid);
  }

  /**
   * @see org.mklab.taskit.client.ui.EvaluationTable#setEvaluationItemCount(int)
   */
  @Override
  public void setEvaluationItemCount(int columnCount) {
    final ListGridField[] fields = new ListGridField[columnCount + 1];

    fields[0] = new ListGridField(fieldNameOf(0));
    for (int i = 0; i < columnCount; i++) {
      final ListGridField field = new ListGridField(fieldNameOf(i + 1));
      field.setValueMap("○", "×", "△");
      field.setCanEdit(Boolean.TRUE);
      field.setEditValueParser(this.valueHandler);
      field.setEditValueFormatter(this.valueHandler);
      field.setCellFormatter(this.valueHandler);
      field.setAlign(Alignment.CENTER);
      fields[i + 1] = field;
    }

    this.listGrid.setFields(fields);
  }

  @SuppressWarnings("nls")
  private String fieldNameOf(int columnIndex) {
    if (columnIndex == 0) return "title";
    return "eval" + String.valueOf(columnIndex - 1);
  }

  /**
   * @see org.mklab.taskit.client.ui.EvaluationTable#setRowCount(int)
   */
  @Override
  public void setRowCount(int rowCount) {
    final ListGridRecord[] records = new ListGridRecord[rowCount];
    for (int i = 0; i < records.length; i++) {
      records[i] = new ListGridRecord();
      records[i].setAttribute(fieldNameOf(i + 1), Integer.valueOf(0));
    }
    this.listGrid.setData(records);
  }

  /**
   * @see org.mklab.taskit.client.ui.EvaluationTable#setRowHeader(int,
   *      java.lang.String)
   */
  @Override
  public void setRowHeader(int rowIndex, String header) {
    final ListGridRecord record = this.listGrid.getRecord(rowIndex);
    record.setAttribute(fieldNameOf(0), header);
  }

  /**
   * @see org.mklab.taskit.client.ui.EvaluationTable#setColumnHeader(int,
   *      java.lang.String)
   */
  @Override
  public void setColumnHeader(int columnIndex, String header) {
    final ListGridField field = this.listGrid.getField(columnIndex + 1);
    field.setTitle(header);
  }

  /**
   * @see org.mklab.taskit.client.ui.EvaluationTable#setEvaluation(int, int,
   *      int)
   */
  @Override
  public void setEvaluation(int rowIndex, int columnIndex, int evaluation) {
    final ListGridRecord record = this.listGrid.getRecord(rowIndex);
    record.setAttribute(fieldNameOf(columnIndex + 1), Integer.valueOf(evaluation));
  }

  @SuppressWarnings("unused")
  static class ScoreValueHandler implements CellEditValueFormatter, CellEditValueParser, CellFormatter {

    /**
     * @see com.smartgwt.client.widgets.grid.CellEditValueParser#parse(java.lang.Object,
     *      com.smartgwt.client.widgets.grid.ListGridRecord, int, int)
     */
    @Override
    public Object parse(Object value, ListGridRecord record, int rowNum, int colNum) {
      final String label = (String)value;
      if (label.equals("-")) return Integer.valueOf(-1); //$NON-NLS-1$

      final ScoreSignature sign = ScoreSignature.fromLabel(label);
      return Integer.valueOf(sign.getScore());
    }

    /**
     * @see com.smartgwt.client.widgets.grid.CellEditValueFormatter#format(java.lang.Object,
     *      com.smartgwt.client.widgets.grid.ListGridRecord, int, int)
     */
    @Override
    public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
      if (value == null) return ScoreSignature.BAD.getLabel();
      final int score = ((Integer)value).intValue();

      // not editable
      if (score < 0) return "-"; //$NON-NLS-1$

      final ScoreSignature sign = ScoreSignature.fromScore(score);
      return sign.getLabel();
    }

  }

  /**
   * @see org.mklab.taskit.client.ui.EvaluationTable#setEditable(int, int,
   *      boolean)
   */
  @Override
  public void setEditable(int rowIndex, int columnIndex, boolean editable) {
    final ListGridRecord record = this.listGrid.getRecord(rowIndex);

    final String fieldName = fieldNameOf(columnIndex + 1);
    record.setAttribute(fieldName, Integer.valueOf(editable ? 0 : -1));
  }
}
