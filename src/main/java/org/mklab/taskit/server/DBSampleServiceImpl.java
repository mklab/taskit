package org.mklab.taskit.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mklab.taskit.shared.service.DBSampleService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * @author Yuhi Ishikura
 * @version $Revision$, Jan 21, 2011
 */
public class DBSampleServiceImpl extends RemoteServiceServlet implements DBSampleService {

  /** for serialization. */
  private static final long serialVersionUID = -7991171859239909416L;

  static {
    try {
      Class.forName("com.mysql.jdbc.Driver"); //$NON-NLS-1$
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private static final String DB_URL = "jdbc:mysql://localhost/taskit"; //$NON-NLS-1$
  private static final String DB_USER = "root"; //$NON-NLS-1$
  private static final String DB_PASSWORD = ""; //$NON-NLS-1$

  /**
   * @see org.mklab.taskit.shared.service.DBSampleService#accessToDatabase()
   */
  @Override
  public String accessToDatabase() {
    try {
      final Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
      final Statement statement = con.createStatement();
      statement.execute("select * from test"); //$NON-NLS-1$

      final ResultSet resultSet = statement.getResultSet();
      StringBuffer sb = new StringBuffer();
      while (resultSet.next()) {
        final int hoge = resultSet.getInt(1);
        sb.append(hoge);
        sb.append(","); //$NON-NLS-1$
      }

      con.close();
      return sb.toString();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
