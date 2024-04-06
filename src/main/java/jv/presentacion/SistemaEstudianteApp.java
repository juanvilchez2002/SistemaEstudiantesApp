package jv.presentacion;

import jv.datos.EstudianteDAO;
import jv.dominio.Estudiante;

import java.util.Scanner;

public class SistemaEstudianteApp {

    public static void main(String[] args) {

        var salir = false;
        var consola = new Scanner(System.in);

        //se crea una instancia clase sericio
        var estudianteDAO = new EstudianteDAO();

        while (!salir){
            try{
                mostrarMenu();
                salir = ejecutarOperaciones(consola, estudianteDAO);
            }catch (Exception e){
                System.out.println("Ocurrion un error al ejecutar operacion: "+e.getMessage());
            }
            System.out.println();
        }//fin while
    }

    public static void mostrarMenu(){
        System.out.print("""
                *** Sistema de Estudiantes ***
                1. Listar Estudiantes
                2. Buscar Estudiante
                3. Agregar Estudiante
                4. Modificar Estudiante
                5. Eliminar Estudiante
                6. Salir
                Elije una opcion:
                
                """);
    }

    private static boolean ejecutarOperaciones(Scanner consola,
                                               EstudianteDAO estudianteDAO){
        var opcion = Integer.parseInt(consola.nextLine());
        var salir = false;

        switch (opcion){
            case 1 ->{
                //listar estudiantes
                System.out.println("Listado de Estudiantes..");
                var estudiantes = estudianteDAO.listarEstudiantes();
                estudiantes.forEach(System.out::println);
            }
            case 2 ->{
                System.out.println("Ingrese el id de estudiante a buscar: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                var estudiante = new Estudiante(idEstudiante);
                var encontrado = estudianteDAO.buscarEstudiantePorId(estudiante);
                if(encontrado)
                    System.out.println("Estudiante encontrado: "+estudiante);
                else
                    System.out.println("No se encontr estudiante: "+estudiante);
            }
            case 3->{
                System.out.println("Agregar Estudiante");
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Telefono: ");
                var telefono = consola.nextLine();
                System.out.print("Email: ");
                var email = consola.nextLine();

                var estudiante = new Estudiante(nombre, apellido, telefono, email);
                var agregado = estudianteDAO.agregarEstudiante(estudiante);

                if(agregado)
                    System.out.println("Estudiante agregado: "+estudiante);
                else
                    System.out.println("No se agregado estudiante: "+estudiante);
            }
            case 4->{
                System.out.println("Modificar estudiante: ");
                System.out.println("Id Estudiante: ");
                var idEstudiante = Integer.parseInt(consola.nextLine());
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Telefono: ");
                var telefono = consola.nextLine();
                System.out.print("Email: ");
                var email = consola.nextLine();

                var estudiante = new Estudiante(idEstudiante,nombre, apellido, telefono, email);
                var modificado = estudianteDAO.modificarEstudiante(estudiante);

                if(modificado)
                    System.out.println("Estudiante modificado: "+estudiante);
                else
                    System.out.println("No se modifico estudiante: "+estudiante);
            }
            case 5 ->{
                System.out.println("Eliminar estudiante");
                System.out.println("Id Estudiante: ");
                var IdEstudiante = Integer.parseInt(consola.nextLine());
                var estudiante = new Estudiante(IdEstudiante);
                var eliminar = estudianteDAO.eliminarEstudiante(estudiante);

                if(eliminar)
                    System.out.println("Estudiante eliminado: "+estudiante);
                else
                    System.out.println("No se elimino estudiante: "+estudiante);
            }
            case 6 ->{
                System.out.println("Hasta pron!!!");
                salir = true;
            }
            default -> System.out.println("Opcion no reconocida");

        }
        return salir;
    }
}