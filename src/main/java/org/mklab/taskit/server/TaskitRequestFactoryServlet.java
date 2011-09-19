package org.mklab.taskit.server;

import javax.servlet.http.HttpSession;

import org.mklab.taskit.server.auth.AuthenticationServiceLayer;
import org.mklab.taskit.shared.model.User;
import org.mklab.taskit.shared.model.UserType;

import com.google.web.bindery.requestfactory.server.DefaultExceptionHandler;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
public class TaskitRequestFactoryServlet extends RequestFactoryServlet {

  /** for serialization. */
  private static final long serialVersionUID = 5800572527405428814L;

  /**
   * {@link TaskitRequestFactoryServlet}オブジェクトを構築します。
   */
  public TaskitRequestFactoryServlet() {
    super(new DefaultExceptionHandler(), new AuthenticationServiceLayer(new SessionUserDetector()));
  }

  static class SessionUserDetector implements AuthenticationServiceLayer.UserDetector {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
      User user = getUser();
      if (user == null) return null;
      return user.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserType getType() {
      User user = getUser();
      if (user == null) return null;
      return user.getType();
    }

    private User getUser() {
      final HttpSession session = getSession();
      if (session == null) return null;
      return SessionUtil.getUser(session);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoggedIn() {
      final HttpSession session = getSession();
      if (session == null) return false;
      return SessionUtil.isLoggedIn(session);
    }

    private HttpSession getSession() {
      return getThreadLocalRequest().getSession();
    }

  }

}
