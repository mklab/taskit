/**
 * 
 */
package org.mklab.taskit.shared;

import org.mklab.taskit.server.domain.Lecture;

import java.util.Date;
import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;


/**
 * @author ishikura
 */
@Service(value = Lecture.class)
public interface LectureRequest extends RequestContext {

  /**
   * 全ての講義を取得します。
   * 
   * @return 全ての講義
   */
  Request<List<LectureProxy>> getAllLectures();

  /**
   * 講義を新規登録します。
   * 
   * @param title タイトル
   * @param description 説明
   * @param date 日付
   * @return リクエスト
   */
  Request<Void> register(String title, String description, Date date);

}
