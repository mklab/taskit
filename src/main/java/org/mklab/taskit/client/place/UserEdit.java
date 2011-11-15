/**
 * 
 */
package org.mklab.taskit.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;


/**
 * ユーザーデータ編集アクティビティの場所を表すクラスです。
 * 
 * @author Yuhi Ishikura
 */
public class UserEdit extends Place {

  /** このアクティビティの場所を表すオブジェクトです。 */
  public static final Place INSTANCE = new UserEdit();

  /**
   * @author Yuhi Ishikura
   */
  public static class Tokenizer implements PlaceTokenizer<UserEdit> {

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEdit getPlace(@SuppressWarnings("unused") String token) {
      return new UserEdit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getToken(@SuppressWarnings("unused") UserEdit place) {
      return ""; //$NON-NLS-1$
    }

  }
}
