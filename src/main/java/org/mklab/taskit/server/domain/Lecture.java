package org.mklab.taskit.server.domain;

import org.mklab.taskit.server.auth.Invoker;
import org.mklab.taskit.shared.model.UserType;

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


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Entity
public class Lecture extends AbstractEntity<Integer> {

  private Integer id;
  @NotNull
  private Date date;
  private String title;
  private String description;

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
   * 講義日を取得します。
   * 
   * @return 講義日
   */
  public Date getDate() {
    return this.date;
  }

  /**
   * 講義日を設定します。
   * 
   * @param date 講義日
   */
  void setDate(Date date) {
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
   * 講義を登録します。
   * 
   * @param title 講義のタイトル
   * @param description 講義の説明。nullも可
   * @param date 講義日
   */
  @Invoker(UserType.TEACHER)
  public static void register(String title, String description, Date date) {
    final Lecture l = new Lecture();
    l.setTitle(title);
    l.setDate(date);
    l.setDescription(description);

    final EntityManager em = EMF.get().createEntityManager();
    final EntityTransaction t = em.getTransaction();
    try {
      t.begin();
      em.persist(l);
    } catch (Throwable e) {
      t.rollback();
    } finally {
      em.clear();
    }
  }
}
