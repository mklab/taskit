/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.shared.RecordProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;


/**
 * @author Yuhi Ishikura
 */
public class RecordView extends Composite {

  static interface Binder extends UiBinder<Composite, RecordView> {
    // noting to do
  }

  @UiField
  Label rankLabel;
  @UiField
  Label rank;
  @UiField
  Label scoreLabel;
  @UiField
  Label score;
  @UiField
  Label deviationLabel;
  @UiField
  Label deviation;

  /**
   * {@link RecordView}オブジェクトを構築します。
   */
  public RecordView() {
    final Binder binder = GWT.create(Binder.class);
    initWidget(binder.createAndBindUi(this));
  }

  /**
   * 成績データを設定します。
   * 
   * @param record 成績
   */
  public void setRecord(RecordProxy record) {
    final NumberFormat fmt = NumberFormat.getFormat("00.00"); //$NON-NLS-1$
    this.rank.setText(String.valueOf(record.getRank()) + "/" + String.valueOf(record.getStatistics().getStudentCount())); //$NON-NLS-1$
    this.score.setText(fmt.format(record.getScore()) + "/" + fmt.format(record.getStatistics().getMaximumScore())); //$NON-NLS-1$
    this.deviation.setText(fmt.format(record.getDeviation()));
  }

}
