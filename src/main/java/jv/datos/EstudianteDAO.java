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
            ps.setInt(1, estudiante.getIdEstudiante());
            rs = ps.executeQuery();

            if(rs.next()){
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

    //probamos que se ejecute
    public static void main(String[] args) {
        //listar estudiantes
        System.out.println("Listado de Estudiantes: ");

        var estudianteDao = new EstudianteDAO();
        List<Estudiante> estudiantes = estudianteDao.listarEstudiantes();
        estudiantes.forEach(System.out::println);

        //buscar por id, le pasamos como parametro 1 al objeto estudiante
        var estudiante1 = new Estudiante(1);
        System.out.println("Estudiante antes de la busqueda: "+estudiante1);
        var encontrado = estudianteDao.buscarEstudiantePorId(estudiante1);
        if(encontrado)
            System.out.println("Estudiante encontrado: "+estudiante1);
        else
            System.out.println("No se encontr estudiante: "+estudiante1.getIdEstudiante());

    }
}
