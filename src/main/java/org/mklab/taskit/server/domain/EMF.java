/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.mklab.taskit.server.domain;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Factory for creating EntityManager.
 */
public final class EMF {

  private static EntityManagerFactory emfInstance;
  /** データベースユーザーのIDを設定するためのキーです。 */
  public static String DB_USER_KEY = "hibernate.connection.username"; //$NON-NLS-1$
  /** データベースユーザーのパスワードを設定するためのキーです。 */
  public static String DB_PASSWORD_KEY = "hibernate.connection.password"; //$NON-NLS-1$
  /** データベースの場所を設定するためのキーです。 */
  public static String DB_URL_KEY = "hibernate.connection.url"; //$NON-NLS-1$
  /** スキーマ自動生成ルールを設定するためのキーです。 */
  public static String DB_SCHEMA_DDL = "hibernate.hbm2ddl.auto"; //$NON-NLS-1$

  private static Map<String, String> props = new HashMap<String, String>();

  @SuppressWarnings("nls")
  private static boolean isDevelopmentMode() {
    return new File("src").exists() && new File("pom.xml").exists();
  }

  /**
   * {@link EntityManagerFactory}インスタンスを必要に応じて生成し取得します。
   * 
   * @return {@link EntityManagerFactory}インスタンス
   */
  public static synchronized EntityManagerFactory get() {
    if (emfInstance == null) {
      if (isDevelopmentMode()) {

      } else {

      }
      try {
        emfInstance = Persistence.createEntityManagerFactory("taskit", props); //$NON-NLS-1$
      } catch (Throwable ex) {
        ex.printStackTrace();
      }
    }
    return emfInstance;
  }

  /**
   * {@link EntityManagerFactory}生成のためのプロパティを設定します。
   * <p>
   * {@link EntityManagerFactory}が既に生成された場合には変更することができません。 変更したい場合には
   * {@link #resetEntityManagerFactory()}を実行後変更してください。
   * <p>
   * 通常実行中に変更すべきではありません。
   * 
   * @param key キー
   * @param value 値
   * @throws IllegalStateException プロパティの変更が無効なタイミングで呼び出された場合
   */
  public static void setPersistenceProperty(String key, String value) {
    if (emfInstance != null) throw new IllegalStateException("EntityManagerFactory has already initialized."); //$NON-NLS-1$
    props.put(key, value);
  }

  /**
   * {@link EntityManagerFactory}で利用していたリソースを開放し、再生成することを要求します。
   */
  public static void resetEntityManagerFactory() {
    if (emfInstance == null) return;
    emfInstance.close();
    emfInstance = null;
  }

  private EMF() {
    // nothing 
  }
}
