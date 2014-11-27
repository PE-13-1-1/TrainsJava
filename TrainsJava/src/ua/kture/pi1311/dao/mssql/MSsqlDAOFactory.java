package ua.kture.pi1311.dao.mssql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ua.kture.pi1311.dao.DAOFactory;
import ua.kture.pi1311.dao.DirectionDAO;
import ua.kture.pi1311.dao.StationDAO;
import ua.kture.pi1311.dao.StopDAO;
import ua.kture.pi1311.dao.TrainDAO;


public class MSsqlDAOFactory extends DAOFactory
{
	public MSsqlDAOFactory() {
		
	}
	private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String DB_URL = "jdbc:sqlserver://localhost; instanceName=SQLEXPRESS; database=KharkovTrain;user=sa; password=master;";
	protected static synchronized Connection getConnection()
			throws SQLException {
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(DB_URL);
			con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			con.setAutoCommit(false);
		} catch (SQLException e) {
		} catch (ClassNotFoundException e) {
		}
		return con;
	}
	@Override
	public StationDAO getStationDAO() {
		return new MSsqlStationDAO();
	}

	@Override
	public TrainDAO getTrainDAO() {
		return new MSsqlTrainDAO();
	}
	@Override
	public DirectionDAO getDirectionDAO() {
		return new MSsqlDirectionDAO();
	}
	@Override
	public StopDAO getStopDAO() {
		return new MSsqlStopDAO();
	}

}
