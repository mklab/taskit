package org.mklab.taskit.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


/**
 * {@link DBSampleService}の非同期実行インターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 21, 2011
 */
public interface DBSampleServiceAsync {

  /**
   * GWT-RPC service asynchronous (client-side) interface
   * 
   * @param callback コールバック
   * @see org.mklab.taskit.client.DBSampleService
   */
  void accessToDatabase(AsyncCallback<java.lang.String> callback);

  /**
   * Utility class to get the RPC Async interface from client-side code
   */
  public static final class Util {

    private static DBSampleServiceAsync instance;

    /**
     * @return インスタンス
     */
    public static final DBSampleServiceAsync getInstance() {
      if (instance == null) {
        instance = (DBSampleServiceAsync)GWT.create(DBSampleService.class);
        ServiceDefTarget target = (ServiceDefTarget)instance;
        target.setServiceEntryPoint(GWT.getModuleBaseURL() + "DBSampleService"); //$NON-NLS-1$
      }
      return instance;
    }

    private Util() {
      // Utility class should not be instanciated
    }
  }
}
