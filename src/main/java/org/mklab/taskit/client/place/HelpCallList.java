/**
 * 
 */
package org.mklab.taskit.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;


/**
 * @author ishikura
 */
public class HelpCallList extends Place {

  /** このアクティビティの場所を表すオブジェクトです。 */
  public static final Place INSTANCE = new HelpCallList();

  /**
   * @author ishikura
   */
  public static class Tokenizer implements PlaceTokenizer<HelpCallList> {

    /**
     * {@inheritDoc}
     */
    @Override
    public HelpCallList getPlace(@SuppressWarnings("unused") String token) {
      return new HelpCallList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getToken(@SuppressWarnings("unused") HelpCallList place) {
      return ""; //$NON-NLS-1$
    }

  }
}
