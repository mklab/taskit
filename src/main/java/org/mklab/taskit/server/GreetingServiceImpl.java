package org.mklab.taskit.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mklab.taskit.client.GreetingService;
import org.mklab.taskit.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

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

	public String greetServer(String input) throws IllegalArgumentException {
		try {
			final Connection con = DriverManager.getConnection(DB_URL, DB_USER,
					DB_PASSWORD);
			final Statement statement = con.createStatement();
			statement.execute("select * from test");

			final ResultSet resultSet = statement.getResultSet();
			StringBuffer sb = new StringBuffer();
			while (resultSet.next()) {
				final int hoge = resultSet.getInt(1);
				sb.append(hoge);
				sb.append(",");
			}

			con.close();
			return sb.toString();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
