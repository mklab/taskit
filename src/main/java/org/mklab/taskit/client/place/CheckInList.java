/**
 * 
 */
package org.mklab.taskit.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;


/**
 * チェックインリストアクティビティの場所を表すクラスです。
 * 
 * @author Yuhi Ishikura
 */
public class CheckInList extends Place {

  /** このアクティビティの場所を表すオブジェクトです。 */
  public static final Place INSTANCE = new CheckInList();

  /**
   * @author Yuhi Ishikura
   */
  public static class Tokenizer implements PlaceTokenizer<CheckInList> {

    /**
     * {@inheritDoc}
     */
    @Override
    public CheckInList getPlace(@SuppressWarnings("unused") String token) {
      return new CheckInList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getToken(@SuppressWarnings("unused") CheckInList place) {
      return ""; //$NON-NLS-1$
    }

  }
}
