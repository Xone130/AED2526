/**
* @author João Pereirinha 64382 j.pereirinha@campus.fct.unl.pt
* @author Miguel Silva 68510 masa.silva@campus.fct.unl.pt
*/

import dataStructures.*;
import dataStructures.exceptions.*;
import enums.*;
import classes.HomewaySystemClass;
import interfaces.*;
import java.util.Scanner;

public class Main {

  //command messages
  public static final String UNKNOWN_COMMAND = "Unknown command. Type help to see available commands.";
  public static final String END             = "Bye!";

  //error messages
  public static final String BOUNDS_NOT_DEFINED     = "System bounds not defined.";
  public static final String BOUNDS_ALREADY_EXIST   = "Bounds already exists. Please load it!";
  public static final String BOUNDS_NOT_EXITS       = "Bounds %s does not exists.";
  public static final String INVALID_BOUNDS         = "Invalid bounds.";
  public static final String INVALID_SERVICE_TYPE   = "Invalid service type!";
  public static final String INVALID_STUDENT_TYPE   = "Invalid student type!";
  public static final String INVALID_LOCATION       = "Invalid location!";
  public static final String INVALID_PRICE_LODGING  = "Invalid room price!";
  public static final String INVALID_PRICE_EATING   = "Invalid menu price!";
  public static final String INVALID_PRICE_LEISURE  = "Invalid ticket price!";
  public static final String INVALID_DISCOUNT       = "Invalid discount price!";
  public static final String INVALID_CAPACITY       = "Invalid capacity!";
  public static final String SERVICE_ALREADY_EXISTS = "%s already exists!";
  public static final String STUDENT_ALREADY_EXISTS = "%s already exists!";
  public static final String LODGING_NOT_EXIST      = "lodging %s does not exist!";
  
