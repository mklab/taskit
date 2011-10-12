package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.UserType;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Entity
public class Report extends AbstractEntity<Integer> {

  private Integer id;
  /** 提出期限です。 */
  private Date period;
  /** レポートのタイトルです。 */
  private String title;
  /** レポートの詳細な説明です。 */
  private String description;
  /** レポートの最大点数です。 */
  private int point;

  private Lecture lecture;

  /**
   * {@link Report}オブジェクトを構築します。
   */
  public Report() {
    // for JPA
  }

  /**
   * {@link Report}オブジェクトを構築します。
   * 
   * @param title タイトル
   * @param point 得点
   * @param lecture 講義
   */
  public Report(String title, int point, Lecture lecture) {
    super();
    this.title = title;
    this.point = point;
    this.lecture = lecture;
  }

  /**
   * 講義を取得します。
   * 
   * @return 講義
   */
  @NotNull
  @ManyToOne(fetch = FetchType.EAGER)
  public Lecture getLecture() {
    return this.lecture;
  }

  /**
   * 講義を設定します。
   * 
   * @param lecture 講義
   */
  public void setLecture(Lecture lecture) {
    this.lecture = lecture;
  }

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
   * レポート提出期限を取得します。
   * 
   * @return レポート提出期限
   */
  public Date getPeriod() {
    return this.period;
  }

  /**
   * レポート提出期限を設定します。
   * 
   * @param period レポート提出期限
   */
  public void setPeriod(Date period) {
    this.period = period;
  }

  /**
   * タイトルを取得します。
   * 
   * @return タイトル
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * タイトルを設定します。
   * 
   * @param title タイトル
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * 説明を取得します。
   * 
   * @return 説明
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * 説明を設定します。
   * 
   * @param description 説明
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * 配点を取得します。
   * 
   * @return 配点
   */
  public int getPoint() {
    return this.point;
  }

  /**
   * 配点を設定します。
   * 
   * @param point 配点
   */
  public void setPoint(int point) {
    this.point = point;
  }

  /**
   * 全てのレポートを取得します。
   * 
   * @return 全てのレポート
   */
  @Invoker({UserType.STUDENT, UserType.TA, UserType.TEACHER})
  @SuppressWarnings("unchecked")
  public static List<Report> getAllReports() {
    final EntityManager em = EMF.get().createEntityManager();
    try {
      Query q = em.createQuery("select r from Report r order by r.lecture.date"); //$NON-NLS-1$
      return q.getResultList();
    } finally {
      em.close();
    }
  }

  /**
   * IDを元に課題データを取得します。
   * 
   * @param id 課題ID
   * @return 課題データ
   */
  public static Report getReportById(int id) {
    return ServiceUtil.findEntity(Report.class, Integer.valueOf(id));
  }

  static boolean lectureIsRefered(Integer lectureId) {
    final EntityManager em = EMF.get().createEntityManager();
    try {
      Query q = em.createQuery("select r from Report r where r.lecture.id=:lectureId"); //$NON-NLS-1$
      q.setParameter("lectureId", lectureId); //$NON-NLS-1$
      return q.getResultList().size() > 0;
    } finally {
      em.close();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Invoker(UserType.TEACHER)
  public void update() {
    super.update();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Invoker(UserType.TEACHER)
  public void persist() {
    super.persist();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Invoker(UserType.TEACHER)
  public void updateOrCreate() {
    super.updateOrCreate();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Invoker(UserType.TEACHER)
  public void delete() {
    @SuppressWarnings("hiding")
    final Integer id = getId();
    if (id == null) throw new IllegalStateException();

    if (Submission.reportIsRefered(id)) {
      throw new IllegalStateException("This report can't be deleted because refered by submission(s) already."); //$NON-NLS-1$
    }

    /*
     * super.delete()で済ませたいけれども、
     * 削除しようとしているReportの参照しているLectureから、OneToManyでReportを参照しており
     * 当然そのなかの一つは今削除しようとしているReportなので、
     * Lectureのflush時のpersistで、削除したReportをpersistしようとしてしまい
     * "deleted entity passed to persist"
     * となってしまう。
     * 
     * そのため、Hibernateの管理の外でJPQLで削除している。
     */

    final EntityManager em = EMF.get().createEntityManager();
    final EntityTransaction t = em.getTransaction();
    final Query q = em.createQuery("delete from Report r where r.id=:id"); //$NON-NLS-1$
    q.setParameter("id", getId()); //$NON-NLS-1$

    try {
      t.begin();
      q.executeUpdate();
      t.commit();
    } catch (Throwable ex) {
      ex.printStackTrace();
      t.rollback();
    } finally {
      em.close();
    }
  }
}
