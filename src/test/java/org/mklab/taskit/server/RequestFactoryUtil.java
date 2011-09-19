package org.mklab.taskit.server;

import org.mklab.taskit.server.auth.AuthenticationServiceLayer;
import org.mklab.taskit.server.auth.AuthenticationServiceLayer.UserDetector;
import org.mklab.taskit.server.domain.EMF;
import org.mklab.taskit.shared.TaskitRequestFactory;
import org.mklab.taskit.shared.model.UserType;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.server.ServiceLayer;
import com.google.web.bindery.requestfactory.server.SimpleRequestProcessor;
import com.google.web.bindery.requestfactory.server.testing.InProcessRequestTransport;
import com.google.web.bindery.requestfactory.vm.RequestFactorySource;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/16
 */
public final class RequestFactoryUtil {

  private static TaskitRequestFactory requestFactory;

  static {
    EMF.setPersistenceUnitName("taskit-test"); //$NON-NLS-1$
  }

  /**
   * リクエストファクトリーを取得します。
   * 
   * @return リクエストファクトリー
   */
  public static TaskitRequestFactory requestFactory() {
    if (requestFactory == null) {
      final ServiceLayer serviceLayer = ServiceLayer.create(new AuthenticationServiceLayer(new UserDetector() {

        /**
         * {@inheritDoc}
         */
        @Override
        public UserType getType() {
          return UserType.TEACHER;
        }

        @Override
        public boolean isLoggedIn() {
          return true;
        }

        @Override
        public String getId() {
          return "testuser"; //$NON-NLS-1$
        }
      }));
      final SimpleRequestProcessor processor = new SimpleRequestProcessor(serviceLayer);
      requestFactory = RequestFactorySource.create(TaskitRequestFactory.class);
      final EventBus eventBus = new SimpleEventBus();
      requestFactory.initialize(eventBus, new InProcessRequestTransport(processor));
    }
    return requestFactory;
  }

}
