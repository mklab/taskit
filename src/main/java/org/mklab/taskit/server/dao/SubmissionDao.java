/**
 * 
 */
package org.mklab.taskit.server.dao;

import java.util.List;

import org.mklab.taskit.shared.model.Submission;


/**
 * @author teshima
 * @version $Revision$, Jan 29, 2011
 */
public interface SubmissionDao {
  List<Submission> getAllSubmission(String studentNo);

  int getEvaluation(String useName, int lectureId, int no);
  

  /**
   * @param submission
   * @throws Exception
   */
  void registerSubmission(Submission submission) throws Exception;
}
