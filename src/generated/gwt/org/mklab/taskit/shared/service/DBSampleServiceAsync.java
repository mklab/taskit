package org.mklab.taskit.shared.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface DBSampleServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.mklab.taskit.shared.service.DBSampleService
     */
    void accessToDatabase( AsyncCallback<java.lang.String> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static DBSampleServiceAsync instance;

        public static final DBSampleServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (DBSampleServiceAsync) GWT.create( DBSampleService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "DBSampleService" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
