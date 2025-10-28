/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package interfaces;

import dataStructures.*;
import dataStructures.exceptions.BoundsExistException;
import dataStructures.exceptions.InvalidBoundsException;
import dataStructures.exceptions.StudentNotExistsException;
import enums.ServiceType;
import enums.StudentType;
import java.net.UnknownServiceException;
import records.Evaluation;

public interface HomeWaySystem{

  /**
   * TO DO: Save currentArea before anything else
   * where the " " becomes "_"
   * 
   * @param top
   * @param left
   * @param bottom
   * @param right
   * @param areaName 
   * @throws BoundsExistException if An area with the same name already exists (the current one or saved in a file)
   * @throws InvalidBoundsException if if the top latitude is lower than or equal to the bottom latitude and the right longitude is lower than or equal to the left longitude
   */
  public void createNewArea(long top, long left, long bottom, long right, String areaName);

  /**
   * save current area to a text file 
   * the name of the file is the same as the name of the area, where the space character is replaced by ” ”
   */
  public void saveCurrentArea();

  /**
   * loads area into current Area from a file
   * @param areaName 
   * @throws NoFileException if there is no file in the root folder with the same name as the given area name
   */
  public void loadArea(String areaName);

  /**
   * ads service to currentArea
   * @param serviceName 
   * @param value may be the capacity for eating and lodging services, and the student service discount for leisure service
   * @param price 
   * @param longitude 
   * @param latitude 
   * @param type 
   * @throws InvalidLocationException If the service location is outside the bounding rectangle defined for the currentArea
   * @throws InvalidPriceException If the price is less or equal to 0
   * @throws InvalidDiscountException If it is a leisure service and the discount is less than 0 or greater than 100
   * @throws InvalidCapacityException If it is a eating or lodging service and the capacity is less or equal to 0
   * @throws ServiceAlreadyExistsException If the service name already exists in the current geography area
   */
  public void addServiceToCurrentArea(ServiceType type, long latitude, long longitude, int price, int value, String serviceName);

  /**
   * 
   * @return iterator with all the services in the currentArea
   */
  public Iterator<Service> getServicesIterator();

  /**
   * 
   * @param type
   * @param studentName
   * @param country
   * @param home
   * @throws LodgingDoesNotExistException If the lodging does not exist in the currentArea
   * @throws LodgingFullException If the lodging service is already full
   * @throws StudentExistsException If the student’s name already exists in the currentArea
   */
  public void addStudentToCurrentArea(StudentType type, String studentName, String country, ServiceType home);

  /**
   * 
   * @param studentName
   * @throws StudentNotExistsException If the student’s name does not exist in the system
   */
  public void removeStudentFromCurrentArea(String studentName);

  /**
   * 
   * @param mode "all" or <name of country>
   * @return an iterator with all students or those of a given country, defined by mode parameter
   */
  public Iterator<Student> getStudentsIterator(String mode);

  /**
   * 
   * @param studentName the name of the student
   * @param serviceName student's new home
   * @throws UnknownServiceException If the name of the location where the student is going is not known to the currentArea
   * @throws StudentNotExistsException If the student’s name does not exist in the currentArea
   * @throws InvalidServiceTypeException If the location where the student is going is not an eating or a leisure service
   * @throws AlreadyThereException If the student decides to go to the location where they are already
   * @throws ServiceFullException If is an eating service and it is already full
   */
  public void goToLocation(String studentName, String serviceName);

  /**
   * Changes the home of a student 
   * Also changes student currentLocation to serviceName parameter
   * @param studentName the name of the student
   * @param serviceName student's new home
   * @throws LodgingDoesNotExistException If the lodging service name does not exist in the currentArea
   * @throws StudentExistsException If the student’s name already exists in the currentArea
   * @throws AlreadyStudentHomeException If the student’s home is already the one provided in the command
   * @throws ServiceFullException If the lodging service is already full
   * @throws ThriftyException If a thrifty student tries to move to a same price or more expensive lodging
   */
  public void moveOut(String studentName, String serviceName);

  /**
   * Creates and returns an Iterator of users of a given service ordered bythe order parameter
   * @param order > - from oldest to newest based on on insertion order || < - from newest to oldest based on insertion order into service
   * @param serviceName the name of the service
   * @return an Iterator of users of a given service ordered bythe order parameter
   * @throws ServiceNotExistsException If the service name does not exist in the currentArea
   * @throws InvalidServiceTypeException If the given service is neither eating nor lodging service
   */
  public Iterator<Student> getUsersIterator(char order, String serviceName);

  /**
   * 
   * @param studentName 
   * @return service where student currently is
   * @throws StudentNotExistsException If the student’s name does not exist in the currentArea
   */
  public Service getStudentLocation(String studentName);

  /**
   * 
   * @param studentName 
   * @return an iterator of the services visited by the given student sorted by visiting order
   * @throws StudentNotExistsException If the student’s name does not exist in the currentArea
   * @throws ThriftyException If the student is thrifty
   */
  public Iterator<Service> getVisitedServicesIterator(String studentName);

  /**
   * 
   * @param serviceName the name of the service
   * @param evaluation (star, description)
   * @throws ServiceNotExistsException If the service’s name does not exist in the currentArea
   */
  public void evaluateService(String serviceName, Evaluation evaluation);

  /**
   * 
   * @return an iterator of all services ordered by star
   */
  public Iterator<Service> getServiceByEvaluationIterator();

  /**
   * 
   * @param type ServiceType object or null
   * @param star evaluation
   * @return an iterator with  the service(s) of the indicated type with the given score that are closer to the student location (manhattan distance)
   * @throws StudentNotExistsException If the student’s name does not exist in the currentArea
   * @throws InvalidServiceTypeException If the service type is not ”eating”, ”lodging” or ”leisure” (to test this do if(type == null) throw InvalidServiceType)
   * @throws NoServicesOfTypeException If no services of the type exist
   * @throws NoServicesOfTypeWithStarException If no services of the type exist with n star average
   */
  public Iterator<Service> getServiceOfTypeWithScore(ServiceType type, int star);

  /**
   * Returns an iterator with all the servies that have at least one evaluation description with given tag (case-insensitive, that is, ”Good” is the same 
   * as ”good” in order of insertion services
   * @param tag string to be searched for in each description
   * @return an iterator with all the servies that have at least one evaluation description with given tag in order of insertion services
   */
  public Iterator<Service> getTaggedServicesIterator(String tag);

  /**
   * Finds the most relevant service of a certain type, for a specific student
   * the system may provide the best service (with best average) of the type (for bookish and outgoing students)
   * or the less expensive one (for thrifty students). 
   * the system should provide the service with more time in this average or the first service inserted in the system, respectively
   * @param studentName
   * @param type
   * @return
   * @throws StudentNotExistsException If the student’s name does not exist in the currentArea
   * @throws NoServicesOfTypeException If no services of the type exist
   */
  public Service findRelevantService(String studentName, ServiceType type);


  /**
   * public helper method. 
   * @return currentArea
   */
  public Area getCurrentArea();

  /**
   * public helper method
   * @return true is currentArea != null
   */
  public boolean hasCurrentArea();


  










}