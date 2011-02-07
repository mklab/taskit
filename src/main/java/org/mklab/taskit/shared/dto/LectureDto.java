/**
 * 
 */
package org.mklab.taskit.shared.dto;

import java.io.Serializable;
import java.util.List;

import org.mklab.taskit.shared.model.Lecture;
import org.mklab.taskit.shared.model.Report;


/**
 * 講義データのDTOです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 7, 2011
 */
public class LectureDto implements Serializable {

  /** for serialization. */
  private static final long serialVersionUID = -3765018987550491034L;
  private Lecture lecture;
  private List<Report> reports;

  /**
   * {@link LectureDto}オブジェクトを構築します。
   */
  LectureDto() {
    // for serialization.
  }

  /**
   * {@link LectureDto}オブジェクトを構築します。
   * 
   * @param lecture 講義データ
   * @param reports 課題リスト
   */
  public LectureDto(Lecture lecture, List<Report> reports) {
    super();
    this.lecture = lecture;
    this.reports = reports;
  }

  /**
   * lectureを設定します。
   * 
   * @param lecture lecture
   */
  void setLecture(Lecture lecture) {
    this.lecture = lecture;
  }

  /**
   * 講義データを取得します。
   * 
   * @return 講義データ
   */
  public Lecture getLecture() {
    return this.lecture;
  }

  /**
   * 課題数を取得します。
   * 
   * @return 課題数
   */
  public int getReportCount() {
    return this.reports.size();
  }

  /**
   * 課題を取得します。
   * 
   * @param index 課題のインデックス
   * @return 課題
   */
  public Report getReport(int index) {
    return this.reports.get(index);
  }

  /**
   * reportsを取得します。
   * 
   * @return reports
   */
  List<Report> getReports() {
    return this.reports;
  }

  /**
   * reportsを設定します。
   * 
   * @param reports reports
   */
  void setReports(List<Report> reports) {
    this.reports = reports;
  }

}
