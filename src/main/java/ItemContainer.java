import java.util.ArrayList;

public class ItemContainer extends Item {
    public ArrayList<Item> container;

    public ItemContainer(String name, int weight){
        super(name, weight);
        container = new ArrayList<Item>();

    }

    public ItemContainer(int id, String name, int weight){
        super(id, name, weight);
        container = new ArrayList<Item>();

    }

    @Override
    public boolean put(Item item) {
        if (item!=null) {
            item.parentId = id;
            container.add(item);
            //item = null;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getWeight() {
        int w = 0;
        for (Item item : container){
            w += item.getWeight();
        }
        return w + weight;
        //return super.getWeight();
    }

    @Override
    public void describe(int i) {
        //super.describe(i);
        for (int x = 0; x<i; x++)System.out.print(" ");
        System.out.println(name+"(id:"+id+", w:"+weight+",total w:"+getWeight()+")");
        for (int x = 0; x < container.size(); x++) container.get(x).describe(i+10);
    }

    @Override
    public Item seekItem(int id) {
        Item result = super.seekItem(id);
        if (result==null) for (Item item : container) {
            result = item.seekItem(id);
            if (result!=null) break;
        }
        return result;
    }

    @Override
    public Item seekChild(int id) {
        Item result = null;
        for (Item item : container) {
            if (item.id == id) {
                result = this;
            } else {
                result = item.seekChild(id);
            }
            if (result!=null) break;
        }
        return result;
    }

    @Override
    public boolean remove(Item item) {
        if (container.remove(item)) return true;
        else return false;
    }

    @Override
    public boolean isContainer() {
        return true;
    }
}
