package org.mklab.taskit.shared.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface HibernateSampleServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.mklab.taskit.shared.service.HibernateSampleService
     */
    void accessThroughHibernate( AsyncCallback<java.util.List> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static HibernateSampleServiceAsync instance;

        public static final HibernateSampleServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (HibernateSampleServiceAsync) GWT.create( HibernateSampleService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "HibernateSampleService" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
