/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package interfaces;

public interface LeisureService extends Service {
  
  /** gets the price of the service after applying the discount
   * @return the price
   */
  @Override
  public int getPrice();
}
