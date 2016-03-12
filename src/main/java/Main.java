import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args){

        Catalogue cat;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter database engine name (H2/MySQL):");
        String command = reader.readLine();
        switch (command.toUpperCase()) {
            case "H2":
                cat = new Catalogue(DatabaseConnection.DbType.H2);
                break;
            case "MYSQL" :
                cat = new Catalogue(DatabaseConnection.DbType.MySQL);
                break;
            default:
                cat = new Catalogue(null);
        }

        //Catalogue cat = new Catalogue(DatabaseConnection.DbType.H2);
        //DatabaseConnection db = new DatabaseConnection("root","MySQLRoot","jdbc:mysql://localhost:3306/dev1");


        while (!command.toUpperCase().equals("EXIT")){
            command = reader.readLine();
            switch (command.toUpperCase()){
                case "LIST":
                    cat.describe(0);
                    break;
                case "NEW":
                    break;
            }
        }

        /*
        Item item1 = new Item("item1",10);
        cat.put(item1);
        //System.out.println(cat.getItem(item1.getId()));
        Item item2 = new Item("item2",15);
        cat.put(item2);
        Item item3 = new Item("item3",20);
        cat.put(item3);
        Item item4 = new Item("item4",25);
        cat.put(item4);
        ItemContainer container1 = new ItemContainer("container1",0);
        cat.put(container1);
        ItemContainer container2 = new ItemContainer("container2",0);
        cat.put(container2);

        cat.push(item1.getId(),container1.getId());
        cat.push(item2.getId(),container1.getId());
        cat.push(item3.getId(),container1.getId());
        cat.push(item4.getId(),container2.getId());
        cat.push(container1.getId(),container2.getId());

        //System.out.println(cat.getItem(item1.getId()));

        cat.describe(0);

        System.out.println(cat.pull(item2.getId()));

        //System.out.println(container1.getWeight());
        //System.out.println(container2.getWeight());
        //container1.describe(0);
        //container2.describe(0);
        */
        cat.getCatalogue();
        /*
        ItemContainer container1 = new ItemContainer("container2",0);
        cat.put(container1);
        Item item = new Item("simple item",10);
        cat.put(item);
        */
        //cat.push(2,1);
        //cat.push(1,0);
        //cat.drop(0);
        cat.pull(2);
        cat.describe(0);
    }
}
