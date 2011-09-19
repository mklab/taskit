package org.mklab.taskit.shared;

import java.util.Date;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
public interface SubmissionProxy {

  Integer getReportId();

  Date getDate();

  CommentProxy getComment();

  int getPoint();

}
