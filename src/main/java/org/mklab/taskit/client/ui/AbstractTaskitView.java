/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * TASKitアプリケーションのビューの抽象実装です。
 * <p>
 * ヘッダなどの共通部分を実装します。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 24, 2011
 */
abstract class AbstractTaskitView extends Composite implements TaskitView {

  private HeaderView header;
  private ClientFactory clientFactory;

  /**
   * {@link AbstractTaskitView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  AbstractTaskitView(ClientFactory clientFactory) {
    if (clientFactory == null) throw new NullPointerException();
    this.clientFactory = clientFactory;
    this.header = new HeaderViewImpl(clientFactory);
    final Widget content = createContent();

    final VerticalPanel vPanel = new VerticalPanel();
    final HTML headerSpace = new HTML();
    headerSpace.setHeight("3.5em"); //$NON-NLS-1$

    vPanel.add(this.header);
    vPanel.add(headerSpace);
    vPanel.add(content);

    initWidget(vPanel);
  }

  /**
   * コンテンツパートのウィジェットを作成します。
   * 
   * @return コンテンツパート
   */
  protected abstract Widget createContent();

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
