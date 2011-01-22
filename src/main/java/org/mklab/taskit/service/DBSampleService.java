package org.mklab.taskit.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * データベースへのアクセスを行うサンプルサービスです。
 * 
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 21, 2011
 */
@RemoteServiceRelativePath("db_sample")
public interface DBSampleService extends RemoteService {

  /**
   * データベースにアクセスし、その結果を文字列で返します。
   * 
   * @return アクセスした結果の文字列
   */
  String accessToDatabase();

}
