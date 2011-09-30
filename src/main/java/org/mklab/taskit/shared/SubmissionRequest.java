package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Submission;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Service(value = Submission.class)
@SuppressWarnings("javadoc")
public interface SubmissionRequest extends RequestContext {

  InstanceRequest<SubmissionProxy, Void> persist();

  Request<List<SubmissionProxy>> getMySubmissions();

  Request<List<SubmissionProxy>> getSubmissionsByAccountId(String accountId);

  Request<Void> submit(AccountProxy submitter, ReportProxy report, int point);

}
