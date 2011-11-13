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
 * 学生の成績を表示するビューです。
 * 
 * @see RecordProxy
 * @author Yuhi Ishikura
 */
public class RecordView extends Composite {

  static interface Binder extends UiBinder<Widget, RecordView> {
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
  @UiField
  Label averageLabel;
  @UiField
  Label average;
  @UiField
  Label standardDeviationLabel;
  @UiField
  Label standardDeviation;

  private Messages messages;

  /**
   * {@link RecordView}オブジェクトを構築します。
   * 
   * @param messages ローカライズに利用するメッセージ
   */
  public RecordView(Messages messages) {
    this.messages = messages;
    final Binder binder = GWT.create(Binder.class);
    initWidget(binder.createAndBindUi(this));

    localizeMessages();
  }

  @SuppressWarnings("nls")
  private void localizeMessages() {
    this.rankLabel.setText(this.messages.rankLabel() + ": ");
    this.scoreLabel.setText(this.messages.scoreLabel() + ": ");
    this.deviationLabel.setText(this.messages.deviationLabel() + ": ");
    this.averageLabel.setText(this.messages.averageLabel() + ": ");
    this.standardDeviationLabel.setText(this.messages.standardDeviation() + ": ");
  }

  /**
   * 成績データを設定します。
   * 
   * @param record 成績
   */
  @SuppressWarnings("nls")
  public void setRecord(RecordProxy record) {
    final double percentOfAverage = record.getStatistics().getAverage() / record.getStatistics().getMaximumScore() * 100;

    final NumberFormat fmt = NumberFormat.getFormat("00.00");
    this.rank.setText(String.valueOf(record.getRank()) + "/" + String.valueOf(record.getStatistics().getStudentCount()));
    this.score.setText(fmt.format(record.getScore()) + "/" + fmt.format(record.getStatistics().getMaximumScore()));
    this.deviation.setText(fmt.format(record.getDeviation()));
    this.average.setText(fmt.format(record.getStatistics().getAverage()) + "(" + fmt.format(percentOfAverage) + ")");
    this.standardDeviation.setText(fmt.format(record.getStatistics().getStandardDeviation()));
  }

}
