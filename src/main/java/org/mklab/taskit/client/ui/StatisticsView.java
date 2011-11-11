/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.Messages;
import org.mklab.taskit.shared.RecordProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author Yuhi Ishikura
 */
public class StatisticsView extends Composite {

  static interface Binder extends UiBinder<Widget, StatisticsView> {
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
  private Messages messages;

  /**
   * {@link StatisticsView}オブジェクトを構築します。
   * 
   * @param messages ローカライズに利用するメッセージ
   */
  public StatisticsView(Messages messages) {
    this.messages = messages;
    final Binder binder = GWT.create(Binder.class);
    initWidget(binder.createAndBindUi(this));
  }

  /**
   * 成績データを設定します。
   * 
   * @param record 成績
   */
  @SuppressWarnings("nls")
  public void setRecord(RecordProxy record) {
    final NumberFormat fmt = NumberFormat.getFormat("00.00");
    this.rankLabel.setText(this.messages.rankLabel() + ": ");
    this.scoreLabel.setText(this.messages.scoreLabel() + ": ");
    this.deviationLabel.setText(this.messages.deviationLabel() + ": ");
    this.rank.setText(String.valueOf(record.getRank()) + "/" + String.valueOf(record.getStatistics().getStudentCount()));
    this.score.setText(fmt.format(record.getScore()) + "/" + fmt.format(record.getStatistics().getMaximumScore()));
    this.deviation.setText(fmt.format(record.getDeviation()));
  }

}
