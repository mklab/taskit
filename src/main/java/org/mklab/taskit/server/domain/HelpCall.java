/**
 * 
 */
package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.UserType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;


/**
 * TAコールに関する情報を保持するクラスです。
 * 
 * @author ishikura
 */
@Entity
public class HelpCall extends AbstractEntity<Integer> {

  private Integer id;
  /** 呼び出した人のアカウントです。 */
  private Account caller;
  /** 呼び出し日時です。 */
  private Date date;
  /** メッセージです。 */
  private String message;

  /**
   * {@inheritDoc}
   */
  @Override
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getId() {
    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * 呼び出した人を取得します。
   * 
   * @return account
   */
  @OneToOne
  @NotNull
  public Account getCaller() {
    return this.caller;
  }

  /**
   * 呼び出した人を設定します。
   * 
   * @param account 呼び出した人のアカウント
   */
  public void setCaller(Account account) {
    this.caller = account;
  }

  /**
   * 呼び出し日時を取得します。
   * 
   * @return 呼び出し日時
   */
  public Date getDate() {
    return this.date;
  }

  /**
   * 呼び出し日時を設定します。
   * 
   * @param date 呼び出し日時
   */
  public void setDate(Date date) {
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
    if (isCalling()) throw new IllegalStateException("Already been calling."); //$NON-NLS-1$

    final HelpCall call = new HelpCall();
    call.setDate(new Date());
    call.setMessage(message);

    final User loginUser = ServiceUtil.getLoginUser();
    if (loginUser == null) throw new IllegalStateException("Not logged in."); //$NON-NLS-1$
    call.setCaller(loginUser.getAccount());

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
    cancelCall(loginUser.getAccount().getId());
  }

  /**
   * 現在呼び出し中であるかどうか調べます。
   * 
   * @return 呼び出し中かどうか
   */
  @Invoker(UserType.STUDENT)
  public static boolean isCalling() {
    final User loginUser = ServiceUtil.getLoginUser();
    if (loginUser == null) throw new IllegalStateException("Not logged in."); //$NON-NLS-1$

    final String callerAccountId = loginUser.getAccount().getId();
    return isCallingByAccountId(callerAccountId);
  }

  /**
   * 呼び出しをキャンセルします。
   * 
   * @param accountId キャンセルする生徒のアカウントID
   */
  @Invoker({UserType.TA, UserType.TEACHER})
  public static void cancelCall(String accountId) {
    if (isCallingByAccountId(accountId) == false) throw new IllegalStateException("Not be calling now."); //$NON-NLS-1$

    final EntityManager em = EMF.get().createEntityManager();
    final EntityTransaction t = em.getTransaction();

    try {
      final Query q = em.createQuery("delete from HelpCall s where s.caller.id=:accountId"); //$NON-NLS-1$
      q.setParameter("accountId", accountId); //$NON-NLS-1$
      t.begin();
      q.executeUpdate();
      t.commit();
    } catch (Throwable e) {
      t.rollback();
    } finally {
      em.close();
    }
  }

  /**
   * 指定されたアカウントIDのユーザーが呼び出し中であるかどうか調べます。
   * 
   * @param callerAccountId 呼び出し中か調べるユーザー
   * @return 呼び出し中ならばtrue,そうでなければfalse
   */
  private static boolean isCallingByAccountId(final String callerAccountId) {
    return getHelpCall(callerAccountId) != null;
  }

  /**
   * 与えられたアカウントIDのユーザーによる呼び出しを取得します。
   * 
   * @param callerAccountId ユーザーのID
   * @return 呼び出し情報。呼び出し中でなければnull
   */
  private static HelpCall getHelpCall(final String callerAccountId) {
    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select s from HelpCall s where s.caller.id=:callerId"); //$NON-NLS-1$
    q.setParameter("callerId", callerAccountId); //$NON-NLS-1$

    @SuppressWarnings("unchecked")
    final List<HelpCall> result = q.getResultList();
    if (result.size() == 0) return null;
    if (result.size() > 1) throw new IllegalStateException();

    return result.get(0);
  }

  /**
   * すべてのヘルプコールを取得します。
   * 
   * @return すべてのヘルプコール
   */
  @Invoker({UserType.TEACHER, UserType.TA})
  public static List<HelpCall> getAllHelpCalls() {
    final EntityManager em = EMF.get().createEntityManager();
    Query q = em.createQuery("select o from HelpCall o order by o.date"); //$NON-NLS-1$

    @SuppressWarnings("unchecked")
    List<HelpCall> list = q.getResultList();
    em.close();
    return list;
  }

  /**
   * すべてのヘルプコールを取得します。
   * <p>
   * {@link #getAllHelpCalls()}と異なり、呼び出し者に対応中のユーザー情報も含まれます。
   * 
   * @return すべてのヘルプコール
   */
  @Invoker({UserType.TEACHER, UserType.TA})
  public static List<HelpCallListItem> getHelpCallListItems() {
    final List<HelpCall> helpCalls = getAllHelpCalls();
    final List<HelpCallListItem> listItems = new ArrayList<HelpCallListItem>();
    for (HelpCall helpCall : helpCalls) {
      final List<String> users = CheckMap.getUsersInCheck(helpCall.getCaller());
      final HelpCallListItem item = new HelpCallListItem(helpCall, users);
      listItems.add(item);
    }
    return listItems;
  }

  /**
   * ヘルプコールの数を取得します。
   * 
   * @return ヘルプコール数
   */
  @Invoker({UserType.TEACHER, UserType.TA})
  public static long getHelpCallCount() {
    final EntityManager em = EMF.get().createEntityManager();
    Query q = em.createQuery("select count(o) from HelpCall o"); //$NON-NLS-1$
    Object result = q.getSingleResult();
    em.close();
    return ((Long)result).longValue();
  }

  /**
   * 自分の呼び出しが何番目なのか調べます。
   * 
   * @return 何番目かどうか。1番目ならば0、2番目なら1...
   */
  @Invoker({UserType.STUDENT})
  public static long getPosition() {
    final User loginUser = ServiceUtil.getLoginUser();
    final HelpCall helpCall = getHelpCall(loginUser.getAccount().getId());
    if (helpCall == null) return -1;

    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select count(o) from HelpCall o where o.date<:date"); //$NON-NLS-1$
    q.setParameter("date", helpCall.getDate()); //$NON-NLS-1$
    try {
      return ((Long)q.getResultList().get(0)).intValue();
    } finally {
      em.close();
    }
  }
}
