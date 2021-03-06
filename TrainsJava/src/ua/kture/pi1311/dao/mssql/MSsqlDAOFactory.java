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
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	
	static String host = "jdbc:mysql://216.231.128.34:3306/monopol_train?characterEncoding=utf8";
    static String username = "monopol";
    static String password = "M8c=?rtHYs";
//	static String host = "jdbc:mysql://localhost/kharkovtrain?characterEncoding=utf8";
//	static String username = "root";
//	static String password = "";
    
	protected static synchronized Connection getConnection()
			throws SQLException {
		Connection con = null;
		try {
			Class.forName(DRIVER).newInstance();
			con = DriverManager.getConnection(host,username,password);
			con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			con.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
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
