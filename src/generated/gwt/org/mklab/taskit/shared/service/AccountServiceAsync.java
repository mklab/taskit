package org.mklab.taskit.shared.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface AccountServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.mklab.taskit.shared.service.AccountService
     */
    void createNewAccount( java.lang.String p0, java.lang.String p1, java.lang.String p2, AsyncCallback<Void> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static AccountServiceAsync instance;

        public static final AccountServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (AccountServiceAsync) GWT.create( AccountService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "AccountService" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
