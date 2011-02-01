/**
 * 
 */
package org.mklab.taskit.server.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mklab.taskit.shared.model.Lecture;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 30, 2011
 */
public class LectureDaoTest extends DaoTest {

  /**
   * 講義データ登録のテストを行ないます。
   */
  @Test
  public void testRegisterLecture() {
    final LectureDao dao = new LectureDaoImpl(createEntityManager());
    dao.registerLecture(new Lecture("Hello", System.currentTimeMillis())); //$NON-NLS-1$
    assertEquals(1, dao.getAllLectures().size());
  }

}
