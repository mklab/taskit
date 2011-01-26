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

  NewAccountViewImpl accountView;

  /**
   * {@link AdminViewImpl}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AdminViewImpl(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * @see org.mklab.taskit.client.ui.AbstractTaskitView#initContent()
   */
  @Override
  protected Widget initContent() {
    this.accountView = new NewAccountViewImpl(getClientFactory());
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
