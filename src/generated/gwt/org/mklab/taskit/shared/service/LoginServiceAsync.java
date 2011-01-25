package org.mklab.taskit.shared.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface LoginServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.mklab.taskit.shared.service.LoginService
     */
    void login( java.lang.String p0, java.lang.String p1, AsyncCallback<org.mklab.taskit.shared.model.User> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.mklab.taskit.shared.service.LoginService
     */
    void logout( AsyncCallback<Void> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static LoginServiceAsync instance;

        public static final LoginServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (LoginServiceAsync) GWT.create( LoginService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "LoginService" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
