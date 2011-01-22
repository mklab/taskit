package org.mklab.taskit.client.ui;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;


/**
 * 生徒ID一覧表を示すクラスです。
 * 
 * @author teshima
 * @version $Revision$, 2011/01/19
 */
public class StudentIDView extends Composite {

  /** 生徒IDの一覧表です。 */
  private FlexTable studentIDTable;

  /**
   * Initialize the generated object of {@link StudentIDView}.
   */
  public StudentIDView() {
    this.studentIDTable = new FlexTable();
    this.studentIDTable.setCellSpacing(5);
    this.studentIDTable.setBorderWidth(2);
    initWidget(this.studentIDTable);
  }

  /**
   * Initialize the generated object of {@link StudentIDView}.
   * 
   * @param studentIDs 生徒のIDのリストです。
   */

  public void setStudentIDonTable(List<String> studentIDs) {

    final Label[] studentIDLabels = new Label[studentIDs.size()];
    for (int i = 0; i < studentIDLabels.length; i++) {
      studentIDLabels[i] = new Label(studentIDs.get(i));
      this.studentIDTable.setWidget(i % 20, i / 20, studentIDLabels[i]);

      final int i_ = i;
      studentIDLabels[i_].addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {
          sendIdToServer(studentIDLabels[i_].getText());
        }
      });
    }
  }

  /**
   * 生徒の情報を取得するためにIDをサーバーに渡します。
   * 
   * @param id 生徒のIDです。
   */
  void sendIdToServer(String id) {
    // TODO
  }
}
