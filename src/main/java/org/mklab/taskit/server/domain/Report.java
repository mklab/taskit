package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.model.UserType;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Entity
public class Report extends AbstractEntity<Integer> {

  private Integer id;
  /** このレポートの属する講義のIDです。 */
  private Integer lectureId;
  /** 提出期限です。 */
  private Date period;
  /** レポートのタイトルです。 */
  @NotNull
  private String title;
  /** レポートの詳細な説明です。 */
  private String description;
  /** レポートの最大点数です。 */
  private int point;

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
  void setId(Integer id) {
    this.id = id;
  }

  /**
   * 講義IDを取得します。
   * 
   * @return 講義ID
   */
  public Integer getLectureId() {
    return this.lectureId;
  }

  /**
   * 講義IDを設定します。
   * 
   * @param lectureId 講義ID
   */
  public void setLectureId(Integer lectureId) {
    this.lectureId = lectureId;
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
  void setPeriod(Date period) {
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
      Query q = em.createQuery("select r from Report r"); //$NON-NLS-1$
      return q.getResultList();
    } finally {
      em.close();
    }
  }

}
