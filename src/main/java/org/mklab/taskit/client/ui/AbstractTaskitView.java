/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.smartgwt.SmartHeaderView;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * TASKitアプリケーションのビューの抽象実装です。
 * <p>
 * ヘッダなどの共通部分を実装します。<br>
 * {@link #init()}を呼び出すことで初めてページの構築を行ないます。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 24, 2011
 */
public abstract class AbstractTaskitView extends Composite implements TaskitView {

  private HeaderView header;
  private ClientFactory clientFactory;

  /**
   * {@link AbstractTaskitView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AbstractTaskitView(ClientFactory clientFactory) {
    if (clientFactory == null) throw new NullPointerException();
    this.clientFactory = clientFactory;
  }

  /**
   * ビューの初期化を行ないます。
   */
  public final void init() {
    this.header = new SmartHeaderView();
    final Widget content = initContent();
    if (content == null) throw new NullPointerException();

    final VerticalPanel vPanel = new VerticalPanel();
    vPanel.setWidth("100%"); //$NON-NLS-1$
    final HTML headerSpace = new HTML();
    headerSpace.setHeight("3.5em"); //$NON-NLS-1$

    vPanel.add(this.header);
    vPanel.add(headerSpace);
    vPanel.add(createCenteringWidget(content));

    initWidget(vPanel);
  }

  private Widget createCenteringWidget(Widget w) {
    final FlowPanel flowPanel = new FlowPanel();
    flowPanel.add(w);
    DOM.setElementAttribute(flowPanel.getElement(), "align", "center"); //$NON-NLS-1$ //$NON-NLS-2$
    return flowPanel;
  }

  /**
   * コンテンツパートのウィジェットを作成します。
   * 
   * @return コンテンツパート
   */
  protected abstract Widget initContent();

  protected final ClientFactory getClientFactory() {
    return this.clientFactory;
  }

  /**
   * @see org.mklab.taskit.client.ui.TaskitView#getHeader()
   */
  @Override
  public final HeaderView getHeader() {
    return this.header;
  }

}
