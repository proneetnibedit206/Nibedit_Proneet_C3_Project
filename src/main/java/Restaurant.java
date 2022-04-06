import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    private List<Item> menu = new ArrayList<Item>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen(LocalTime currenTime) {
        // the curren Time of searching should be greater than or equal to open time and less than or equal to close time
        int deltaTimeFromOpen = currenTime.compareTo(openingTime);
        int deltaTimeFromClose = closingTime.compareTo(currenTime);
        if (deltaTimeFromOpen >= 0 && deltaTimeFromClose >=0 ) {
            return true;
        }
        return false;
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {
        if(menu != null && menu.size() > 0){
            return menu;
        }
        return null;
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }
    
    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public String getName() {
        return name;
    }

    public int getTotalOrder(List<Item> items){
        if(items == null || items.size() == 0){
            return 0;
        }
        int totalCost = 0;
        for(Item item : items){
            totalCost+=item.getPrice();
        }
        return totalCost;
    }

}
