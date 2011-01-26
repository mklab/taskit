package org.mklab.taskit.client;

import com.google.gwt.junit.client.GWTTestCase;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 21, 2011
 */
public class GwtTestTaskit extends GWTTestCase {

  /**
   * @see com.google.gwt.junit.client.GWTTestCase#getModuleName()
   */
  @Override
  public String getModuleName() {
    return "org.mklab.taskit.taskitTest"; //$NON-NLS-1$
  }

  /**
   * データベースへのアクセスがうまくいったかどうかテストします。
   */
  public void testDbAccess() {
    //    final DBSampleServiceAsync service = GWT.create(DBSampleService.class);
    //    final ServiceDefTarget target = (ServiceDefTarget)service;
    //    target.setServiceEntryPoint(GWT.getModuleBaseURL() + "taskit/db_sample"); //$NON-NLS-1$
    //
    //    delayTestFinish(1000 * 10);
    //    service.accessToDatabase(new AsyncCallback<String>() {
    //
    //      @SuppressWarnings("synthetic-access")
    //      @Override
    //      public void onSuccess(String result) {
    //        assertNotNull(result);
    //        finishTest();
    //      }
    //
    //      @Override
    //      public void onFailure(Throwable caught) {
    //        fail(caught.toString());
    //      }
    //    });
  }

}
