package org.mklab.taskit.shared;

import java.util.Date;

import org.mklab.taskit.server.domain.Submission;
import org.mklab.taskit.server.domain.SubmissionLocator;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;


@ProxyFor(value = Submission.class, locator = SubmissionLocator.class)
@SuppressWarnings("javadoc")
public interface SubmissionProxy extends EntityProxy {

  Integer getId();

  String getAccountId();

  Integer getReportId();

  Date getDate();

  CommentProxy getComment();

  int getPoint();

}
