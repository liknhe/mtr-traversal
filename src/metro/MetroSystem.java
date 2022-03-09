package metro;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for storing different metro stations and metro lines
 * Hash maps are used to map the MTR network
 *
 * @author Ben Withington, Li He, Hannah Miller
 * @version 12/12/2017
 */

public class MetroSystem {

	//map of lines
	private Map<String, MetroLine> lines;
	//map of stations
	private Map<String, MetroStation> stations;

	public MetroSystem(){
		lines = new HashMap<>();
		stations = new HashMap<>();
	}

	/**
	 * Adds a {@link MetroLine} with a specified string line name to the map as a key
	 * @param
	 * @see MetroLine
	 */
	public void addLine(MetroLine line) {
		lines.put(line.toString(), line);
	}

	/**
	 * method to return the length of the lines
	 * @return size of lines in the map
	 * @see MetroLine
	 */
	public int size() {
		return lines.size();
	}

	/**
	 * method to get a station name from the map
	 * @param name of the station
	 * @return name of the {@link MetroStation}
	 * @see MetroStation
	 */
	public MetroStation getStation(String name){
		return stations.get(name);
	}
	
	/**
	 * method to get a station name from the map
	 * @param station name of the station
	 * @see MetroStation
	 */
	public void addStation(MetroStation station){
		stations.put(station.toString(), station);
	}

	/**
	 * Method to search hash map for a line
	 * @param name, of the metro line
	 * @return the line's name
	 */
	public MetroLine getOneLine(String name) {
		return lines.get(name);
	}

	/**
	 * Method to search hash map for a line
	 * @return the line's name
	 * @see MetroLine
	 */
	public Map<String, MetroLine> getLines() {
		return lines;
	}
	
	/**
	 * Method to search hash map for a station
	 *@return the station's name
	 *@see MetroStation
	 */
	public Map<String, MetroStation> getStations() {
		return stations;
	}

}
