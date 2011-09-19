package org.mklab.taskit.server.domain;

import java.util.Date;

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
import org.mklab.taskit.shared.CommentProxy;
import org.mklab.taskit.shared.model.UserType;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Entity
public class Submission extends AbstractEntity<Integer> {

  private Integer id;
  /** 提出者のIDです。 */
  @NotNull
  private String accountId;
  /** この提出に関する課題のIDです。 */
  @NotNull
  private Integer reportId;
  /** 提出日です。 */
  @NotNull
  private Date date;
  /** 提出結果の得点です。 */
  private int point;
  /** TA、先生からのコメントです。 */
  @Embedded
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

  void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  String getAccountId() {
    return this.accountId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  void setId(Integer id) {
    this.id = id;
  }

  Integer getReportId() {
    return this.reportId;
  }

  void setReportId(Integer reportId) {
    this.reportId = reportId;
  }

  Date getDate() {
    return this.date;
  }

  void setDate(Date date) {
    this.date = date;
  }

  int getPoint() {
    return this.point;
  }

  void setPoint(int point) {
    this.point = point;
  }

  Comment getComment() {
    return this.comment;
  }

  void setComment(Comment comment) {
    this.comment = comment;
  }

  @Invoker({UserType.TA, UserType.TEACHER})
  public static void submit(String accountId, Integer reportId, int point) {
    if (isAlreadySubmit(accountId, reportId)) throw new IllegalArgumentException("already submitted.");

    final EntityManager em = EMF.get().createEntityManager();
    final EntityTransaction t = em.getTransaction();
    try {
      t.begin();
      final Submission submission = new Submission();
      submission.setAccountId(accountId);
      submission.setReportId(reportId);
      submission.setPoint(point);
      submission.setDate(new Date());
      em.persist(submission);
      t.commit();
    } catch (Throwable e) {
      t.rollback();
    } finally {
      em.close();
    }
  }

  public static boolean isAlreadySubmit(String accountId, Integer reportId) {
    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select s from Submission s where s.accountId=:accountId and s.reportId=:reportId");
    q.setParameter("accountId", accountId);
    q.setParameter("reportId", reportId);
    return q.getResultList().size() > 0;
  }

}