  //user messages
  public static final String AREA_CREATED   = "%s created.";
  public static final String AREA_SAVED     = "%s saved.";
  public static final String AREA_LOADED    = "%s loaded.";
  public static final String NO_SERVICES    = "No services yet!";
  public static final String LIST_SERVICES  = "%s: %s (%d, %d).";
  


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
        case SERVICES -> cmd_services(hs, in);
        case STUDENT -> cmd_student(hs, in);
        case LEAVE -> cmd_leave(hs, in);
        case STUDENTS -> cmd_students(hs, in);
        case GO -> cmd_go(hs, in);
        case MOVE -> cmd_move(hs, in);
        case USERS -> cmd_users(hs, in);
        case WHERE -> cmd_where(hs, in);
        case VISITED -> cmd_visited(hs, in);
        case STAR -> cmd_star(hs, in);
        case RANKING -> cmd_ranking(hs, in);
        case RANKED -> cmd_ranked(hs, in);
        case TAG -> cmd_tag(hs, in);
        case FIND -> cmd_find(hs, in);
        case UNKNOWN -> {
                        System.out.println(UNKNOWN_COMMAND);
                        in.nextLine();
                        }
      }
    }while (!cmd.equals(Command.EXIT));

  in.close();
  }

  private static Command getCommand(Scanner in) {
    try {
      String comm = in.next().toUpperCase();
      return Command.valueOf(comm);
    } catch (IllegalArgumentException e) {
        return Command.UNKNOWN;
    }
  }

  private static void cmd_help() {
    Command[] help = Command.values();
    for(int i=0; i<help.length-1;i++)
      System.out.println(help[i].getMsg());
  }
  
  private static void cmd_bounds(HomeWaySystem hs, Scanner in) {
    long top = in.nextLong();
    long left = in.nextLong();
		long bottom = in.nextLong();
		long right = in.nextLong();
    String name = in.nextLine().trim();

    try {
      hs.createNewArea(top, left, bottom, right, name);
      System.out.printf(AREA_CREATED + "\n", name);

    } catch (BoundsExistException e) {
      System.out.println(BOUNDS_ALREADY_EXIST);
    } catch (InvalidBoundsException e) {
      System.out.println(INVALID_BOUNDS);
    }
  } 

  private static void cmd_save(HomeWaySystem hs) {
    if(!hasArea(hs)) return;

    hs.saveCurrentArea();
    System.out.printf(AREA_CREATED + "\n", hs.getCurrentArea().getName());
  }

  private static void cmd_load(HomeWaySystem hs, Scanner in) {
    String name = in.nextLine().trim();

    try {
      hs.loadArea(name);
      System.out.printf(AREA_LOADED, name);
    } catch (NoFileException e) {
      System.out.printf(BOUNDS_NOT_EXITS + "\n", name);
    }
  }

  //TO DO: mudar os try catches
  private static void cmd_service(HomeWaySystem hs, Scanner in) {
    if(!hasArea(hs)) return;

    ServiceType type;
    try {
      type = ServiceType.valueOf(in.next().toUpperCase());
    } catch (IllegalArgumentException e) {
      System.out.println(INVALID_SERVICE_TYPE);
      in.nextLine();
      return;
    }

    long latitude = in.nextLong();
    long longitude = in.nextLong();
    int price = in.nextInt();
    int value = in.nextInt();
    String name = in.nextLine().trim();

    try {
      hs.addServiceToCurrentArea( type, latitude, longitude, price, value, name );
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
    } catch (ServiceAlreadyExistsException e) {
      System.out.printf(SERVICE_ALREADY_EXISTS + "\n", name);
    }
  }

  private static void cmd_services(HomeWaySystem hs, Scanner in) {
    if(!hasArea(hs)) return;
    
    try {
      Iterator<Service> services = hs.getServicesIterator();

      Service current = services.next();
      while(services.hasNext()) {
        System.out.printf(LIST_SERVICES + "\n", current.getName(), current.getType().toString().toLowerCase(), current.getLatiture(), current.getLongitude());
      }
      
    } catch (NoServicesException e) {
      System.out.println(NO_SERVICES);
    }
  }

  //TO DO: mudar os try catches
  private static void cmd_student(HomeWaySystem hs, Scanner in) {
    if(!hasArea(hs)) return;
 
    StudentType type; 
    try { 
      type = StudentType.valueOf(in.nextLine().toUpperCase());
      in.nextLine();
      in.nextLine();
      in.nextLine();
    } catch (IllegalArgumentException e) {
      System.out.println(INVALID_SERVICE_TYPE);
      in.nextLine();
      return;
    }

    String name = in.nextLine();
    String country = in.nextLine();
    
    StudentType home = StudentType.valueOf(in.nextLine().toUpperCase());
    try {
      home = StudentType.valueOf(in.nextLine().toUpperCase());
      in.nextLine();
    } catch (IllegalArgumentException e) {
      
      in.nextLine();
      return;
    }

    try {
      hs.addStudentToCurrentArea( type, name, country, home );
  }  catch (LodgingDoesNotExistException e) {
      System.out.println(LODGING_NOT_EXIST);
    } catch (LodgingFullException e) {
      System.out.println(LODGING_FULL);
    } catch (StudentExistsException e){
      System.out.println(STUDENT_ALREADY_EXISTS);
    }
    
  }

  private static void cmd_leave(HomeWaySystem hs, Scanner in) {
    if(!hasArea(hs)) return;
    
    String name = in.nextLine();
  }

  private static void cmd_students(HomeWaySystem hs, Scanner in) {
    if(!hasArea(hs)) return;
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'cmd_students'");
  }

  private static void cmd_go(HomeWaySystem hs, Scanner in) {
    if(!hasArea(hs)) return;
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'cmd_go'");
  }

  private static void cmd_move(HomeWaySystem hs, Scanner in) {
    if(!hasArea(hs)) return;
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'cmd_move'");
  }

  private static void cmd_users(HomeWaySystem hs, Scanner in) {
    if(!hasArea(hs)) return;
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'cmd_users'");
  }

  private static void cmd_where(HomeWaySystem hs, Scanner in) {
    if(!hasArea(hs)) return;
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'cmd_where'");
  }

  private static void cmd_visited(HomeWaySystem hs, Scanner in) {
    if(!hasArea(hs)) return;
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'cmd_visited'");
  }

  private static void cmd_star(HomeWaySystem hs, Scanner in) {
    if(!hasArea(hs)) return;
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'cmd_star'");
  }

  private static void cmd_ranking(HomeWaySystem hs, Scanner in) {
    if(!hasArea(hs)) return;
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'cmd_ranking'");
  }

  private static void cmd_ranked(HomeWaySystem hs, Scanner in) {
    if(!hasArea(hs)) return;
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'cmd_ranked'");
  }

  private static void cmd_tag(HomeWaySystem hs, Scanner in) {
    if(!hasArea(hs)) return;
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'cmd_tag'");
  }

  private static void cmd_find(HomeWaySystem hs, Scanner in) {
    if(!hasArea(hs)) return;
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'cmd_find'");
  }


  // helpers -------------------------------------------

  public static boolean hasArea(HomeWaySystem hs){
    boolean bounds = hs.hasCurrentArea();

    if(!bounds) System.out.println(BOUNDS_NOT_DEFINED);
    return bounds;
  }

}
