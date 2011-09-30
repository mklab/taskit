/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.LayoutPanel;
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
public abstract class AbstractTaskitView extends LayoutPanel implements TaskitView {

  private ClientFactory clientFactory;

  /**
   * {@link AbstractTaskitView}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AbstractTaskitView(ClientFactory clientFactory) {
    if (clientFactory == null) throw new NullPointerException();
    this.clientFactory = clientFactory;
    init();
  }

  /**
   * ビューの初期化を行ないます。
   */
  private final void init() {
    final Widget content = initContent();
    if (content == null) throw new NullPointerException();
    add(content);
  }

  /**
   * コンテンツパートのウィジェットを作成します。
   * 
   * @return コンテンツパート
   */
  protected abstract Widget initContent();

  /**
   * クライアントファクトリを取得します。
   * 
   * @return クライアントファクトリ
   */
  protected final ClientFactory getClientFactory() {
    return this.clientFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showMessage(String message) {
    Window.alert(message);
  }

}
