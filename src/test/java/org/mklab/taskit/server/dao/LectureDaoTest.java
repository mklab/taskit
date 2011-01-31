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
  
  /**
   * 講義数を取得できているかのテストを行ないます。
   */
  @Test
  public void testGetLectureCount() {
    final LectureDao dao = new LectureDaoImpl(createEntityManager());
    dao.registerLecture(new Lecture("Hello world.", System.currentTimeMillis())); //$NON-NLS-1$
    dao.registerLecture(new Lecture("Let's GUI", System.currentTimeMillis())); //$NON-NLS-1$
    assertEquals(2, dao.getLectureCount());
  }
}
