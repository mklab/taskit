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

  private int accountId;

  /**
   * {@link StudentScore}オブジェクトを構築します。
   * 
   * @param accountId アカウントID
   */
  public StudentScore(int accountId) {
    this.accountId = accountId;
  }

  /**
   * 表示するアカウントのIDを取得します。
   * 
   * @return accountId 表示するアカウントのID
   */
  public int getAccountId() {
    return this.accountId;
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
      return new StudentScore(Integer.parseInt(token));
    }

    /**
     * @see com.google.gwt.place.shared.PlaceTokenizer#getToken(com.google.gwt.place.shared.Place)
     */
    @Override
    public String getToken(StudentScore place) {
      return String.valueOf(place.getAccountId());
    }

  }
}
