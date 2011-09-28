/**
 * 
 */
package org.mklab.taskit.server.domain;

import static org.junit.Assert.assertEquals;

import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.LectureRequest;
import org.mklab.taskit.shared.ReportProxy;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.web.bindery.requestfactory.shared.Receiver;


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

    // persist
    {
      LectureRequest req = getRequestFactory().lectureRequest();
      persist(req, "title1", "description1", new Date()); //$NON-NLS-1$//$NON-NLS-2$
      persist(req, "title2", "description2", new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7))); //$NON-NLS-1$//$NON-NLS-2$
      persist(req, "title3", "description3", new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(14))); //$NON-NLS-1$//$NON-NLS-2$
      req.fire();
    }

    // get and test
    {
      LectureRequest req = getRequestFactory().lectureRequest();
      req.getAllLectures().with("reports").fire(new Receiver<List<LectureProxy>>() { //$NON-NLS-1$

            @Override
            public void onSuccess(List<LectureProxy> response) {
              assertEquals(3, response.size());
              assertEquals(2, response.get(0).getReports().size());
              assertEquals(2, response.get(1).getReports().size());
              assertEquals(2, response.get(2).getReports().size());
              assertEquals("title1", response.get(0).getTitle()); //$NON-NLS-1$
              assertEquals("title2", response.get(1).getTitle()); //$NON-NLS-1$
              assertEquals("title3", response.get(2).getTitle()); //$NON-NLS-1$
            }

          });
    }
    // add report and test
    {
      final LectureRequest requestForGet = getRequestFactory().lectureRequest();
      requestForGet.getAllLectures().with("reports").fire(new Receiver<List<LectureProxy>>() { //$NON-NLS-1$

            @Override
            public void onSuccess(List<LectureProxy> response) {
              final LectureRequest requestForUpdate = getRequestFactory().lectureRequest();
              final LectureProxy lecture = response.get(0);
              final ReportProxy report = requestForUpdate.create(ReportProxy.class);
              report.setTitle("aaa"); //$NON-NLS-1$
              report.setLecture(lecture);

              lecture.getReports().add(report);
              requestForUpdate.update().using(lecture).fire();
            }

          });

      final LectureRequest requestForTest = getRequestFactory().lectureRequest();
      requestForTest.getAllLectures().with("reports").fire(new Receiver<List<LectureProxy>>() { //$NON-NLS-1$

            @Override
            public void onSuccess(List<LectureProxy> response) {
              final LectureProxy lecture = response.get(0);
              assertEquals(3, lecture.getReports().size());
            }

          });
    }
  }

  private static void persist(LectureRequest req, String title, String description, Date date) {
    LectureProxy l = req.create(LectureProxy.class);
    l.setDescription(description);
    l.setDate(date);
    l.setTitle(title);

    final ReportProxy report = req.create(ReportProxy.class);
    report.setTitle("aaa"); //$NON-NLS-1$
    report.setLecture(l);
    final ReportProxy report2 = req.create(ReportProxy.class);
    report2.setTitle("aaa"); //$NON-NLS-1$
    report2.setLecture(l);
    l.setReports(Arrays.asList(report, report2));
    req.persist().using(l);
  }

}
