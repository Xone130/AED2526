package classes.students;

import enums.StudentType;
import dataStructures.DoublyLinkedList;
import dataStructures.Iterator;
import dataStructures.List;
import enums.ServiceType;
import interfaces.BookishStudent;
import interfaces.LodgingService;
import interfaces.LeisureService;
import interfaces.Service;

public class BookishClass extends StudentClass implements BookishStudent {

  private List<LeisureService> leisuresVisisted;

  public BookishClass(StudentType type, String studentName, String country, LodgingService home) {
    super(type, studentName, country, home);

    leisuresVisisted = new DoublyLinkedList<>();
  }

  @Override
  public Iterator<LeisureService> getLeisuresVisitedIterator() {
    return leisuresVisisted.iterator();
  }

  @Override
  public void updateLeisuresVisited(Service visitedService) {

    if(visitedService.getType() == ServiceType.LEISURE){
      LeisureService service = ( (LeisureService) visitedService);
      if(listContains(service)) leisuresVisisted.remove(leisuresVisisted.indexOf(service));
      leisuresVisisted.addFirst(service);
    }
  }


  private boolean listContains(LeisureService service){
    Iterator<LeisureService> it = leisuresVisisted.iterator();

    LeisureService current;
    while(it.hasNext()){
      current = it.next();
      if(service.getName().equals(current.getName())) return true;
    }
    return false;
  }
  

}
