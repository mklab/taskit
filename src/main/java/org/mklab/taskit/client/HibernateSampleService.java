package org.mklab.taskit.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 21, 2011
 */
@RemoteServiceRelativePath("hibernate_sample")
public interface HibernateSampleService extends RemoteService {

	/**
	 * Hibernate経由でデータベースへアクセスします。
	 * 
	 * @return アクセス結果
	 */
	String accessThroughHibernate();

}
