/**
 * 
 */
package org.mklab.taskit.server.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
    dao.registerLecture(new Lecture("Hello World.", System.currentTimeMillis())); //$NON-NLS-1$
    dao.registerLecture(new Lecture("Let's GUI", System.currentTimeMillis())); //$NON-NLS-1$
    assertEquals(2, dao.getLectureCount());
  }

  /**
   * 全ての講義データを時間順（昇順）で取得出来ているかどうかのテストです。
   */
  @Test
  public void testGetAllLectures() {
    final LectureDao dao = new LectureDaoImpl(createEntityManager());
    dao.registerLecture(new Lecture("Hello World.", 100)); //$NON-NLS-1$
    dao.registerLecture(new Lecture("Show your name.", 400)); //$NON-NLS-1$
    dao.registerLecture(new Lecture("Let's use GUI.", 200)); //$NON-NLS-1$
    dao.registerLecture(new Lecture("Appletviewer.", 300)); //$NON-NLS-1$
    List<Lecture> lectures = dao.getAllLectures();
    assertEquals("Hello World.", lectures.get(0).getTitle()); //$NON-NLS-1$
    assertEquals("Let's use GUI.", lectures.get(1).getTitle()); //$NON-NLS-1$
    assertEquals("Appletviewer.", lectures.get(2).getTitle()); //$NON-NLS-1$
    assertEquals("Show your name.", lectures.get(3).getTitle()); //$NON-NLS-1$
  }
}
