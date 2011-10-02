/**
 * 
 */
package org.mklab.taskit.client.ui.cw;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.AbstractTaskitView;
import org.mklab.taskit.client.ui.HelpCallListView;
import org.mklab.taskit.shared.HelpCallProxy;

import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;


/**
 * @author ishikura
 */
public class CwHelpCallListView extends AbstractTaskitView implements HelpCallListView {

  private Presenter presenter;
  private CellList<HelpCallProxy> list;

  /**
   * {@link CwHelpCallListView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public CwHelpCallListView(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHelpCalls(List<HelpCallProxy> helpCalls) {
    this.list.setRowData(helpCalls);
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
    this.list = new CellList<HelpCallProxy>(new AbstractCell<HelpCallProxy>() {

      @SuppressWarnings("deprecation")
      @Override
      public void render(@SuppressWarnings("unused") com.google.gwt.cell.client.Cell.Context context, HelpCallProxy value, SafeHtmlBuilder sb) {
        final Date date = value.getDate();
        final String callerId = value.getCaller().getId();
        final String message = value.getMessage();

        sb.appendHtmlConstant(date.toLocaleString());
        sb.appendHtmlConstant("<br>"); //$NON-NLS-1$
        sb.appendHtmlConstant("<b>"+callerId+"</b>"); //$NON-NLS-1$ //$NON-NLS-2$
        if (message != null && message.length() > 0) {
          sb.appendHtmlConstant("<font color='red'> '" + message + "'</font>"); //$NON-NLS-1$ //$NON-NLS-2$
        }
      }

    });

    final SingleSelectionModel<HelpCallProxy> selectionModel = new SingleSelectionModel<HelpCallProxy>();
    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

      @SuppressWarnings("synthetic-access")
      @Override
      public void onSelectionChange(@SuppressWarnings("unused") SelectionChangeEvent event) {
        final HelpCallProxy selectedCall = selectionModel.getSelectedObject();
        CwHelpCallListView.this.presenter.helpCallSelected(selectedCall);
      }
    });
    this.list.setSelectionModel(selectionModel);

    return new ScrollPanel(this.list);
  }

}
