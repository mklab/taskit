package org.mklab.taskit.shared.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface AttendanceServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.mklab.taskit.shared.service.AttendanceService
     */
    void getBaseData( AsyncCallback<org.mklab.taskit.shared.dto.AttendanceBaseDto> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.mklab.taskit.shared.service.AttendanceService
     */
    void setAttendanceType( java.lang.String p0, int p1, java.lang.String p2, AsyncCallback<Void> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.mklab.taskit.shared.service.AttendanceService
     */
    void getLecturewiseAttendanceData( int p0, AsyncCallback<org.mklab.taskit.shared.dto.AttendanceDto> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static AttendanceServiceAsync instance;

        public static final AttendanceServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (AttendanceServiceAsync) GWT.create( AttendanceService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "AttendanceService" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
