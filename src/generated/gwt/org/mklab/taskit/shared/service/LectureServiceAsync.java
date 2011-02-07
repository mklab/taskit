package org.mklab.taskit.shared.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface LectureServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.mklab.taskit.shared.service.LectureService
     */
    void getLecture( int p0, AsyncCallback<org.mklab.taskit.shared.dto.LectureDto> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.mklab.taskit.shared.service.LectureService
     */
    void getAllLectures( AsyncCallback<org.mklab.taskit.shared.dto.LectureDto[]> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.mklab.taskit.shared.service.LectureService
     */
    void getLectureCount( AsyncCallback<java.lang.Integer> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static LectureServiceAsync instance;

        public static final LectureServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (LectureServiceAsync) GWT.create( LectureService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "LectureService" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
