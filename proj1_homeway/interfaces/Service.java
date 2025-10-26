package interfaces;
import enums.ServiceType;

public interface Service {

  public String getName();

  public ServiceType getType();

  public long getLatiture();

  public long getLongitude();
  
}
