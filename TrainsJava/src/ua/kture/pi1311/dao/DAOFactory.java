package ua.kture.pi1311.dao;

import ua.kture.pi1311.dao.mssql.MSsqlDAOFactory;


public abstract class DAOFactory {

	public static final int MSSQL = 1;
	
	public static final int SQLite = 2;
	
	public abstract StationDAO getStationDAO();
	
	public abstract TrainDAO getTrainDAO();
	
	public abstract DirectionDAO getDirectionDAO();
	
	public abstract StopDAO getStopDAO();
	
	public static DAOFactory getDAOFactory(int whichFactory) {

		switch (whichFactory) {
		case MSSQL:
			return new MSsqlDAOFactory();
		case SQLite:
			return null;
		default:
			return null;
		}
	}
	
}
