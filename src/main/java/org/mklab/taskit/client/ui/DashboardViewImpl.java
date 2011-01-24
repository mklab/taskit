/**
 * 
 */
package org.mklab.taskit.client.ui;

import org.mklab.taskit.client.ClientFactory;

import com.google.gwt.user.client.ui.Composite;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 23, 2011
 */
public class DashboardViewImpl extends Composite implements DashboardView {

  private HeaderView header;

  /**
   * {@link DashboardViewImpl}オブジェクトを構築します。
   */
  public DashboardViewImpl(ClientFactory clientFactory) {
    this.header = new HeaderViewImpl(clientFactory);

    initWidget(this.header.asWidget());
  }

  /**
   * headerを取得します。
   * 
   * @return header
   */
  public HeaderView getHeader() {
    return this.header;
  }
}
