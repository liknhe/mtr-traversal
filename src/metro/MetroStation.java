package metro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * A node to represent a metro station within the MTR.
 * Additionally, it also holds which line/s this station is on and all connecting stations across the map
 * 
 * @author Li He, Hannah Miller, Ben Withington
 * @version 12/12/2017
 */

public class MetroStation {

	//String name of station
	private String name;
	//Map to link station name with line
	private Map<String, MetroLine> lines;
	//array list of adjacent Station objects
	private ArrayList<MetroStation> connectingStations;
	//queue of the current stations adjacent stations
	private Queue<MetroStation> potentialStations;
	//queue to back up potential storage for later use, keeps complexity O(n)
	private Queue<MetroStation> stationStorage;
	// To see if a station is visited or not
	private boolean isVisited;
	
	/**
	 * Constructs a new {@link MetroStation} with a specified string station name
	 * Sets visited boolean to false, creates a hashMap, ArrayList and Linkedlist 
	 * @param name, of the station
	 */
	public MetroStation(String name) {
		this.name = name;
		lines = new HashMap<String, MetroLine>();
		isVisited = false;
		connectingStations = new ArrayList<MetroStation>();
		potentialStations = new LinkedList<MetroStation>();
		stationStorage = new LinkedList<MetroStation>();
	}

	/**
	 * Method to convert station name into a String
	 * @return name of station in string form
	 */
	public String toString() {
		return name;
	}

	/**
	 * Method to search the hash map to get the lines connected
	 * @return the line connecting to a station
	 * @see MetroLine
	 */
	public Map<String, MetroLine> getLines(){
		return lines;
	}

	/**
	 * Method to check whether a potential station contains a station
	 * if not add the station as a potential station 
	 */
	public void nextPotentialStations(){
		for(MetroStation station : connectingStations){
			if(!potentialStations.contains(station)){
				potentialStations.add(station);
				stationStorage.add(station);
			}
		}
	}
		
	/**
	 * Method to return a queue of potential stations
	 * @return a queue of potential stations
	 */
	public Queue<MetroStation> potentialStations(){
		return potentialStations;
	}

	public void resetPotentialStations(){
		potentialStations = stationStorage; 
	}
	/**
	 * Method to add the station specified in parameter to the front of array list containing connecting Stations
	 * @param station name of type Station
	 */
	public void addNextStation(MetroStation station){
		connectingStations.add(station);
	}

	/**
	 * Method to add a {@link line}line of type string as the key line in the map
	 * @param line name of type Line
	 * @see MetroLine
	 */
	public void addLine(MetroLine line){
		lines.put(line.toString(), line);
	}

	/**
	 * Method to check if station has been visited
	 * @return boolean value if been visited or not
	 */
	public boolean isVisited(){
		return isVisited;
	}
	
	/**
	 * Mutator to mark a station has been visited
	 */
	public void markVisited(){
		isVisited = true;
	}
	
	/**
	 * Mutator method to reset visited, in preparation of the next execution
	 */
	public void resetVisited(){
		isVisited = false;
	}
	/**
	 * Method to add the station specified in parameter to the front of array list containing connecting Stations
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Method to compare an obj to see if it is a station
	 * @param obj to see if it is an instance of a station
	 * @return if station name in constructor is equal to the parameter returns true
	 * @return if station name in constructor is not equal to parameter returns false
	 * @return if the obj in parameter is not an instance of a station returns false
	 * @return if station name doesn't exist but station other does then return false
	 * @return if station name doesn't equal station other return false
	  
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MetroStation))
			return false;
		MetroStation other = (MetroStation) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}