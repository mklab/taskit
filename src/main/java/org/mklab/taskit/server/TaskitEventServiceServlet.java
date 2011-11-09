/**
 * 
 */
package org.mklab.taskit.server;

import org.mklab.taskit.server.domain.ServiceUtil;
import org.mklab.taskit.server.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.novanic.eventservice.client.event.DomainEvent;
import de.novanic.eventservice.service.EventServiceImpl;
import de.novanic.eventservice.service.registry.EventRegistry;
import de.novanic.eventservice.service.registry.EventRegistryFactory;


/**
 * @author Yuhi Ishikura
 */
public class TaskitEventServiceServlet extends EventServiceImpl {

  private Map<String, User> clientIdToUser = new HashMap<String, User>();

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

    this.clientIdToUser.put(clientId, user);
    final EventRegistry registory = EventRegistryFactory.getInstance().getEventRegistry();
    
    
    return super.listen();
  }

}
