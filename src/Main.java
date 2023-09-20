import java.util.*;
class RentalSystem
{
    private HashMap<Vehicle,Boolean> availableVehicles;
    private HashMap<Vehicle,Boolean> rentedVehicles;
    public RentalSystem()
    {
        this.availableVehicles= new HashMap<>();
        this.rentedVehicles= new HashMap<>();
    }
    private int totalRevenue;
    public void addVehicle(Vehicle vehicle)
    {
        this.availableVehicles.put(vehicle,true);
    }
    private boolean searchInAvailable(String registrationNumber)
    {
        Iterator<Map.Entry<Vehicle,Boolean>>it=this.availableVehicles.entrySet().iterator();
        while(it.hasNext())
        {
            if(it.next().getKey().getRegistrationNumber().equals(registrationNumber))
            {
                return true;
            }
        }
        return false;
    }
    private Vehicle getAvailable(String registrationNumber)
    {
        for(Map.Entry<Vehicle,Boolean> mapEntry : this.availableVehicles.entrySet())
        {
            if(mapEntry.getKey().getRegistrationNumber().equals(registrationNumber))
            {
                return mapEntry.getKey();
            }
        }
        return this.availableVehicles.keySet().iterator().next();
    }
    private Vehicle getRent(String registrationNumber)
    {
        for(Map.Entry<Vehicle,Boolean> mapEntry : this.rentedVehicles.entrySet())
        {
            if(mapEntry.getKey().getRegistrationNumber().equals(registrationNumber))
            {
                return mapEntry.getKey();
            }
        }
        return this.rentedVehicles.keySet().iterator().next();
    }
    private void removeFromAvailable(String registrationNumber)
    {
        Iterator<Map.Entry<Vehicle,Boolean>>it=this.availableVehicles.entrySet().iterator();
        while(it.hasNext())
        {
            if(it.next().getKey().getRegistrationNumber().equals(registrationNumber))
            {
                it.remove();
                break;
            }
        }
    }
    private void removeFromRent(String registrationNumber)
    {
        Iterator<Map.Entry<Vehicle,Boolean>>it=this.rentedVehicles.entrySet().iterator();
        while(it.hasNext())
        {
            if(it.next().getKey().getRegistrationNumber().equals(registrationNumber))
            {
                it.remove();
                break;
            }
        }
    }
    public void rentVehicle(String registrationNumber, int rentalPeriod)
    {
        boolean found = this.searchInAvailable(registrationNumber);
        if(found)
        {
            Vehicle vehicle=this.getAvailable(registrationNumber);
            this.removeFromAvailable(registrationNumber);
            this.rentedVehicles.put(vehicle,true);
            int totalRentCost=vehicle.calculateRentalCost(rentalPeriod);
            System.out.printf("Vehicle rented successfully with cost %d\n",totalRentCost);
            totalRevenue+=totalRentCost;
        }
        else
        {
            System.out.println("Vehicle is not found in available Vehicles");
        }
    }
    public void returnVehicle(String registrationNumber)
    {
        Vehicle vehicle=this.getRent(registrationNumber);
        this.availableVehicles.put(vehicle,true);
        this.removeFromRent(registrationNumber);
    }
    public int getTotalRevenue()
    {
        return this.totalRevenue;
    }
}
abstract class Vehicle
{
    private String registrationNumber;
    private String brand;
    private String year;
    public Vehicle(String registrationNumber, String brand, String year) {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.year = year;
    }
    public String getRegistrationNumber() {
        return this.registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    public String getBrand() {
        return this.brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getYear() {
        return this.year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public abstract int calculateRentalCost(int rentalPeriod);
}
class Car extends Vehicle
{

    private int fuelType;
    public int getFuelType() {
        return fuelType;
    }

    public void setFuelType(int fuelType) {
        this.fuelType = fuelType;
    }

    public Car(String registrationNumber, String brand, String year, int fuelType) {
        super(registrationNumber,brand,year);
        this.fuelType=fuelType;
    }
    public int calculateRentalCost(int rentalPeriod)
    {
        return this.fuelType*rentalPeriod;
    }
}
class Motorcycle extends Vehicle
{
    private int engineCapacity;
    public int getEngineCapacity() {
        return this.engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public Motorcycle(String registrationNumber, String brand, String year,int engineCapacity) {
        super(registrationNumber,brand,year);
        this.engineCapacity=engineCapacity;
    }
    public int calculateRentalCost(int rentalPeriod)
    {
        return this.engineCapacity*rentalPeriod;
    }
}
class Bicycle extends Vehicle
{
    public Bicycle(String registrationNumber, String brand, String year) {
        super(registrationNumber,brand,year);
    }
    public int calculateRentalCost(int rentalPeriod)
    {
        return rentalPeriod;
    }
}
public class Main {
    public static void main(String[] args) {
        RentalSystem rentalSystem= new RentalSystem();
        Car car1 = new Car("0001","new","2010",5);
        Car car2 = new Car("0002","new","2012",4);
        Motorcycle motorcycle1 = new Motorcycle("0003","new","2013",5);
        Motorcycle motorcycle2 = new Motorcycle("0004","new","2013",10);
        Bicycle bicycle1 = new Bicycle("0005","new","2013");
        Bicycle bicycle2 = new Bicycle("0006","new","2013");
        rentalSystem.addVehicle(car1);
        rentalSystem.addVehicle(car2);
        rentalSystem.addVehicle(motorcycle1);
        rentalSystem.addVehicle(motorcycle2);
        rentalSystem.addVehicle(bicycle1);
        rentalSystem.addVehicle(bicycle2);
        rentalSystem.rentVehicle(car1.getRegistrationNumber(), 5);
        rentalSystem.rentVehicle(car1.getRegistrationNumber(), 10);
        rentalSystem.returnVehicle(car1.getRegistrationNumber());
        rentalSystem.rentVehicle(car1.getRegistrationNumber(), 10);
        System.out.println(rentalSystem.getTotalRevenue());

    }
}


