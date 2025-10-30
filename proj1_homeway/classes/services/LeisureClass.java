
package classes.services;

import enums.ServiceType;
import interfaces.LeisureService;
import interfaces.Student;

public class LeisureClass extends ServiceClass implements LeisureService  {

  public LeisureClass(ServiceType type, long latitude, long longitude, int price, int value, String serviceName) {
    super(type, latitude, longitude, price, value, serviceName);
  }


}
