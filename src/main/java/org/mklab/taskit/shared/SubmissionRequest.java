package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Submission;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @author ishikura
 * @version $Revision$, 2011/09/19
 */
@Service(value = Submission.class)
public interface SubmissionRequest extends RequestContext {

  /**
   * レポートを提出します。
   * 
   * @param accountId 提出者ID
   * @param reportId レポートID
   * @param point 得点
   * @param comment コメント
   * @return リクエスト
   */
  Request<Void> submit(String accountId, Integer reportId, int point);
}
