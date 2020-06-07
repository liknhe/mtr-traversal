/**
 * 
 */
package mvc;

/**
 * A controller for the MTR Information Centre system.
 * This controller includes the 4 features that the intended
 * prototype MTR Information Centre system is expected to have.
 * 
 * @author Sylvia Wong
 * @version 15/10/2017
 */
public interface Controller {
	
	/**
	 * Lists all termini in this MTR network.
	 * @return the name of all MTR stations that are the end point of the lines in the MTR network. 
	 */
	String listAllTermini();
	
	/**
	 * Lists the stations in their respective order in the specified MTR line.
	 * @param line	a specified line in the MTR network
	 * @return	a String representation of all stations in the specified MTR line.
	 */
	String listStationsInLine(String line);

	/**
	 * Lists the name of the line(s) that is/are directly connected with the specified MTR line.
	 * @param line	a specified line in the MTR network
	 * @return	a String representation of the name of the required line(s)
	 */
	String listAllDirectlyConnectedLines(String line);
	
	/**
	 * Lists a path between the specified stations.
	 * The path is represented as a sequence of the name of the stations between the specified stations. 
	 * @param stationA	the name of a station
	 * @param stationB	the name of another station
	 * @return	a String representation of a path between the specified stations
	 */
	String showPathBetween(String stationA, String stationB);
}
