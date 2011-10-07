/**
 * 
 */
package org.mklab.taskit.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;


/**
 * @author ishikura
 */
public class LectureEdit extends Place {

  /** このアクティビティの場所を表すオブジェクトです。 */
  public static final Place INSTANCE = new LectureEdit();

  /**
   * @author ishikura
   */
  public static class Tokenizer implements PlaceTokenizer<LectureEdit> {

    /**
     * {@inheritDoc}
     */
    @Override
    public LectureEdit getPlace(@SuppressWarnings("unused") String token) {
      return new LectureEdit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getToken(@SuppressWarnings("unused") LectureEdit place) {
      return ""; //$NON-NLS-1$
    }

  }
}
