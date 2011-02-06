/**
 * 
 */
package org.mklab.taskit.client.ui.smartgwt;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.AbstractTaskitView;
import org.mklab.taskit.client.ui.StudentScoreView;

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
public class SmartGwtStudentScoreView extends AbstractTaskitView implements StudentScoreView {

  private ListGrid listGrid;
  private ScoreValueHandler valueHandler = new ScoreValueHandler();
  private Presenter presenter;

  /**
   * {@link SmartGwtStudentScoreView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public SmartGwtStudentScoreView(ClientFactory clientFactory) {
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
    this.listGrid.addEditCompleteHandler(new EditCompleteHandler() {

      @Override
      public void onEditComplete(EditCompleteEvent event) {
        final int no = event.getColNum() - 1;
        final int lecture = event.getRowNum();

        final String fieldKey = String.valueOf(no);
        final Integer newValue = (Integer)event.getNewValues().get(fieldKey);
        presenter.onEvaluationChange(lecture, no, newValue.intValue());
      }
    });
    return this.listGrid;
  }

  /**
   * @see org.mklab.taskit.client.ui.StudentScoreView#setLectureCount(int)
   */
  @Override
  public void setLectureCount(int lectureCount) {
    final ListGridRecord[] records = new ListGridRecord[lectureCount];
    for (int i = 0; i < records.length; i++) {
      records[i] = new ListGridRecord();
    }
    this.listGrid.setRecords(records);
  }

  /**
   * @see org.mklab.taskit.client.ui.StudentScoreView#setLectureInfo(int, int,
   *      java.lang.String)
   */
  @SuppressWarnings("nls")
  @Override
  public void setLectureInfo(int index, int reportCount, String title) {
    // setup table model
    final ListGridField[] fields = new ListGridField[reportCount + 1];

    fields[0] = new ListGridField("title", "Title");
    for (int i = 0; i < reportCount; i++) {
      final ListGridField field = new ListGridField(String.valueOf(i));
      fields[i + 1] = field;
      field.setValueMap("○", "×", "△");
      field.setCanEdit(Boolean.TRUE);
      field.setEditValueParser(this.valueHandler);
      field.setEditValueFormatter(this.valueHandler);
      field.setCellFormatter(this.valueHandler);
      field.setAlign(Alignment.CENTER);
    }
    this.listGrid.setFields(fields);

    // setup row header
    final ListGridRecord record = this.listGrid.getRecord(index);
    record.setAttribute("title", title);
  }

  /**
   * @see org.mklab.taskit.client.ui.StudentScoreView#setEvaluation(int, int,
   *      int)
   */
  @Override
  public void setEvaluation(int index, int no, int evaluation) {
    final ListGridRecord record = this.listGrid.getRecord(index);
    record.setAttribute(String.valueOf(no), Integer.valueOf(evaluation));
  }

  /**
   * @see org.mklab.taskit.client.ui.StudentScoreView#setPresenter(org.mklab.taskit.client.ui.StudentScoreView.Presenter)
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  @SuppressWarnings("nls")
  static enum ScoreSignature {

    GOOD(100, "○"), SOSO(50, "△"), BAD(0, "×");

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
      return null;
    }

  }

  @SuppressWarnings("unused")
  static class ScoreValueHandler implements CellEditValueFormatter, CellEditValueParser, CellFormatter {

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
      if (value == null) return ScoreSignature.BAD.getLabel();
      final ScoreSignature sign = ScoreSignature.fromScore(((Integer)value).intValue());
      return sign.getLabel();
    }

  }
}
