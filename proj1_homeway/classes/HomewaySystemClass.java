/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package classes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import dataStructures.Iterator;
import dataStructures.exceptions.BoundsExistException;
import dataStructures.exceptions.InvalidBoundsException;
import dataStructures.exceptions.InvalidCapacityException;
import dataStructures.exceptions.InvalidDiscountException;
import dataStructures.exceptions.InvalidLocationException;
import dataStructures.exceptions.InvalidPriceException;
import dataStructures.exceptions.InvalidServiceTypeException;
import dataStructures.exceptions.NoFileException;
import dataStructures.exceptions.NoServicesOfTypeException;
import dataStructures.exceptions.RedundantMoveException;
import dataStructures.exceptions.ServiceExistsException;
import dataStructures.exceptions.ServiceNotExistsException;
import dataStructures.exceptions.StudentExistsException;
import dataStructures.exceptions.StudentNotExistsException;
import dataStructures.exceptions.ThriftyException;
import enums.ServiceType;
import enums.StudentType;
import interfaces.Area;
import interfaces.HomeWaySystem;
import interfaces.Service;
import interfaces.Student;
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
    saveCurrentArea();
    currentArea = newArea;
  }

  @Override
  public void saveCurrentArea() {
    if(currentArea == null) return;

    String fileName = currentArea.getName().replace(" ", "_") + ".ser";

    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream( fileName ))) {
      oos.writeObject(currentArea);
      oos.flush();
      oos.close();
    } catch (IOException e) {
      System.out.println("Erro no save");
    }
  }

  @Override
  public void loadArea(String areaName) throws NoFileException {

    String fileName = areaName.replace(' ', '_') + ".ser";
    
    try{
      saveCurrentArea();
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream( fileName ) );
      currentArea = (Area) ois.readObject();
    ois.close();
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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addServiceToCurrentArea'");
  }

  @Override
  public Iterator<Service> getServicesIterator() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getServicesIterator'");
  }

  @Override
  public void addStudentToCurrentArea(StudentType type, String studentName, String country, ServiceType home)
      throws ServiceNotExistsException, InvalidCapacityException, StudentExistsException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addStudentToCurrentArea'");
  }

  @Override
  public void removeStudentFromCurrentArea(String studentName) throws StudentNotExistsException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'removeStudentFromCurrentArea'");
  }

  @Override
  public Iterator<Student> getStudentsIterator(String mode) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getStudentsIterator'");
  }

  @Override
  public void goToLocation(String studentName, String serviceName) throws ServiceNotExistsException,
      StudentNotExistsException, InvalidServiceTypeException, RedundantMoveException, InvalidCapacityException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'goToLocation'");
  }

  @Override
  public void moveOut(String studentName, String serviceName) throws ServiceNotExistsException, StudentExistsException,
      RedundantMoveException, InvalidCapacityException, ThriftyException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'moveOut'");
  }

  @Override
  public Iterator<Student> getUsersIterator(char order, String serviceName)
      throws ServiceNotExistsException, InvalidServiceTypeException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUsersIterator'");
  }

  @Override
  public Service getStudentLocation(String studentName) throws StudentNotExistsException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getStudentLocation'");
  }

  @Override
  public Iterator<Service> getVisitedServicesIterator(String studentName)
      throws StudentNotExistsException, ThriftyException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getVisitedServicesIterator'");
  }

  @Override
  public void evaluateService(String serviceName, Evaluation evaluation) throws ServiceNotExistsException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'evaluateService'");
  }

  @Override
  public Iterator<Service> getServiceByEvaluationIterator() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getServiceByEvaluationIterator'");
  }

  @Override
  public Iterator<Service> getServiceOfTypeWithScore(ServiceType type, int star) throws StudentNotExistsException,
      InvalidServiceTypeException, ServiceNotExistsException, NoServicesOfTypeException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getServiceOfTypeWithScore'");
  }

  @Override
  public Iterator<Service> getTaggedServicesIterator(String tag) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTaggedServicesIterator'");
  }

  @Override
  public Service findRelevantService(String studentName, ServiceType type)
      throws StudentNotExistsException, NoServicesOfTypeException {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findRelevantService'");
  }

  @Override
  public boolean isStudentDistracted(String studentName, String serviceName) {
    
    return (getStudent(studentName).getType() == StudentType.THRIFTY && 
    getService(serviceName).getType() == ServiceType.EATING  
    //&& getStudent(studentName).getCurrentCheapestEating().getPrice() < getService(serviceName).getPrice());
    );
  }

  //helpers --------------------------------------------------------------------------

  // public helper
  @Override
  public Area getCurrentArea() {
    return currentArea;
  }

  // public helper
  @Override
  public boolean hasCurrentArea() {
    return currentArea != null;
  }

  /**
   * returns the object of the Student class with studentName
   * @param studentName name of the student 
   * @return the object of the Student class with studentName
   */
  private Student getStudent(String studentName) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getStudent'");
  }

  /**
   * returns the object of the Service class with serviceName
   * @param serviceName name of the service 
   * @return the object of the Service class with serviceName
   */
  private Service getService(String serviceName) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getService'");
  }

  private boolean areaExists(String areaName) {
    String fileName = areaName.replace(" ", "_") + ".ser";
    File file = new File(fileName);
    return file.exists();
  }
  

}
