package ua.kture.pi1311.dao.mssql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ua.kture.pi1311.dao.DirectionDAO;
import ua.kture.pi1311.electrotrain.parametres.MapperParameters;
import ua.kture.pi1311.entity.Direction;

public class MSsqlDirectionDAO implements DirectionDAO{
	private static final String SQL__FIND_DIRECTION_BY_ID = "SELECT * FROM Direction WHERE directionId=?;";
	private static final String SQL__UPDATE_DIRECTION = "UPDATE Direction SET [directionTitle]=? WHERE [directionId]=?;";
	private static final String SQL__INSERT_DIRECTION = "INSERT INTO Direction (directionTitle) VALUES (?);";
	private static final String SQL__DELETE_DIRECTION = "DELETE FROM Direction WHERE directionId=?";
	
	public boolean insertDirection(Direction direction) {
		Connection con = null;
		boolean result = false;
		try {
			con = MSsqlDAOFactory.getConnection();
			result = insertDirection(con, direction);
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
	public boolean insertDirection(Connection con, Direction direction) throws SQLException {
		PreparedStatement pstmt = null;
		boolean result = true;
		try {
			pstmt = con.prepareStatement(SQL__INSERT_DIRECTION,
					Statement.RETURN_GENERATED_KEYS);
			mapDirectionForInsert(direction, pstmt);
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


	private void mapDirectionForInsert(Direction direction,
			PreparedStatement pstmt) throws SQLException {
		pstmt.setString(1, direction.getDirectionTitle());
		
	}
	public Direction findDirection(int directionId) {
		Connection con = null;
		Direction direction = null;
		try {
			con = MSsqlDAOFactory.getConnection();
			direction = findDirection(con, directionId);
		} catch (SQLException e) {
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return direction;
	}
private Direction findDirection(Connection con, int directionId) throws SQLException {
	PreparedStatement pstmt = null;
	Direction direction = null;
	try {
		pstmt = con.prepareStatement(SQL__FIND_DIRECTION_BY_ID);
		pstmt.setInt(1, directionId);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			direction = unMapDirection(rs);
		}
		return direction;
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


	private Direction unMapDirection(ResultSet rs) throws SQLException {
		Direction direction = new Direction();
		direction.setDirectionId(rs.getInt(MapperParameters.DIRECTION_ID));
		direction.setDirectionTitle(rs.getString(MapperParameters.DIRECTION_TITLE));
		return direction;
}
	public boolean updateDirection(Direction direction) {
		Connection con = null;
		boolean updateResult = false;
		try {
			con = MSsqlDAOFactory.getConnection();
			updateResult = updateTrain(con, direction);
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

	private boolean updateTrain(Connection con, Direction direction) throws SQLException {
		boolean result;
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL__UPDATE_DIRECTION);
			mapDirectionForInsert(direction, pstmt);
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
	public boolean deleteDirection(int directionId) {
		Connection con = null;
		Boolean result = false;
		try {
			con = MSsqlDAOFactory.getConnection();
			deleteDirection(directionId, con);
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
	private void deleteDirection(int directionId, Connection con) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(SQL__DELETE_DIRECTION);
			pstmt.setLong(1, directionId);
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
}
