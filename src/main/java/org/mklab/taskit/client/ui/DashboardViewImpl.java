/**
 * 
 */
package org.mklab.taskit.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 23, 2011
 */
public class DashboardViewImpl extends Composite implements DashboardView {

  /**
   * {@link DashboardViewImpl}オブジェクトを構築します。
   */
  public DashboardViewImpl() {
    initWidget(new Label("Dashboard")); //$NON-NLS-1$
  }

}
