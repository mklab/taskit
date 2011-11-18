/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.HelpCallList;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
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
public abstract class AbstractTaskitView extends LayoutPanel implements TaskitView, HelpCallDisplayable {

  private ClientFactory clientFactory;
  private MessageDisplay informationDisplay;
  private MessageDisplay errorDisplay;

  private DockLayoutPanel header;
  private Widget helpCallArea;
  private Label helpCallCountLabel;

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

  private final void init() {
    final Widget content = initContent();
    if (content == null) throw new NullPointerException();
    final HTML messageArea = new HTML();
    this.informationDisplay = new MessageDisplay(new MessageTarget(messageArea, "white", "#bbb")); //$NON-NLS-1$ //$NON-NLS-2$
    this.errorDisplay = new MessageDisplay(new MessageTarget(messageArea, "yellow", "#f00")); //$NON-NLS-1$ //$NON-NLS-2$

    this.informationDisplay.clearMessage();

    final DockLayoutPanel dock = new DockLayoutPanel(Unit.EM);
    this.header = new DockLayoutPanel(Unit.EM);
    {
      this.helpCallCountLabel = new Label();
      this.helpCallCountLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
      this.helpCallCountLabel.setSize("100%", "100%"); //$NON-NLS-1$ //$NON-NLS-2$
      DOM.setStyleAttribute(this.helpCallCountLabel.getElement(), "color", "white"); //$NON-NLS-1$ //$NON-NLS-2$

      final Image image = new Image("taskit/hand32.png"); //$NON-NLS-1$
      image.setSize("1em", "1em"); //$NON-NLS-1$ //$NON-NLS-2$

      final LayoutPanel layoutPanel = new LayoutPanel();
      layoutPanel.add(image);
      layoutPanel.setWidgetTopHeight(image, 0, Unit.EM, 1, Unit.EM);
      layoutPanel.setWidgetLeftWidth(image, 0, Unit.EM, 2, Unit.EM);
      layoutPanel.add(this.helpCallCountLabel);
      layoutPanel.setWidgetTopHeight(this.helpCallCountLabel, 0, Unit.EM, 1, Unit.EM);
      layoutPanel.setWidgetLeftWidth(this.helpCallCountLabel, 0, Unit.EM, 2, Unit.EM);
      DOM.setStyleAttribute(layoutPanel.getElement(), "backgroundColor", "#070"); //$NON-NLS-1$ //$NON-NLS-2$
      this.helpCallArea = layoutPanel;

      this.header.addWest(layoutPanel, 2);
    }
    this.header.add(messageArea);
    dock.addNorth(this.header, 1);
    dock.add(content);

    add(dock);

    // ビューらしからぬ処理。どうにかしたい。
    this.helpCallCountLabel.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        getClientFactory().getPlaceController().goTo(HelpCallList.INSTANCE);
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHelpCallDisplayEnabled(boolean enabled) {
    this.header.setWidgetSize(this.helpCallArea, enabled ? 2 : 0);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void showHelpCallCount(int count) {
    this.helpCallCountLabel.setText(String.valueOf(count));
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
