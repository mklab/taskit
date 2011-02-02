/**
 * 
 */
package org.mklab.taskit.shared.dto;

/**
 * 講義別の全学生の出席データを表すクラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 1, 2011
 */
public class LecturewiseAllStudentAttendanceData {

  private String choosableTypes[];
  private int[] typeIndices;

  /**
   * {@link LecturewiseAllStudentAttendanceData}オブジェクトを構築します。
   * 
   * @param choosableTypes 選択可能な出席種類
   * @param typeIndices 学籍番号順に並んだ出席種別(0~)
   */
  public LecturewiseAllStudentAttendanceData(String[] choosableTypes, int[] typeIndices) {
    this.choosableTypes = choosableTypes;
    this.typeIndices = typeIndices;
  }

  /**
   * choosableTypesを取得します。
   * 
   * @return choosableTypes
   */
  public String[] getChoosableTypes() {
    return this.choosableTypes;
  }

  /**
   * 出席状況を取得します。
   * 
   * @param rowIndex 学生のインデックス
   * @return 出席状況
   */
  public int getAttendanceTypeIndexOf(int rowIndex) {
    return this.typeIndices[rowIndex];
  }

}
