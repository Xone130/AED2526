package classes;

import interfaces.Area;
import dataStructures.*;
import interfaces.Service;

public class AreaClass implements Area {

  private static final long serialVersionUID = 0L;

  private String areaName;
  private long top;
  private long left;
  private long bottom;
  private long right;

  private List<Service> servicesInThisArea;

  public AreaClass(long top, long left, long bottom, long right, String areaName){
    this.areaName = areaName;
    this.top = top;
    this.left = left;
    this.bottom = bottom;
    this.right = right;
  }

  @Override
  public String getName() {
    return areaName;
  }

  @Override
  public void addServiceHere(Service newService) {
    
  }
  
}
