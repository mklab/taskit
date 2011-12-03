/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.ClientFactory;
import org.mklab.taskit.client.place.LectureEdit;
import org.mklab.taskit.client.place.ReportEdit;
import org.mklab.taskit.client.place.UserEdit;
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
    adminView.getUserEditorLink().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(@SuppressWarnings("unused") ClickEvent event) {
        getClientFactory().getPlaceController().goTo(UserEdit.INSTANCE);
      }
    });
    return adminView;
  }

}
