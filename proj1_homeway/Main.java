/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/

/**
 * TO DO: mudar condicao dos thrifty cmd_go 
 * 
 * TO DO: mudar latitude e longitude para record Point (e bounds)
 * TO DO: reminder that created services start with evaluation of 4 by default
 */

import dataStructures.*;
import dataStructures.exceptions.*;
import enums.*;
import classes.HomewaySystemClass;
import interfaces.*;
import records.Evaluation;

import java.util.Scanner;

public class Main {

  //command messages
  public static final String UNKNOWN_COMMAND = "Unknown command. Type help to see available commands.";
  public static final String END             = "Bye!";

  //error messages
  public static final String BOUNDS_NOT_DEFINED       = "System bounds not defined.";
  public static final String BOUNDS_ALREADY_EXIST     = "Bounds already exists. Please load it!";
  public static final String BOUNDS_NOT_EXITS         = "Bounds %s does not exists.";
  public static final String INVALID_BOUNDS           = "Invalid bounds.";
  public static final String INVALID_SERVICE_TYPE     = "Invalid service type!";
  public static final String INVALID_STUDENT_TYPE     = "Invalid student type!";
  public static final String INVALID_LOCATION         = "Invalid location!";
  public static final String INVALID_PRICE_LODGING    = "Invalid room price!";
  public static final String INVALID_PRICE_EATING     = "Invalid menu price!";
  public static final String INVALID_PRICE_LEISURE    = "Invalid ticket price!";
  public static final String INVALID_DISCOUNT         = "Invalid discount price!";
  public static final String INVALID_CAPACITY         = "Invalid capacity!";
  public static final String SERVICE_ALREADY_EXISTS   = "%s already exists!";
  public static final String STUDENT_ALREADY_EXISTS   = "%s already exists!";
  public static final String SERVICE_NOT_EXISTS       = "%s does not exist!";
  public static final String STUDENT_NOT_EXISTS       = "%s does not exist!";
  public static final String LODGING_NOT_EXIST        = "lodging %s does not exist!";
  public static final String UNKNOWN_SERVICE          = "Unknown %s!"; 
  public static final String NOT_EATING_LEISURE       = "%s is not a valid service!"; 
  public static final String STUDENT_ALREADY_THERE    = "Already there!"; 
  public static final String HOME_ALREADY_THAT        = "That is %s's home!"; 
  public static final String EATING_IS_FULL           = "eating %s is full!"; 
  public static final String LODGING_IS_FULL          = "lodging %s is full!"; 
  public static final String THRIFTY_MOVE_ERROR       = "Move is not acceptable for %s!"; 
  public static final String SERVICE_EMPTY            = "No students on %s!"; 
  public static final String ORDER_NOT_EXIST          = "This order does not exists!"; 
  public static final String SERVICE_NOT_CONTROL      = "%s does not control student entry and exit!"; 
  public static final String NO_VISITED_LOCATIONS     = "%s has not visited any locations!"; 
  public static final String STUDENT_IS_THRIFTY       = "%s is thrifty!"; 
  public static final String INVALID_EVALUATION       = "Invalid evaluation!"; 
  public static final String NO_SERVICES_OF_TYPE      = "No %s services!"; 
  public static final String NO_SERVICES_OF_TYPE_STAR = "No %s services with average!"; 
  public static final String NO_SERVICES_WITH_TAG     = "There are no services with this tag!"; 
  
