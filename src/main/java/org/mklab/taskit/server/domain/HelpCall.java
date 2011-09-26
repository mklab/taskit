/**
 * 
 */
package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.model.UserType;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.Query;


/**
 * TAコールに関する情報を保持するクラスです。
 * 
 * @author ishikura
 */
@Entity
public class HelpCall extends AbstractEntity<String> {

  /** 呼んでいる人のアカウントIDです。 */
  private String id;
  /** 呼び出し日時です。 */
  private Date date;
  /** メッセージです。 */
  private String message;

  /**
   * {@inheritDoc}
   */
  @Override
  @Id
  public String getId() {
    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String id) {
    this.id = id;
  }

  /**
   * 呼び出し日時を取得します。
   * 
   * @return 呼び出し日時
   */
  public Date getDate() {
    return this.date;
  }

  void setDate(Date date) {
    this.date = date;
  }

  /**
   * メッセージを取得します。
   * 
   * @return メッセージ
   */
  public String getMessage() {
    return this.message;
  }

  void setMessage(String message) {
    this.message = message;
  }

  // service methods

  /**
   * 呼び出しを宣言します。
   * 
   * @param message メッセージ。nullを許容します。
   */
  @Invoker(UserType.STUDENT)
  public static void call(String message) {
    final HelpCall call = new HelpCall();
    call.setDate(new Date());
    call.setMessage(message);

    final User loginUser = ServiceUtil.getLoginUser();
    if (loginUser == null) throw new IllegalStateException("Not logged in."); //$NON-NLS-1$
    call.setId(loginUser.getId());

    final EntityManager em = EMF.get().createEntityManager();
    final EntityTransaction t = em.getTransaction();

    try {
      t.begin();
      em.persist(call);
      t.commit();
    } catch (Throwable e) {
      t.rollback();
    } finally {
      em.close();
    }
  }

  /**
   * 呼び出しをキャンセルします。
   */
  @Invoker(UserType.STUDENT)
  public static void uncall() {
    final User loginUser = ServiceUtil.getLoginUser();
    uncall(loginUser.getId());
  }

  /**
   * 呼び出しをキャンセルします。
   * 
   * @param accountId キャンセルする生徒のアカウントID
   */
  @Invoker({UserType.TA, UserType.TEACHER})
  public static void uncall(String accountId) {
    final EntityManager em = EMF.get().createEntityManager();
    final EntityTransaction t = em.getTransaction();

    try {
      t.begin();
      em.remove(accountId);
      t.commit();
    } catch (Throwable e) {
      t.rollback();
    } finally {
      em.close();
    }
  }

  /**
   * すべてのヘルプコールを取得します。
   * 
   * @return すべてのヘルプコール
   */
  @Invoker(UserType.STUDENT)
  public static List<HelpCall> getAllHelpCalls() {
    final EntityManager em = EMF.get().createEntityManager();
    Query q = em.createQuery("select o from HelpCall o"); //$NON-NLS-1$

    @SuppressWarnings("unchecked")
    List<HelpCall> list = q.getResultList();
    return list;
  }

}
