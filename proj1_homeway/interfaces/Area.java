/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package interfaces;
import dataStructures.Iterator;
import enums.ServiceType;
import enums.StudentType;
import java.io.Serializable;

public interface Area extends Serializable {
  
  /**
   * returns student name
   * @return name of this area
   */
  public String getAreaName();

  /**
   * returns the service type
   * @pre hasService(serviceName)
   * @param serviceName name of the service
   * @return the type of the service
   */
  public ServiceType getServiceType(String serviceName);

  /**
   * gets the type of the student
   * @param studentName name of the student
   * @return type of the student 
   */
  StudentType getStudentType(String studentName);

  /**
   * returns the student location
   * @param studentName name of the student
   * @return the student location
   */
  public Service getStudentLocation(String studentName);

  /**
   * returns the student's home
   * @param studentName student name
   * @return the student's home
   */
  public LodgingService getStudentHome(String studentName);

  /**
   * adds a new service to this area
   * @param newService service to be added
   */
  public void addServiceHere(ServiceType type, long latitude, long longitude, int price, int value, String serviceName);
  
  /**
   * adds a student to current geographical area
   * @param type type of student 
   * @param studentName name of the student 
   * @param country country of the student 
   * @param home String that's the name of the Lodging service corresponding to home
   */
  public void addStudentHere(StudentType type, String studentName, String country, String home);

  /**
   * Removes a student from the area
   * @param studentName name of the student
   */
  public void removeStudent(String studentName);

  /**
   * Changes the location of a student 
   * @param studentName name of the stundent
   * @param serviceName new location (string)
   */
  public void changeStudentLocation(String studentName, String serviceName);

  /**
   * changes the home of a student with the given name 
   * @param studentName student name of student whose home is changing 
   * @param serviceName new home name
   */
  public void changeStudentHome(String studentName, String serviceName);

  /**
   * gets an iterator with the services in this area by insertion order
   * @return an iterator with the services in this area by insertion order
   */
  public Iterator<Service> getServicesIterator();

  /**
   * gets an iterator with the students in this area by alphabetical order
   * @return an iterator with the students in this area by alphabetical order
   */
  public Iterator<Student> getStudentsByAlphabetIterator();

  /**
   * gets an iterator with the students in this area of this country by 
   * @param country string country
   * @return an iterator with the students in this area of this country by 
   */
  public Iterator<Student> getStudentsByCountryIterator(String country);

  /**
   * public helper method. 
   * checks if  the point is inside the bounds 
   * @param latitude
   * @param longitude
   * @return true if the point is inside the bounds 
   */
  public boolean isPointInsideBounds(long latitude, long longitude);

  /**
   * public helper method. 
   * checks if the service exists in thie area
   * @param serviceName name of the service
   * @return true if the service with the given name exists
   */
  public boolean hasService(String serviceName);

  /**
   * public helper method. 
   * checks if the student exists in thie area
   * @param studentName name of the student
   * @return true if the student with the given name exists
   */
  public boolean hasStudent(String studentName);

  /**
   * pre condition: hasService(serviceName)
   * pre condition: service is lodging or eating
   * check if a service is full
   * @param serviceName name of the
   * @return true if service is full
   */
  public boolean isServiceFull(String serviceName);

  /**
   * checks if service is more expensive than students saved currentCheapest
   * @pre student is thrifty
   * @param student thrifty student 
   * @param service service to compare
   * @return true if is more expensive than students saved currentCheapest
   */
  public boolean isthriftyServiceMoreExpensive(String studentName, String serviceName);






  
}
