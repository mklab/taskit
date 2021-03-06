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
import org.mklab.taskit.client.LocalDatabase;
import org.mklab.taskit.client.Messages;
import org.mklab.taskit.client.place.CheckInList;
import org.mklab.taskit.client.place.StudentList;
import org.mklab.taskit.client.ui.HelpCallListView;
import org.mklab.taskit.client.ui.TaskitView;
import org.mklab.taskit.shared.AccountProxy;
import org.mklab.taskit.shared.CheckMapProxy;
import org.mklab.taskit.shared.HelpCallProxy;
import org.mklab.taskit.shared.event.CheckMapEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.place.shared.Place;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

import de.novanic.eventservice.client.event.Event;


/**
 * 現在呼び出し中のヘルプコールのリストを表示するアクティビティです。
 * 
 * @author Yuhi Ishikura
 */
public final class HelpCallListActivity extends TaskitActivity implements HelpCallListView.Presenter {

  /**
   * {@link HelpCallListActivity}オブジェクトを構築します。
   * 
   * @param clientFactory クライアントファクトリ
   */
  public HelpCallListActivity(ClientFactory clientFactory) {
    super(clientFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onViewShown() {
    super.onViewShown();
    updateHelpCallListAsync(false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TaskitView createTaskitView(ClientFactory clientFactory) {
    final HelpCallListView view = clientFactory.getHelpCallListView();
    view.setPresenter(this);

    return view;
  }

  /**
   * ヘルプコールリストを非同期で更新します。
   * <p>
   * ローカルにキャッシュが存在した場合は新たに取得せずそれを表示します。(ヘルプコールはアプリケーション全体で同期しているため、
   * このアクティビティで取得する必要はありません。)
   * 
   * @param isAuto 定期的に行う自動更新の場合はtrue,そうでなければfalse。この値は表示するステータスメッセージにのみ影響します。
   */
  private void updateHelpCallListAsync(final boolean isAuto) {
    final Messages messages = getClientFactory().getMessages();
    showInformationMessage(isAuto ? messages.fetchingCallListAutoMessage() : messages.fetchingCallListMessage());

    /*
     * org.mklab.taskit.shared.HelpCallListItemProxyにより一度の通信で済ませたいが、
     * GWTのバグ(?)により取得できない。
     * ValueProxyのリストの場合、Resolutionがリストの最後の要素にしか適用されず、その結果
     * 最後のHelpCallListItem要素以外のHelpCallProxyのcallerがnullになってしまう。
     */
    getClientFactory().getLocalDatabase().getCacheOrExecute(LocalDatabase.CALL_LIST, new Receiver<List<HelpCallProxy>>() {

      @Override
      public void onSuccess(List<HelpCallProxy> response) {
        showInformationMessage(isAuto ? messages.fetchedCallListAutoMessage() : messages.fetchedCallListMessage());
        ((HelpCallListView)getTaskitView()).setHelpCalls(response);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        if (isAuto) {
          showErrorMessage(messages.fetchedCallListFailMessage() + ":" + error.getMessage()); //$NON-NLS-1$
        } else {
          showErrorDialog(messages.fetchedCallListAutoFailMessage() + ":" + error.getMessage()); //$NON-NLS-1$
        }
      }
    });
    getClientFactory().getRequestFactory().checkMapRequest().getAllCheckMap().with("student").fire(new Receiver<List<CheckMapProxy>>() { //$NON-NLS-1$

          @Override
          public void onSuccess(List<CheckMapProxy> response) {
            final Map<String, List<String>> studentToUsers = new HashMap<String, List<String>>();
            for (CheckMapProxy checkMap : response) {
              final String taId = checkMap.getId();
              final String studentId = checkMap.getStudent().getId();

              List<String> students = studentToUsers.get(studentId);
              if (students == null) {
                students = new ArrayList<String>();
                studentToUsers.put(studentId, students);
              }
              students.add(taId);
            }
            ((HelpCallListView)getTaskitView()).setCheckMaps(studentToUsers);
          }
        });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void helpCallSelected(HelpCallProxy selectedHelpCall) {
    final AccountProxy caller = selectedHelpCall.getCaller();
    final Place to = new StudentList(caller.getId());
    getClientFactory().getPlaceController().goTo(to);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void goToCheckInList() {
    getClientFactory().getPlaceController().goTo(CheckInList.INSTANCE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void apply(Event evt) {
    super.apply(evt);
    if (evt instanceof CheckMapEvent) {
      updateHelpCallListAsync(true);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onHelpCallListChanged(List<HelpCallProxy> helpCallList) {
    super.onHelpCallListChanged(helpCallList);
    ((HelpCallListView)getTaskitView()).setHelpCalls(helpCallList);
  }

}
