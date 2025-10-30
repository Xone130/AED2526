package classes.services;

import enums.ServiceType;
import interfaces.EatingService;
import interfaces.Student;

public class EatingClass extends ServiceClass implements EatingService {

  public EatingClass(ServiceType type, long latitude, long longitude, int price, int value, String serviceName) {
    super(type, latitude, longitude, price, value, serviceName);
  }

  @Override
  public boolean isEatingFull() {
    return super.getNumberOfStudentsHere() < super.getValue();
  }

}
