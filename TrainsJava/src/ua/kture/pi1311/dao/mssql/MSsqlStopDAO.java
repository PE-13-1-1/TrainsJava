package ua.kture.pi1311.dao.mssql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ua.kture.pi1311.dao.StopDAO;
import ua.kture.pi1311.electrotrain.parametres.MapperParameters;
import ua.kture.pi1311.entity.Stop;
import ua.kture.pi1311.entity.Train;

public class MSsqlStopDAO  implements StopDAO{
	private static final String SQL__FIND_STOP_BY_ID = "SELECT * FROM Stop WHERE stopId=?;";
	private static final String SQL__UPDATE_STOP = "UPDATE Stop SET [stationId]=? [timeArrival]=? [timeDeparture]=? [trainId]=? [staying]=? WHERE [stopId]=?;";
	private static final String SQL__INSERT_STOP = "INSERT INTO Stop (stationId, timeArrival, timeDeparture, "
			+ "staying,trainId) VALUES (?, ?, ?, ?, ?);";
	private static final String SQL__DELETE_STOP = "DELETE FROM Stop WHERE stopId=?";
	private static final String SQL_TRUNCATE_STOP = "USE KharkovTrain;TRUNCATE TABLE dbo.Stop; ";
	public boolean insertStop(Stop stop) {
		Connection con = null;
		boolean result = false;
		try {
			con = MSsqlDAOFactory.getConnection();
			result = insertStop(con, stop);
			con.commit();
		} catch (SQLException e) {
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return result;
	}
	public boolean insertStop(Connection con, Stop stop) throws SQLException {
		PreparedStatement pstmt = null;
		boolean result = true;
		try {
			pstmt = con.prepareStatement(SQL__INSERT_STOP,
					Statement.RETURN_GENERATED_KEYS);
			mapStopForInsert(stop, pstmt);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			result = false;
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}


	public Stop findStop(int stopId) {
		Connection con = null;
		Stop train = null;
		try {
			con = MSsqlDAOFactory.getConnection();
			train = findStop(con, stopId);
		} catch (SQLException e) {
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return train;
	}
	private Stop findStop(Connection con, int stopId) throws SQLException {
		PreparedStatement pstmt = null;
		Stop train = null;
		try {
			pstmt = con.prepareStatement(SQL__FIND_STOP_BY_ID);
			pstmt.setLong(1, stopId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				train = unMapStop(rs);
			}
			return train;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public boolean updateStop(Stop stop) {
		Connection con = null;
		boolean updateResult = false;
		try {
			con = MSsqlDAOFactory.getConnection();
			updateResult = updateStop(con, stop);
		} catch (SQLException e) {
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return updateResult;
	}
	private boolean updateStop(Connection con, Stop stop)
			throws SQLException {
		boolean result;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL__UPDATE_STOP);
			mapStopForInsert(stop, pstmt);
			int updatedRows = pstmt.executeUpdate();
			con.commit();
			result = updatedRows != 0;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}
	public boolean deleteStop(int stopId) {
		Connection con = null;
		Boolean result = false;
		try {
			con = MSsqlDAOFactory.getConnection();
			deleteStop(stopId, con);
			result = true;
		} catch (SQLException e) {
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return result;
	}
	private void deleteStop(long id, Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL__DELETE_STOP);
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					throw e;
				}
			}
		}

	}
	public boolean truncateStop() {
		Connection con = null;
		Boolean result = false;
		try {
			con = MSsqlDAOFactory.getConnection();
			truncateStop(con);
			result = true;
		} catch (SQLException e) {
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return result;
	}
	private void truncateStop(Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_TRUNCATE_STOP);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					throw e;
				}
			}
		}

	}
	private void mapStopForInsert(Stop stop, PreparedStatement pstmt)
			throws SQLException {
		pstmt.setInt(1, stop.getStationId());
		pstmt.setDate(2, stop.getTimeArrival());
		pstmt.setDate(3, stop.getTimeDeparture());
		pstmt.setInt(4, stop.getStaying());
		pstmt.setInt(5, stop.getTrainId());
	}

	private Stop unMapStop(ResultSet rs) throws SQLException {
		Stop stop = new Stop();
		stop.setTrainId(rs.getInt(MapperParameters.STOP_LIST_ID));
		stop.setStationId(rs.getInt(MapperParameters.STOP_STATION_ID));
		stop.setStaying(rs.getInt(MapperParameters.STOP_STAYING));
		stop.setStopId(rs.getInt(MapperParameters.STOP_ID));
		stop.setTimeArrival(rs.getDate(MapperParameters.STOP_TIME_ARRIVAL));
		stop.setTimeDeparture(rs.getDate(MapperParameters.STOP_TIME_DEPARTURE));
		return stop;
	}
}
