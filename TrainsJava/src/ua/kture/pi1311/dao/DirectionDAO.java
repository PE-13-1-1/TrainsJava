package ua.kture.pi1311.dao;

import ua.kture.pi1311.entity.Direction;


public interface DirectionDAO {
	public boolean insertDirection(Direction direction);
	
	public Direction findDirection (int directionId);
	
	public boolean updateDirection(Direction direction);
	
	public boolean deleteDirection (int directionId);
}
