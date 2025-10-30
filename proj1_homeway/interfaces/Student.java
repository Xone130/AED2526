/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package interfaces;
import enums.StudentType;

public interface Student {

  public String getName();

  public String getCountry();

  public StudentType getType();

  public LodgingService getHome();

  public Service getLocation();

  public void setHome(LodgingService newHome);

  /**
   * alters the location of a student to newLocation
   * @param newLocation newLocation
   */
  public void setLocation(Service newLocation);
  
}
