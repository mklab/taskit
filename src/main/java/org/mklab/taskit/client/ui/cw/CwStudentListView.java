/**
 * 
 */
package org.mklab.taskit.client.ui.cw;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.AbstractTaskitView;
import org.mklab.taskit.client.ui.HeaderView;
import org.mklab.taskit.client.ui.StudentListView;

import java.util.Arrays;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;


/**
 * @author ishikura
 */
public class CwStudentListView extends AbstractTaskitView implements StudentListView {

  private CellList<String> cellList;
  private Presenter presenter;

  /**
   * {@link CwStudentListView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public CwStudentListView(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setListData(String[] listData) {
    this.cellList.setRowData(Arrays.asList(listData));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
    init();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget initContent() {
    final ClickableTextCell cell = new ClickableTextCell() {

      /**
       * {@inheritDoc}
       */
      @Override
      protected void render(com.google.gwt.cell.client.Cell.Context context, SafeHtml value, SafeHtmlBuilder sb) {
        sb.appendHtmlConstant("<div style='font-size:2em'>"); //$NON-NLS-1$
        super.render(context, value, sb);
        sb.appendHtmlConstant("</div>"); //$NON-NLS-1$
      }
    };
    this.cellList = new CellList<String>(cell);
    this.cellList.setSelectionModel(new SingleSelectionModel<String>());
    this.cellList.getSelectionModel().addSelectionChangeHandler(new Handler() {

      @Override
      public void onSelectionChange(SelectionChangeEvent event) {
        System.out.println(event);
      }
    });
    StudentScorePanel pn = new StudentScorePanel();
    return pn;
    //return this.cellList;
  }
}
