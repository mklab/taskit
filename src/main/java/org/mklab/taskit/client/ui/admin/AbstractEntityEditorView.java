/**
 * 
 */
package org.mklab.taskit.client.ui.admin;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.AbstractTaskitView;

import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author ishikura
 * @param <E> エンティティの型
 */
public abstract class AbstractEntityEditorView<E> extends AbstractTaskitView implements EntityEditorView<E> {

  private static final Binder binder = GWT.create(Binder.class);

  static interface Binder extends UiBinder<Widget, AbstractEntityEditorView<?>> {
    // empty
  }

  /** エンティティを編集するテーブルです。 */
  @UiField(provided = true)
  protected CellTable<E> table;
  private Presenter<E> presenter;

  /**
   * {@link AbstractEntityEditorView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AbstractEntityEditorView(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final Widget initContent() {
    this.table = new CellTable<E>();
    initTable();
    return binder.createAndBindUi(this);
  }

  /**
   * エンティティを編集するテーブルを設定します。
   */
  protected abstract void initTable();

  /**
   * エンティティ削除ボタン列を生成します。
   * 
   * @return エンティティ削除ボタン列
   */
  protected Column<E, String> createDeleteColumn() {
    final Column<E, String> deleteButtonColumn = new Column<E, String>(new ButtonCell()) {

      @SuppressWarnings({"unused", "synthetic-access"})
      @Override
      public String getValue(E object) {
        return getClientFactory().getMessages().deleteLabel();
      }

    };
    deleteButtonColumn.setFieldUpdater(new FieldUpdater<E, String>() {

      @Override
      @SuppressWarnings("unused")
      public void update(int index, E object, String value) {
        final E lecture = getPresenter().edit(object);
        getPresenter().delete(lecture);
      }
    });

    return deleteButtonColumn;
  }

  @UiHandler("newButton")
  void onNewButtonClicked(@SuppressWarnings("unused") ClickEvent evt) {
    final E entity = this.presenter.newEntity();
    if (entity == null) return;
    createDefaultEntity(entity);
    this.presenter.save(entity);
  }

  /**
   * 新規生成されたエンティティにデフォルトのプロパティを設定します。
   * 
   * @param entity エンティティ
   */
  protected abstract void createDefaultEntity(E entity);

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setEntities(List<E> lectures) {
    this.table.setRowData(lectures);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setPresenter(org.mklab.taskit.client.ui.admin.EntityEditorView.Presenter<E> presenter) {
    this.presenter = presenter;
  }

  /**
   * プレゼンターを取得します。
   * 
   * @return プレゼンター
   */
  protected final Presenter<E> getPresenter() {
    return this.presenter;
  }

}
