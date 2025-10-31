/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package classes;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;
import dataStructures.exceptions.*;
import enums.ServiceType;
import enums.StudentType;
import interfaces.Area;
import interfaces.HomeWaySystem;
import interfaces.Service;
import interfaces.Student;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import records.Evaluation;

public class HomewaySystemClass implements HomeWaySystem {

  /**
   * current geographic area loaded into system to work with
   */
  private Area currentArea;

  /**
   * HomeSystemClass constructor
   */
  public HomewaySystemClass(){
    this.currentArea = null;
  }

  @Override
  public void createNewArea(long top, long left, long bottom, long right, String areaName) throws InvalidBoundsException, BoundsExistException {
    if(top <= bottom || left >= right) throw new InvalidBoundsException();
    if(areaExists(areaName)) throw new BoundsExistException();

    Area newArea = new AreaClass(top, left, bottom, right, areaName);
    currentArea = newArea;
    saveCurrentArea();
  }

  @Override
  public void saveCurrentArea() {
    if(currentArea == null) return;

    String fileName = currentArea.getAreaName().replace(" ", "_") + ".ser";

    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream( fileName ))) {
      oos.writeObject(currentArea);
      oos.flush();
      oos.close();
    } catch (IOException e) {
      //System.out.println("Erro no save");
    }
  }

  @Override
  public void loadArea(String areaName) throws NoFileException {

    String fileName = areaName.replace(' ', '_') + ".ser";
    
    try{
      saveCurrentArea();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream( fileName ) )) {
            currentArea = (Area) ois.readObject();
        }
    } catch (FileNotFoundException  e ) {
      throw new NoFileException(); // no file with same name
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Erro no load");
    }
  }

  @Override
  public void addServiceToCurrentArea(ServiceType type, long latitude, long longitude, int price, int value,
    String serviceName) throws InvalidLocationException, InvalidPriceException, InvalidDiscountException,
    InvalidCapacityException, ServiceExistsException {

      if(!currentArea.isPointInsideBounds(latitude, longitude)) throw new InvalidLocationException();
      if(price <= 0) throw new InvalidPriceException();
      if (type == ServiceType.LEISURE) if(value > 100 || value < 0) throw new InvalidDiscountException();
      if( (type == ServiceType.EATING || type == ServiceType.LODGING ) && value <= 0) throw new InvalidCapacityException();
      if(currentArea.hasService(serviceName)) throw new ServiceExistsException();

      currentArea.addServiceHere( type, latitude, longitude, price, value, serviceName );
      this.saveCurrentArea();

  }

  @Override
  public Iterator<Service> getServicesIterator() {
    return currentArea.getServicesIterator();
  }

  @Override
  public void addStudentToCurrentArea(StudentType type, String studentName, String country, String home)
    throws ServiceNotExistsException, InvalidCapacityException, StudentExistsException {
    
    if( !(currentArea.hasService(home)) ) throw new ServiceNotExistsException();
    if( currentArea.getServiceType(home) != ServiceType.LODGING  ) throw new ServiceNotExistsException();
    if(currentArea.isServiceFull(home)) throw new InvalidCapacityException();
    if(currentArea.hasStudent(studentName)) throw new StudentExistsException();

    currentArea.addStudentHere(type, studentName , country, home);
  }

  @Override
  public void removeStudentFromCurrentArea(String studentName) throws StudentNotExistsException {
    if(!currentArea.hasStudent(studentName)) throw new StudentNotExistsException();

    currentArea.removeStudent(studentName);
    this.saveCurrentArea();
  }

  @Override
  public Iterator<Student> getStudentsIterator(String mode) {
    if(mode.equals("all")) return currentArea.getStudentsByAlphabetIterator();
    return currentArea.getStudentsByCountryIterator(mode);
  }

  @Override
  public void goToLocation(String studentName, String serviceName) throws ServiceNotExistsException,
    StudentNotExistsException, InvalidServiceTypeException, RedundantMoveException, InvalidCapacityException {
    if( !currentArea.hasService(serviceName) ) throw new ServiceNotExistsException();
    if( !currentArea.hasStudent(studentName) ) throw new StudentNotExistsException();
    if( !(currentArea.getServiceType(serviceName) == ServiceType.EATING) && !(currentArea.getServiceType(serviceName) == ServiceType.LEISURE) ) throw new InvalidServiceTypeException();
    if( currentArea.getStudentLocation(studentName).getName().equals(serviceName) ) throw new RedundantMoveException();
    if(currentArea.getServiceType(serviceName) != ServiceType.EATING) if(currentArea.isServiceFull(serviceName)) throw new InvalidCapacityException();

    currentArea.changeStudentLocation(studentName, serviceName);
    this.saveCurrentArea();
  }

  @Override
  public void moveOut(String studentName, String serviceName) throws ServiceNotExistsException, StudentExistsException,
    RedundantMoveException, InvalidCapacityException, ThriftyException {
    if( !(currentArea.hasService(serviceName) )) if( currentArea.getServiceType(serviceName) != ServiceType.LODGING ) throw new ServiceNotExistsException();
    if(currentArea.hasStudent(studentName)) throw new StudentExistsException();
    if(currentArea.getStudentHome(studentName).getName().equals(serviceName)) throw new RedundantMoveException();
    if(currentArea.isServiceFull(serviceName)) throw new InvalidCapacityException();
    if(currentArea.getStudentType(studentName) == StudentType.THRIFTY) if(currentArea.isthriftyServiceMoreExpensive(studentName, serviceName)) throw new ThriftyException();

    currentArea.changeStudentHome(studentName, serviceName);
    this.saveCurrentArea();
  }

  @Override
  public TwoWayIterator<Student> getUsersIterator(char order, String serviceName)
      throws ServiceNotExistsException, InvalidServiceTypeException {
    if( !currentArea.hasService(serviceName) ) throw new ServiceNotExistsException();
    if( currentArea.getServiceType(serviceName) != ServiceType.LODGING && currentArea.getServiceType(serviceName) != ServiceType.EATING ) throw new InvalidServiceTypeException();

    return currentArea.getStudentsInServiceIterator(order, serviceName);
  }

  @Override
  public Service getStudentLocation(String studentName) throws StudentNotExistsException {
    if(!currentArea.hasStudent(studentName)) throw new StudentNotExistsException();

    return currentArea.getStudentLocation(studentName);
  }

  @Override
  public Iterator<Service> getVisitedServicesIterator(String studentName)
    throws StudentNotExistsException, ThriftyException {
    if(!currentArea.hasStudent(studentName)) throw new StudentNotExistsException();
    if(currentArea.getStudentType(studentName) == StudentType.THRIFTY) throw new ThriftyException();

    return currentArea.getStudentVisitedLocationsIterator(studentName);
  }

  @Override
  public void evaluateService(String serviceName, Evaluation evaluation) throws ServiceNotExistsException {
    if( !(currentArea.hasService(serviceName) )) throw new ServiceNotExistsException();

    currentArea.evaluateService(serviceName, evaluation);
    this.saveCurrentArea();
  }

  @Override
  public Iterator<Service> getServiceByEvaluationIterator() {
    return currentArea.getServicesByStarIterator();
  }

  @Override
  public Iterator<Service> getServiceOfTypeWithScore(ServiceType type, int star, String studentName) throws StudentNotExistsException,
    InvalidServiceTypeException, ServiceNotExistsException, NoServicesOfTypeException {
    if(!currentArea.hasStudent(studentName)) throw new StudentNotExistsException();
    if(type != ServiceType.EATING && type != ServiceType.LEISURE && type != ServiceType.LODGING) throw new InvalidServiceTypeException();
    if(!currentArea.hasServiceOfType(type)) throw new ServiceNotExistsException();
    if(!currentArea.hasServiceOfTypeWithAverage(type, star)) throw new NoServicesOfTypeException();

    return currentArea.getServiceOfTypeWithScoreClosestIterator(type, star, studentName);
  }

  @Override
  public Iterator<Service> getTaggedServicesIterator(String tag) {
    return currentArea.getTaggedServicesIterator(tag);
  }

  @Override
  public Service findRelevantService(String studentName, ServiceType type)
    throws StudentNotExistsException, NoServicesOfTypeException {
    if(!currentArea.hasStudent(studentName)) throw new StudentNotExistsException();
    if(!currentArea.hasServiceOfType(type)) throw new NoServicesOfTypeException();

    return currentArea.getMostRelevantService(studentName, type);
  }

    //helpers & auxiliar methods --------------------------------------------------------------------------

  @Override
  public String getCurrentAreaName() {
    return currentArea.getAreaName();
  }

  @Override
  public boolean hasCurrentArea() {
    return currentArea != null;
  }

  @Override
  public boolean isStudentDistracted(String studentName, String serviceName) {
    return currentArea.isThriftyDistracted(studentName, serviceName);
  }

  /**
   * checks if if area with same name as name given exists in the system
   * @param areaName name of the area to check
   * @return true if area with same name as name given exists in the system
   */
  private boolean areaExists(String areaName) {
    String fileName = areaName.replace(" ", "_") + ".ser";

    File file = new File(fileName);
    return file.exists();
  }

}
