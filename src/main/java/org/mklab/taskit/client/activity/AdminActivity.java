/**
 * 
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.LectureEdit;
import org.mklab.taskit.client.place.ReportEdit;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.client.ui.admin.AdminView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;


/**
 * 管理者アクティビティを表すクラスです。
 * 
 * @author Yuhi Ishikura
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
   * {@inheritDoc}
   */
  @Override
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    final AdminView adminView = clientFactory.getAdminView();
    adminView.getLectureEditorLink().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        getClientFactory().getPlaceController().goTo(LectureEdit.INSTANCE);
      }
    });
    adminView.getReportEditorLink().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        getClientFactory().getPlaceController().goTo(ReportEdit.INSTANCE);
      }
    });
    return adminView;
  }

}
