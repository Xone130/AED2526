package interfaces;

import dataStructures.Iterator;

public interface BookishStudent extends Student {
  
  /**
   * gets an iterator with the visited Leisures
   * @return an iterator with the visited Leisures
   */
  public Iterator<LeisureService> getLeisuresVisitedIterator();

  /**
   * - checks if visitedService is leisure
   * - checks if the list contains visitedService
   * - if it does, remove it
   * - adds the visitedService to list of visited leisures
   * @param visitedService service visited
   */
  public void updateLeisuresVisited(Service visitedService);
}
