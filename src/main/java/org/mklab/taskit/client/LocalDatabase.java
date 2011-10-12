/**
 * 
 */
package org.mklab.taskit.client;

import org.mklab.taskit.shared.HelpCallProxy;
import org.mklab.taskit.shared.LectureProxy;
import org.mklab.taskit.shared.TaskitRequestFactory;
import org.mklab.taskit.shared.UserProxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.google.web.bindery.requestfactory.shared.Violation;


/**
 * クライアントサイドでのデータベースです。
 * <p>
 * サーバーからのデータ取得をキャッシュする機能を提供します。
 * 
 * @author ishikura
 */
public class LocalDatabase {

  private Map<Query<?>, Object> cache = new HashMap<Query<?>, Object>();
  private TaskitRequestFactory requestFactory;

  /** 全学生のリストです。 */
  public static Query<List<UserProxy>> STUDENT_LIST = new Query<List<UserProxy>>() {

    @Override
    public void query(TaskitRequestFactory requestFactory, Receiver<List<UserProxy>> receiver) {
      requestFactory.userRequest().getAllStudents().with("account").fire(receiver); //$NON-NLS-1$
    }

  };

  /** 全講義のリストです。 */
  public static Query<List<LectureProxy>> LECTURE_LIST = new Query<List<LectureProxy>>() {

    @Override
    public void query(TaskitRequestFactory requestFactory, Receiver<List<LectureProxy>> receiver) {
      requestFactory.lectureRequest().getAllLectures().with("reports.lecture").fire(receiver); //$NON-NLS-1$
    }

  };

  /** 全講義のリストです。 */
  public static Query<List<HelpCallProxy>> CALL_LIST = new Query<List<HelpCallProxy>>() {

    @Override
    public void query(TaskitRequestFactory requestFactory, Receiver<List<HelpCallProxy>> receiver) {
      requestFactory.helpCallRequest().getAllHelpCalls().with("account", "caller").fire(receiver); //$NON-NLS-1$ //$NON-NLS-2$
    }

  };

  /** ログインユーザーを取得するためのクエリです。 */
  public static Query<UserProxy> LOGIN_USER = new Query<UserProxy>() {

    @Override
    public void query(TaskitRequestFactory requestFactory, Receiver<UserProxy> receiver) {
      requestFactory.userRequest().getLoginUser().with("account", "caller").fire(receiver); //$NON-NLS-1$ //$NON-NLS-2$
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
   * @return キャッシュされたデータ
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

  <T> void setCache(Query<T> query, T value) {
    this.cache.put(query, value);
  }

  /**
   * キャッシュされているかどうか調べます。
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
      public void onViolation(Set<Violation> errors) {
        receiver.onViolation(errors);
      }
    });
  }

  /**
   * データを取得するクラスです。
   * 
   * @author ishikura
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
