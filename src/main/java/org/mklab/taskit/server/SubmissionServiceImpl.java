/**
 * 
 */
package org.mklab.taskit.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.mklab.taskit.server.dao.SubmissionDao;
import org.mklab.taskit.server.dao.SubmissionDaoImpl;
import org.mklab.taskit.server.dao.SubmissionRegistrationException;
import org.mklab.taskit.shared.dto.LectureDto;
import org.mklab.taskit.shared.dto.StudentwiseScoreTable;
import org.mklab.taskit.shared.dto.StudentwiseScoresDto;
import org.mklab.taskit.shared.model.Report;
import org.mklab.taskit.shared.model.Submission;
import org.mklab.taskit.shared.service.SubmissionService;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 7, 2011
 */
public class SubmissionServiceImpl extends TaskitRemoteService implements SubmissionService {

  /** for serialization. */
  private static final long serialVersionUID = -3245369053714279353L;
  private SubmissionDao submissionDao;
  private LectureQuery lectureQuery;

  /**
   * {@link SubmissionServiceImpl}オブジェクトを構築します。
   */
  public SubmissionServiceImpl() {
    final EntityManager entityManager = createEntityManager();
    this.submissionDao = new SubmissionDaoImpl(entityManager);
    this.lectureQuery = new LectureQuery(entityManager);
  }

  /**
   * @see org.mklab.taskit.shared.service.SubmissionService#getStudentwiseScores(java.lang.String)
   */
  @Override
  public StudentwiseScoresDto getStudentwiseScores(String userName) {
    final List<Submission> submissions = this.submissionDao.getSubmissionsFromUserName(userName);
    final LectureDto[] lecturesDto = this.lectureQuery.getAllLectures();

    // reportIdからレポートの実体、講義IDから講義実体へのマッピングを準備
    final Map<Integer, Report> reportIdToReportMap = new HashMap<Integer, Report>();
    final Map<Integer, Integer> lectureIdToLectureIndexMap = new HashMap<Integer, Integer>();
    for (int i = 0; i < lecturesDto.length; i++) {
      final LectureDto lectureDto = lecturesDto[i];
      lectureIdToLectureIndexMap.put(Integer.valueOf(lectureDto.getLecture().getLectureId()), Integer.valueOf(i));
      for (int j = 0; j < lectureDto.getReportCount(); j++) {
        Report r = lectureDto.getReport(j);
        reportIdToReportMap.put(Integer.valueOf(r.getReportId()), r);
      }
    }

    // 成績のデフォルト値設定
    final int[][] scores = new int[lecturesDto.length][];
    for (int i = 0; i < scores.length; i++) {
      scores[i] = new int[lecturesDto[i].getReportCount()];
    }

    // 全成績を設定
    for (int i = 0; i < submissions.size(); i++) {
      final Submission submission = submissions.get(i);
      final Report report = reportIdToReportMap.get(Integer.valueOf(submission.getReportId()));
      final int lectureIndex = lectureIdToLectureIndexMap.get(Integer.valueOf(report.getLectureId())).intValue();
      final LectureDto lecture = lecturesDto[lectureIndex];
      if (scores[lectureIndex] == null) {
        scores[lectureIndex] = new int[lecture.getReportCount()];
      }
      scores[lectureIndex][report.getNo() - 1] = submission.getEvaluation();
    }

    final StudentwiseScoresDto dto = new StudentwiseScoresDto(lecturesDto, new StudentwiseScoreTable(scores));
    return dto;
  }

  /**
   * @see org.mklab.taskit.shared.service.SubmissionService#setEvaluation(java.lang.String,
   *      int, int, int)
   */
  @Override
  public void setEvaluation(String userName, int lectureIndex, int reportIndex, int evaluation) {
    final LectureDto lectureDto = this.lectureQuery.getLecture(lectureIndex);
    final Report report = lectureDto.getReport(reportIndex);
    try {
      this.submissionDao.setEvaluation(userName, report.getReportId(), evaluation, 0, "", "");
    } catch (SubmissionRegistrationException e) {
      throw new RuntimeException(e);
    }
  }

}
