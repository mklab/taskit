package org.mklab.taskit.server;

import org.mklab.taskit.client.HibernateSampleService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 21, 2011
 */
public class HibernateSampleServiceImpl extends RemoteServiceServlet implements
		HibernateSampleService {

	/**
	 * @see org.mklab.taskit.client.HibernateSampleService#accessThroughHibernate()
	 */
	public String accessThroughHibernate() {
		return "hoge";
	}

}
