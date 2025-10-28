/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package interfaces;
import enums.ServiceType;
import records.Evaluation;

public interface Service {

  public String getName();

  public ServiceType getType();

  public long getLatitude();

  public long getLongitude();

  public Evaluation getEvaluation();
  
}
