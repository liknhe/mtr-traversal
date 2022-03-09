package metro;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class models a metro line by storing each station using a linked-list.
 * Additionally, It stores any intercepting metro lines
 * 
 * @author Ben Withington, Li He, Hannah Miller
 * @version 12/12/2017
 *
 */
public class MetroLine {

	//Name of Metro Line
	private final String name;
	//list of metro station
	private final List<MetroStation> metroLine;
	//first station in line
	private MetroStation firstStation;
	//last station in line
	private MetroStation lastStation;
	//number of metro stations on the metro line
	private int size;

	// Storing connecting station with lines
	private final Map<String, MetroLine> connections;
	// Storing connecting station with lines
	private final Map<String, MetroStation> stations;

	/**
	 * Constructs a new {@link MetroLine} with a specified string line name
	 * sets size to zero, makes the first and last line null, creates a linked list and 2 hash maps
	 * @param name of the Metro line
	 */
	public MetroLine(String name) {
		this.name = name;
		size = 0;
		lastStation = null;
		firstStation = null;
		metroLine = new LinkedList<>();
		connections = new HashMap<>();
		stations = new HashMap<>();
	}

	/**
	 * Method to add a station to the linked list line, checks to see if first is null and makes it equal to the
	 * station in parameter, adds this to the line linked list and set it to the last in the list.
	 * increments the size to keep track of number in list
	 * @param station variable of type {@link MetroStation}
	 * @see MetroStation
	 */
	public void addStation(MetroStation station) {
		
		// creates the first station of a line
		if (firstStation == null) {
			firstStation = station;
			metroLine.add(firstStation);
			lastStation = firstStation;
		} 
		else 
		// creates the rest of the stations
		{
			metroLine.add(station);
			lastStation = station;
		}
		
		//adds all station into a hashmap for later use
		stations.put(station.toString(), station);
		size++;
	}

	/**
	 * Method to return the size of the linked list
	 * @return size of linked list
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Method to get the last station in the line
	 * @return name of station that's last in the line
	 * @see MetroStation
	 */
	public MetroStation getLast() {
		return lastStation;
	}

	/**
	 * Method to get the first station in the line
	 * @return name of station that's first in the line
	 * @see MetroStation
	 */
	public MetroStation getFirst() {
		return firstStation;
	}

	/**
	 * Method to get the name of the line
	 * @return name of line in string form
	 */
	public String toString() {
		return name;
	}

	/**
	 * Method to add connecting stations, sets a {@link MetroStation} type previous and next to null
	 * checks to see if there is a station when the iterator gets the next element, if it does: sets it to previous and
	 * sets next station to the one after the current station
	 * @see MetroStation
	 * 
	 */
	public void addConnectingStations(){
		Iterator<MetroStation> stationItr = iterator();
		MetroStation prev;
		MetroStation next = null;
		
		// adds all adjacent stations of each station
		if(stationItr.hasNext()) {
			prev = stationItr.next();
			if(stationItr.hasNext()) {
				next = stationItr.next();
				prev.addNextStation(next);
				next.addNextStation(prev);
			}
			while(stationItr.hasNext()) {
				prev = next;
				next = stationItr.next();
				prev.addNextStation(next);
				next.addNextStation(prev);
			}
		}
	}	
	
	/**
	 * Method to connect lines together
	 * @param map connecting line string with line object
	 */
	public void addConnections(Map<String, MetroLine> map){
		connections.putAll(map);
	}

	/**
	 * Method to get the connections from the method above
	 * @return the line connections
	 */
	public Map<String, MetroLine> getConnections(){
		return connections;
	}

	/**
	 * Method to get the {@link MetroStation}s in the line
	 * @return {@link MetroStation}s
	 * @see MetroStation
	 */
	public Map<String, MetroStation> getAllStations(){
		return stations;
	}

	/**
	 * @return an iterator to return lines
	 * @see MetroStation
	 */
	public Iterator<MetroStation> iterator(){
		return metroLine.iterator();
	}
}

