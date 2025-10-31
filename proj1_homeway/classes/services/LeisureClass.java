/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package classes.services;

import enums.ServiceType;
import interfaces.LeisureService;

public class LeisureClass extends ServiceClass implements LeisureService  {

  private final int discount;

  public LeisureClass(ServiceType type, long latitude, long longitude, int price, int value, String serviceName) {
    super(type, latitude, longitude, price, value, serviceName);

    this.discount = value;
  }

  
  @Override
  public int getPrice(){
    int price = super.getPrice();
    int value;
    if(discount == 0) value = price;
    else value = price - (price * discount/100);
    return value;
  }

}
