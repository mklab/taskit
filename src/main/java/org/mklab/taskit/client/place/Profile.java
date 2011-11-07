/**
 * 
 */
package org.mklab.taskit.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;


/**
 * プロフィール編集アクティビティの場所を表すクラスです。
 * 
 * @author Yuhi Ishikura
 */
public class Profile extends Place {

  /** このアクティビティの場所を表すオブジェクトです。 */
  public static final Place INSTANCE = new Profile();

  /**
   * @author Yuhi Ishikura
   */
  public static class Tokenizer implements PlaceTokenizer<Profile> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Profile getPlace(@SuppressWarnings("unused") String token) {
      return new Profile();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getToken(@SuppressWarnings("unused") Profile place) {
      return ""; //$NON-NLS-1$
    }

  }
}