  //user messages
  public static final String AREA_CREATED          = "%s created.";
  public static final String AREA_SAVED            = "%s saved.";
  public static final String AREA_LOADED           = "%s loaded.";
  public static final String NO_SERVICES           = "No services yet!";
  public static final String NO_STUDENTS           = "No students yet!";
  public static final String NO_STUDENTS_FROM      = "No students from %s!";
  public static final String NO_SERVICES_IN_SYSTEM = "No services in the system.";
  public static final String SERVICE_ADDED         = "%s %s added.";
  public static final String STUDENT_ADDED         = "%s added.";
  public static final String LIST_SERVICES         = "%s: %s (%d, %d).";
  public static final String LIST_STUDENTS         = "%s: %s at %s.";
  public static final String STUDENT_LEFT          = "%s has left.";
  public static final String STUDENT_WENT_HERE     = "%s is now at %s.";
  public static final String THRIFTY_DISTRACTED    = "%s is distracted!";
  public static final String STUDENT_MOVED         = "lodging %s is now %s's home. %s is at home."; 
  public static final String LIST_USERS            = "%s: %s";
  public static final String WHERE_STUDENT         = "%s is at %s %s (%d, %d).";
  public static final String EVALUATION_REGISTERED = "Your evaluation has been registered!";
  public static final String RANKING_HEADER        = "Services sorted in descending order";
  public static final String LIST_RANKING          = "%s: %d";
  public static final String RANKED_HEADER         = "%s services closer with %d average";
  public static final String LIST_TAGGED           = "%s %s";
  


  public static void main(String args[]){
    Scanner in = new Scanner(System.in);
    HomeWaySystem hs = new HomewaySystemClass();
    Command cmd;

    do {
      cmd = getCommand(in);

      switch(cmd){
        case HELP -> { cmd_help(); in.nextLine(); }
        case EXIT -> System.out.println(END);
        case BOUNDS -> cmd_bounds(hs, in);
        case SAVE -> cmd_save(hs);
        case LOAD -> cmd_load(hs, in);
        case SERVICE -> cmd_service(hs, in);
        case SERVICES -> cmd_services(hs);
        case STUDENT -> cmd_student(hs, in);
        case LEAVE -> cmd_leave(hs, in);
        case STUDENTS -> cmd_students(hs, in);
        case GO -> cmd_go(hs, in);
        case MOVE -> cmd_move(hs, in);
        case USERS -> cmd_users(hs, in);
        case WHERE -> cmd_where(hs, in);
        case VISITED -> cmd_visited(hs, in);
        case STAR -> cmd_star(hs, in);
        case RANKING -> cmd_ranking(hs);
        case RANKED -> cmd_ranked(hs, in);
        case TAG -> cmd_tag(hs, in);
        case FIND -> cmd_find(hs, in);
        case UNKNOWN -> { System.out.println(UNKNOWN_COMMAND); in.nextLine(); }
      }
    }while (!cmd.equals(Command.EXIT));

  in.close();
  }

  private static void cmd_help() {
    Command[] help = Command.values();
    for(int i=0; i<help.length-1;i++) System.out.println(help[i].getMsg());
  }
  
  private static void cmd_bounds(HomeWaySystem hs, Scanner in) {
    long top = in.nextLong();
    long left = in.nextLong();
		long bottom = in.nextLong();
		long right = in.nextLong();
    String areaName = in.nextLine().trim();

    try {
      hs.createNewArea(top, left, bottom, right, areaName);
      System.out.printf(AREA_CREATED + "\n", areaName);

    } catch (BoundsExistException e) {
      System.out.println(BOUNDS_ALREADY_EXIST);
    } catch (InvalidBoundsException e) {
      System.out.println(INVALID_BOUNDS);
    }
  } 

  private static void cmd_save(HomeWaySystem hs) {
    if(!hasArea(hs)) return;

    hs.saveCurrentArea();
    System.out.printf(AREA_SAVED + "\n", hs.getCurrentArea().getName());
  }

  private static void cmd_load(HomeWaySystem hs, Scanner in) {
    String areaName = in.nextLine().trim();

    try {
      hs.loadArea(areaName);
      System.out.printf(AREA_LOADED + "\n", areaName);
    } catch (NoFileException e) {
      System.out.printf(BOUNDS_NOT_EXITS + "\n", areaName);
    } 
  }

