package interfaces;

import dataStructures.*;
import dataStructures.exceptions.BoundsExistException;
import dataStructures.exceptions.InvalidBoundsException;
import enums.ServiceType;
import enums.StudentType;

public interface HomeWaySystem{

  /**
   * TO DO: Save currentArea before anything else
   * where the " " becomes "_"
   * 
   * @param top
   * @param left
   * @param bottom
   * @param right
   * @param name 
   * @throws BoundsExistException if An area with the same name already exists (the current one or saved in a file)
   * @throws InvalidBoundsException if if the top latitude is lower than or equal to the bottom latitude and the right longitude is lower than or equal to the left longitude
   */
  public void createNewArea(long top, long left, long bottom, long right, String name);

  /**
   * save current area to a text file 
   * the name of the file is the same as the name of the area, where the space character is replaced by ” ”
   */
  public void saveCurrentArea();

  /**
   * loads area into current Area from a file
   * @param name 
   * @throws NoFileException if there is no file in the root folder with the same name as the given area name
   */
  public void loadArea(String name);

  /**
   * ads service to currentArea
   * @param name 
   * @param value may be the capacity for eating and lodging services, and the student service discount for leisure service
   * @param price 
   * @param longitude 
   * @param latitude 
   * @param type 
   * @throws InvalidLocationException If the service location is outside the bounding rectangle defined for the system
   * @throws InvalidPriceException If the price is less or equal to 0
   * @throws InvalidDiscountException If it is a leisure service and the discount is less than 0 or greater than 100
   * @throws InvalidCapacityException If it is a eating or lodging service and the capacity is less or equal to 0
   */
  public void addServiceToCurrentArea(ServiceType type, long latitude, long longitude, int price, int value, String name);

  /**
   * 
   * @param type
   * @param name
   * @param country
   * @param home
   * @throws LodgingDoesNotExistException If the lodging does not exist in the system
   * @throws LodgingFullException If the lodging service is already full
   * @throws StudentExistsException If the student’s name already exists in the system
   */
  public void addStudentToCurrentArea(StudentType type, String name, String country, StudentType home);

/**
 * 
 * @return iterator with all the services in the currentArea
 */
  public Iterator<Service> getServicesIterator();



  /**
   * public helper method
   * @return currentArea
   */
  public Area getCurrentArea();

  /**
   * public helper method
   * @return true is currentArea != null
   */
  public boolean hasCurrentArea();



}