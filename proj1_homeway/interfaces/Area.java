/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package interfaces;
import java.io.Serializable;

public interface Area extends Serializable {
  
  public String getName();

  public void addServiceHere(Service newService);
  
}
