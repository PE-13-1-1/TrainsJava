package ua.kture.pi1311.dao.mssql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import ua.kture.pi1311.dao.TrainDAO;
import ua.kture.pi1311.electrotrain.parametres.MapperParameters;
import ua.kture.pi1311.entity.Train;

public class MSsqlTrainDAO implements TrainDAO {
	private static final String SQL__FIND_TRAIN_BY_ID = "SELECT * FROM Train WHERE trainId=?;";
	private static final String SQL__UPDATE_TRAIN = "UPDATE Train SET [startPoint]=?, [finalPoint]=?, [status]=?,"
			+ "[trainNumber]=?,[trainURL]=?, [scheduleId]=? WHERE [trainId]=?;";
	private static final String SQL__INSERT_TRAIN = "INSERT INTO Train (startPoint, finalPoint, status, "
			+ "trainNumber,trainURL) VALUES (?, ?, ?, ?, ?);";
	private static final String SQL__DELETE_TRAIN = "DELETE FROM Train WHERE trainId=?";
	private static final String SQL_TRUNCATE_TRAIN = "Truncate train;";
	private static final String SQL_GET_TRAIN_URL = "SELECT trainURL FROM Train";
	private static final String SQL_SELECT_ALL_TRAINS = "SELECT * FROM Train";
	
	public boolean insertTrain(Train train) {
		Connection con = null;
		boolean result = false;
		try {
			con = MSsqlDAOFactory.getConnection();
			result = insertTrain(con, train);
			con.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}

	public boolean insertTrain(Connection con, Train train) throws SQLException {
		PreparedStatement pstmt = null;
		boolean result = true;
		try {
			pstmt = con.prepareStatement(SQL__INSERT_TRAIN,
					Statement.RETURN_GENERATED_KEYS);
			mapTrainForInsert(train, pstmt);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			result = false;
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return result;
	}

	public Train findTrain(int trainId) {
		Connection con = null;
		Train train = null;
		try {
			con = MSsqlDAOFactory.getConnection();
			train = findTrain(con, trainId);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return train;
	}

	private Train findTrain(Connection con, int trainId) throws SQLException {
		PreparedStatement pstmt = null;
		Train train = null;
		try {
			pstmt = con.prepareStatement(SQL__FIND_TRAIN_BY_ID);
			pstmt.setLong(1, trainId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				train = unMapTrain(rs);
			}
			return train;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	public ArrayList<Train> findAllTrains() {
		Connection con = null;
		ArrayList<Train> trains = new ArrayList<Train>();
		try {
			con = MSsqlDAOFactory.getConnection();
			trains = findAllTrains(con);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return trains;
	}

	private ArrayList<Train> findAllTrains(Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		Train train = null;
		ArrayList<Train> trains = new ArrayList<Train>();
		try {
			pstmt = con.prepareStatement(SQL_SELECT_ALL_TRAINS);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				train = unMapTrain(rs);
				trains.add(train);
			}
			return trains;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	public boolean updateTrain(Train train) {
		Connection con = null;
		boolean updateResult = false;
		try {
			con = MSsqlDAOFactory.getConnection();
			updateResult = updateTrain(con, train);
		} catch (SQLException e) {
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return updateResult;
	}

	private boolean updateTrain(Connection con, Train train)
			throws SQLException {
		boolean result;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL__UPDATE_TRAIN);
			mapTrainForInsert(train, pstmt);
			int updatedRows = pstmt.executeUpdate();
			con.commit();
			result = updatedRows != 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return result;
	}

	public boolean deleteTrain(int trainId) {
		Connection con = null;
		Boolean result = false;
		try {
			con = MSsqlDAOFactory.getConnection();
			deleteTrain(trainId, con);
			result = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}

	private void deleteTrain(long id, Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL__DELETE_TRAIN);
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
					System.out.println(e.getMessage());
					throw e;
				}
			}
		}

	}
	public boolean truncateTrain() {
		Connection con = null;
		Boolean result = false;
		try {
			con = MSsqlDAOFactory.getConnection();
			truncateTrain(con);
			result = true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
	}

	private void truncateTrain(Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL_TRUNCATE_TRAIN);
			pstmt.execute();
			con.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
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

	private void mapTrainForInsert(Train train, PreparedStatement pstmt)
			throws SQLException {
		pstmt.setString(1, train.getStartPoint());
		pstmt.setString(2, train.getFinalPoint());
		pstmt.setString(3, train.getStatus());
		pstmt.setInt(4, train.getTrainNumber());
		pstmt.setString(5, train.getTrainUrl());
	}

	private Train unMapTrain(ResultSet rs) throws SQLException {
		Train train = new Train();
		train.setStartPoint(rs.getString(MapperParameters.TRAIN_START_POINT));
		train.setFinalPoint(rs.getString(MapperParameters.TRAIN_FINAL_POINT));
		train.setTrainId(rs.getInt(MapperParameters.TRAIN_ID));
		train.setStatus(rs.getString(MapperParameters.TRAIN_STATUS));
		train.setTrainNumber(rs.getInt(MapperParameters.TRAIN_NUMBER));
		train.setTrainUrl(rs.getString(MapperParameters.TRAIN_URL));
		return train;
	}

	public Set<String> getTrainURL() {
		Connection con = null;
		Set<String> urles = null;
		try {
			con = MSsqlDAOFactory.getConnection();
			urles = getTrainURL(con);
		} catch (SQLException e) {
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return urles;
	}
	private Set<String> getTrainURL(Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		 Set<String> urles = new HashSet<String>();
		try {
			pstmt = con.prepareStatement(SQL_GET_TRAIN_URL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String s = rs.getString("trainURL");
				urles.add(s);
			}
			return urles;
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
}
