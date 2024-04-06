package jv.datos;

//DAO - Data Acces Object

import jv.dominio.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static jv.conexion.Conexion.getConexion;
public class EstudianteDAO {

    public List<Estudiante> listarEstudiantes(){

        //creamos una lista
        List<Estudiante> estudiantes = new ArrayList<>();

        //PreparedStatement - nos sirve para preparar la sentencia SQL a ejecutar
        PreparedStatement ps;

        //ResulSet - nos sirve para almacenar el resultado obtenido de la db
        ResultSet rs;

        //obj de tipo conexion para la db
        Connection con = getConexion();

        String sql = """
            SELECT * FROM estudiante ORDER BY id_estudiante
            """;
        try{
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            //recuperando todos los registros de la consulta a la db
            while (rs.next()){

                var estudiante = new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));

                estudiantes.add(estudiante);
            }
        }catch (Exception e){
            System.out.println("Ocurrio un error al seleccionar datos: "+e.getMessage());
        }finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Ocurrio un error al cerrar conexion: "+e.getMessage());
            }
        }
        return estudiantes;
    }

    //findById
    public boolean buscarEstudiantePorId(Estudiante estudiante){

        PreparedStatement ps;
        ResultSet rs;

        Connection con = getConexion();
        String sql = """
            SELECT * FROM estudiante WHERE id_estudiante= ?
            """;

        try {
            ps = con.prepareStatement(sql);
            //se adiciona el parametro de la consulta de la variable sql
            ps.setInt(1, estudiante.getIdEstudiante());
            //se usa executeQuery para ejecutar la sentencia y poder recuperar datos
            rs = ps.executeQuery();

            if(rs.next()){
                //recuperamos datos
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                return true;
            }
        }catch (Exception e){
            System.out.println("Ocurrio un error al buscar estudiante: "+e.getMessage());
        }finally {
            try{
                con.close();
            }catch (Exception e){
                System.out.println("Ocurrio un error al cerrar la conexion: "+e.getMessage());
            }
        }
        return false;
    }

    public boolean agregarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = """
                INSERT INTO estudiante(nombre, apellido, telefono, email) 
                VALUES(?,?,?,?)""";
        try {

            ps = con.prepareStatement(sql);
            //se sustituye los parametros de la variable sql en forma ordenada
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            //usamos execute para ejecutar una sentencia sin necesidad de recuperar datos
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("Error al agregar estudiante: "+e.getMessage());
        }finally {
            try{
                con.close();
            }catch (Exception e){
                System.out.println("Ocurrio un error al cerrar la conexion: "+e.getMessage());
            }
        }
        return false;
    }

    public boolean modificarEstudiante(Estudiante estudiante){
        PreparedStatement ps;
        Connection con = getConexion();

        String sql = """
                    UPDATE estudiante SET nombre=?, apellido=?, telefono=?, email=?
                    WHERE id_estudiante=?
                """;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.setInt(5, estudiante.getIdEstudiante());

            ps.execute();

            return true;
        }catch (Exception e){
            System.out.println("Ocurrio un error: "+e.getMessage());
        }finally {
            try{
                con.close();
            }catch (Exception e){
                System.out.println("Ocurrio un error al cerrar la conexion: "+e.getMessage());
            }
        }

        return false;
    }

    //probamos que se ejecute
    public static void main(String[] args) {
        //DAO Estudiante
        var estudianteDao = new EstudianteDAO();

        //agregamos un estudiante
        /*System.out.println("Agregando de Estudiantes: ");
        var nuevoEstudiante = new Estudiante("Carlos", "Lara", "159159159", "tuemail@gmailcom");
        var agregado = estudianteDao.agregarEstudiante(nuevoEstudiante);
        if(agregado)
            System.out.println("Estudiante agregado: "+nuevoEstudiante);
        else
            System.out.println("No se agregado estudiante: "+nuevoEstudiante);*/

        //modificar un registro existente (1)
        var estudianteModificar = new Estudiante(1, "Juan de Dios", "Vilchez Sanchez",
                "951951", "michinito@gmail.com");
        var modificado = estudianteDao.modificarEstudiante(estudianteModificar);
        if(modificado)
            System.out.println("Estudiante modificado: "+estudianteModificar);
        else
            System.out.println("No se modifico estudiante: "+estudianteModificar);


        //listar estudiantes
        System.out.println("Listado de Estudiantes: ");
        List<Estudiante> estudiantes = estudianteDao.listarEstudiantes();
        estudiantes.forEach(System.out::println);

        //buscar por id, le pasamos como parametro 1 al objeto estudiante
        var estudiante1 = new Estudiante(3);
        System.out.println("\nEstudiante antes de la busqueda: "+estudiante1);
        var encontrado = estudianteDao.buscarEstudiantePorId(estudiante1);
        if(encontrado)
            System.out.println("Estudiante encontrado: "+estudiante1);
        else
            System.out.println("No se encontr estudiante: "+estudiante1.getIdEstudiante());


    }
}
