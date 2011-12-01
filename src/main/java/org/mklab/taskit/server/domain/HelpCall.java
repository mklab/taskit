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
package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.UserType;
import org.mklab.taskit.shared.event.Domains;
import org.mklab.taskit.shared.event.HelpCallEvent;
import org.mklab.taskit.shared.event.MyHelpCallEvent;

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

import org.apache.log4j.Logger;


/**
 * TAコールに関する情報を保持するクラスです。
 * 
 * @author ishikura
 */
@Entity
public class HelpCall extends AbstractEntity<Integer> {

  private static Logger logger = Logger.getLogger(HelpCall.class);
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
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.caller == null) ? 0 : this.caller.hashCode());
    result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());
    result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
    result = prime * result + ((this.message == null) ? 0 : this.message.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    HelpCall other = (HelpCall)obj;
    if (this.caller == null) {
      if (other.caller != null) return false;
    } else if (!this.caller.equals(other.caller)) return false;
    if (this.date == null) {
      if (other.date != null) return false;
    } else if (!this.date.equals(other.date)) return false;
    if (this.id == null) {
      if (other.id != null) return false;
    } else if (!this.id.equals(other.id)) return false;
    if (this.message == null) {
      if (other.message != null) return false;
    } else if (!this.message.equals(other.message)) return false;
    return true;
  }

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

      fireHelpCallStateChanged(loginUser, HelpCallEvent.Type.ADDED, call.getDate(), message);
    } catch (Throwable e) {
      logger.error("Failed to call.", e); //$NON-NLS-1$
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

      fireHelpCallStateChanged(User.getUserByAccountId(accountId), HelpCallEvent.Type.DELETED, new Date(), null);
    } catch (Throwable e) {
      logger.error("Failed to cancel calling.", e); //$NON-NLS-1$
      t.rollback();
    } finally {
      em.close();
    }
  }

  private static void fireHelpCallStateChanged(User user, HelpCallEvent.Type type, Date date, String message) {
    // TA、Teacherに通知
    final HelpCallEvent eventForTaAndTeacher = new HelpCallEvent(date, user.getAccount().getId(), message, type, getHelpCallCount());
    ServiceUtil.fireEvent(Domains.TEACHER, eventForTaAndTeacher);
    ServiceUtil.fireEvent(Domains.TA, eventForTaAndTeacher);

    // 呼び出している学生本人に通知
    final MyHelpCallEvent event = new MyHelpCallEvent();
    ServiceUtil.fireEvent(Domains.STUDENT, event, user);
    // その他の呼び出し中の学生に通知
    for (HelpCall helpCall : getAllHelpCalls()) {
      // 本人はすでに通知済み
      if (helpCall.getCaller().getId().equals(user.getAccount().getId())) continue;

      ServiceUtil.fireEvent(Domains.STUDENT, event);
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
      listItems.add(new HelpCallListItem(helpCall, users));
    }
    return listItems;
  }

  /**
   * ヘルプコールの数を取得します。
   * 
   * @return ヘルプコール数
   */
  @Invoker({UserType.TEACHER, UserType.TA})
  public static int getHelpCallCount() {
    final EntityManager em = EMF.get().createEntityManager();
    Query q = em.createQuery("select count(o) from HelpCall o"); //$NON-NLS-1$
    Object result = q.getSingleResult();
    em.close();
    return ((Long)result).intValue();
  }

  /**
   * 自分の呼び出しが何番目なのか調べます。
   * 
   * @return 何番目かどうか。1番目ならば0、2番目なら1...
   */
  @Invoker({UserType.STUDENT})
  public static int getPosition() {
    final User loginUser = ServiceUtil.getLoginUser();
    final HelpCall helpCall = getHelpCall(loginUser.getAccount().getId());
    if (helpCall == null) return -1;

    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select count(o) from HelpCall o where o.date<:date"); //$NON-NLS-1$
    q.setParameter("date", helpCall.getDate()); //$NON-NLS-1$
    try {
      return ((Long)q.getResultList().get(0)).intValue();
    } catch (Throwable e) {
      logger.error("Failed to get student position at caller queue."); //$NON-NLS-1$
      throw new RuntimeException(e);
    } finally {
      em.close();
    }
  }
}
