package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.UserType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Entity
public class Lecture extends AbstractEntity<Integer> {

  private Integer id;
  private Date date;
  private String title;
  private String description;
  private List<Report> reports;

  /**
   * {@link Lecture}オブジェクトを構築します。
   */
  public Lecture() {
    // for JPA
  }

  /**
   * {@link Lecture}オブジェクトを構築します。
   * 
   * @param date 日付
   */
  public Lecture(Date date) {
    if (date == null) throw new NullPointerException();
    this.date = date;
    this.reports = new ArrayList<Report>();
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
   * 課題を取得します。
   * 
   * @return 課題
   */
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "lecture", fetch = FetchType.EAGER, targetEntity = Report.class)
  public List<Report> getReports() {
    return this.reports;
  }

  /**
   * 課題を設定します。
   * 
   * @param reports 課題
   */
  public void setReports(List<Report> reports) {
    this.reports = reports;
  }

  /**
   * 講義日を取得します。
   * 
   * @return 講義日
   */
  @NotNull
  public Date getDate() {
    return this.date;
  }

  /**
   * 講義日を設定します。
   * 
   * @param date 講義日
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * 講義タイトルを取得します。
   * 
   * @return 講義タイトル
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * 講義タイトルを設定します。
   * 
   * @param title 講義タイトル
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * 講義の説明を取得します。
   * 
   * @return 講義の説明
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * 講義の説明を設定します。
   * 
   * @param description 講義の説明
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * 全ての講義を日付順で取得します。
   * 
   * @return 全ての講義
   */
  @SuppressWarnings("unchecked")
  @Invoker({UserType.STUDENT, UserType.TA, UserType.TEACHER})
  public static List<Lecture> getAllLectures() {
    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select l from Lecture l order by l.date"); //$NON-NLS-1$
    try {
      return q.getResultList();
    } finally {
      em.close();
    }
  }

  /**
   * 講義をタイトルで取得します。
   * 
   * @param title 講義タイトル
   * @return 講義
   */
  @SuppressWarnings("unchecked")
  public static List<Lecture> getLectureByTitle(String title) {
    final EntityManager em = EMF.get().createEntityManager();
    final Query q = em.createQuery("select l from Lecture l where l.title=:title"); //$NON-NLS-1$
    q.setParameter("title", title); //$NON-NLS-1$
    try {
      return q.getResultList();
    } finally {
      em.close();
    }
  }

  /**
   * IDを元に講義データを取得します。
   * 
   * @param id 講義ID
   * @return 講義データ
   */
  public static Lecture getLectureById(int id) {
    return ServiceUtil.findEntity(Lecture.class, Integer.valueOf(id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Invoker({UserType.TEACHER})
  public void persist() {
    super.persist();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Invoker({UserType.TEACHER})
  public void update() {
    super.update();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Invoker({UserType.TEACHER})
  public void updateOrCreate() {
    super.updateOrCreate();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Invoker({UserType.TEACHER})
  public void delete() {
    @SuppressWarnings("hiding")
    final Integer id = getId();
    if (id == null) throw new IllegalStateException();

    if (Report.lectureIsRefered(id)) {
      throw new IllegalStateException("The lecture can't be deleted because it has already refered by report(s)."); //$NON-NLS-1$
    }
    if (Attendance.lectureIsRefered(id)) {
      throw new IllegalStateException("The lecture can't be deleted because it has already refered by attendance(s)."); //$NON-NLS-1$
    }

    super.delete();
  }
}
