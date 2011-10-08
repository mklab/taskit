/**
 * 
 */
package org.mklab.taskit.client.ui.admin;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.Messages;
import org.mklab.taskit.client.ui.AbstractTaskitView;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * @author ishikura
 */
public class AdminViewImpl extends AbstractTaskitView implements AdminView {

  Button lectureButton;
  Button reportButton;

  /**
   * {@link AdminViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AdminViewImpl(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HasClickHandlers getLectureEditorLink() {
    return this.lectureButton;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HasClickHandlers getReportEditorLink() {
    return this.reportButton;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Widget initContent() {
    final Messages messages = getClientFactory().getMessages();
    this.lectureButton = new Button(messages.editLecturesLabel());
    this.reportButton = new Button(messages.editReportsLabel());

    this.lectureButton.setWidth("100%"); //$NON-NLS-1$
    this.reportButton.setWidth("100%"); //$NON-NLS-1$

    final VerticalPanel pn = new VerticalPanel();
    pn.add(this.lectureButton);
    pn.add(this.reportButton);
    pn.setCellHorizontalAlignment(this.lectureButton, HasHorizontalAlignment.ALIGN_CENTER);
    pn.setCellHorizontalAlignment(this.reportButton, HasHorizontalAlignment.ALIGN_CENTER);

    final CaptionPanel caption = new CaptionPanel();
    caption.setCaptionText("Menu"); //$NON-NLS-1$
    caption.add(pn);

    final HorizontalPanel container = new HorizontalPanel();
    container.add(caption);
    return container;
  }

}
