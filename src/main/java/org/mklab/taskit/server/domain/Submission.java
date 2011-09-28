package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.model.UserType;

import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Entity
public class Submission extends AbstractEntity<Integer> {

  private Integer id;
  /** 提出者のアカウントです。 */
  private Account submitter;
  /** この提出に関する課題のIDです。 */
  private Report report;
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

  /**
   * 提出者のアカウントを取得します。
   * 
   * @return 提出者のアカウント
   */
  @OneToOne
  @NotNull
  public Account getSubmitter() {
    return this.submitter;
  }

  /**
   * 提出者のアカウントを設定します。
   * 
   * @param account 提出者のアカウント
   */
  public void setSubmitter(Account account) {
    this.submitter = account;
  }

  /**
   * 課題を取得します。
   * 
   * @return 課題
   */
  @NotNull
  @OneToOne
  public Report getReport() {
    return this.report;
  }

  /**
   * 課題を設定します。
   * 
   * @param report 課題
   */
  public void setReport(Report report) {
    this.report = report;
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

  /**
   * 得点を設定します。
   * 
   * @param point 得点
   */
  public void setPoint(int point) {
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
    final Query q = em.createQuery("select s from Submission s where s.submitter.id=:accountId order by s.report.id");
    q.setParameter("accountId", accountId);
    return q.getResultList();
  }

  /**
   * 提出物を保存します。
   * 
   * @throws IllegalArgumentException すでに提出されていた場合
   */
  @Override
  @Invoker({UserType.TA, UserType.TEACHER})
  public void persist() {
    if (isAlreadySubmit(this)) throw new IllegalArgumentException("already submitted."); //$NON-NLS-1$
    setDate(new Date());
    super.persist();
  }

  @SuppressWarnings("nls")
  private static boolean isAlreadySubmit(Submission submission) {
    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select s from Submission s where s.submitter.id=:accountId and s.report.id=:reportId");
    q.setParameter("accountId", submission.getSubmitter().getId());
    q.setParameter("reportId", submission.getReport().getId());
    return q.getResultList().size() > 0;
  }

}
