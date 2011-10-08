/**
 * 
 */
package org.mklab.taskit.client.ui.admin;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.Messages;
import org.mklab.taskit.shared.LectureProxy;

import java.util.Date;

import com.google.gwt.cell.client.DatePickerCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.user.cellview.client.Column;


/**
 * 講義データを編集するビューです。
 * 
 * @author ishikura
 */
public class LectureEditorView extends AbstractEntityEditorView<LectureProxy> {

  /**
   * {@link LectureEditorView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public LectureEditorView(ClientFactory clientFactory) {
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

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initTable() {
    Messages messages = getClientFactory().getMessages();
    this.table.addColumn(createDateColumn(), messages.dateLabel());
    this.table.addColumn(createTitleColumn(), messages.titleLabel());
    this.table.addColumn(createDescriptionColumn(), messages.descriptionLabel());
    this.table.addColumn(createDeleteColumn());
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
