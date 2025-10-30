/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package interfaces;

import dataStructures.*;
import dataStructures.exceptions.*;
import enums.ServiceType;
import enums.StudentType;
import records.Evaluation;

public interface HomeWaySystem{

  /**
   * TO DO: Save currentArea before anything else
   * where the " " becomes "_"
   * 
   * @param top top point
   * @param left left point
   * @param bottom bottom point
   * @param right right point
   * @param areaName name of the area
   * @throws BoundsExistException if An area with the same name already exists (the current one or saved in a file)
   * @throws InvalidBoundsException if if the top latitude is lower than or equal to the bottom latitude and the right longitude is lower than or equal to the left longitude
   */
  public void createNewArea(long top, long left, long bottom, long right, String areaName) throws BoundsExistException, InvalidBoundsException;

  /**
   * save current area to a text file 
   * the name of the file is the same as the name of the area, where the space character is replaced by ” ”
   */
  public void saveCurrentArea();

  /**
   * loads area into current Area from a file
   * @param areaName name of the area
   * @throws NoFileException if there is no file in the root folder with the same name as the given area name
   */
  public void loadArea(String areaName) throws NoFileException;

  /**
   * ads service to currentArea
   * reminder to start with default evaluation of 4
   * @param serviceName name of the service
   * @param value may be the capacity for eating and lodging services, and the student service discount for leisure service
   * @param price price of the service
   * @param longitude longitude
   * @param latitude latitude
   * @param type type of service
   * @throws InvalidLocationException If the service location is outside the bounding rectangle defined for the currentArea
   * @throws InvalidPriceException If the price is less or equal to 0
   * @throws InvalidDiscountException If it is a leisure service and the discount is less than 0 or greater than 100
   * @throws InvalidCapacityException If it is a eating or lodging service and the capacity is less or equal to 0
   * @throws ServiceExistsException If the service name already exists in the current geography area
   */
  public void addServiceToCurrentArea(ServiceType type, long latitude, long longitude, int price, int value, String serviceName) throws 
  InvalidLocationException, InvalidPriceException, InvalidDiscountException, InvalidCapacityException, ServiceExistsException;

  /**
   * 
   * @return iterator with all the services in the currentArea
   */
  public Iterator<Service> getServicesIterator();

  /**
   * Adds a student to the currentArea
   * @param type type of the student
   * @param studentName name of the student
   * @param country country where the student is from
   * @param home lodging name to become student's home
   * @throws ServiceNotExistsException If the lodging does not exist in the currentArea
   * @throws InvalidCapacityException If the lodging service is already full
   * @throws StudentExistsException If the student’s name already exists in the currentArea
   */
  public void addStudentToCurrentArea(StudentType type, String studentName, String country, String home) throws ServiceNotExistsException, 
InvalidCapacityException, StudentExistsException;

  /**
   * Removes a student from the currentArea
   * @param studentName name of the student
   * @throws StudentNotExistsException If the student’s name does not exist in the currentArea
   */
  public void removeStudentFromCurrentArea(String studentName) throws StudentNotExistsException;

  /**
   * Lists all the students (if mode is "all") or those of a given country (otherwise)
   * @param mode "all" or <name of country>
   * @return an iterator with all students or those of a given country, defined by mode parameter
   */
  public Iterator<Student> getStudentsIterator(String mode);

  /**
   * Changes the location of a student to a eating service, or leisure service
   * @param studentName the name of the student
   * @param serviceName student's new home
   * @throws ServiceNotExistsException If the name of the location where the student is going is not known to the currentArea
   * @throws StudentNotExistsException If the student’s name does not exist in the currentArea
   * @throws InvalidServiceTypeException If the location where the student is going is not an eating or a leisure service
   * @throws RedundantMoveException If the student decides to go to the location where they are already
   * @throws InvalidCapacityException If is an eating service and it is already full
   */
  public void goToLocation(String studentName, String serviceName) throws ServiceNotExistsException, StudentNotExistsException, 
  InvalidServiceTypeException, RedundantMoveException, InvalidCapacityException;

