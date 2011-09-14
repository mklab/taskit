/**
 * 
 */
package org.mklab.taskit.server;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mklab.taskit.server.dao.DaoFactory;
import org.mklab.taskit.server.dao.SubmissionDao;
import org.mklab.taskit.server.dao.SubmissionDaoFactory;
import org.mklab.taskit.server.dao.SubmissionRegistrationException;
import org.mklab.taskit.shared.dto.LectureDto;
import org.mklab.taskit.shared.dto.StudentwiseScoreTable;
import org.mklab.taskit.shared.dto.StudentwiseScoresDto;
import org.mklab.taskit.shared.model.Report;
import org.mklab.taskit.shared.model.Submission;
import org.mklab.taskit.shared.model.User;
import org.mklab.taskit.shared.service.ServiceException;
import org.mklab.taskit.shared.service.SubmissionService;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 7, 2011
 */
public class SubmissionServiceImpl extends TaskitRemoteService implements SubmissionService {

  /** for serialization. */
  private static final long serialVersionUID = -3245369053714279353L;
  private DaoFactory<SubmissionDao> submissionDaoFactory = new SubmissionDaoFactory();
  private LectureQuery lectureQuery = new LectureQuery();

  /**
   * @see org.mklab.taskit.shared.service.SubmissionService#getStudentwiseScores(java.lang.String)
   */
  @Override
  public StudentwiseScoresDto getStudentwiseScores(String userName) {
    if (SessionUtil.isTAOrTeacher(getSession()) == false) {
      final User user = SessionUtil.getUser(getSession());
      if (user == null || user.getId().equals(userName) == false) throw new IllegalStateException("Illegal access."); //$NON-NLS-1$
    }

    final SubmissionDao submissionDao = this.submissionDaoFactory.create();
    final List<Submission> submissions = submissionDao.getSubmissionsFromUserName(userName);
    submissionDao.close();

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
   *      int, int, int, String, String)
   */
  @SuppressWarnings("boxing")
  @Override
  public void setEvaluation(String userName, int lectureIndex, int reportIndex, int evaluation, String publicComment, String privateComment) throws ServiceException {
    SessionUtil.assertIsTAOrTeacher(getSession());

    final LectureDto lectureDto = this.lectureQuery.getLecture(lectureIndex);
    final int reportCount = lectureDto.getReportCount();
    if (reportCount <= reportIndex) {
      throw new ServiceException(MessageFormat.format("Lecture:{0} has only {1} reports.{2}>={1}", lectureDto.getLecture(), reportCount, reportIndex)); //$NON-NLS-1$
    }

    try {
      final Report report = lectureDto.getReport(reportIndex);
      final SubmissionDao submissionDao = this.submissionDaoFactory.create();
      submissionDao.setEvaluation(userName, report.getReportId(), evaluation, 0, publicComment, privateComment);
      submissionDao.close();
    } catch (SubmissionRegistrationException e) {
      throw new RuntimeException(e);
    }
  }

}
