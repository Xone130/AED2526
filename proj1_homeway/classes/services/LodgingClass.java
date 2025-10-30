

package classes.services;

import enums.ServiceType;
import interfaces.LodgingService;
import interfaces.Student;

public class LodgingClass extends ServiceClass implements LodgingService {

  public LodgingClass(ServiceType type, long latitude, long longitude, int price, int value, String serviceName) {
    super(type, latitude, longitude, price, value, serviceName);
  }

  @Override
  public boolean isLodgingFull() {
    return super.getNumberOfStudentsHere() < super.getValue();
  }



}
