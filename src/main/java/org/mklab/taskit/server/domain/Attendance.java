package org.mklab.taskit.server.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.model.UserType;


/**
 * 出席状況を表すクラスです。
 * 
 * @author ishikura
 * @version $Revision$, 2011/09/20
 */
@Entity
public class Attendance extends AbstractEntity<Integer> {

  /** IDです。 */
  private Integer id;
  /** 出席者のIDです。 */
  private String accountId;
  /** 講義IDです。 */
  private Integer lectureId;
  /** 出席種別です。 */
  private AttendanceType type;
  /** 出席チェックを行った時間です。 */
  private Date date;

  /**
   * {@inheritDoc}
   */
  @Override
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  public Integer getId() {
    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  void setId(Integer id) {
    this.id = id;
  }

  /**
   * 出席者のアカウントIDを取得します。
   * 
   * @return 出席者のアカウントID
   */
  public String getAccountId() {
    return this.accountId;
  }

  void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  /**
   * 出席対象講義のIDを取得します。
   * 
   * @return 出席対象講義のID
   */
  public Integer getLectureId() {
    return this.lectureId;
  }

  void setLectureId(Integer lectureId) {
    this.lectureId = lectureId;
  }

  /**
   * 出席チェックを行った時間を取得します。
   * 
   * @return 出席チェックを行った時間
   */
  @NotNull
  public Date getDate() {
    return this.date;
  }

  void setDate(Date date) {
    this.date = date;
  }

  /**
   * 出席種別を取得します。
   * 
   * @return type
   */
  @NotNull
  public AttendanceType getType() {
    return this.type;
  }

  void setType(AttendanceType type) {
    this.type = type;
  }

  // service methods

  /**
   * 特定のユーザーのすべての出席状況を取得します。
   * 
   * @param accountId ユーザーのアカウントID
   * @return 出席状況
   */
  @SuppressWarnings({"nls", "unchecked"})
  @Invoker({UserType.TA, UserType.TEACHER})
  public static List<Attendance> getAllAttendancesByAccountId(String accountId) {
    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select s from Attendance s where s.accountId=:accountId");
    q.setParameter("accountId", accountId);
    return q.getResultList();
  }

  /**
   * 出席チェックを行います。
   * 
   * @param accountId 出席者のアカウントID
   * @param lectureId 出席対象の講義ID
   * @param type 出席種別
   * @return 出席情報
   */
  @Invoker({UserType.TA, UserType.TEACHER})
  public static Attendance mark(String accountId, Integer lectureId, AttendanceType type) {
    if (isAlreadyMarked(accountId, lectureId)) throw new IllegalArgumentException("Already marked."); //$NON-NLS-1$

    final Attendance attendance = new Attendance();
    attendance.setAccountId(accountId);
    attendance.setLectureId(lectureId);
    attendance.setType(type);
    attendance.setDate(new Date());

    final EntityManager em = EMF.get().createEntityManager();
    final EntityTransaction t = em.getTransaction();
    try {
      t.begin();
      em.persist(attendance);
      t.commit();
    } catch (Throwable e) {
      t.rollback();
    } finally {
      em.close();
    }

    return attendance;
  }

  @SuppressWarnings("nls")
  private static boolean isAlreadyMarked(String accountId, Integer lectureId) {
    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select s from Attendance s where s.accountId=:accountId and s.lectureId=:lectureId");
    q.setParameter("accountId", accountId);
    q.setParameter("lectureId", lectureId);
    return q.getResultList().size() > 0;
  }

}
