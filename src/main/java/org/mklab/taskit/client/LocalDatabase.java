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
package org.mklab.taskit.client;

import org.mklab.taskit.shared.CheckMapProxy;
import org.mklab.taskit.shared.HelpCallProxy;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.RecordProxy;
import org.mklab.taskit.shared.TaskitRequestFactory;
import org.mklab.taskit.shared.UserProxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;


/**
 * クライアントサイドでのデータベースです。
 * <p>
 * サーバーからのデータ取得をキャッシュする機能を提供します。
 * 
 * @author Yuhi Ishikura
 */
public class LocalDatabase {

  private Map<Query<?>, Object> cache = new HashMap<Query<?>, Object>();
  private TaskitRequestFactory requestFactory;

  /** 全学生のリストを取得するクエリです。 */
  public static Query<List<UserProxy>> STUDENT_LIST = new Query<List<UserProxy>>() {

    @Override
    public void query(TaskitRequestFactory requestFactory, Receiver<List<UserProxy>> receiver) {
      requestFactory.userRequest().getAllStudents().with("account").fire(receiver); //$NON-NLS-1$
    }

  };

  /** 全講義のリストを取得するクエリです。 */
  public static Query<List<LectureProxy>> LECTURE_LIST = new Query<List<LectureProxy>>() {

    @Override
    public void query(TaskitRequestFactory requestFactory, Receiver<List<LectureProxy>> receiver) {
      requestFactory.lectureRequest().getAllLectures().with("reports.lecture").fire(receiver); //$NON-NLS-1$
    }

  };

  /** 全講義のリストを取得するクエリです。 */
  public static Query<List<HelpCallProxy>> CALL_LIST = new Query<List<HelpCallProxy>>() {

    @Override
    public void query(TaskitRequestFactory requestFactory, Receiver<List<HelpCallProxy>> receiver) {
      requestFactory.helpCallRequest().getAllHelpCalls().with("caller").fire(receiver); //$NON-NLS-1$ 
    }

  };

  /** ログインユーザーを取得するためのクエリです。 */
  public static Query<UserProxy> LOGIN_USER = new Query<UserProxy>() {

    @Override
    public void query(TaskitRequestFactory requestFactory, Receiver<UserProxy> receiver) {
      requestFactory.userRequest().getLoginUser().with("account").fire(receiver); //$NON-NLS-1$
    }

  };

  /** 全生徒の成績統計を取得するクエリです。 */
  public static Query<List<RecordProxy>> RECORDS = new Query<List<RecordProxy>>() {

    @Override
    public void query(TaskitRequestFactory requestFactory, Receiver<List<RecordProxy>> receiver) {
      requestFactory.recordRequest().getAllRecords().with("statistics").fire(receiver); //$NON-NLS-1$
    }

  };

  /** どのTAがどの生徒を担当しているか(チェックイン状態)を取得するクエリです。 */
  public static Query<List<CheckMapProxy>> CHECKS = new Query<List<CheckMapProxy>>() {

    @Override
    public void query(TaskitRequestFactory requestFactory, Receiver<List<CheckMapProxy>> receiver) {
      requestFactory.checkMapRequest().getAllCheckMap().with("student").fire(receiver); //$NON-NLS-1$
    }

  };

  /**
   * {@link LocalDatabase}オブジェクトを構築します。
   * 
   * @param requestFactory リクエストファクトリ
   */
  public LocalDatabase(TaskitRequestFactory requestFactory) {
    if (requestFactory == null) throw new NullPointerException();
    this.requestFactory = requestFactory;
  }

  /**
   * キャッシュが存在すれば取得します。
   * 
   * @param query クエリ
   * @return キャッシュされたデータ。存在しなければnull
   */
  public <T> T getCache(Query<T> query) {
    @SuppressWarnings("unchecked")
    final T cachedValue = (T)this.cache.get(query);
    return cachedValue;
  }

  /**
   * 与えられたクエリのキャッシュをクリアします。
   * 
   * @param query クエリ
   */
  public <T> void clearCache(final Query<T> query) {
    this.cache.remove(query);
  }

  /**
   * すべてのキャッシュをクリアします。
   */
  public <T> void clearAllCache() {
    this.cache.clear();
  }

  /**
   * クエリのキャッシュを設定します。
   * 
   * @param query クエリ
   * @param value キャッシュ
   */
  <T> void setCache(Query<T> query, T value) {
    this.cache.put(query, value);
  }

  /**
   * 与えられたクエリがキャッシュされているかどうか調べます。
   * 
   * @param query クエリ
   * @return キャッシュされているかどうか
   */
  private boolean isCached(Query<?> query) {
    return this.cache.containsKey(query);
  }

  /**
   * キャッシュが存在すればそれをレシーバーに渡し、存在しなければクエリを実行してからレシーバーに実行結果を渡します。
   * 
   * @param query クエリ
   * @param receiver レシーバー
   */
  public <T> void getCacheOrExecute(final Query<T> query, final Receiver<T> receiver) {
    if (isCached(query)) {
      final T cachedValue = getCache(query);
      receiver.onSuccess(cachedValue);
      return;
    }
    execute(query, receiver);
  }

  /**
   * キャッシュが存在すればレシーバーに渡した後に、クエリを実行しもう一度レシーバーに結果を渡します。
   * 
   * @param query クエリ
   * @param receiver レシーバー。キャッシュが存在した場合は二度呼び出されます。
   */
  public <T> void getCacheAndExecute(final Query<T> query, final Receiver<T> receiver) {
    if (isCached(query)) {
      final T cachedValue = getCache(query);
      receiver.onSuccess(cachedValue);
    }
    execute(query, receiver);
  }

  /**
   * クエリを実行します。
   * 
   * @param query クエリ
   */
  public <T> void execute(final Query<T> query) {
    execute(query, new Receiver<T>() {

      @SuppressWarnings("unused")
      @Override
      public void onSuccess(T response) {
        // do nothing
      }
    });
  }

  /**
   * データベースに問い合せます。
   * 
   * @param query クエリ
   * @param receiver 結果を処理するレシーバー
   */
  public <T> void execute(final Query<T> query, final Receiver<T> receiver) {
    query.query(this.requestFactory, new Receiver<T>() {

      @Override
      public void onSuccess(T response) {
        setCache(query, response);
        receiver.onSuccess(response);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onFailure(ServerFailure error) {
        receiver.onFailure(error);
      }

      /**
       * {@inheritDoc}
       */
      @Override
      public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
        receiver.onConstraintViolation(violations);
      }
    });
  }

  /**
   * データを取得するインターフェースです。
   * 
   * @author Yuhi Ishikura
   * @param <T> データの型
   */
  public static interface Query<T> {

    /**
     * データを取得し、結果をレシーバーに渡します。
     * 
     * @param requestFactory リクエストファクトリ
     * @param receiver レシーバー
     */
    void query(TaskitRequestFactory requestFactory, Receiver<T> receiver);

  }

}