  private static void cmd_service(HomeWaySystem hs, Scanner in) {

    ServiceType type = getServiceType(in.next());
    long latitude = in.nextLong();
    long longitude = in.nextLong();
    int price = in.nextInt();
    int value = in.nextInt();
    String serviceName = in.nextLine().trim();
    
    if(!hasArea(hs)) return;
    if (type == null) { System.out.println(INVALID_SERVICE_TYPE); return; }

    try {
      hs.addServiceToCurrentArea( type, latitude, longitude, price, value, serviceName );
      System.out.printf(SERVICE_ADDED + "\n", type.toString().toLowerCase(), serviceName);
    } catch (InvalidLocationException e) {
      System.out.println(INVALID_LOCATION);
    } catch (InvalidPriceException e){
      switch (type){
        case LODGING -> System.out.println(INVALID_PRICE_LODGING);
        case EATING -> System.out.println(INVALID_PRICE_EATING);
        case LEISURE -> System.out.println(INVALID_PRICE_LEISURE);
      }
    } catch (InvalidDiscountException e) {
        System.out.println(INVALID_DISCOUNT);
    } catch (InvalidCapacityException e) {
        System.out.println(INVALID_CAPACITY);
    } catch (ServiceExistsException e) {
      System.out.printf(SERVICE_ALREADY_EXISTS + "\n", serviceName);
    }
  }

  private static void cmd_services(HomeWaySystem hs) {
    if(!hasArea(hs)) return;
  
    Iterator<Service> services = hs.getServicesIterator();

    if(!services.hasNext()) System.out.println(NO_SERVICES);
    else{
      Service current;
      while(services.hasNext()) {
        current = services.next();
        System.out.printf(LIST_SERVICES + "\n", current.getName(), current.getType().toString().toLowerCase(), current.getLatitude(), current.getLongitude());
      }
    }
  }

  private static void cmd_student(HomeWaySystem hs, Scanner in) {
 
    StudentType type = getStudentType(in.nextLine().trim());
    String studentName = in.nextLine().trim();
    String country = in.nextLine().trim();
    ServiceType home = getServiceType(in.nextLine().trim());

    if(!hasArea(hs)) return;
    if (type == null) { System.out.println(INVALID_STUDENT_TYPE); return; }
    if (home == null) { System.out.printf(LODGING_NOT_EXIST + "\n", home); return; }

    try {
      hs.addStudentToCurrentArea( type, studentName, country, home );
      System.out.printf(STUDENT_ADDED + "\n", studentName);
    } catch (ServiceNotExistsException e) {
      System.out.printf(LODGING_NOT_EXIST + "\n", home);
    } catch (InvalidCapacityException e) {
      System.out.printf(LODGING_IS_FULL + "\n", home);
    } catch (StudentExistsException e){
      System.out.printf(STUDENT_ALREADY_EXISTS + "\n", studentName);
    }
  }

  private static void cmd_leave(HomeWaySystem hs, Scanner in) {
    String studentName = in.nextLine().trim();
    if(!hasArea(hs)) return;

    try {
      hs.removeStudentFromCurrentArea(studentName);
      System.out.printf(STUDENT_LEFT + "\n", studentName);
    } catch (StudentNotExistsException e) {
      System.out.printf(STUDENT_NOT_EXISTS + "\n", studentName);
    }
  }

  private static void cmd_students(HomeWaySystem hs, Scanner in) {
    String mode = in.nextLine().trim();
    if(!hasArea(hs)) return;

    Iterator<Student> students = hs.getStudentsIterator(mode);

      if (!students.hasNext()){ //students is empty
        if(mode.equals("all")) System.out.println(NO_STUDENTS);
        else System.out.printf(NO_STUDENTS_FROM + "\n", mode);
      }else{
        Student current;
        while(students.hasNext()) {
          current = students.next();
          System.out.printf(LIST_STUDENTS + "\n", current.getName(), current.getType().toString().toLowerCase(), current.getHome());
        }
      }
      
  }

  private static void cmd_go(HomeWaySystem hs, Scanner in) {
    String studentName = in.nextLine();
    String serviceName = in.nextLine();
    if(!hasArea(hs)) return;

    try {
      hs.goToLocation(studentName, serviceName);
      System.out.printf(STUDENT_WENT_HERE, studentName, serviceName); //no new line here
      if (hs.isStudentDistracted(studentName, serviceName)) System.out.printf(THRIFTY_DISTRACTED + "\n", studentName); //thrifty special case

    } catch (ServiceNotExistsException e) {
      System.out.printf(UNKNOWN_SERVICE +"\n", serviceName);
    } catch (StudentNotExistsException e) {
      System.out.printf(STUDENT_NOT_EXISTS + "\n", studentName);
    } catch (InvalidServiceTypeException e){
      System.out.printf(NOT_EATING_LEISURE + "\n", serviceName);
    } catch (RedundantMoveException e){
      System.out.println(STUDENT_ALREADY_THERE);
    } catch (InvalidCapacityException e){
      System.out.printf(EATING_IS_FULL + "\n", serviceName);
    }
  }

