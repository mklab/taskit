/**
 * 
 */
package org.mklab.taskit.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 22, 2011
 */
public class StudentScore extends Place {

  private String userName;

  /**
   * {@link StudentScore}オブジェクトを構築します。
   * 
   * @param userName ユーザー名
   */
  public StudentScore(String userName) {
    this.userName = userName;
  }

  /**
   * ユーザー名を取得します。
   * 
   * @return userName
   */
  public String getUserName() {
    return this.userName;
  }

  /**
   * @author Yuhi Ishikura
   * @version $Revision$, Jan 22, 2011
   */
  public static class Tokenizer implements PlaceTokenizer<StudentScore> {

    /**
     * @see com.google.gwt.place.shared.PlaceTokenizer#getPlace(java.lang.String)
     */
    @Override
    public StudentScore getPlace(String token) {
      return new StudentScore(token);
    }

    /**
     * @see com.google.gwt.place.shared.PlaceTokenizer#getToken(com.google.gwt.place.shared.Place)
     */
    @Override
    public String getToken(StudentScore place) {
      return String.valueOf(place.getUserName());
    }

  }
}
