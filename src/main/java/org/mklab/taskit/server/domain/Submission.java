package org.mklab.taskit.server.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.model.UserType;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Entity
public class Submission extends AbstractEntity<Integer> {

  private Integer id;
  /** 提出者のIDです。 */
  private String accountId;
  /** この提出に関する課題のIDです。 */
  private Integer reportId;
  /** 提出日です。 */
  private Date date;
  /** 提出結果の得点です。 */
  private int point;
  /** TA、先生からのコメントです。 */
  private Comment comment;

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

  void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  /**
   * 提出者のアカウントIDを取得します。
   * 
   * @return 提出者のアカウントID
   */
  @NotNull
  public String getAccountId() {
    return this.accountId;
  }

  /**
   * レポートIDを取得します。
   * 
   * @return レポートID
   */
  @NotNull
  public Integer getReportId() {
    return this.reportId;
  }

  void setReportId(Integer reportId) {
    this.reportId = reportId;
  }

  /**
   * 提出日を取得します。
   * 
   * @return 提出日
   */
  public Date getDate() {
    return this.date;
  }

  void setDate(Date date) {
    this.date = date;
  }

  /**
   * 得点を取得します。
   * 
   * @return 得点
   */
  public int getPoint() {
    return this.point;
  }

  void setPoint(int point) {
    this.point = point;
  }

  /**
   * コメントを取得します。
   * 
   * @return コメント
   */
  @Embedded
  public Comment getComment() {
    return this.comment;
  }

  void setComment(Comment comment) {
    this.comment = comment;
  }

  // Service methods

  /**
   * 特定のユーザーによるすべての提出物を取得します。
   * 
   * @param accountId ユーザーのアカウントID
   * @return 提出物
   */
  @SuppressWarnings({"nls", "unchecked"})
  @Invoker({UserType.TA, UserType.TEACHER})
  public static List<Submission> getSubmissionsByAccountId(String accountId) {
    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select s from Submission s where s.accountId=:accountId order by s.reportId");
    q.setParameter("accountId", accountId);
    return q.getResultList();
  }

  /**
   * 提出を行います。
   * 
   * @param accountId 提出者
   * @param reportId 課題
   * @param point 得点
   * @return 提出物
   * @throws IllegalArgumentException すでに提出されていた場合
   */
  @Invoker({UserType.TA, UserType.TEACHER})
  public static Submission submit(String accountId, Integer reportId, int point) {
    if (isAlreadySubmit(accountId, reportId)) throw new IllegalArgumentException("already submitted."); //$NON-NLS-1$

    final Submission submission = new Submission();
    submission.setAccountId(accountId);
    submission.setReportId(reportId);
    submission.setPoint(point);
    submission.setDate(new Date());

    final EntityManager em = EMF.get().createEntityManager();
    final EntityTransaction t = em.getTransaction();
    try {
      t.begin();
      em.persist(submission);
      t.commit();
    } catch (Throwable e) {
      t.rollback();
    } finally {
      em.close();
    }

    return submission;
  }

  @SuppressWarnings("nls")
  private static boolean isAlreadySubmit(String accountId, Integer reportId) {
    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select s from Submission s where s.accountId=:accountId and s.reportId=:reportId");
    q.setParameter("accountId", accountId);
    q.setParameter("reportId", reportId);
    return q.getResultList().size() > 0;
  }

}
