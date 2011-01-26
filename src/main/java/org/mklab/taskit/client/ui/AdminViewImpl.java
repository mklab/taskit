/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;

import com.google.gwt.user.client.ui.Widget;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 26, 2011
 */
public class AdminViewImpl extends AbstractTaskitView implements AdminView {

  final NewAccountViewImpl accountView = new NewAccountViewImpl(getClientFactory());

  /**
   * {@link AdminViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AdminViewImpl(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * @see org.mklab.taskit.client.ui.AbstractTaskitView#createContent()
   */
  @Override
  protected Widget createContent() {
    return this.accountView;
  }

  /**
   * @see org.mklab.taskit.client.ui.AdminView#getNewAccountView()
   */
  @Override
  public NewAccountView getNewAccountView() {
    return this.accountView;
  }

}
