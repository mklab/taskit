package org.mklab.taskit.shared.model;

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
  private int lectureId = 0;
  /** 講義の日付です。 */
  private String date;
  /** 講義タイトルです。 */
  private String title;
  /** 講義回です。 */
  private int no;

  /**
   * {@link Lecture}オブジェクトを構築します。
   * 
   * @param date 日付
   * @param no 講義回
   */
  public Lecture(String date, int no) {
    super();
    this.date = date;
    this.no = no;
  }

  /**
   * idを取得します。
   * 
   * @return id
   */
  public int getLectureId() {
    return this.lectureId;
  }

  /**
   * idを設定します。
   * 
   * @param id id
   */
  public void setLectureId(int id) {
    this.lectureId = id;
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
  public int getNo() {
    return this.no;
  }

  /**
   * lessonNoを設定します。
   * 
   * @param no lessonNo
   */
  public void setNo(int no) {
    this.no = no;
  }

  /**
   * titleを取得します。
   * 
   * @return title
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * titleを設定します。
   * 
   * @param title title
   */
  public void setTitle(String title) {
    this.title = title;
  }

}
