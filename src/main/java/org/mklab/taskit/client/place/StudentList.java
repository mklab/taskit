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
public class StudentList extends Place {

  /** このアクティビティの場所を表すオブジェクトです。 */
  public static final Place INSTANCE = new StudentList();

  /**
   * @author Yuhi Ishikura
   * @version $Revision$, Jan 22, 2011
   */
  public static class Tokenizer implements PlaceTokenizer<StudentList> {

    /**
     * @see com.google.gwt.place.shared.PlaceTokenizer#getPlace(java.lang.String)
     */
    @Override
    public StudentList getPlace(@SuppressWarnings("unused") String token) {
      return new StudentList();
    }

    /**
     * @see com.google.gwt.place.shared.PlaceTokenizer#getToken(com.google.gwt.place.shared.Place)
     */
    @Override
    public String getToken(@SuppressWarnings("unused") StudentList place) {
      return ""; //$NON-NLS-1$
    }

  }
}
