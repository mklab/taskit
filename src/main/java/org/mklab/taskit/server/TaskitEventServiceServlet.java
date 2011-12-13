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
package org.mklab.taskit.server;

import org.mklab.taskit.server.domain.ServiceUtil;
import org.mklab.taskit.server.domain.User;

import java.util.List;

import de.novanic.eventservice.client.event.DomainEvent;
import de.novanic.eventservice.client.event.domain.Domain;
import de.novanic.eventservice.client.event.service.EventService;
import de.novanic.eventservice.service.EventServiceImpl;


/**
 * {@link EventService}を実装したサーブレットです。
 * <p>
 * クライアントIDからTASKitユーザーへのマッピングを作成するためにオーバーライドし割り込みます。
 * 
 * @author Yuhi Ishikura
 */
public class TaskitEventServiceServlet extends EventServiceImpl {

  /** for serialization. */
  private static final long serialVersionUID = 1096653980134462889L;

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DomainEvent> listen() {
    final String clientId = getClientId();
    final User user = (User)getThreadLocalRequest().getSession().getAttribute(ServiceUtil.USER_KEY);
    if (user == null) {
      throw new IllegalStateException("Not logged in."); //$NON-NLS-1$
    }

    ServiceUtil.registerClient(clientId, user);

    return super.listen();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void unlisten() {
    super.unlisten();
    ServiceUtil.unregisterClient(getClientId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void unlisten(Domain aDomain) {
    super.unlisten(aDomain);
    ServiceUtil.unregisterClient(getClientId());
  }

}
