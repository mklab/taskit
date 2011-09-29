package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.AttendanceType;
import org.mklab.taskit.shared.model.UserType;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;


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
  /** 出席者のアカウントです。 */
  private Account attender;
  /** 講義です。 */
  private Lecture lecture;
  /** 出席種別です。 */
  private AttendanceType type;
  /** 出席チェックを行った時間です。 */
  private Date date;

  /**
   * {@link Attendance}オブジェクトを構築します。
   */
  public Attendance() {
    // for JPA
  }

  /**
   * {@link Attendance}オブジェクトを構築します。
   * 
   * @param type 出席種別
   * @param attender 出席者
   * @param lecture 対象講義
   */
  public Attendance(AttendanceType type, Account attender, Lecture lecture) {
    super();
    this.type = type;
    this.attender = attender;
    this.lecture = lecture;
  }

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
   * 出席者のアカウントを取得します。
   * 
   * @return 出席者のアカウント
   */
  @OneToOne
  public Account getAttender() {
    return this.attender;
  }

  /**
   * 出席者のアカウントを設定します。
   * 
   * @param account 出席者のアカウント
   */
  public void setAttender(Account account) {
    this.attender = account;
  }

  /**
   * 出席した講義を取得します。
   * 
   * @return 出席した講義
   */
  @OneToOne
  public Lecture getLecture() {
    return this.lecture;
  }

  /**
   * 出席した講義を設定します。
   * 
   * @param lecture 出席した講義
   */
  public void setLecture(Lecture lecture) {
    this.lecture = lecture;
  }

  /**
   * 出席チェックを行った時間を取得します。
   * 
   * @return 出席チェックを行った時間
   */
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

  /**
   * 出席種別を設定します。
   * 
   * @param type 出席種別
   */
  public void setType(AttendanceType type) {
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
    final Query q = em.createQuery("select s from Attendance s where s.attender.id=:accountId");
    q.setParameter("accountId", accountId);
    return q.getResultList();
  }

  /**
   * 出席情報を保存します。
   */
  @Invoker({UserType.TA, UserType.TEACHER})
  @Override
  public void persist() {
    if (isAlreadyMarked(this)) throw new IllegalArgumentException("Already marked."); //$NON-NLS-1$
    setDate(new Date());
    super.persist();
  }

  @SuppressWarnings("nls")
  private static boolean isAlreadyMarked(Attendance attendance) {
    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select s from Attendance s where s.attender=:account and s.lecture=:lecture");
    q.setParameter("account", attendance.attender);
    q.setParameter("lecture", attendance.lecture);
    return q.getResultList().size() > 0;
  }

}
