package interfaces;

public interface ThriftyStudent extends Student {
  
  /**
   * gets getCurrentCheapesEating
   * @return getCurrentCheapesEating
   */
  public EatingService getCurrentCheapestEating();

  /**
   * gets currentCheapestlodging
   * @return currentCheapestlodging
   */
  public LodgingService getCurrentCheapesLodging();

  /**
   * - checks if the visitedService is of type eating or lodging
   * - if currentEating is null, and it's eating updates
   * - checks if it's cheaper than currentEating or currentLodging
   * @param visitedService
   */
  public void updateCurrentCheapest( Service visitedService );

  /**
   * @pre service is either eating or lodging
   * @param service service to compare
   * @return true if service is more expensive than current
   */
  public boolean isMoreExpensiveThanCurrent ( Service service );
}
