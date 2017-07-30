package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        checkSummDB();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Тестовая таблица");
        primaryStage.setScene(new Scene(root, 378, 600));
        primaryStage.show();
    }
    private void checkSummDB(){ //это класс Вашего коннекта. то что вы там написали копирую просто все как есть

        String strDatabasePath = "/Users/svetlanailina/Desktop/test_fire/EXAM.FDB";
        String strURL="jdbc:firebirdsql:localhost/3050:"+strDatabasePath+"?lc_ctype=WIN1251;sql_dialect=3";
        String strUser="SYSDBA";
        String strPassword= "sysdba";
        String strSQL="SELECT * from OTVET ";
        try
        {
            Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
        }
        catch(IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
        catch(InstantiationException ex)
        {
            ex.printStackTrace();
        }
        catch(ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }

        Connection conn=null;
        try
        {
            conn = DriverManager.getConnection(
                    strURL,
                    strUser, strPassword);

            if (conn==null)
            {
                System.err.println(" Нет соединения с БД! ");
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(strSQL);
            int nColumnsCount = rs.getMetaData().getColumnCount();
            while(rs.next())
            {
                System.out.println();
                for (int n=1;n < nColumnsCount+1;n++)
                {
                    Object obj = rs.getObject(n);
                    if (obj!=null)
                    {
                        System.out.print(obj+" | ");
                    }
                }
            }
            stmt.close();
            conn.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
