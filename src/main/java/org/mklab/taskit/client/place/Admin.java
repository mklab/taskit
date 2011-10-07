/**
 * 
 */
package org.mklab.taskit.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;


/**
 * @author ishikura
 */
public class Admin extends Place {

  /** このアクティビティの場所を表すオブジェクトです。 */
  public static final Place INSTANCE = new Admin();

  /**
   * @author ishikura
   */
  public static class Tokenizer implements PlaceTokenizer<Admin> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Admin getPlace(@SuppressWarnings("unused") String token) {
      return new Admin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getToken(@SuppressWarnings("unused") Admin place) {
      return ""; //$NON-NLS-1$
    }

  }
}
