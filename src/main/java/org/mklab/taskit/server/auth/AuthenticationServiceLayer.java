package org.mklab.taskit.server.auth;

import java.lang.reflect.Method;

import org.mklab.taskit.shared.model.UserType;

import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
public class AuthenticationServiceLayer extends ServiceLayerDecorator {

  private UserDetector user;

  /**
   * {@link AuthenticationServiceLayer}オブジェクトを構築します。
   * 
   * @param user ユーザー
   */
  public AuthenticationServiceLayer(UserDetector user) {
    if (user == null) throw new NullPointerException();
    this.user = user;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object invoke(Method domainMethod, Object... args) {
    boolean invokable = false;

    if (isAuthenticationEntryPoint(domainMethod)) {
      invokable = true;
    }

    if (invokable == false) {
      final Invoker invoker = domainMethod.getAnnotation(Invoker.class);
      if (invoker != null) {
        final UserType userType = this.user.getType();
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

    report("\"%s\" has no permission to \"%s\".", this.user.getId(), domainMethod); //$NON-NLS-1$
    return null;
  }

  private boolean isAuthenticationEntryPoint(Method m) {
    return m.getAnnotation(AuthenticationEntryPoint.class) != null;
  }

  /**
   * サービスを利用するユーザーを検出するインターフェースです。
   * 
   * @author ishikura
   * @version $Revision$, 2011/09/19
   */
  public static interface UserDetector {

    /**
     * ユーザー種別を取得します。
     * 
     * @return ユーザー種別
     */
    UserType getType();

    /**
     * IDを取得します。
     * 
     * @return ID
     */
    String getId();

    /**
     * ログイン済みかどうか調べます。
     * 
     * @return ログイン済みかどうか
     */
    boolean isLoggedIn();
  }

}