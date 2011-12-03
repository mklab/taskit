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
package org.mklab.taskit.server.domain;

/**
 * 学生全体の統計情報を保持するクラスです。
 * 
 * @author Yuhi Ishikura
 */
public class Statistics {

  /** 全生徒数です。 */
  private int studentCount;
  /** 現時点での最大得点です。 */
  private int maximumScore;
  /** 平均点です。 */
  private double average;
  /** 標準偏差です。 */
  private double standardDeviation;

  /**
   * 学生数を取得します。
   * 
   * @return 学生数
   */
  public int getStudentCount() {
    return this.studentCount;
  }

  /**
   * 学生数を設定します。
   * 
   * @param studentCount 学生数
   */
  public void setStudentCount(int studentCount) {
    this.studentCount = studentCount;
  }

  /**
   * 最大得点を取得します。
   * 
   * @return 最大得点
   */
  public int getMaximumScore() {
    return this.maximumScore;
  }

  /**
   * 最大得点を設定します。
   * 
   * @param maximumScore 最大得点
   */
  public void setMaximumScore(int maximumScore) {
    this.maximumScore = maximumScore;
  }

  /**
   * 平均点を取得します。
   * 
   * @return 平均点
   */
  public double getAverage() {
    return this.average;
  }

  /**
   * 平均点を設定します。
   * 
   * @param average 平均点
   */
  public void setAverage(double average) {
    this.average = average;
  }

  /**
   * 標準偏差を取得します。
   * 
   * @return 標準偏差
   */
  public double getStandardDeviation() {
    return this.standardDeviation;
  }

  /**
   * 標準偏差を設定します。
   * 
   * @param standardDeviation 標準偏差
   */
  public void setStandardDeviation(double standardDeviation) {
    this.standardDeviation = standardDeviation;
  }

}
