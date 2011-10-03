package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Submission;
import org.mklab.taskit.server.domain.SubmissionLocator;

import java.util.Date;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;


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
