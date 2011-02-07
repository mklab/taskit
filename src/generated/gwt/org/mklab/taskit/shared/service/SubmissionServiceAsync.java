package org.mklab.taskit.shared.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface SubmissionServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.mklab.taskit.shared.service.SubmissionService
     */
    void getStudentwiseScores( java.lang.String p0, AsyncCallback<org.mklab.taskit.shared.dto.StudentwiseScoresDto> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see org.mklab.taskit.shared.service.SubmissionService
     */
    void setEvaluation( java.lang.String p0, int p1, int p2, int p3, AsyncCallback<Void> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static SubmissionServiceAsync instance;

        public static final SubmissionServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (SubmissionServiceAsync) GWT.create( SubmissionService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "SubmissionService" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
