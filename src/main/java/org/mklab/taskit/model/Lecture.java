package org.mklab.taskit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import net.sf.gilead.pojo.gwt.LightEntity;


/**
 * 講義を表すクラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
@Entity(name = "LECTURE")
public final class Lecture extends LightEntity {

  /** for serialization. */
  private static final long serialVersionUID = 7240243621664480506L;
  /** 管理用IDです。 */
  @Id
  @GeneratedValue
  private int id;
  /** 講義の日付です。 */
  private String date;
  /** 講義回です。 */
  private int lessonNo;

  /**
   * {@link Lecture}オブジェクトを構築します。
   * 
   * @param date 日付
   * @param lessonNo 講義回
   */
  public Lecture(String date, int lessonNo) {
    super();
    this.date = date;
    this.lessonNo = lessonNo;
  }

  /**
   * idを取得します。
   * 
   * @return id
   */
  public int getId() {
    return this.id;
  }

  /**
   * idを設定します。
   * 
   * @param id id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * dateを取得します。
   * 
   * @return date
   */
  public String getDate() {
    return this.date;
  }

  /**
   * dateを設定します。
   * 
   * @param date date
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   * lessonNoを取得します。
   * 
   * @return lessonNo
   */
  public int getLessonNo() {
    return this.lessonNo;
  }

  /**
   * lessonNoを設定します。
   * 
   * @param lessonNo lessonNo
   */
  public void setLessonNo(int lessonNo) {
    this.lessonNo = lessonNo;
  }

}
