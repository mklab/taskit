/**
 * 
 */
package org.mklab.taskit.shared.service;

import org.mklab.taskit.shared.dto.LectureDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * 講義データのサービスを提供するクラスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Feb 7, 2011
 */
@RemoteServiceRelativePath("lecture")
public interface LectureService extends RemoteService {

  /**
   * index番目の講義データを取得します。
   * 
   * @param index 講義インデックス
   * @return 講義データ
   */
  LectureDto getLecture(int index);

  /**
   * 全ての講義データを取得します。
   * 
   * @return 全ての講義データ
   */
  LectureDto[] getAllLectures();

  /**
   * 登録されている講義数を取得します。
   * 
   * @return 講義数
   */
  int getLectureCount();

  /**
   * 講義データを登録します。
   * @param title タイトル
   * @param date 日付（ミリ秒）
   */
  void createNewLecture(String title, long date);
}
