/**
 * 
 */
package org.mklab.taskit.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;


/**
 * 学生用アクティビティの場所を表すクラスです。
 * 
 * @author Yuhi Ishikura
 */
public class Student extends Place {

  /** このアクティビティの場所を表すオブジェクトです。 */
  public static final Place INSTANCE = new Student();

  /**
   * @author ishikura
   */
  public static class Tokenizer implements PlaceTokenizer<Student> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Student getPlace(@SuppressWarnings("unused") String token) {
      return new Student();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getToken(@SuppressWarnings("unused") Student place) {
      return ""; //$NON-NLS-1$
    }

  }
}
