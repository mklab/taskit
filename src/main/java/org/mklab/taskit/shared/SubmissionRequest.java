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
public interface SubmissionRequest extends RequestContext {

  /**
   * レポートを提出します。
   * 
   * @return リクエスト
   */
  InstanceRequest<SubmissionProxy, Void> persist();

  /**
   * 提出者を指定してすべての提出情報を取得します。
   * 
   * @param accountId 提出者
   * @return 提出情報
   */
  Request<List<SubmissionProxy>> getSubmissionsByAccountId(String accountId);

}
