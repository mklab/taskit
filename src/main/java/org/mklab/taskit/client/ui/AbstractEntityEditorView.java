/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
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

  @UiField(provided = true)
  CellTable<E> table;
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
    initTable(this.table);
    return binder.createAndBindUi(this);
  }

  /**
   * エンティティを編集するテーブルを設定します。
   * 
   * @param cellTable エンティティを編集するテーブル
   */
  protected abstract void initTable(CellTable<E> cellTable);

  @UiHandler("newButton")
  void onNewButtonClicked(@SuppressWarnings("unused") ClickEvent evt) {
    final E entity = this.presenter.newEntity();
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
  public final void setPresenter(org.mklab.taskit.client.ui.EntityEditorView.Presenter<E> presenter) {
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
