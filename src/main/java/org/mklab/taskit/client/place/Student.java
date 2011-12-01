/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
