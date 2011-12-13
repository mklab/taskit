/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Submission;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @see Submission
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Service(value = Submission.class)
@SuppressWarnings("javadoc")
public interface SubmissionRequest extends RequestContext {

  InstanceRequest<SubmissionProxy, Void> persist();

  InstanceRequest<SubmissionProxy, Void> delete();

  Request<List<SubmissionProxy>> getMySubmissions();

  Request<List<SubmissionProxy>> getSubmissionsByAccountId(String accountId);

  Request<Void> submit(AccountProxy submitter, ReportProxy report, int point);

}
