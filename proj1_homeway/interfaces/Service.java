/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package interfaces;
import records.Evaluation;
import dataStructures.TwoWayIterator;
import enums.ServiceType;

public interface Service {

  public String getName();

  public ServiceType getType();

  public long getLatitude();

  public long getLongitude();

  public int getEvaluationAverage();

  public int getPrice();

  public int getValue();

  /**
   * the number of studends currently in this service
   * @return the number of studends currently in this service
   */
  public int getNumberOfStudentsHere();

  /**
   * adds a student currently in this service
   * @param newStudent student to be added
   */
  public void addStudentHere( Student newStudent );

  /**
   * removes a student from this service
   * @param student student to be removed
   */
  public void removeStudentHere(Student student);

  /**
   * - adds an evaluation to the list of evaluations of this Service
   * - recalculates average
   * @param newEvaluation evaluation to be added
   */
  public void addEvaluation ( Evaluation newEvaluation );

  /**
   * checks if a student is here by name
   * @param studentName the name of the student to be searched for
   * @return true if the student is here, false otherwise
   */
  public boolean isStudentHere(String studentName);

  /**
   * gets a TwoWayIterator of the students in this service (used in users command for reverse order)
   * @return a TwoWayIterator of the students in this service
   */
  public TwoWayIterator<Student> getStudentsHereTwoWayIterator();

  /**
   * checks if this service has a description with that tag
   * @return true if this service has a description with that tag
   */
  public boolean hasDescription(String tag);
}
