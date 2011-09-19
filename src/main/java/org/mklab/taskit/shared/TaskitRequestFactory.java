package org.mklab.taskit.shared;

import com.google.web.bindery.requestfactory.shared.RequestFactory;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/15
 */
public interface TaskitRequestFactory extends RequestFactory {

  AccountRequest accountRequest();

  SubmissionRequest submissionRequest();
}
