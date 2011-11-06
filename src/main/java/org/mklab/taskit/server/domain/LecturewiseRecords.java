/**
 * 
 */
package org.mklab.taskit.server.domain;

import java.util.List;


/**
 * ある生徒のすべての講義を合わせた成績を表すクラスです。
 * 
 * @author ishikura
 */
public class LecturewiseRecords {

  private User student;
  private List<LecturewiseRecord> records;

  /**
   * {@link LecturewiseRecords}オブジェクトを構築します。
   */
  public LecturewiseRecords() {
    // for framework
  }

  LecturewiseRecords(User student, List<LecturewiseRecord> records) {
    this.student = student;
    this.records = records;
  }

  /**
   * 成績保持者を取得します。
   * 
   * @return 成績保持者
   */
  public User getStudent() {
    return this.student;
  }

  /**
   * 講義別の成績を取得します。
   * 
   * @return 講義別の成績
   */
  public List<LecturewiseRecord> getRecords() {
    return this.records;
  }
}
