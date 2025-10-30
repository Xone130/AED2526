/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package classes.students;

import enums.ServiceType;
import enums.StudentType;
import interfaces.ThriftyStudent;
import interfaces.EatingService;
import interfaces.LodgingService;
import interfaces.Service;

public class ThriftyClass extends StudentClass implements ThriftyStudent {

  EatingService currentCheapestEating;
  LodgingService currentCheapesLodging;

  public ThriftyClass(StudentType type, String studentName, String country, LodgingService home) {
    super(type, studentName, country, home);
    
    this.currentCheapesLodging = home;
  }

  @Override
  public EatingService getCurrentCheapestEating() {
    return currentCheapestEating;
  }

  @Override
  public LodgingService getCurrentCheapesLodging() {
    return currentCheapesLodging;
  }

  @Override
  public void updateCurrentCheapest(Service newLocation) {
    switch(newLocation.getType()){
      case EATING  -> {  
        if(currentCheapestEating == null) { currentCheapestEating = ( (EatingService) (newLocation)); return; }
        if(newLocation.getPrice() < currentCheapestEating.getPrice()) currentCheapestEating = ( (EatingService) (newLocation));
      }
      case LODGING -> { if (newLocation.getPrice() < currentCheapesLodging.getPrice()) currentCheapesLodging = ( (LodgingService) (newLocation)); }
      default      -> {}
    }
  }

  @Override
  public boolean isMoreExpensiveThanCurrent(Service service) {
    if(service.getType() == ServiceType.EATING) return service.getPrice() > currentCheapestEating.getPrice();
    if(service.getType() == ServiceType.LODGING) return service.getPrice() > currentCheapesLodging.getPrice();

    return false;
  }
  
}
