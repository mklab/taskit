package org.mklab.taskit.server.auth;

import java.lang.reflect.Method;

import org.mklab.taskit.shared.UserType;
import org.mklab.taskit.server.domain.ServiceUtil;
import org.mklab.taskit.server.domain.User;

import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
public class AuthenticationServiceLayer extends ServiceLayerDecorator {

  /**
   * {@inheritDoc}
   */
  @Override
  public Object invoke(Method domainMethod, Object... args) {
    boolean invokable = false;

    if (isAuthenticationEntryPoint(domainMethod)) {
      invokable = true;
    }

    final User loginUser = ServiceUtil.getLoginUser();
    if (invokable == false) {
      if (loginUser == null) {
        report("Not logged in."); //$NON-NLS-1$
        return null;
      }
      final Invoker invoker = domainMethod.getAnnotation(Invoker.class);
      if (invoker != null) {

        final UserType userType = loginUser.getType();
        for (UserType accessibleUserType : invoker.value()) {
          if (userType == accessibleUserType) {
            invokable = true;
            break;
          }
        }
      }
    }

    if (invokable) {
      return super.invoke(domainMethod, args);
    }

    report("\"%s\" has no permission to \"%s\".", loginUser, domainMethod); //$NON-NLS-1$
    return null;
  }

  private static boolean isAuthenticationEntryPoint(Method m) {
    return m.getAnnotation(AuthenticationEntryPoint.class) != null;
  }

}