/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package classes;

import classes.services.EatingClass;
import classes.services.LeisureClass;
import classes.services.LodgingClass;
import classes.students.BookishClass;
import classes.students.OutgoingClass;
import classes.students.ThriftyClass;
import dataStructures.*;
import enums.ServiceType;
import enums.StudentType;
import interfaces.*;
import records.Evaluation;

public class AreaClass implements Area {

  private static final long serialVersionUID = 0L;

  private final String areaName;
  private final long top;
  private final long left;
  private final long bottom;
  private final long right;

  private List<Service> servicesInThisArea;
  private List<Student> studentsInThisArea;
  private SortedList<Student> studentsByAlphabet;

  private SortedList<Service> servicesByPrice;
  private SortedList<Service> servicesByAverage;

  public AreaClass(long top, long left, long bottom, long right, String areaName){
    this.areaName = areaName;
    this.top = top;
    this.left = left;
    this.bottom = bottom;
    this.right = right;

    this.servicesInThisArea = new DoublyLinkedList<>();
    this.studentsInThisArea = new DoublyLinkedList<>();
    this.studentsByAlphabet = new SortedDoublyLinkedList<>( (Student student1, Student student2) -> student1.getName().compareToIgnoreCase(student2.getName()) );
    this.servicesByAverage = new SortedDoublyLinkedList<>( (Service service1, Service service2) -> Integer.compare(service2.getEvaluationAverage(), service1.getEvaluationAverage()) );
  }

  // getters, gets, adders & setters ---------------------------------------------------------------------------------------

  @Override
  public String getAreaName() {
    return areaName;
  }

  @Override
  public ServiceType getServiceType (String serviceName){
    return searchService(serviceName).getType();
  }

  @Override
  public StudentType getStudentType (String studentName){
    return searchStudent(studentName).getType();
  }

  @Override
  public LodgingService getStudentHome(String studentName){
    return ( (LodgingService) searchStudent(studentName).getHome() );
  }


  @Override
  public Service getStudentLocation(String studentName) {
    return searchStudent(studentName).getLocation();
  }

  @Override
  public Iterator<Service> getServicesIterator() { // services
    return servicesInThisArea.iterator();
  }

  @Override
  public Iterator<Student> getStudentsByAlphabetIterator() { // students
    return studentsByAlphabet.iterator();
  }

  @Override
  public Iterator<Student> getStudentsByCountryIterator(String country) { // students 
    List<Student> studentsByCountry = new DoublyLinkedList<>();
    Iterator<Student> students = studentsInThisArea.iterator();

    Student current;
    while ( students.hasNext() ){
      current = students.next();
      if(current.getCountry().equals(country)) studentsByCountry.addLast(current);
    }

    return studentsByCountry.iterator();
  }

  @Override
  public TwoWayIterator<Student> getStudentsInServiceIterator(char order, String serviceName) { // users
    return searchService(serviceName).getStudentsHereTwoWayIterator();
  }

  @Override
  public Iterator<Service> getStudentVisitedLocationsIterator(String studentName) { // visited
    Student student = searchStudent(studentName);

    if(student.getType() == StudentType.OUTGOING) return ( (OutgoingStudent) student).getServicesVisitedIterator();
    return ( (BookishStudent) student).getLeisuresVisitedIterator();
  }

  @Override
  public Iterator<Service> getServicesByStarIterator() { // ranking
    return servicesByAverage.iterator();
  }

  @Override
  public Iterator<Service> getServiceOfTypeWithScoreClosestIterator(ServiceType type, int star, String studentName) { // ranked
    // creates filtered iterator by type & average
    Predicate<Service> typeAndAverageFilter = service -> service.getType() == type && service.getEvaluationAverage() == star;
    FilterIterator<Service> servicesByTypeWithAverage = new FilterIterator<>(servicesByAverage.iterator(), typeAndAverageFilter); 
    List<Service> validServices = new DoublyLinkedList<>();
    long minDistance = Long.MAX_VALUE;
    Service studentLocation = searchStudent(studentName).getLocation();

    // finds minimum distance 
    Service current;
    while(servicesByTypeWithAverage.hasNext()){
      current = servicesByTypeWithAverage.next();
      long distance = this.manhattanDistance(current, studentLocation);

      validServices.addLast(current);
      if( minDistance > distance ) minDistance = distance;
    }

    // returns iterator with services minDistance away from the student 
    final long minDistanceFinal = minDistance;
    Predicate<Service> minDistanceFilter = service -> manhattanDistance(service, studentLocation) == minDistanceFinal;
    return new FilterIterator<>( validServices.iterator(), minDistanceFilter); 
  }

  // command methods ---------------------------------------------------------------------------------------
  
  @Override
  public void addServiceHere( ServiceType type, long latitude, long longitude, int price, int value, String serviceName ) { // service
    Service service = createService(type, latitude, longitude, price, value, serviceName);

    servicesInThisArea.addLast( service );
  }

  @Override
  public void addStudentHere(StudentType type, String studentName, String country, String home) { // student
    Student student = createStudent(type, studentName, country, home);

    studentsInThisArea.addLast( student );
    studentsByAlphabet.add(student);
    alterStudentLocation(student, searchService(home) );
  }

  @Override
  public void removeStudent(String studentName) { // leave
    Student student = searchStudent(studentName);

    student.getLocation().removeStudentHere(student);
    studentsInThisArea.remove(studentsInThisArea.indexOf(student));
  }

  @Override
  public void changeStudentLocation(String studentName, String serviceName) { // go
    Student student = searchStudent(studentName);
    Service newLocation = searchService(serviceName);

    updateStudentLists(student, newLocation);
    alterStudentLocation(student, newLocation);
  }

