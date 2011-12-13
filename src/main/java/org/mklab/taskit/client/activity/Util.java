/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mklab.taskit.client.activity;

import org.mklab.taskit.client.model.StudentwiseRecordModel;
import org.mklab.taskit.client.model.StudentwiseRecordModel.LectureScore;
import org.mklab.taskit.shared.LectureProxy;

import java.util.Date;
import java.util.List;


/**
 * アクティビティで共通に利用するユーティリティを提供するクラスです。
 * 
 * @author Yuhi Ishikura
 */
final class Util {

  /**
   * {@link Util}オブジェクトを構築します。
   */
  private Util() {
    // Forbid instantiation
  }

  /**
   * 与えられた講義データの中から最も現在の時刻に近い講義を取得します。
   * 
   * @param lectures 講義データ
   * @return 最も現在の時刻に近い講義
   */
  static LectureProxy getLatestLecture(List<LectureProxy> lectures) {
    return getLatestItem(lectures, new DateExtractor<LectureProxy>() {

      @Override
      public Date getDateOf(LectureProxy item) {
        return item.getDate();
      }
    });
  }

  /**
   * 講義別の学生成績から、最も現在の時刻に近い成績を取得します。
   * 
   * @param model 講義別の学生成績
   * @return 最も現在の時刻に近い講義の成績
   */
  static StudentwiseRecordModel.LectureScore getLatestScore(StudentwiseRecordModel model) {
    final List<LectureScore> list = model.asList();
    if (list.size() == 0) return null;
    return getLatestItem(list, new Util.DateExtractor<StudentwiseRecordModel.LectureScore>() {

      @Override
      public Date getDateOf(LectureScore item) {
        return item.getLecture().getDate();
      }

    });
  }

  /**
   * 与えられたアイテムの中から、最も今に近い時刻のアイテムを取得します。
   * 
   * @param items アイテムのリスト
   * @param dateExtractor 単一のアイテムから時刻を取得するためのオブジェクト
   * @return 最も今に近い時刻。アイテムが空だった場合はnull
   */
  static <E> E getLatestItem(List<E> items, DateExtractor<E> dateExtractor) {
    final long now = System.currentTimeMillis();
    long minimumMillisToNow = Long.MAX_VALUE;
    E latestItem = null;
    for (E item : items) {
      final Date date = dateExtractor.getDateOf(item);
      final long millisToNow = Math.abs(now - date.getTime());
      if (millisToNow < minimumMillisToNow) {
        minimumMillisToNow = millisToNow;
        latestItem = item;
      }
    }
    return latestItem;
  }

  interface DateExtractor<E> {

    Date getDateOf(E item);

  }

}
