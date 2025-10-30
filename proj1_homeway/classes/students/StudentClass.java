/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package classes.students;
import enums.StudentType;
import interfaces.Student;
import interfaces.Service;
import interfaces.LodgingService;

public abstract class StudentClass implements Student {

  private final String studentName;
  private final String country;
  private final StudentType type;
  private LodgingService home;
  private Service location;


  public StudentClass(StudentType type, String studentName, String country, LodgingService home){
    this.type = type;
    this.studentName = studentName;
    this.country = country;
    this.home = home;

    this.location = home;
  }

  @Override
  public String getName() {
    return studentName;
  }

  @Override
  public StudentType getType() {
    return type;
  }

  @Override
  public LodgingService getHome() {
    return home;
  }

  @Override
  public String getCountry(){
    return country;
  }

  @Override
  public Service getLocation(){
    return location;
  }

  public void setHome(LodgingService newHome){
    this.home = newHome;
  }


  @Override
  public void setLocation(Service newLocation){
    location = newLocation;
  }
  
}
