/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/

package classes.services;

import enums.ServiceType;
import interfaces.LodgingService;

public class LodgingClass extends ServiceClass implements LodgingService {

  public LodgingClass(ServiceType type, long latitude, long longitude, int price, int value, String serviceName) {
    super(type, latitude, longitude, price, value, serviceName);
  }

  @Override
  public boolean isLodgingFull() {
    return super.getNumberOfStudentsHere() < super.getValue();
  }



}