  /**
   * Changes the home of a student 
   * Also changes student currentLocation to serviceName parameter
   * @param studentName the name of the student
   * @param serviceName student's new home
   * @throws ServiceNotExistsException If the lodging service name does not exist in the currentArea
   * @throws StudentExistsException If the student’s name already exists in the currentArea
   * @throws RedundantMoveException If the student’s home is already the one provided in the command
   * @throws InvalidCapacityException If the lodging service is already full
   * @throws ThriftyException If a thrifty student tries to move to a same price or more expensive lodging
   */
  public void moveOut(String studentName, String serviceName) throws ServiceNotExistsException, StudentExistsException, RedundantMoveException, 
  InvalidCapacityException, ThriftyException;

  /**
   * Creates and returns a TwoWayIterator of users in a service
   * @param order > - from oldest to newest based on on insertion order || < - from newest to oldest based on insertion order into service
   * @param serviceName the name of the service
   * @return an Iterator of users of a given service ordered by insertion order 
   * @throws ServiceNotExistsException If the service name does not exist in the currentArea
   * @throws InvalidServiceTypeException If the given service is neither eating nor lodging service
   */
  public TwoWayIterator<Student> getUsersIterator(char order, String serviceName) throws ServiceNotExistsException, InvalidServiceTypeException;

  /**
   * 
   * @param studentName name of the student
   * @return service where student currently is
   * @throws StudentNotExistsException If the student’s name does not exist in the currentArea
   */
  public Service getStudentLocation(String studentName) throws StudentNotExistsException;

  /**
   * gets an iterator of the locations visited and stored by one student sorted by visiting order
   * @param studentName name of the student
   * @return an iterator of the services visited by the given student sorted by visiting order
   * @throws StudentNotExistsException If the student’s name does not exist in the currentArea
   * @throws ThriftyException If the student is thrifty
   */
  public Iterator<Service> getVisitedServicesIterator(String studentName) throws StudentNotExistsException, ThriftyException;

  /**
   * Evaluates a service
   * @param serviceName the name of the service
   * @param evaluation (star, description)
   * @throws ServiceNotExistsException If the service’s name does not exist in the currentArea
   */
  public void evaluateService(String serviceName, Evaluation evaluation) throws ServiceNotExistsException;

  /**
   * 
   * @return an iterator of all services ordered by star
   */
  public Iterator<Service> getServiceByEvaluationIterator();

  /**
   * Returns an iterator with  the service(s) of the indicated type with the given score that are closer to the student location
   * @param type ServiceType object or null
   * @param star evaluation
   * @param studentName name of the student
   * @return an iterator with  the service(s) of the indicated type with the given score that are closer to the student location (manhattan distance)
   * @throws StudentNotExistsException If the student’s name does not exist in the currentArea
   * @throws InvalidServiceTypeException If the service type is not ”eating”, ”lodging” or ”leisure” (to test this do if(type == null) throw InvalidServiceType)
   * @throws ServiceNotExistsException If no services of the type exist
   * @throws NoServicesOfTypeException If no services of the type exist with n star average
   */
  public Iterator<Service> getServiceOfTypeWithScore(ServiceType type, int star, String studentName) throws StudentNotExistsException, InvalidServiceTypeException, 
  ServiceNotExistsException, NoServicesOfTypeException;

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
   * @param studentName name of the student
   * @param type type the service
   * @return the best service (with best average) of the type (for bookish and outgoing students) OR the less expensive one (for thrifty students). 
   * @throws StudentNotExistsException If the student’s name does not exist in the currentArea
   * @throws NoServicesOfTypeException If no services of the type exist
   */
  public Service findRelevantService(String studentName, ServiceType type) throws StudentNotExistsException, NoServicesOfTypeException;


  /**
   * public helper method. 
   * @return currentArea
   */
  public Area getCurrentArea();

  /**
   * public helper method.
   * @return true is currentArea != null
   */
  public boolean hasCurrentArea();

  /**
   * public helper method.
   * If the student is thrifty, and the command is moving their location to a eating service more expensive than the cheapest they have visited so far, 
   * the student is distracted
   * @param studentName name of the student
   * @param serviceName name of the service
   * @return true if the student is distracted
   */
  public boolean isStudentDistracted(String studentName, String serviceName);


}