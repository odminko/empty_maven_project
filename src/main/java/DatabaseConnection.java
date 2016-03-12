

import java.sql.*;

public class DatabaseConnection {
    protected String user;
    protected String url;
    protected String pass;
    public enum DbType {H2,MySQL}
    protected DbType dbType;

    public DatabaseConnection(DbType dbType, String user, String pass, String url){
        this.user = user;
        this.pass = pass;
        this.url = url;
        this.dbType = dbType;
        switch (dbType) {
            case H2:
                try {
                    Class.forName("org.h2.Driver").newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MySQL:
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }



    public ResultSet getResultSet(String sqlQuery) {
        try {

            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();
            //String SQLQuery = "";
            ResultSet rs = statement.executeQuery(sqlQuery);

            //statement.close();
            //connection.close();
            return  rs;
        } catch (Exception e) {
            return null;
        }
    }

    public void execute(String sqlQuery) {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, pass);
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            //String SQLQuery = "";
            statement.execute();//  .executeQuery(sqlQuery);

            //statement.close();
            //connection.close();
            //return  rs;
        } catch (Exception e) {
            //return null;
            e.printStackTrace();
        }
    }
}
