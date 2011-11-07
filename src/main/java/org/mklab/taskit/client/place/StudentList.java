/**
 * 
 */
package org.mklab.taskit.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;


/**
 * 学生一覧アクティビティの場所を表すクラスです。
 * <p>
 * 学生IDを引数に与えることで、その学生データを直接表示します。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class StudentList extends Place {

  /** このアクティビティの場所を表すオブジェクトです。 */
  public static final Place INSTANCE = new StudentList(""); //$NON-NLS-1$

  /** 初期表示学生IDです。 */
  private String studentId;

  /**
   * {@link StudentList}オブジェクトを構築します。
   * 
   * @param studentId 初期表示学生ID
   */
  public StudentList(String studentId) {
    this.studentId = studentId;
  }

  /**
   * 初期表示学生IDを取得します。
   * 
   * @return studentId
   */
  public String getStudentId() {
    return this.studentId;
  }

  /**
   * @author Yuhi Ishikura
   * @version $Revision$, Jan 22, 2011
   */
  public static class Tokenizer implements PlaceTokenizer<StudentList> {

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentList getPlace(String token) {
      return new StudentList(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getToken(StudentList place) {
      return place.getStudentId();
    }

  }
}
