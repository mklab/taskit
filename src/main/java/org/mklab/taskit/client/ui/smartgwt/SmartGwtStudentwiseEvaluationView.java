/**
 * 
 */
package org.mklab.taskit.client.ui.smartgwt;

import java.util.Map;
import java.util.Map.Entry;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.AbstractTaskitView;
import org.mklab.taskit.client.ui.StudentwiseEvaluationView;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.widgets.grid.CellEditValueFormatter;
import com.smartgwt.client.widgets.grid.CellEditValueParser;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 6, 2011
 */
public class SmartGwtStudentwiseEvaluationView extends AbstractTaskitView implements StudentwiseEvaluationView {

  /** レコード中の行ヘッダの値の属性値の名前です。 */
  private static final String ROW_HEADER_ATTRIBUTE = "rowHeader"; //$NON-NLS-1$
  /** レコード中の列数の値の属性値の名前です。 */
  private static final String COLUMN_COUNT_ATTRIBUTE = "columnCount"; //$NON-NLS-1$
  /** 行ヘッダとして利用する列数です。 */
  private static final int ROW_HEADER_COLUMNS = 1;

  private ListGrid listGrid;
  private ScoreValueHandler valueHandler = new ScoreValueHandler();
  private Presenter presenter;

  /**
   * {@link SmartGwtStudentwiseEvaluationView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public SmartGwtStudentwiseEvaluationView(ClientFactory clientFactory) {
    super(clientFactory);
    init();
  }

  /**
   * @see org.mklab.taskit.client.ui.AbstractTaskitView#initContent()
   */
  @Override
  protected Widget initContent() {
    this.listGrid = new ListGrid();
    final ListGridField titleField = new ListGridField("title", "Title"); //$NON-NLS-1$ //$NON-NLS-2$
    this.listGrid.setFields(titleField);
    this.listGrid.setHeight100();
    this.listGrid.setWidth100();

    this.listGrid.setEditEvent(ListGridEditEvent.CLICK);
    this.listGrid.setEditByCell(Boolean.TRUE); // 行単位編集ではなくセル単位編集
    this.listGrid.freezeField(0);

    this.listGrid.addEditCompleteHandler(new EditCompleteHandler() {

      @SuppressWarnings({"unqualified-field-access", "synthetic-access"})
      @Override
      public void onEditComplete(EditCompleteEvent event) {
        if (isEditable(event.getRowNum(), event.getColNum() - ROW_HEADER_COLUMNS) == false) return;
        final int lecture = event.getRowNum();

        /*
         * Comments not implemented.
         */
        final String publicComment = ""; //$NON-NLS-1$
        final String privateComment = ""; //$NON-NLS-1$

        for (Entry<?, ?> entry : ((Map<?, ?>)event.getNewValues()).entrySet()) {
          final String fieldName = (String)entry.getKey();

          final Integer score = (Integer)entry.getValue();
          final int no;
          try {
            no = Integer.parseInt(fieldName);
          } catch (NumberFormatException ex) {
            // よくわからないフィールド名、__gwt_ObjectIdが含まれるため、その場合には何もしない
            continue;
          }

          presenter.onEvaluationChange(lecture, no, score.intValue(), publicComment, privateComment);
        }
      }
    });
    return this.listGrid;
  }

  /**
   * @see org.mklab.taskit.client.ui.StudentwiseEvaluationView#setEvaluation(int,
   *      int, int)
   */
  @Override
  public void setEvaluation(int index, int no, int evaluation) {
    final ListGridRecord record = this.listGrid.getRecord(index);
    record.setAttribute(String.valueOf(no), Integer.valueOf(evaluation));
  }

  /**
   * @see org.mklab.taskit.client.ui.EvaluationTable#setPresenter(org.mklab.taskit.client.ui.EvaluationTable.Presenter)
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  /**
   * @see org.mklab.taskit.client.ui.EvaluationTable#setColumnCount(int)
   */
  @Override
  public void setColumnCount(int columnCount) {
    final ListGridField[] fields = new ListGridField[columnCount + ROW_HEADER_COLUMNS];
    fields[0] = new ListGridField(ROW_HEADER_ATTRIBUTE, ""); //$NON-NLS-1$
    for (int i = 0; i < columnCount; i++) {
      final ListGridField field = new ListGridField(String.valueOf(i));
      fields[i + 1] = field;
      field.setValueMap(ScoreSignature.GOOD.getLabel(), ScoreSignature.SOSO.getLabel(), ScoreSignature.BAD.getLabel());
      field.setCanEdit(Boolean.TRUE);
      field.setEditValueParser(this.valueHandler);
      field.setEditValueFormatter(this.valueHandler);
      field.setCellFormatter(this.valueHandler);
      field.setAlign(Alignment.CENTER);
    }
    this.listGrid.setFields(fields);
  }

