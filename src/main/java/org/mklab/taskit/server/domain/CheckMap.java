/**
 * 
 */
package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.UserType;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Query;


/**
 * あるユーザーが現在どのユーザー(生徒)を見ているのかを表すクラスです。
 * 
 * @author Yuhi Ishikura
 */
@Entity
public class CheckMap extends AbstractEntity<String> {

  /** ユーザー(TAもしくは先生)のIDです。 */
  private String id;
  /** ユーザーが現在参照している学生です。 */
  private Account student;
  /** 参照した日時です。 */
  private Date date;

  /**
   * {@link CheckMap}オブジェクトを構築します。
   * 
   * @param id ユーザーID
   * @param student {@code id}で指定されたユーザーが現在参照中の学生
   * @param date 参照した日時
   */
  public CheckMap(String id, Account student, Date date) {
    this.id = id;
    this.student = student;
    this.date = date;
  }

  /**
   * {@link CheckMap}オブジェクトを構築します。
   */
  CheckMap() {
    // for JPA
  }

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
   * 参照中の学生を取得します。
   * 
   * @return student
   */
  @OneToOne
  public Account getStudent() {
    return this.student;
  }

  /**
   * 参照中の学生を設定します。
   * 
   * @param student 参照中の学生
   */
  public void setStudent(Account student) {
    this.student = student;
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
   * ログインユーザーが参照している学生を設定します。
   * 
   * @param student 学生
   */
  @Invoker({UserType.TA, UserType.TEACHER})
  public static void checkIn(Account student) {
    final User loginUser = ServiceUtil.getLoginUser();
    if (loginUser == null) throw new IllegalStateException("Not logged in."); //$NON-NLS-1$

    final CheckMap check = new CheckMap(loginUser.getAccount().getId(), student, new Date());
    check.updateOrCreate();
  }

  /**
   * ログインユーザーが参照している学生情報を破棄します。
   */
  @Invoker({UserType.TA, UserType.TEACHER})
  public static void checkOut() {
    final User loginUser = ServiceUtil.getLoginUser();
    if (loginUser == null) throw new IllegalStateException("Not logged in."); //$NON-NLS-1$

    final CheckMap check = getCheckOf(loginUser.getAccount());
    if (check == null) return;
    check.delete();
  }

  /**
   * 与えられたユーザーの担当中の学生を取得します。
   * 
   * @param user ユーザー
   * @return 担当中の学生。担当していなければnull
   */
  @Invoker({UserType.TA, UserType.TEACHER})
  public static Account getTargetStudentOf(Account user) {
    final CheckMap check = getCheckOf(user);
    if (check == null) return null;

    return check.getStudent();
  }

  private static CheckMap getCheckOf(Account user) {
    final CheckMap check = ServiceUtil.findEntity(CheckMap.class, user.getId());
    if (check == null) return null;

    return check;
  }

  /**
   * 与えられた学生を担当中のユーザーすべてを取得します。
   * 
   * @param student 学生
   * @return 与えられた学生を担当中のユーザーのリスト。一人もいなければ空のリスト
   */
  @SuppressWarnings("unchecked")
  @Invoker({UserType.TA, UserType.TEACHER, UserType.STUDENT})
  public static List<String> getUsersInCheck(Account student) {
    final EntityManager em = EMF.get().createEntityManager();
    final EntityTransaction t = em.getTransaction();

    List<String> userIdList = null;
    try {
      final Query q = em.createQuery("select c.id from CheckMap c where student.id=:studentId"); //$NON-NLS-1$
      q.setParameter("studentId", student.getId()); //$NON-NLS-1$
      t.begin();
      userIdList = q.getResultList();
      t.commit();
    } catch (Throwable e) {
      t.rollback();
    } finally {
      em.close();
    }
    return userIdList;
  }
}
