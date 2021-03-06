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

import org.mklab.taskit.server.auth.AuthenticationServiceLayer;
import org.mklab.taskit.server.domain.EMF;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

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
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    final String url = config.getInitParameter("taskit-db-url"); //$NON-NLS-1$
    final String id = config.getInitParameter("taskit-dbuser-id"); //$NON-NLS-1$
    final String password = config.getInitParameter("taskit-dbuser-password"); //$NON-NLS-1$
    final String schemaddl = config.getInitParameter("taskit-db-ddl"); //$NON-NLS-1$

    if (url != null) EMF.setPersistenceProperty(EMF.DB_URL_KEY, url);
    if (id != null) EMF.setPersistenceProperty(EMF.DB_USER_KEY, id);
    if (password != null) EMF.setPersistenceProperty(EMF.DB_PASSWORD_KEY, password);
    if (schemaddl != null) EMF.setPersistenceProperty(EMF.DB_SCHEMA_DDL, schemaddl);
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
