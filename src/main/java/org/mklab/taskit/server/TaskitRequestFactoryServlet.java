/**
 * 
 */
package org.mklab.taskit.server;

import org.mklab.taskit.server.auth.AuthenticationServiceLayer;
import org.mklab.taskit.server.domain.EMF;

import javax.servlet.ServletConfig;

import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * @author ishikura
 */
public class TaskitRequestFactoryServlet extends RequestFactoryServlet {

  /** for serialization. */
  private static final long serialVersionUID = -1477449634514434453L;

  /**
   * {@link TaskitRequestFactoryServlet}オブジェクトを構築します。
   */
  public TaskitRequestFactoryServlet() {
    super(new MyExceptionHandler(), new AuthenticationServiceLayer());
    loadDatabaseInfo();
  }

  private void loadDatabaseInfo() {
    final ServletConfig config = getServletConfig();

    final String url = config.getInitParameter("taskit-db-url"); //$NON-NLS-1$
    final String id = config.getInitParameter("taskit-dbuser-id"); //$NON-NLS-1$
    final String password = config.getInitParameter("taskit-dbuser-password"); //$NON-NLS-1$

    if (url != null) EMF.setPersistenceProperty(EMF.DB_URL_KEY, url);
    if (id != null) EMF.setPersistenceProperty(EMF.DB_USER_KEY, id);
    if (password != null) EMF.setPersistenceProperty(EMF.DB_PASSWORD_KEY, password);
  }

  static class MyExceptionHandler implements ExceptionHandler {

    /**
     * {@inheritDoc}
     */
    @Override
    public ServerFailure createServerFailure(Throwable throwable) {
      final ServerFailure s = new ServerFailure(throwable.getMessage());
      return s;
    }

  }

}
