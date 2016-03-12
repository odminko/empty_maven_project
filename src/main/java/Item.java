
public class Item {
    public String name;
    public int weight;
    public int id;
    public int parentId;

    public int getParentId() {
        return parentId;
    }

    public boolean haveParent(){
        return parentId>0?true:false;
    }

    public Item(String name, int weight) {
        this.name = name;
        this.weight = weight;
        id=-1;
    }

    public Item(int id, String name, int weight) {
        this.name = name;
        this.weight = weight;
        this.id=id;
    }

    public boolean put(Item item) {
        return false;
    }

    public int getWeight(){
        return weight;
    }

    public void describe(int i) {
        for (int x = 0; x<i; x++)System.out.print(" ");
        System.out.println(name+"(id:"+id+", w:"+weight+")");
    }

    public Item seekItem(int id) {
        return (this.id==id)?this:null;
    }

    public Item seekChild(int id) {
        return null;
    }

    public boolean remove(Item item) {
        return false;
    }

    public int getId() {
        return id;
    }

    public boolean isContainer() {
        return false;
    }
}
