/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.Messages;
import org.mklab.taskit.shared.LectureProxy;

import java.util.Date;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;


/**
 * 講義データを編集するビューです。
 * 
 * @author ishikura
 */
public class LectureEditor extends AbstractEntityEditorView<LectureProxy> {

  /**
   * {@link LectureEditor}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public LectureEditor(ClientFactory clientFactory) {
    super(clientFactory);
  }

  private Column<LectureProxy, Date> createDateColumn() {
    final Column<LectureProxy, Date> dateColumn = new Column<LectureProxy, Date>(new DatePickerCell()) {

      @Override
      public Date getValue(LectureProxy object) {
        return object.getDate();
      }

    };
    dateColumn.setFieldUpdater(new FieldUpdater<LectureProxy, Date>() {

      @Override
      public void update(@SuppressWarnings("unused") int index, LectureProxy object, Date value) {
        final LectureProxy lecture = getPresenter().edit(object);
        lecture.setDate(value);
        getPresenter().save(lecture);
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

      @Override
      public void update(@SuppressWarnings("unused") int index, LectureProxy object, String value) {
        final LectureProxy lecture = getPresenter().edit(object);
        lecture.setTitle(value);
        getPresenter().save(lecture);
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

      @Override
      public void update(@SuppressWarnings("unused") int index, LectureProxy object, String value) {
        final LectureProxy lecture = getPresenter().edit(object);
        lecture.setDescription(value);
        getPresenter().save(lecture);
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

      @Override
      @SuppressWarnings("unused")
      public void update(int index, LectureProxy object, String value) {
        final LectureProxy lecture = getPresenter().edit(object);
        getPresenter().delete(lecture);
      }
    });

    return deleteButtonColumn;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initTable(CellTable<LectureProxy> cellTable) {
    Messages messages = getClientFactory().getMessages();
    cellTable.addColumn(createDateColumn(), messages.dateLabel());
    cellTable.addColumn(createTitleColumn(), messages.titleLabel());
    cellTable.addColumn(createDescriptionColumn(), messages.descriptionLabel());
    cellTable.addColumn(createDeleteColumn());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void createDefaultEntity(LectureProxy entity) {
    final Messages messages = getClientFactory().getMessages();
    entity.setDate(new Date());
    entity.setTitle(messages.defaultLectureTitle());
  }

}
