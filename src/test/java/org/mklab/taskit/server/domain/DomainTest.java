/**
 * 
 */
package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.AuthenticationServiceLayer;
import org.mklab.taskit.shared.TaskitRequestFactory;
import org.mklab.taskit.shared.model.UserType;

import org.junit.Before;
import org.junit.Ignore;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.server.ServiceLayer;
import com.google.web.bindery.requestfactory.server.SimpleRequestProcessor;
import com.google.web.bindery.requestfactory.server.testing.InProcessRequestTransport;
import com.google.web.bindery.requestfactory.vm.RequestFactorySource;


/**
 * @author ishikura
 */
@Ignore
public abstract class DomainTest {

  /** テスト用学生アカウントです。 */
  public static User STUDENT;
  /** テスト用TAアカウントです。 */
  public static User TA;
  /** テスト用先生アカウントです。 */
  public static User TEACHER;

  private static TaskitRequestFactory requestFactory;

  static {
    EMF.setPersistenceUnitName("taskit-test"); //$NON-NLS-1$

    final ServiceLayer serviceLayer = ServiceLayer.create(new AuthenticationServiceLayer());
    final SimpleRequestProcessor processor = new SimpleRequestProcessor(serviceLayer);
    requestFactory = RequestFactorySource.create(TaskitRequestFactory.class);
    final EventBus eventBus = new SimpleEventBus();
    requestFactory.initialize(eventBus, new InProcessRequestTransport(processor));

    ServiceUtil.setImplementation(new ServiceUtilImplementationForTest());
    STUDENT = registerUser("10236005", "student1", UserType.STUDENT); //$NON-NLS-1$ //$NON-NLS-2$
    TA = registerUser("10675005", "ta1", UserType.TA); //$NON-NLS-1$//$NON-NLS-2$
    TEACHER = registerUser("teacher", "teacher1", UserType.TEACHER); //$NON-NLS-1$ //$NON-NLS-2$
  }

  /**
   * リクエストファクトリーを取得します。
   * 
   * @return リクエストファクトリー
   */
  public static TaskitRequestFactory getRequestFactory() {
    return requestFactory;
  }

  /**
   * サーバーサイドテストの初期化を行います。
   */
  public static void initialize() {
    logout();
  }

  private static User registerUser(String accountId, String name, UserType type) {
    Account.registerNewAccount(accountId, "password", type); //$NON-NLS-1$
    User user = User.getUserByAccountId(accountId);
    user.setName(name);
    user.update();

    return user;
  }

  /**
   * 学生としてログインします。
   * 
   * @return ログインユーザー
   */
  public static User loginAsStudent() {
    login(STUDENT);
    return STUDENT;
  }

  /**
   * TAとしてログインします。
   * 
   * @return ログインユーザー
   */
  public static User loginAsTA() {
    login(TA);
    return TA;
  }

  /**
   * 先生としてログインします。
   * 
   * @return ログインユーザー
   */
  public static User loginAsTeacher() {
    login(TEACHER);
    return TEACHER;
  }

  /**
   * 与えられたユーザーとしてログインします。
   * 
   * @param user ログインユーザー
   */
  public static void login(User user) {
    ServiceUtil.login(user);
  }

  /**
   * ログアウトします。
   */
  public static void logout() {
    ServiceUtil.logout();
  }

  /**
   * テスト準備を行います。
   */
  @Before
  public void setup() {
    initialize();
  }

}
