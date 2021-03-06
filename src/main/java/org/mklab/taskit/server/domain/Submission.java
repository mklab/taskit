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
import org.mklab.taskit.shared.event.MyRecordChangeEvent;

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

import org.apache.log4j.Logger;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Entity
public class Submission extends AbstractEntity<Integer> {

  private static Logger logger = Logger.getLogger(Submission.class);
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
   * {@link Submission}オブジェクトを構築します。
   */
  public Submission() {
    // for JPA
  }

  /**
   * {@link Submission}オブジェクトを構築します。
   * 
   * @param point 得点
   * @param submitter 提出者
   * @param report 提出課題
   */
  public Submission(int point, Account submitter, Report report) {
    super();
    this.point = point;
    this.submitter = submitter;
    this.report = report;
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
  public void setId(Integer id) {
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

  /**
   * 提出日を設定します。
   * 
   * @param date 提出日
   */
  public void setDate(Date date) {
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
   * ログインユーザーのすべての提出物を取得します。
   * 
   * @return ログインユーザーのすべての提出物
   */
  @Invoker(UserType.STUDENT)
  public static List<Submission> getMySubmissions() {
    final User user = ServiceUtil.getLoginUser();
    if (user == null) throw new IllegalStateException("Not logged in."); //$NON-NLS-1$

    return getSubmissionsByAccountId(user.getAccount().getId());
  }

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

  @SuppressWarnings({"nls", "unchecked"})
  static List<Submission> getSubmissionByAccountIdAndLectureId(String accountId, Integer lectureId) {
    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select s from Submission s where s.submitter.id=:accountId and s.report.lecture.id=:lectureId order by s.report.id");
    q.setParameter("accountId", accountId);
    q.setParameter("lectureId", lectureId);
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

    recomputeScore();
    ServiceUtil.fireEvent(Domains.STUDENT, new MyRecordChangeEvent(), this.submitter.getId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update() {
    super.update();

    recomputeScore();
    ServiceUtil.fireEvent(Domains.STUDENT, new MyRecordChangeEvent(), this.submitter.getId());
  }

  private void recomputeScore() {
    RecordService.recomputeScore(this.submitter.getId());
  }

  /**
   * 提出を行います。
   * 
   * @param submitter 提出する学生のアカウント
   * @param report 提出課題
   * @param point 得点
   */
  @Invoker({UserType.TA, UserType.TEACHER})
  public static void submit(Account submitter, Report report, int point) {
    Submission submission = getSubmissionIfExists(submitter, report);
    if (submission == null) {
      submission = new Submission(point, submitter, report);
      submission.persist();
      return;
    }
    submission.setPoint(point);
    submission.setDate(new Date());
    submission.update();
  }

  /**
   * 提出物を削除します。
   */
  @Override
  @Invoker({UserType.TA, UserType.TEACHER})
  public void delete() {
    super.delete();
    ServiceUtil.fireEvent(Domains.STUDENT, new MyRecordChangeEvent(), this.submitter.getId());
  }

  @SuppressWarnings({"nls", "unchecked"})
  private static Submission getSubmissionIfExists(Account submitter, Report report) {
    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select s from Submission s where s.submitter=:submitter and s.report=:report");
    q.setParameter("submitter", submitter);
    q.setParameter("report", report);
    List<Submission> submission = q.getResultList();
    if (submission.size() > 1) throw new IllegalStateException("Internal Error.");
    return submission.size() == 0 ? null : submission.get(0);
  }

  /**
   * すべての提出物を取得します。
   * 
   * @return すべての提出物
   */
  public static List<Submission> getAllSubmissions() {
    return ServiceUtil.getAllEntities("Submission"); //$NON-NLS-1$
  }

  private static boolean isAlreadySubmit(Submission submission) {
    return getSubmissionIfExists(submission.getSubmitter(), submission.getReport()) != null;
  }

  /**
   * Submissionテーブル中に、与えられたレポートIDを参照する行があるかどうか調べます。
   * 
   * @param reportId レポートID
   * @return 存在するかどうか
   */
  static boolean reportIsRefered(Integer reportId) {
    final EntityManager em = EMF.get().createEntityManager();
    try {
      Query q = em.createQuery("select s from Submission s where s.report.id=:reportId"); //$NON-NLS-1$
      q.setParameter("reportId", reportId); //$NON-NLS-1$
      return q.getResultList().size() > 0;
    } catch (Throwable e) {
      logger.error("Failed to check if report is refered.", e); //$NON-NLS-1$
      throw new RuntimeException(e);
    } finally {
      em.close();
    }
  }

}
