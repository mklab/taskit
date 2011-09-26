package org.mklab.taskit.server;

import org.mklab.taskit.server.auth.AuthenticationServiceLayer;

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
    super(new DefaultExceptionHandler(), new AuthenticationServiceLayer());
  }

}