  private static void cmd_move(HomeWaySystem hs, Scanner in) {
    String studentName = in.nextLine().trim();
    String serviceName = in.nextLine().trim();
    if(!hasArea(hs)) return;

    try {
      hs.moveOut(studentName, serviceName);
      System.out.printf( STUDENT_MOVED + "\n", serviceName, studentName, studentName );

    } catch (ServiceNotExistsException e) {
      System.out.printf(LODGING_NOT_EXIST + "\n", serviceName);
    } catch (StudentExistsException e){
      System.out.printf(STUDENT_ALREADY_EXISTS + "\n", studentName);
    } catch (RedundantMoveException e) {
      System.out.printf(HOME_ALREADY_THAT + "\n", serviceName);
    } catch (InvalidCapacityException e) {
      System.out.printf(LODGING_IS_FULL + "\n", serviceName);
    } catch (ThriftyException e) {
      System.out.printf(THRIFTY_MOVE_ERROR + "\n", studentName);
    }
  }

  private static void cmd_users(HomeWaySystem hs, Scanner in) {
    char order = in.next().charAt(0);
    String serviceName = in.nextLine().trim();
    if(!hasArea(hs)) return;

    if( !(order == '<' || order == '>') ) { System.out.println(ORDER_NOT_EXIST); return; }
    
    try {
      Iterator<Student> students = hs.getUsersIterator(order, serviceName);
      if(!students.hasNext()) {System.out.printf(SERVICE_EMPTY + "\n", serviceName); return;}

        Student current;
        while(students.hasNext()){
          current = students.next();
          System.out.printf(LIST_USERS + "\n", current.getName(), current.getType().toString().toLowerCase());
        }

    } catch (ServiceNotExistsException e) {
      System.out.printf(SERVICE_NOT_EXISTS + "\n", serviceName);
    } catch (InvalidServiceTypeException e) {
      System.out.printf(SERVICE_NOT_CONTROL + "\n", serviceName);
    }
  }

  private static void cmd_where(HomeWaySystem hs, Scanner in) {
    String studentName = in.nextLine().trim();
    if(!hasArea(hs)) return;

    try {
      Service studentLocation = hs.getStudentLocation(studentName);
      System.out.printf(WHERE_STUDENT + "\n", studentName, studentLocation.getName(), studentLocation.getType().toString().toLowerCase(), studentLocation.getLatitude(),
                        studentLocation.getLongitude());
    } catch (StudentNotExistsException e) {
      System.out.printf(STUDENT_NOT_EXISTS + "\n", studentName);
    }
  }

  private static void cmd_visited(HomeWaySystem hs, Scanner in) {
    String studentName = in.nextLine().trim();
    if(!hasArea(hs)) return;

    try {
      Iterator<Service> services = hs.getVisitedServicesIterator(studentName);
      if(!services.hasNext()) {System.out.printf(NO_VISITED_LOCATIONS + "\n", studentName); return;}

        Service current;
        while(services.hasNext()){
          current = services.next();
          System.out.println(current.getName());
        }

    } catch (StudentNotExistsException e) {
      System.out.printf(STUDENT_NOT_EXISTS + "\n", studentName);
    } catch (ThriftyException e) {
      System.out.printf(STUDENT_IS_THRIFTY + "\n", studentName);
    }
  
  }

  private static void cmd_star(HomeWaySystem hs, Scanner in) {
    int star = in.nextInt();
    String serviceName = in.nextLine().trim();
    String description = in.nextLine().trim();
    if(!hasArea(hs)) return;

    if(star < 1 || star > 5) { System.out.println(INVALID_EVALUATION); return;}
    try {
      hs.evaluateService(serviceName, new Evaluation(star, description));
      System.out.println(EVALUATION_REGISTERED);
      
    } catch (ServiceNotExistsException e) {
      System.out.printf(SERVICE_NOT_EXISTS + "\n", serviceName);
    }

  }

