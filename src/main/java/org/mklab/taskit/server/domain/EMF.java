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
  private static String persistenceUnitName = "taskit-local"; //$NON-NLS-1$
  /** データベースユーザーのIDを設定するためのキーです。 */
  public static String DB_USER_KEY = "hibernate.connection.username"; //$NON-NLS-1$
  /** データベースユーザーのパスワードを設定するためのキーです。 */
  public static String DB_PASSWORD_KEY = "hibernate.connection.password"; //$NON-NLS-1$
  /** データベースの場所を設定するためのキーです。 */
  public static String DB_URL_KEY = "hibernate.connection.url"; //$NON-NLS-1$

  private static Map<String, String> props = new HashMap<String, String>();

  static {
    if (isDevelopmentMode()) {
      persistenceUnitName = "taskit-local"; //$NON-NLS-1$
    } else {
      persistenceUnitName = "taskit"; //$NON-NLS-1$
    }
  }

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
      try {
        emfInstance = Persistence.createEntityManagerFactory(persistenceUnitName, props);
      } catch (Throwable ex) {
        ex.printStackTrace();
      }
      props = null;
    }
    return emfInstance;
  }

  /**
   * 永続化設定の識別子を設定します。
   * <p>
   * テスト目的以外で利用しないでください。
   * 
   * @param name 永続化設定の識別子
   */
  public static void setPersistenceUnitName(String name) {
    persistenceUnitName = name;
    emfInstance = null;
  }

  /**
   * {@link EntityManagerFactory}生成のためのプロパティを設定します。
   * 
   * @param key キー
   * @param value 値
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
