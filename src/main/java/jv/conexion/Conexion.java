package jv.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    //es estatico xq se ha de llamar desde otras clases sin crear una instancia
    //retorna una valor de tipo Connection
    public static Connection getConexion(){
        Connection conexion = null;

        //variables de conexion
        var baseDatos = "estudiantes_db";//nombre de la bd
        var url = "jdbc:mysql://localhost:3306/" + baseDatos;//dirección de la db
        var usuario ="root";
        var pass ="root";

       try{
           //cargamos la clase del driver de mysql en memoria Driver
           Class.forName("com.mysql.cj.jdbc.Driver");

           conexion = DriverManager.getConnection(url, usuario, pass);
       }catch (ClassNotFoundException | SQLException e){
           System.out.println("Ocurrio un error en la conexion: "+e.getMessage());
       }

        return conexion;
    }

    public static void main(String[] args) {
        var conexion = Conexion.getConexion();
        if(conexion != null)
            System.out.println("Conexión exitosa: "+conexion);
        else
            System.out.println("Error al conectarse");
    }
}
