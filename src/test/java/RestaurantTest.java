import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    RestaurantService service = new RestaurantService();
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void beforeAllTestCases(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Pizza",120);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        LocalTime currenTime = LocalTime.parse("13:08:00");
        assertEquals(true,restaurant.isRestaurantOpen(currenTime));
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime currenTimeBeforeOpenTime = LocalTime.parse("09:08:00");
        LocalTime currenTimeAfterCloseTime = LocalTime.parse("23:08:00");
        assertEquals(false,restaurant.isRestaurantOpen(currenTimeBeforeOpenTime));
        assertEquals(false,restaurant.isRestaurantOpen(currenTimeAfterCloseTime));

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<<TDD case>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void when_calculating_total_cost_with_no_items_it_should_return_0(){
        List<Item> list = new ArrayList<>();
        int totalOrderActual = restaurant.getTotalOrder(list);
        assertEquals(0,totalOrderActual);
    }

    @Test
    public void when_calculating_total_cost_with_2_items_it_should_return_actual_price(){
        List<Item> items = restaurant.getMenu();
        int totalOrderActual = restaurant.getTotalOrder(items);
        assertEquals(389,totalOrderActual);
    }

    @Test
    public void order_value_should_reduce_cumulative_total_when_an_item_removed(){

        List<Item> items = restaurant.getMenu();
        int total = restaurant.getTotalOrder(items);
        int afterTotal = items.get(1).getPrice();
        items.remove(1);
        assertEquals(total-afterTotal,restaurant.getTotalOrder(items));
    }

    @Test
    public void order_value_should_increase_cumulative_total_when_an_item_is_added(){
        List<Item> items = restaurant.getMenu();
        int total = restaurant.getTotalOrder(items);
        items.add(new Item("Pasta" , 200));
        int addedItemprice = items.get(items.size()-1).getPrice();
        assertEquals(total+addedItemprice,restaurant.getTotalOrder(items));
    }



}