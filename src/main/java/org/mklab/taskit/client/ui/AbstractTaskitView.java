/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
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
  private MessageDisplay informationDisplay;
  private MessageDisplay errorDisplay;

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
    final HTML messageArea = new HTML();
    this.informationDisplay = new MessageDisplay(new MessageTarget(messageArea, "white", "#bbb")); //$NON-NLS-1$ //$NON-NLS-2$
    this.errorDisplay = new MessageDisplay(new MessageTarget(messageArea, "yellow", "#f00")); //$NON-NLS-1$ //$NON-NLS-2$

    this.informationDisplay.clearMessage();

    final DockLayoutPanel dock = new DockLayoutPanel(Unit.EM);
    dock.addNorth(messageArea, 1);
    dock.add(content);

    add(dock);
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
  public void showInformationDialog(String message) {
    Window.alert(message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showErrorDialog(String message) {
    Window.alert(message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showInformationMessage(String message) {
    this.errorDisplay.clearMessage();
    this.informationDisplay.showMessage(message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showErrorMessage(String message) {
    this.informationDisplay.clearMessage();
    this.errorDisplay.showMessage(message);
  }

  static class MessageTarget implements MessageDisplay.Target {

    private static final String DEFAULT_BACKGROUND_COLOR = "#ccc"; //$NON-NLS-1$
    String fontColor;
    String backgroundColor;
    HTML messageArea;

    /**
     * {@link MessageTarget}オブジェクトを構築します。
     * 
     * @param messageArea 表示対象要素
     * @param fontColor フォントカラー
     * @param backgroundColor 背景色
     */
    public MessageTarget(HTML messageArea, String fontColor, String backgroundColor) {
      this.messageArea = messageArea;
      this.fontColor = fontColor;
      this.backgroundColor = backgroundColor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMessage(String message) {
      setText(message, this.fontColor, this.backgroundColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearMessage() {
      setText("", this.fontColor, DEFAULT_BACKGROUND_COLOR); //$NON-NLS-1$
    }

    @SuppressWarnings("nls")
    private void setText(String text, String foregroundColor, String bgColor) {
      final String html = "<div style='width:100%;height:100%;font-size:0.9em;background-color:" + bgColor + "'><span style='color:" + foregroundColor + "'>" + SafeHtmlUtils.htmlEscape(text)
          + "</span></div>";
      this.messageArea.setHTML(html);
    }

  }

}
