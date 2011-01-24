package org.mklab.taskit.client.ui;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mklab.taskit.shared.model.Lecture;
import org.mklab.taskit.shared.model.Submission;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;


/**
 * 生徒の提出チェック画面を表すクラスです。
 * 
 * @author teshima
 * @version $Revision$, 2011/01/19
 */
public class StudentScoreView extends Composite {

  /** 成績チェック用のテーブルです。 */
  private FlexTable scoreCheckTable;

  /**
   * Initialize the generated object of {@link StudentScoreView}.
   * 
   * @param id 生徒のIDです。
   */
  public StudentScoreView(String id) {
    ListBox scoreListBox = new ListBox();
    scoreListBox.setItemText(0, "〇");
    scoreListBox.setItemText(1, "△");
    scoreListBox.setItemText(2, "×");

    Label informationLabel = new Label(id + " さんの提出チェック画面");
    this.scoreCheckTable = new FlexTable();
  }

  /**
   * 指定した講義の日付と同じ日付の行が何行目かを調べ、その行番号を返します。。
   * 
   * @param date 講義の日付です。
   * @return 指定した講義の日付と同じ日付の行番号です。
   */
  private int getRowOfSameDate(String date) {
    int row = 0;
    for (int i = 0; i < this.scoreCheckTable.getRowCount(); i++) {
      if (this.scoreCheckTable.getText(i, 0).equals(date)) {
        row = i;
      }
    }
    return row;
  }

  /**
   * 日付だけでなく第何講なのかも示す時に用います。
   * 
   * @param lectures 講義回
   */
  public void setLecture(List<Lecture> lectures) {
    // TODO
  }
}