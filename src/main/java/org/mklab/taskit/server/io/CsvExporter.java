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
package org.mklab.taskit.server.io;

import org.mklab.taskit.server.domain.Account;
import org.mklab.taskit.server.domain.Attendance;
import org.mklab.taskit.server.domain.Lecture;
import org.mklab.taskit.server.domain.Report;
import org.mklab.taskit.server.domain.Submission;
import org.mklab.taskit.server.domain.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;


/**
 * @author ishikura
 */
@SuppressWarnings("nls")
public class CsvExporter {

  CSVWriter writer;

  /**
   * {@link CsvExporter}オブジェクトを構築します。
   * 
   * @param writer 書き出し先
   */
  private CsvExporter(CSVWriter writer) {
    this.writer = writer;
  }

  /**
   * {@link CsvExporter}オブジェクトを構築します。
   * 
   * @param stream 書き出し先
   */
  public CsvExporter(OutputStream stream) {
    this(new CSVWriter(writerOf(stream)));
  }

  /**
   * {@link CsvExporter}オブジェクトを構築します。
   * 
   * @param output 書き出し先ファイル
   * @throws FileNotFoundException ファイルが存在しない場合
   */
  public CsvExporter(File output) throws FileNotFoundException {
    this(new FileOutputStream(output));
  }

  private static OutputStreamWriter writerOf(OutputStream stream) {
    try {
      return new OutputStreamWriter(stream, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 与えられたエンティティのリストを書き出します。
   * 
   * @param name エンティティの識別名
   * @param list エンティティのリスト
   */
  public void write(String name, List<?> list) {
    @SuppressWarnings("rawtypes")
    final TableSplitter splitter = TableSplitters.getSplitter(name);
    if (splitter == null) throw new IllegalArgumentException("unknown table : " + name);

    final String[] descriptions = splitter.getDescription();
    if (descriptions.length <= 1) throw new IllegalStateException();

    this.writer.writeNext(new String[] {name});
    this.writer.writeNext(descriptions);
    for (Object object : list) {
      @SuppressWarnings("unchecked")
      final String[] splitted = splitter.split(object);
      if (splitted.length != descriptions.length) throw new IllegalStateException();

      this.writer.writeNext(splitted);
    }

    this.writer.writeNext(new String[] {});
  }

  /**
   * フラッシュします。
   * 
   * @throws IOException フラッシュできなかった場合
   */
  public void close() throws IOException {
    this.writer.close();
  }

  /**
   * メインメソッドです。
   * 
   * @param args プログラム引数
   * @throws IOException エクスポート出来なかった場合
   */
  public static void main(String[] args) throws IOException {
    final CsvExporter exporter = new CsvExporter(new File("backup.csv"));
    exporter.write("Account", Account.getAllAccounts());
    exporter.write("User", User.getAllUsers());
    exporter.write("Lecture", Lecture.getAllLectures());
    exporter.write("Report", Report.getAllReports());
    exporter.write("Submission", Submission.getAllSubmissions());
    exporter.write("Attendance", Attendance.getAllAttendance());
    exporter.close();
  }

}
