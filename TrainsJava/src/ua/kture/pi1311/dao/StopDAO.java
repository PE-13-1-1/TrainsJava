package ua.kture.pi1311.dao;

import ua.kture.pi1311.entity.Stop;


public interface StopDAO {
	
	public boolean insertStop(Stop stop);
	
	public Stop findStop (int stopId);
	
	public boolean updateStop (Stop stop);
	
	public boolean deleteStop (int stopId);
	
	public boolean truncateStop();
}
