/**
 * 
 */
package org.mklab.taskit.server.domain;

import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.LectureRequest;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.web.bindery.requestfactory.shared.Receiver;
import static org.junit.Assert.*;


/**
 * @author yuhi
 */
public class LectureRequestTest extends DomainTest {

  /**
   * {@link LectureRequest#getAllLectures()}のテストを行います。
   */
  @Test
  public void testGetAllLectures() {
    loginAsTeacher();

    final LectureRequest req = getRequestFactory().lectureRequest();
    req.register("title1", "description1", new Date()); //$NON-NLS-1$//$NON-NLS-2$
    req.register("title2", "description2", new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7))); //$NON-NLS-1$//$NON-NLS-2$
    req.register("title3", "description3", new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(14))); //$NON-NLS-1$//$NON-NLS-2$

    req.getAllLectures().fire(new Receiver<List<LectureProxy>>() {

      @Override
      public void onSuccess(List<LectureProxy> response) {
        assertEquals(3, response.size());
        assertEquals("title1", response.get(0).getTitle()); //$NON-NLS-1$
        assertEquals("title2", response.get(1).getTitle()); //$NON-NLS-1$
        assertEquals("title3", response.get(2).getTitle()); //$NON-NLS-1$
      }

    });
  }

}