  @Override
  public void changeStudentHome(String studentName, String serviceName) { // move
    searchStudent(studentName).setHome( ( (LodgingService) searchService(serviceName)) );
    changeStudentLocation(studentName, serviceName);
  }

  @Override
  public void evaluateService(String serviceName, Evaluation evaluation) { // star
    Service service = searchService(serviceName);
    service.addEvaluation(evaluation);
    this.updateServicesByAverage(service);
  }

  // verification methods  ---------------------------------------------------------------------------------------

  @Override
  public boolean isPointInsideBounds(long latitude, long longitude) {
    return latitude >= bottom && latitude <= top &&
    longitude >= left && longitude <= right;
  }

  @Override
  public boolean hasService(String serviceName) {
    if(servicesInThisArea.isEmpty()) return false;

    return searchService(serviceName) != null;
  }

  @Override
  public boolean hasStudent(String studentName){
    if(studentsInThisArea.isEmpty()) return false;

    return searchStudent(studentName) != null;
  }

  @Override
  public boolean isServiceFull(String home) {
    Service service = searchService(home);
    if(service instanceof LodgingService lodgingService) return lodgingService.isLodgingFull();
    if(service instanceof EatingService eatingService) return eatingService.isEatingFull();

    return false;
  }

  @Override
  public boolean isthriftyServiceMoreExpensive(String studentName, String serviceName){
    return ( (ThriftyStudent) searchStudent(studentName)).isMoreExpensiveThanCurrent(searchService(serviceName));
  }

  @Override
  public boolean hasServiceOfType(ServiceType type) {
    Iterator<Service> services = servicesInThisArea.iterator();

    while(services.hasNext()){
      if(services.next().getType() == type) return true;
    }
    return false;
  }

  @Override
  public boolean hasServiceOfTypeWithAverage(ServiceType type, int average) {
    Iterator<Service> services = servicesByAverage.iterator();

    Service current;
    while(services.hasNext()){
      current = services.next();
      if(current.getType() == type && current.getEvaluationAverage() == average) return true;
    }
    return false;
  }


  // helpers ---------------------------------------------------------------------------------------

  /**
   * Gets the Service with the given name
   * @param serviceName name of the service
   * @return Service with the given name
   */
  private Service searchService(String serviceName){
    Iterator<Service> services = servicesInThisArea.iterator();
    Service current;
    while(services.hasNext()){
      current = services.next();
      if (serviceName.equals( current.getName() )) return current;
    }
    return null;
  }

  /**
   * gets the Student with the given name
   * @param studentName name of the service 
   * @return Service with the given name
   */
  private Student searchStudent(String studentName){
    Iterator<Student> students = studentsInThisArea.iterator();
    Student current;
    while(students.hasNext()){
      current = students.next();
      if (studentName.equals( current.getName() )) return current;
    }
    return null;
  }

  /**
   * creates a service with given atributes
   * @param serviceName name of the service
   * @param value may be the capacity for eating and lodging services, and the student service discount for leisure service
   * @param price price of the service
   * @param longitude longitude
   * @param latitude latitude
   * @param type type of service
   * @return the created service
   */
  private Service createService(ServiceType type, long latitude, long longitude, int price, int value, String serviceName){
    switch(type){
      case EATING -> { return new EatingClass(type, latitude, longitude, price, value, serviceName); }
      case LEISURE -> { return new LeisureClass(type, latitude, longitude, price, value, serviceName); }
      case LODGING -> { return new LodgingClass(type, latitude, longitude, price, value, serviceName); }
    }
    return null;
  }

  /**
   * creates a new student with given atributes
   * @param type type of student 
   * @param studentName name of the student 
   * @param country country of the student 
   * @param home name of the lodging which is student's home (string)
   * @return new student created
   */
  private Student createStudent(StudentType type, String studentName, String country, String home) {
      switch(type){
      case BOOKISH -> { return new BookishClass(type, studentName, country, ( (LodgingService) searchService(home) )); }
      case OUTGOING -> { return new OutgoingClass(type, studentName, country, ( (LodgingService) searchService(home) ) ); }
      case THRIFTY -> { return new ThriftyClass(type, studentName, country, ( (LodgingService) searchService(home) ) ); }
    }
    return null;
  }

  /**
   * updates the lists of the students depending on type
   * @param student Student to update 
   * @param newLocation new Service location
   */
  private void updateStudentLists(Student student, Service newLocation) {
    switch(student.getType()){
      case BOOKISH -> { ( (BookishStudent) student).updateLeisuresVisited(newLocation); }
      case OUTGOING -> { ( (OutgoingStudent) student).updateServicesVisited(newLocation); }
      case THRIFTY -> { ( (ThriftyStudent) student).updateCurrentCheapest( newLocation ); }
    }
  }

  /**
   * removes student from previous location and adds to the newLocation
   * @param student student 
   * @param newLocation location where student is moving
   */
  private void alterStudentLocation(Student student, Service newLocation) {
    student.getLocation().removeStudentHere(student);
    newLocation.addStudentHere(student);
    student.setLocation(newLocation);
  }

  private void updateServicesByAverage(Service service) {

    servicesByAverage.remove(service);
    servicesByAverage.add(service);
  }

  /**
   * gets the manhattan distance between the two services
   * @param service1 1st service
   * @param service2 2nd service
   * @return the manhattan distance between the two services
   */
  private long manhattanDistance(Service service1, Service service2){
    return (Math.abs(service1.getLatitude() - service2.getLatitude()) + Math.abs(service1.getLongitude() - service2.getLongitude()));
  }

}
