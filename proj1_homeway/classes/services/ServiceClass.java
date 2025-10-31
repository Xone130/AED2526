/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package classes.services;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.ListInArray;
import dataStructures.TwoWayIterator;
import dataStructures.TwoWayList;
import enums.ServiceType;
import interfaces.Service;
import interfaces.Student;
import records.Evaluation;

public abstract class ServiceClass implements Service {

  private static final int STARTING_EVALUATION = 4;
  
  private final String serviceName;
  private final ServiceType type;
  private final long latitude;
  private final long longitude;
  private final int price;
  private final int value;

  private int timeInThisAverage;
  private int currentAverage;
  private ListInArray<Evaluation> evaluations;
  private TwoWayList<Student> studentsHere;


  public ServiceClass( ServiceType type, long latitude, long longitude, int price, int value, String serviceName ){
    this.serviceName = serviceName;
    this.type = type;
    this.latitude = latitude;
    this.longitude = longitude;
    this.price = price;
    this.value = value;

    //evaluations
    this.evaluations = new ListInArray<>(value);
    evaluations.addFirst(new Evaluation(STARTING_EVALUATION, ""));
    this.currentAverage = STARTING_EVALUATION;
    this.timeInThisAverage = 1;

    this.studentsHere = new DoublyLinkedList<>();
  }
  
  @Override
  public String getName(){
    return serviceName;
  }

  @Override
  public ServiceType getType(){
    return type;
  }

  @Override
  public long getLatitude(){
    return latitude;
  }

  @Override
  public long getLongitude(){
    return longitude;
  }

  @Override
  public int getEvaluationAverage(){
    return currentAverage;
  }

  @Override
  public int getPrice(){
    return price;
  }

  @Override
  public int getValue(){
    return value;
  }

  @Override
  public int getNumberOfStudentsHere(){
    return studentsHere.size();
  }

  @Override
  public void addStudentHere( Student newStudent ){
    studentsHere.addFirst(newStudent);
  }

  @Override
  public void removeStudentHere(Student student){
    studentsHere.remove(studentsHere.indexOf(student));
  }

  @Override
  public void addEvaluation ( Evaluation newEvaluation ){
    evaluations.addLast(newEvaluation);
    this.updateAverage();
  }

  // validations ----------------------------------------------------

  @Override
  public TwoWayIterator<Student> getStudentsHereTwoWayIterator(){
    return studentsHere.twoWayiterator();
  }

  @Override
  public boolean isStudentHere(String studentName){
    Iterator<Student> students = studentsHere.iterator();

    Student current;
    while(students.hasNext()){
      current = students.next();
      if(current.getName().equals(studentName)) return true;
    }
    return false;
  }

  @Override
  public boolean hasDescription(String tag){
    Iterator<Evaluation> evals = evaluations.iterator();

    while(evals.hasNext()){
      if(evals.next().description().toLowerCase().contains(tag.toLowerCase())) return true;
    }
    return false;
  }

  // helpers ----------------------------------------------------

  /** 
   * updates currentAverage
   */
  private void updateAverage(){
    Iterator<Evaluation> evals = evaluations.iterator();

    int counter = 0;
    while(evals.hasNext()){counter = counter + evals.next().star();}
    this.currentAverage = Math.round( (float)counter / evaluations.size() );
    
  }

  
}
