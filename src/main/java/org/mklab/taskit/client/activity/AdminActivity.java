/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.ui.AdminView;
import org.mklab.taskit.client.ui.AdminViewImpl;
import org.mklab.taskit.client.ui.NewAccountView;
import org.mklab.taskit.client.ui.TaskitView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 26, 2011
 */
public class AdminActivity extends TaskitActivity {

  /**
   * {@link AdminActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public AdminActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * @see org.mklab.taskit.client.activity.TaskitActivity#createTaskitView(org.mklab.taskit.client.ClientFactory)
   */
  @Override
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    final AdminView view = new AdminViewImpl(clientFactory);
    final NewAccountView newAccountView = view.getNewAccountView();
    newAccountView.getSubmitTrigger().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        final String id = newAccountView.getUserId();
        final String password = newAccountView.getPassword();
        final String accountType = newAccountView.getAccountType();
      }
    });
    return view;
  }
}
