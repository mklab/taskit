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
  Label achievementRatioLabel;
  @UiField
  Label achievementRatio;
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
    this.achievementRatioLabel.setText(this.messages.achievementRatioLabel() + ": ");
    this.deviationLabel.setText(this.messages.deviationLabel() + ": ");
    this.averageLabel.setText(this.messages.averageLabel() + ": ");
    this.standardDeviationLabel.setText(this.messages.standardDeviation() + ": ");
  }

  /*
   * ここで見せる成績はあくまでTASKitが独自に計算しているもので
   * 実際の成績とは直結しないため、むやみに見せるとまずい
   */
  /**
   * 学生向けに見せる情報をフィルタリングします。
   */
  public void filterContentsForStudent() {
    this.rankLabel.setVisible(false);
    this.rank.setVisible(false);
    this.deviationLabel.setVisible(false);
    this.deviation.setVisible(false);
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
    this.achievementRatio.setText(fmt.format(record.getScore() / record.getStatistics().getMaximumScore() * 100) + "%");
    this.deviation.setText(fmt.format(record.getDeviation()));
    this.average.setText(fmt.format(percentOfAverage) + "%");
    this.standardDeviation.setText(fmt.format(record.getStatistics().getStandardDeviation()));
  }

}
