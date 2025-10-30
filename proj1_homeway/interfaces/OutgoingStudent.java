/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package interfaces;

import dataStructures.Iterator;

public interface OutgoingStudent extends Student {
  
  /**
   * gets an iterator with the visited services
   * @return an iterator with the visited services
   */
  public Iterator<Service> getServicesVisitedIterator();

  /**
   * - checks if the list contains visitedService
   * - if it does, remove it
   * - adds the visitedService to list of visited leisures
   * @param visitedService service visited
   */
  public void updateServicesVisited(Service visitedService);
}

