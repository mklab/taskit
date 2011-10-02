/**
 * 
 */
package org.mklab.taskit.client.ui.cw;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.model.AttendanceListItem;
import org.mklab.taskit.client.ui.AbstractTaskitView;
import org.mklab.taskit.client.ui.AttendanceListView;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.UserProxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.AbstractInputCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author ishikura
 */
public class CwAttendanceListView extends AbstractTaskitView implements AttendanceListView {

  private static final Binder binder = GWT.create(Binder.class);
  private Presenter presenter;
  private Map<LectureProxy, LectureListItem> lectureToListItem = new HashMap<LectureProxy, CwAttendanceListView.LectureListItem>();
  @UiField(provided = true)
  ValueListBox<LectureListItem> lectureList;
  @UiField(provided = true)
  CellTable<AttendanceListItem> table;

  interface Binder extends UiBinder<Widget, CwAttendanceListView> {
    // empty
  }

  /**
   * {@link CwAttendanceListView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public CwAttendanceListView(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget initContent() {
    this.lectureList = new ValueListBox<LectureListItem>(new Renderer<LectureListItem>() {

      @SuppressWarnings({"nls", "deprecation"})
      @Override
      public String render(LectureListItem object) {
        if (object == null) return null;
        return String.valueOf(object.getIndex() + 1) + " " + object.getLecture().getTitle() + " [" + object.getLecture().getDate().toLocaleString() + "]";
      }

      @Override
      public void render(LectureListItem object, Appendable appendable) throws IOException {
        appendable.append(render(object));
      }

    });
    this.lectureList.addValueChangeHandler(new ValueChangeHandler<CwAttendanceListView.LectureListItem>() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onValueChange(ValueChangeEvent<LectureListItem> event) {
        CwAttendanceListView.this.presenter.lectureSelectionChanged(event.getValue().getLecture());
      }
    });

    this.table = new CellTable<AttendanceListItem>();
    final Column<AttendanceListItem, UserProxy> userColumn = new Column<AttendanceListItem, UserProxy>(new AbstractCell<UserProxy>() {

      @Override
      public void render(@SuppressWarnings("unused") com.google.gwt.cell.client.Cell.Context context, UserProxy value, SafeHtmlBuilder sb) {
        String name = value.getName();
        if (name == null) name = value.getAccount().getId();
        sb.appendHtmlConstant(value.getAccount().getId());
      }

    }) {

      @Override
      public UserProxy getValue(AttendanceListItem object) {
        return object.getUser();
      }
    };
    this.table.addColumn(userColumn, "User"); //$NON-NLS-1$
    for (AttendanceType type : AttendanceType.values()) {
      final Column<AttendanceListItem, AttendanceListItem> attendanceCell = new Column<AttendanceListItem, AttendanceListItem>(new AttendanceCell(type)) {

        @Override
        public AttendanceListItem getValue(AttendanceListItem object) {
          return object;
        }

      };
      this.table.addColumn(attendanceCell, type.name());
    }

    return binder.createAndBindUi(this);
  }

  /**
   * 出席種別の選択を行うラジオボタンのセルです。
   * <p>
   * 直接サーバーと通信を行わず、セルの段階ではValueUpdaterの呼び出しにとどめたい・・・。
   * 
   * @author ishikura
   */
  class AttendanceCell extends AbstractInputCell<AttendanceListItem, AttendanceListItem> {

    private AttendanceType attendanceType;

    AttendanceCell(AttendanceType type) {
      super("change"); //$NON-NLS-1$
      this.attendanceType = type;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"synthetic-access", "unused"})
    @Override
    public void onBrowserEvent(com.google.gwt.cell.client.Cell.Context context, Element parent, AttendanceListItem value, NativeEvent event, ValueUpdater<AttendanceListItem> valueUpdater) {
      if ("change".equals(event.getType()) == false) return; //$NON-NLS-1$

      final InputElement input = parent.getFirstChild().cast();
      final boolean isChecked = input.isChecked();

      if (isChecked == false) return;

      CwAttendanceListView.this.presenter.attend(value.getUser().getAccount(), this.attendanceType);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"nls", "unused"})
    @Override
    public void render(com.google.gwt.cell.client.Cell.Context context, AttendanceListItem value, SafeHtmlBuilder sb) {
      final String id = value.getUser().getAccount().getId();
      final boolean checked = value.getAttendance() != null && value.getAttendance().getType() == this.attendanceType;
      sb.appendHtmlConstant("<input type='radio' name='" + id + "'" + (checked ? " checked" : "") + "/>");
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLectures(List<LectureProxy> lectures) {
    final List<LectureListItem> listItems = new ArrayList<CwAttendanceListView.LectureListItem>();
    this.lectureToListItem.clear();
    int i = 0;
    for (LectureProxy lecture : lectures) {
      final LectureListItem listItem = new LectureListItem(i++, lecture);
      this.lectureToListItem.put(lecture, listItem);
      listItems.add(listItem);
    }
    this.lectureList.setAcceptableValues(listItems);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAttendances(List<AttendanceListItem> attendances) {
    this.table.setRowData(attendances);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public LectureProxy getSelectedLecture() {
    return this.lectureList.getValue().getLecture();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSelectedLecture(LectureProxy lecture) {
    final LectureListItem listItem = this.lectureToListItem.get(lecture);
    this.lectureList.setValue(listItem, true);
  }

  /**
   * {@link ValueListBox}で表示するアイテムを表すクラスです。
   * <p>
   * {@link Renderer}
   * では、描画時に講義インデックスを取得することができないため、専用のバリューオブジェクトのためにこのクラスを作成しています。
   * 
   * @author ishikura
   */
  static class LectureListItem {

    private int index;
    private LectureProxy lecture;

    LectureListItem(int index, LectureProxy lecture) {
      if (lecture == null) throw new NullPointerException();
      this.index = index;
      this.lecture = lecture;
    }

    /**
     * indexを取得します。
     * 
     * @return index
     */
    public int getIndex() {
      return this.index;
    }

    /**
     * lectureを取得します。
     * 
     * @return lecture
     */
    public LectureProxy getLecture() {
      return this.lecture;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((this.lecture == null) ? 0 : this.lecture.hashCode());
      return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      LectureListItem other = (LectureListItem)obj;
      if (this.lecture == null) {
        if (other.lecture != null) return false;
      } else if (!this.lecture.equals(other.lecture)) return false;
      return true;
    }

  }

}
