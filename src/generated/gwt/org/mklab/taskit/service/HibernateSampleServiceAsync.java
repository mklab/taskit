package org.mklab.taskit.service;

import java.util.List;

import org.mklab.taskit.model.Test;
import org.mklab.taskit.service.HibernateSampleService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;


/**
 * {@link HibernateSampleService}の非同期実行インターフェースです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 21, 2011
 */
public interface HibernateSampleServiceAsync {

  /**
   * GWT-RPC service asynchronous (client-side) interface
   * 
   * @param callback コールバック
   * @see org.mklab.taskit.service.HibernateSampleService
   */
  void accessThroughHibernate(AsyncCallback<List<Test>> callback);

  /**
   * Utility class to get the RPC Async interface from client-side code
   */
  public static final class Util {

    private static HibernateSampleServiceAsync instance;

    /**
     * @return インスタンス
     */
    public static final HibernateSampleServiceAsync getInstance() {
      if (instance == null) {
        instance = (HibernateSampleServiceAsync)GWT.create(HibernateSampleService.class);
        ServiceDefTarget target = (ServiceDefTarget)instance;
        target.setServiceEntryPoint(GWT.getModuleBaseURL() + "HibernateSampleService"); //$NON-NLS-1$
      }
      return instance;
    }

    private Util() {
      // Utility class should not be instanciated
    }
  }
}
