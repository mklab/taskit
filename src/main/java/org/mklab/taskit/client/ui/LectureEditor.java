/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.Messages;
import org.mklab.taskit.shared.LectureProxy;

import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author yuhi
 */
public class LectureEditor extends Composite {

  @UiField(provided = true)
  CellTable<LectureProxy> table;
  private static final Binder binder = GWT.create(Binder.class);

  static interface Binder extends UiBinder<Widget, LectureEditor> {
    // empty
  }

  /**
   * {@link LectureEditor}オブジェクトを構築します。
   */
  public LectureEditor(Messages messages) {
    this.table = new CellTable<LectureProxy>();
    this.table.addColumn(new Column<LectureProxy, Date>(new DatePickerCell()) {

      @Override
      public Date getValue(LectureProxy object) {
        return object.getDate();
      }

    });
    this.table.addColumn(new Column<LectureProxy, String>(new TextInputCell()) {

      @Override
      public String getValue(LectureProxy object) {
        return object.getTitle();
      }
    }, messages.titleLabel());
    this.table.addColumn(new Column<LectureProxy, String>(new TextInputCell()) {

      @Override
      public String getValue(LectureProxy object) {
        return object.getDescription();
      }
    });

    initWidget(binder.createAndBindUi(this));
  }

  public void setLectures(List<LectureProxy> lectures) {
    this.table.setRowData(lectures);
  }
}
