//import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by Odminko on 12.03.2016.
 */
public class ItemsTest {

    @Test
    public void createItemsTest(){
        Item item = new Item("",0);
        item = new Item(null, -5);
        item.describe(-5);
        Item container = new ItemContainer(null,8);
        container.put(null);
        container.describe(0);
        container.put(item);
        container.describe(2);
    }
}
