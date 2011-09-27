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

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Factory for creating EntityManager.
 */
public final class EMF {

  private static EntityManagerFactory emfInstance;
  private static String persistenceUnitName;

  /**
   * {@link EntityManagerFactory}インスタンスを必要に応じて生成し取得します。
   * 
   * @return {@link EntityManagerFactory}インスタンス
   */
  public static synchronized EntityManagerFactory get() {
    if (emfInstance == null) {
      try {
        emfInstance = Persistence.createEntityManagerFactory(persistenceUnitName);
      } catch (Throwable ex) {
        ex.printStackTrace();
      }
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
