/**
 * 
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