  private static void cmd_ranking(HomeWaySystem hs) {
    if(!hasArea(hs)) return;
    
    Iterator<Service> services = hs.getServiceByEvaluationIterator();
    if(!services.hasNext()) {System.out.println(NO_SERVICES_IN_SYSTEM); return;}

    System.out.println(RANKING_HEADER);
    Service current;
    while(services.hasNext()) {
      current = services.next();
      System.out.printf(LIST_RANKING + "\n", current.getName(), current.getEvaluation().star());
    }
  }

  private static void cmd_ranked(HomeWaySystem hs, Scanner in) {
    ServiceType type = getServiceType(in.next()); 
    int star = in.nextInt();
    String studentName = in.nextLine();
    if(!hasArea(hs)) return;

    if(star < 1 || star > 5) { System.out.println(INVALID_EVALUATION); return;}
    try {

      Iterator<Service> services = hs.getServiceOfTypeWithScore(type, star);
      System.out.printf(RANKED_HEADER + "\n", type.toString().toLowerCase(), star);

      Service current;
      while(services.hasNext()) {
        current = services.next();
        System.out.println(current.getName());
      }

    } catch (StudentNotExistsException e) {
      System.out.printf(STUDENT_NOT_EXISTS + "\n", studentName);
    } catch (InvalidServiceTypeException e){
      System.out.println(INVALID_SERVICE_TYPE);
    } catch (ServiceNotExistsException e){
      System.out.printf(NO_SERVICES_OF_TYPE + "\n", type.toString().toLowerCase());
    } catch (NoServicesOfTypeException e){
      System.out.printf(NO_SERVICES_OF_TYPE_STAR + "\n", type.toString().toLowerCase());
    }
  }

  private static void cmd_tag(HomeWaySystem hs, Scanner in) {
    String tag = in.nextLine().trim();
    if(!hasArea(hs)) return;

    Iterator<Service> services = hs.getTaggedServicesIterator(tag);
    if(!services.hasNext()) {System.out.println(NO_SERVICES_WITH_TAG); return;}

    Service current;
    while(services.hasNext()) {
      current = services.next();
      System.out.printf(LIST_TAGGED + "\n", current.getType().toString().toLowerCase(), current.getName());
    }
  }

  private static void cmd_find(HomeWaySystem hs, Scanner in) {
    String studentName = in.nextLine().trim();
    ServiceType type = getServiceType(in.next());

    if(!hasArea(hs)) return;
    if (type == null) { System.out.println(INVALID_SERVICE_TYPE); return; }

    try {
      Service service = hs.findRelevantService(studentName, type);
      System.out.println(service.getName());
    } catch (StudentNotExistsException e) {
      System.out.printf(STUDENT_NOT_EXISTS + "\n", studentName);
    } catch (NoServicesOfTypeException e){
      System.out.printf(NO_SERVICES_OF_TYPE + "\n", type.toString().toLowerCase());
    }
    
  }


  // helpers -------------------------------------------

    private static Command getCommand(Scanner in) {
    try {
      String comm = in.next().toUpperCase();
      return Command.valueOf(comm);
    } catch (IllegalArgumentException e) {
        return Command.UNKNOWN;
    }
  }

  public static boolean hasArea(HomeWaySystem hs){
    boolean bounds = hs.hasCurrentArea();

    if(!bounds) System.out.println(BOUNDS_NOT_DEFINED);
    return bounds;
  }

  /**
   * 
   * @param serviceType type of service
   * @return null if serviceType is not a valid type or the ServiceType object otherwise
   */
  private static ServiceType getServiceType(String serviceType){
    try {
      String type = serviceType.toUpperCase();
      return ServiceType.valueOf(type);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }
 
  /**
   * 
   * @param studentType type of student 
   * @return null if studentType is not a valid type or the StudenType object otherwise
   */
  private static StudentType getStudentType(String studentType){
    try {
      String type = studentType.toUpperCase();
      return StudentType.valueOf(type);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

}
