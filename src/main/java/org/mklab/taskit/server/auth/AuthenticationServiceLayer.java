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
package org.mklab.taskit.server.auth;

import java.lang.reflect.Method;

import org.mklab.taskit.shared.UserType;
import org.mklab.taskit.server.domain.ServiceUtil;
import org.mklab.taskit.server.domain.User;

import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;


/**
 * {@link RequestFactoryServlet}に独自の認証機能を付加するデコレーターです。
 * 
 * @author Yuhi Ishikura
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
        final Class<? extends InvocationEntrance> entranceClass = invoker.entrance();
        final boolean entranceIsSet = entranceClass == InvocationEntrance.class;
        if (entranceIsSet) {
          for (UserType accessibleUserType : invoker.value()) {
            if (userType == accessibleUserType) {
              invokable = true;
              break;
            }
          }
        } else {
          try {
            InvocationEntrance entrance = entranceClass.newInstance();
            invokable = entrance.accept(loginUser);
          } catch (Throwable e) {
            throw new RuntimeException(e);
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