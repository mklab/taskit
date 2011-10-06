/**
 * 
 */
package org.mklab.taskit.server;

import org.mklab.taskit.server.domain.Account;
import org.mklab.taskit.server.domain.Lecture;
import org.mklab.taskit.server.domain.Report;
import org.mklab.taskit.shared.UserType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


/**
 * @author ishikura
 */
public abstract class DBInitializer {

  private final Map<String, String> idToPass = new TreeMap<String, String>();

  /**
   * {@link DBInitializer}オブジェクトを構築します。
   */
  public DBInitializer() {
    super();
  }

  /**
   * すべてのエンティティを登録します。
   */
  public void registerAll() {
    registerAccounts();
    registerLectures();
  }

  /**
   * アカウントをファイルにエクスポートします。
   */
  public void exportAccounts() {
    try {
      PrintWriter pw = new PrintWriter(new File("target/accounts.csv")); //$NON-NLS-1$
      for (Entry<String, String> account : this.idToPass.entrySet()) {
        pw.println(MessageFormat.format("\"{0}\",\"{1}\"", account.getKey(), account.getValue())); //$NON-NLS-1$
      }
      pw.close();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 講義を作成します。
   * 
   * @param title 講義タイトル
   * @param date 講義日
   * @return 講義
   */
  protected static Lecture createLecture(String title, Date date) {
    final Lecture l = new Lecture(date);
    l.setTitle(title);
    return l;
  }

  private final void registerAccounts() {
    for (final String id : getStudentIds()) {
      final String pass = Passwords.generatePassword(8);
      Account.registerNewAccount(id, pass, UserType.STUDENT);
      this.idToPass.put(id, pass);
    }
    for (final String id : getTaIds()) {
      final String pass = Passwords.generatePassword(8);
      Account.registerNewAccount(id, pass, UserType.TA);
      this.idToPass.put(id, pass);
    }
    for (final String id : getTeacherIds()) {
      final String pass = Passwords.generatePassword(8);
      Account.registerNewAccount(id, pass, UserType.TEACHER);
      this.idToPass.put(id, pass);
    }
  }

  /**
   * 講義を登録します。
   */
  final void registerLectures() {
    for (Lecture lecture : createLectures()) {
      lecture.persist();
    }
  }

  /**
   * 講義データを作成します。
   * 
   * @return 講義データ
   */
  protected abstract List<Lecture> createLectures();

  /**
   * 先生アカウントIDを取得します。
   * 
   * @return 先生アカウントのID
   */
  protected abstract String[] getTeacherIds();

  /**
   * TAアカウントのIDを取得します。
   * 
   * @return TAアカウントのID
   */
  protected abstract String[] getTaIds();

  /**
   * 生徒アカウントのIDを取得します。
   * 
   * @return 生徒アカウントのID
   */
  protected abstract String[] getStudentIds();

  /**
   * 課題を生成します。
   * 
   * @param lecture 講義
   * @param title タイトル
   * @param point 配点
   * @return 課題
   */
  protected static Report createReport(Lecture lecture, String title, int point) {
    Report report = new Report(title, point, lecture);
    lecture.getReports().add(report);
    return report;
  }

  /**
   * 日付文字列をパースします。
   * 
   * @param date 日付
   * @return 日付
   */
  protected static Date parseDate(String date) {
    final DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm"); //$NON-NLS-1$
    try {
      return format.parse(date);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

}