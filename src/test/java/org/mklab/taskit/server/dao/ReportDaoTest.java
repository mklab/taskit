/**
 * 
 */
package org.mklab.taskit.server.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.mklab.taskit.shared.model.Report;



/**
 * @author teshima
 * @version $Revision$, Feb 7, 2011
 */
public class ReportDaoTest extends DaoTest{

  /**
   * 指定の講義IDからレポートテーブルの内容を取得できるかどうかテストします。
   * @throws ReportRegistrationException レポート登録ができないときのための例外
   */
  @Test
  public void testGetReportsFromLectureId() throws ReportRegistrationException {
    final ReportDao dao = new ReportDaoImpl(createEntityManager());
    dao.registerReport(new Report(0, "Hello World.", "detail", 1, 0));  //$NON-NLS-1$//$NON-NLS-2$
    dao.registerReport(new Report(1, "Show your name.", "detail", 1, 0)); //$NON-NLS-1$ //$NON-NLS-2$
    dao.registerReport(new Report(0, "Let's use GUI.", "detail", 1, 1)); //$NON-NLS-1$ //$NON-NLS-2$
    
    List<Report> reports0 = dao.getReportsFromLectureId(0);
    assertEquals(2, reports0.size());
    assertEquals("Hello World.", reports0.get(0).getTitle()); //$NON-NLS-1$
    assertEquals("Show your name.", reports0.get(1).getTitle()); //$NON-NLS-1$
    
    List<Report> reports1 = dao.getReportsFromLectureId(1);
    assertEquals(1, reports1.size());
    assertEquals("Let's use GUI.", reports1.get(0).getTitle()); //$NON-NLS-1$
  }
}
