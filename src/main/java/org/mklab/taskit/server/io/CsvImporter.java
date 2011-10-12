/**
 * 
 */
package org.mklab.taskit.server.io;

import org.mklab.taskit.server.domain.Account;
import org.mklab.taskit.server.domain.Attendance;
import org.mklab.taskit.server.domain.Lecture;
import org.mklab.taskit.server.domain.Report;
import org.mklab.taskit.server.domain.Submission;
import org.mklab.taskit.server.domain.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;


/**
 * @author ishikura
 */
public class CsvImporter {

  CSVReader reader;

  private CsvImporter(CSVReader reader) {
    this.reader = reader;
  }

  /**
   * {@link CsvImporter}オブジェクトを構築します。
   * 
   * @param is 入力ストリーム
   */
  public CsvImporter(InputStream is) {
    this(new CSVReader(readerOf(is)));
  }

  /**
   * {@link CsvImporter}オブジェクトを構築します。
   * 
   * @param in 入力ファイル
   * @throws FileNotFoundException ファイルが存在しない場合
   */
  public CsvImporter(File in) throws FileNotFoundException {
    this(new FileInputStream(in));
  }

  private static Reader readerOf(InputStream is) {
    try {
      return new InputStreamReader(is, "UTF-8"); //$NON-NLS-1$
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 次のテーブルを読み込みます。
   * 
   * @return 読込結果
   * @throws IOException 読み込みに失敗した場合
   */
  @SuppressWarnings("unchecked")
  public <E> List<E> readNext() throws IOException {
    String[] next = this.reader.readNext();
    if (next.length != 1) throw new IllegalArgumentException();

    final String name = next[0];
    final TableSplitter<?> splitter = TableSplitters.getSplitter(name);
    if (splitter == null) throw new IllegalStateException("unknown table : " + name); //$NON-NLS-1$

    final String[] descriptions = this.reader.readNext();
    if (Arrays.equals(splitter.getDescription(), descriptions) == false) {
      throw new IllegalStateException(MessageFormat.format("Descriptions of {0} was unexpected : {1}", name, Arrays.toString(descriptions))); //$NON-NLS-1$
    }
    if (descriptions.length <= 1) throw new IllegalArgumentException();

    final List<E> list = new ArrayList<E>();
    while (true) {
      next = this.reader.readNext();
      if (next.length == 1 && next[0].length() == 0) break;
      list.add((E)splitter.merge(next));
    }

    return list;
  }

  /**
   * メインメソッドです。
   * 
   * @param args プログラム引数
   * @throws IOException インポート出来なかった場合
   */
  public static void main(String[] args) throws IOException {
    /*
     * 基本的にインポートできるが、IDに問題がある。
     * ほとんどのIDの生成ルールでIDENTITYを指定しているため
     * IDを設定してもそれを上書きして設定されてしまう。
     */
    final CsvImporter importer = new CsvImporter(new File("backup.csv")); //$NON-NLS-1$
    for (Account account : importer.<Account> readNext()) {
      account.update();
    }

    for (User user : importer.<User> readNext()) {
      user.update();
    }

    for (Lecture lecture : importer.<Lecture> readNext()) {
      lecture.update();
    }

    for (Report report : importer.<Report> readNext()) {
      report.update();
    }

    for (Submission submission : importer.<Submission> readNext()) {
      submission.update();
    }

    for (Attendance attendance : importer.<Attendance> readNext()) {
      attendance.update();
    }
  }
}
