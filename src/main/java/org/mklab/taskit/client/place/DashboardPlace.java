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
public class DashboardPlace extends Place {

  private String name;

  /**
   * {@link DashboardPlace}オブジェクトを構築します。
   * 
   * @param name 名前
   */
  public DashboardPlace(String name) {
    super();
    this.name = name;
  }

  /**
   * nameを取得します。
   * 
   * @return name
   */
  public String getName() {
    return this.name;
  }

  /**
   * @author Yuhi Ishikura
   * @version $Revision$, Jan 22, 2011
   */
  public static class Tokenizer implements PlaceTokenizer<DashboardPlace> {

    /**
     * @see com.google.gwt.place.shared.PlaceTokenizer#getPlace(java.lang.String)
     */
    @Override
    public DashboardPlace getPlace(String token) {
      return new DashboardPlace(token);
    }

    /**
     * @see com.google.gwt.place.shared.PlaceTokenizer#getToken(com.google.gwt.place.shared.Place)
     */
    @Override
    public String getToken(DashboardPlace place) {
      return place.getName();
    }

  }
}
