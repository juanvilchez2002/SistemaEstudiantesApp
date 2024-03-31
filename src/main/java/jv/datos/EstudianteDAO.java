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

    //probamos que se ejecute
    public static void main(String[] args) {
        //listar estudiantes
        System.out.println("Listado de Estudiantes: ");

        var estudianteDao = new EstudianteDAO();
        List<Estudiante> estudiantes = estudianteDao.listarEstudiantes();
        estudiantes.forEach(System.out::println);

    }
}
