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
import org.mklab.taskit.server.domain.SubmissionLocator;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * @see Submission
 * @author Yuhi Ishikura
 */
@ProxyFor(value = Submission.class, locator = SubmissionLocator.class)
@SuppressWarnings("javadoc")
public interface SubmissionProxy extends EntityProxy {

  Integer getId();

  AccountProxy getSubmitter();

  void setSubmitter(AccountProxy account);

  ReportProxy getReport();

  void setReport(ReportProxy report);

  Date getDate();

  CommentProxy getComment();

  void setComment(CommentProxy comment);

  int getPoint();

  void setPoint(int point);

}
