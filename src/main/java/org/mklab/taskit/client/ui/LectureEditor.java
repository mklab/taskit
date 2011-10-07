/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.Messages;
import org.mklab.taskit.shared.LectureProxy;

import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;


/**
 * 講義データを編集するビューです。
 * 
 * @author ishikura
 */
public class LectureEditor extends AbstractTaskitView implements Editor<LectureProxy> {

  private static final Binder binder = GWT.create(Binder.class);

  static interface Binder extends UiBinder<Widget, LectureEditor> {
    // empty
  }

  @UiField(provided = true)
  CellTable<LectureProxy> table;
  private Presenter presenter;

  /**
   * {@link LectureEditor}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public LectureEditor(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget initContent() {
    final Messages messages = getClientFactory().getMessages();
    this.table = new CellTable<LectureProxy>();
    this.table.addColumn(createDateColumn(), messages.dateLabel());
    this.table.addColumn(createTitleColumn(), messages.titleLabel());
    this.table.addColumn(createDescriptionColumn(), messages.descriptionLabel());
    this.table.addColumn(createDeleteColumn());
    return binder.createAndBindUi(this);
  }

  private Column<LectureProxy, Date> createDateColumn() {
    final Column<LectureProxy, Date> dateColumn = new Column<LectureProxy, Date>(new DatePickerCell()) {

      @Override
      public Date getValue(LectureProxy object) {
        return object.getDate();
      }

    };
    dateColumn.setFieldUpdater(new FieldUpdater<LectureProxy, Date>() {

      @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
      @Override
      public void update(@SuppressWarnings("unused") int index, LectureProxy object, Date value) {
        final LectureProxy lecture = presenter.edit(object);
        lecture.setDate(value);
        presenter.save(lecture);
      }
    });
    return dateColumn;
  }

  private Column<LectureProxy, String> createTitleColumn() {
    final Column<LectureProxy, String> titleColumn = new Column<LectureProxy, String>(new TextInputCell()) {

      @Override
      public String getValue(LectureProxy object) {
        return object.getTitle();
      }
    };
    titleColumn.setFieldUpdater(new FieldUpdater<LectureProxy, String>() {

      @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
      @Override
      public void update(@SuppressWarnings("unused") int index, LectureProxy object, String value) {
        final LectureProxy lecture = presenter.edit(object);
        lecture.setTitle(value);
        presenter.save(lecture);
      }

    });
    return titleColumn;
  }

  private Column<LectureProxy, String> createDescriptionColumn() {
    final Column<LectureProxy, String> descriptionColumn = new Column<LectureProxy, String>(new TextInputCell()) {

      @Override
      public String getValue(LectureProxy object) {
        return object.getDescription();
      }
    };
    descriptionColumn.setFieldUpdater(new FieldUpdater<LectureProxy, String>() {

      @SuppressWarnings({"synthetic-access", "unqualified-field-access"})
      @Override
      public void update(@SuppressWarnings("unused") int index, LectureProxy object, String value) {
        final LectureProxy lecture = presenter.edit(object);
        lecture.setDescription(value);
        presenter.save(lecture);
      }

    });
    return descriptionColumn;
  }

  private Column<LectureProxy, String> createDeleteColumn() {
    final Column<LectureProxy, String> deleteButtonColumn = new Column<LectureProxy, String>(new ButtonCell()) {

      @SuppressWarnings("unused")
      @Override
      public String getValue(LectureProxy object) {
        return getClientFactory().getMessages().deleteLabel();
      }

    };
    deleteButtonColumn.setFieldUpdater(new FieldUpdater<LectureProxy, String>() {

      @SuppressWarnings({"synthetic-access", "unused"})
      @Override
      public void update(int index, LectureProxy object, String value) {
        final LectureProxy lecture = LectureEditor.this.presenter.edit(object);
        LectureEditor.this.presenter.delete(lecture);
      }
    });

    return deleteButtonColumn;
  }

  @UiHandler("newButton")
  void onNewButtonClicked(@SuppressWarnings("unused") ClickEvent evt) {
    final LectureProxy lecture = this.presenter.newLecture();
    lecture.setDate(new Date());
    lecture.setTitle(getClientFactory().getMessages().defaultLectureTitle());
    this.presenter.save(lecture);
  }

  /**
   * 講義データを設定します。
   * 
   * @param lectures 講義データ
   */
  public void setLectures(List<LectureProxy> lectures) {
    this.table.setRowData(lectures);
  }

  /**
   * プレゼンターを設定します。
   * 
   * @param presenter プレゼンター
   */
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  /**
   * {@link LectureEditor}のプレゼンターです。
   * 
   * @author ishikura
   */
  public static interface Presenter {

    /**
     * 新たな講義オブジェクトを生成します。
     * 
     * @return 講義オブジェクト
     */
    LectureProxy newLecture();

    /**
     * 与えられたオブジェクトの編集可能にしたものを返します。
     * 
     * @param lecture 講義データ
     * @return 編集可能な講義データ
     */
    LectureProxy edit(LectureProxy lecture);

    /**
     * 講義を削除します。
     * 
     * @param lecture 講義データ
     */
    void delete(LectureProxy lecture);

    /**
     * 変更を保存します。
     * 
     * @param lecture 講義データ
     */
    void save(LectureProxy lecture);

  }

}
