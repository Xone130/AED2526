/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/
package classes.students;

import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.List;
import enums.StudentType;
import interfaces.LodgingService;
import interfaces.OutgoingStudent;
import interfaces.Service;

public class OutgoingClass extends StudentClass implements OutgoingStudent {

  private final List<Service> servicesVisisted;

  public OutgoingClass(StudentType type, String studentName, String country, LodgingService homeName) {
    super(type, studentName, country, homeName);

    servicesVisisted = new DoublyLinkedList<>();
  }

  @Override
  public Iterator<Service> getServicesVisitedIterator() {
    return servicesVisisted.iterator();
  }

  @Override
  public void updateServicesVisited(Service visitedService) {

    if(listContains(visitedService)) servicesVisisted.remove(servicesVisisted.indexOf(visitedService));
    servicesVisisted.addFirst(visitedService);
  }

  private boolean listContains(Service service) {
    Iterator<Service> it = servicesVisisted.iterator();

    Service current;
    while(it.hasNext()){
      current = it.next();
      if(service.getName().equals(current.getName())) return true;
    }
    return false;
  }

  
}