  /**
   * @see org.mklab.taskit.client.ui.EvaluationTable#setRowCount(int)
   */
  @Override
  public void setRowCount(int rowCount) {
    final ListGridRecord[] records = new ListGridRecord[rowCount];
    for (int i = 0; i < records.length; i++) {
      records[i] = new ListGridRecord();
    }
    this.listGrid.setRecords(records);
  }

  /**
   * @see org.mklab.taskit.client.ui.EvaluationTable#setRowHeader(int,
   *      java.lang.String)
   */
  @Override
  public void setRowHeader(int rowIndex, String header) {
    final ListGridRecord record = this.listGrid.getRecord(rowIndex);
    record.setAttribute(ROW_HEADER_ATTRIBUTE, header);
  }

  /**
   * @see org.mklab.taskit.client.ui.EvaluationTable#setColumnHeader(int,
   *      java.lang.String)
   */
  @Override
  public void setColumnHeader(int columnIndex, String header) {
    this.listGrid.getField(columnIndex + 1).setTitle(header);
  }

  /**
   * @see org.mklab.taskit.client.ui.EvaluationTable#setColumnCountAt(int, int)
   */
  @Override
  public void setColumnCountAt(int rowIndex, int columnCount) {
    this.listGrid.getRecord(rowIndex).setAttribute(COLUMN_COUNT_ATTRIBUTE, Integer.valueOf(columnCount));
  }

  boolean isEditable(int rowIndex, int columnIndex) {
    String columnCount = this.listGrid.getRecord(rowIndex).getAttribute(COLUMN_COUNT_ATTRIBUTE);
    if (columnCount == null) return true;
    return columnIndex < Integer.parseInt(columnCount);
  }

  @SuppressWarnings("nls")
  static enum ScoreSignature {

    GOOD(100, "○"), SOSO(50, "△"), BAD(0, "×"), NOT_EVALUATED(-1, "-");

    private int score;
    private String label;

    /**
     * {@link ScoreSignature}オブジェクトを構築します。
     * 
     * @param score
     */
    private ScoreSignature(int score, String label) {
      this.score = score;
      this.label = label;
    }

    /**
     * scoreを取得します。
     * 
     * @return score
     */
    public int getScore() {
      return this.score;
    }

    /**
     * labelを取得します。
     * 
     * @return label
     */
    public String getLabel() {
      return this.label;
    }

    static ScoreSignature fromScore(int score) {
      if (score < 50) return BAD;
      if (score < 100) return SOSO;
      if (score >= 100) return GOOD;
      return null;
    }

    static ScoreSignature fromLabel(String label) {
      if (label.equals("○")) return GOOD;
      if (label.equals("△")) return SOSO;
      if (label.equals("×")) return BAD;
      if (label.equals("-")) return NOT_EVALUATED;
      return null;
    }

  }

  @SuppressWarnings("unused")
  class ScoreValueHandler implements CellEditValueFormatter, CellEditValueParser, CellFormatter {

    /**
     * @see com.smartgwt.client.widgets.grid.CellEditValueParser#parse(java.lang.Object,
     *      com.smartgwt.client.widgets.grid.ListGridRecord, int, int)
     */
    @Override
    public Object parse(Object value, ListGridRecord record, int rowNum, int colNum) {
      final ScoreSignature sign = ScoreSignature.fromLabel((String)value);
      return Integer.valueOf(sign.getScore());
    }

    /**
     * @see com.smartgwt.client.widgets.grid.CellEditValueFormatter#format(java.lang.Object,
     *      com.smartgwt.client.widgets.grid.ListGridRecord, int, int)
     */
    @Override
    public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
      final Integer columnCount = record.getAttributeAsInt(COLUMN_COUNT_ATTRIBUTE);
      if (isEditable(rowNum, colNum - ROW_HEADER_COLUMNS) == false) return ScoreSignature.NOT_EVALUATED.getLabel();

      if (value == null) return ScoreSignature.BAD.getLabel();

      final ScoreSignature sign = ScoreSignature.fromScore(((Integer)value).intValue());
      return sign.getLabel();
    }

  }
}
