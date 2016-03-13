import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Catalogue {
    private HashMap<Integer, Item> map;
    DatabaseConnection dbConnection;

    public Catalogue(DatabaseConnection.DbType dbType){
        map = new HashMap<Integer, Item>();
        switch (dbType){
            case H2:
                try {
                    dbConnection = new DatabaseConnection(DatabaseConnection.DbType.H2,"sa","","jdbc:h2:~/test");
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case MySQL:
                try {
                    dbConnection = new DatabaseConnection(DatabaseConnection.DbType.MySQL,"root","MySQLRoot","jdbc:mysql://localhost:3306/dev1");
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            default:
                dbConnection = null;
        }

        //dbConnection = new DatabaseConnection("root","MySQLRoot","jdbc:mysql://localhost:3306/dev1");
        //dbConnection = new DatabaseConnection(DatabaseConnection.DbType.H2,"sa","","jdbc:h2:~/test");
    }

    public boolean getCatalogue(){
        if (dbConnection!=null) {
            try {
                String SQLQuery = "select * from items";
                ResultSet rs = dbConnection.getResultSet(SQLQuery);
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int weight = rs.getInt("weight");
                    String name = rs.getString("name");
                    Item item;
                    if (rs.getBoolean("isContainer")) item = new ItemContainer(id, name, weight);
                    else item = new Item(id, name, weight);
                    int parent = rs.getInt("parentId");
                    item.parentId = parent;
                    map.put(id, item);
                }
                //rs.beforeFirst();
                rs.close();
                for (Map.Entry<Integer, Item> entry : map.entrySet()) {
                    if (entry.getValue().haveParent()) map.get(entry.getValue().getParentId()).put(entry.getValue());
                }

                //return true;
            } catch (Exception e) {
                e.printStackTrace();

            }
        } //else return false;
        return dbConnection==null?false:true;
    }

    public boolean put(Item item){
        if (item.id==-1) {
            Set<Integer> keys = map.keySet();
            for (int x : keys) if (item.id<x) item.id = x;
            item.id++;
        }
        map.put(item.id, item);
        //if (item  )
        if (dbConnection!=null) {
            String SQLQuery = "insert into items (id, name, weight, isContainer, parentId) values ";
            SQLQuery += "(" + item.id + ",'" + item.name + "'," + item.weight + "," + item.isContainer() + "," + item.parentId + ")";
            //ResultSet rs =
            dbConnection.execute(SQLQuery);
            System.out.println(SQLQuery);

        }
        return true;
    }

    public boolean drop(int id){
        if (dbConnection!=null) {
            String SQLQuery = "delete from items  where id=" + id;
            dbConnection.execute(SQLQuery);
        }
        //map.remove(id);
        return (map.remove(id)!=null);
    }

    public boolean push(int idItem, int idContainer){

        if (getItem(idContainer).put(getItem(idItem))) {
            if (dbConnection!=null) {
                String SQLQuery = "update items set parentId=" + idContainer + " where id=" + idItem;
                dbConnection.execute(SQLQuery);
            }
            map.remove(idItem);
            return true;
        } else return false;

    }

    public boolean pull(int id){
        Item parent = getParent(id);
        //System.out.println("Parent for id="+id+" is "+parent.name);
        Item item = getItem(id);
        //System.out.println("Item for id="+id+" is "+item.name);
        if (parent!=null) {
            if (dbConnection!=null) {
                String SQLQuery = "update items set parentId=0 where id=" + id;
                dbConnection.execute(SQLQuery);
            }
            map.put(id, getItem(id));
            parent.remove(item);

            return true;
        } else
        return false;
    }

    private Item getParent(int id) {
        Item result = null;
        for (Map.Entry<Integer,Item> entry : map.entrySet()) {
            result = entry.getValue().seekChild(id);
            if (result!=null) break;
        }
        return result;
    }

    public Item getItem(int id){
        Item result = map.get(id);
        if (result==null)
            for (Map.Entry<Integer,Item> entry : map.entrySet()) {
                result = entry.getValue().seekItem(id);
                if (result!=null) break;
            }
        return result;
    }

    public void describe(int tabs) {
        for (Map.Entry<Integer,Item> entry : map.entrySet())  entry.getValue().describe(tabs);
    }
}
