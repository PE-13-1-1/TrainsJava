package ua.kture.pi1311.dao;

import java.util.ArrayList;
import java.util.Set;

import ua.kture.pi1311.entity.Train;

public interface TrainDAO {
	
	public boolean insertTrain(Train train);
	
	public Train findTrain (int trainId);
	
	public boolean updateTrain (Train train);
	
	public boolean deleteTrain (int trainId);
	
	public boolean truncateTrain();
	
	public Set<String> getTrainURL();
	
	public ArrayList<Train> findAllTrains();
	
}
