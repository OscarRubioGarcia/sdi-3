using sdi3_13.Cli_CSharp.localhost;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
namespace ConsoleApplication1
{
    class Program
    {
        static EjbUserServiceService userService;

        public static void Main(String[] args)
        {
            Program main = new Program();
            try
            {
                main.Run(null);
            } catch (Exception e)
            {
                Console.Write(e.StackTrace);
            }
        }
        void Run(string[] args)
        {
            userService = new EjbUserServiceService();
            //EjbAlumnosServiceService ws =  new EjbAlumnosServiceService();
            //user[] list = us.listusers();

            //foreach(user usher in list)
            //  printLine(usher);

            Console.WriteLine("Bienvenido a GTD Task Manager \nSomos Óscar y "
                + "Christian, tus hosts\nPor favor, elige una de las opciones "
                + "siguientes o escribe exit para salir o volver al menú:\n"
                + "---------------");
            String opcion = "";

            while (true)
            {
                Console.WriteLine("");
                //currentMenu.showOptions();
                Console.WriteLine("1: Listar usuarios del sistema (y tareas)\n"
                                    + "2: Deshabilitar un usuario\n"
                                    + "3: Eliminar un usuario (y tareas)\n"
                                    + "4: Habilitar un usuario");

                opcion = Console.ReadLine();
                if ("1".Equals(opcion))
                    printAll();
                else if ("2".Equals(opcion))
                    disableuser();
                else if ("3".Equals(opcion))
                    deleteuser();
                else if ("4".Equals(opcion))
                    enableuser();
                else if ("exit".Equals(opcion))
                    break;
                else
                    Console.WriteLine("Esa opción no es válida");
            }

            Console.WriteLine("Hasta la próxima!");

            Console.WriteLine("\n -- Servicio Web consumido desde C#.Net --");
            Console.ReadKey();
        }

        private static void printHeader()
        {
            Console.WriteLine("\n\t\t * *Listado de alumnos * *\n\n");
            string format = "{0,-15} {1, -15} {2, -10} {3, -20} {4, -10}";
            string line = String.Format(format, "ID_________", "EMAIL____________", "LOGIN", "PASSWORD__________", "STATUS");
            Console.WriteLine(line);
        }




        /**
         * Muestra los usuarios habilitados y da la opción de deshabilitarlos. Estos
         * usuarios no podrán loguearse.
         * @throws IOException
         * @throws NumberFormatException
         * @throws BusinessException_Exception
         * @throws EntityNotFoundException_Exception
         */
        private void disableuser()
        {

            printEnabledusers(true);

            String opcion = "";

            while (true)
                try
                {
                    Console.WriteLine("Introduce el id del usuario que desees "
                                        + "deshabilitar");
                    opcion = Console.ReadLine();

                    if ("exit".Equals(opcion)) return;

                    userService.disableUser(Convert.ToInt64(opcion),true);

                    return;
                } catch (FormatException nfe)
                {
                    Console.WriteLine("Type a number, please");
                }
        }

        /**
         * Muestra los usuarios deshabilitados y da la opción de habilitarlos. Estos
         * usuarios podrán loguearse.
         * @throws IOException
         * @throws NumberFormatException
         * @throws BusinessException_Exception
         * @throws EntityNotFoundException_Exception
         */
        private void enableuser()
        {

            printEnabledusers(false);
            String opcion = "";

            while (true)
                try
                {
                    Console.WriteLine("Introduce el id del usuario que desees "
                                        + "habilitar");
                    opcion = Console.ReadLine();

                    if ("exit".Equals(opcion)) return;

                    userService.enableUser(Convert.ToInt64(opcion),true);

                    return;
                } catch (FormatException nfe)
                {
                    Console.WriteLine("Type a number, please");
                }
        }
        /**
         * Elimina de la base de datos todo rastro del ususario (datos personales,
         * tareas y categorías).
         * @throws IOException
         * @throws NumberFormatException
         * @throws EntityNotFoundException_Exception
         */
        private void deleteuser()
        {

            printusers();
            String opcion = "";
            Console.WriteLine("Introduce el id del usuario que "
                                        + "desees eliminar (también eliminará sus "
                                        + "tareas y categorías, ¡cuidado!)");
            while (true) { 
                try
                {
                    opcion = Console.ReadLine();

                    if ("exit".Equals(opcion)) return;

                    userService.deleteUser((long)Convert.ToInt64(opcion), true);

                    return;
                } catch (FormatException e)
                {
                    Console.WriteLine("Type a number, please");
                }
            }
        }
        
        /**
         * Muestra en una lista a todos los usuarios con el número de tareas de 
         * diversos tipos (completadas, completadas con retraso, planificadas y sin
         * planificar)
         * @throws BusinessException_Exception 
         */
        private void printAll()
        {
            user[] users;
            users = userService.allUsersInfoAndTasks();
            StringBuilder userBuilder = new StringBuilder();

            foreach (user user in users)
            {
                Console.WriteLine(user.login + " - " + user.email
                        + " - " + user.status + " - " + user.isAdmin
                        + "\n\t--" + user.tareas[0] + " tareas completadas"
                        + "\n\t--" + user.tareas[1] + " de ellas con retraso"
                        + "\n\t--" + user.tareas[2] + " planificadas"
                        + "\n\t--" + user.tareas[3] + " sin planificar");
                Console.WriteLine(userBuilder.ToString());

                Console.WriteLine();
            }
        }

        /**
         * true enabled, false disabled
         * @param status
         */
        private void printEnabledusers(bool enabled)
        {
            ICollection<user> users;
            try
            {
                users = userService.listUsers();
            } catch (Exception e)
            {
                Console.WriteLine("Se ha producido un error de conexión con la bbdd");
                Console.WriteLine(e.StackTrace);
                return;
            }

            Console.WriteLine("Listado Usuarios");
            if (enabled)
            {
                foreach (user a in users)
                {
                    if (userStatus.ENABLED == a.status)
                        printLine(a);
                }
            } else
            {
                foreach (user a in users)
                {
                    if (userStatus.DISABLED == a.status)
                        printLine(a);
                }
            }

        }
        private void printusers()
        {
            ICollection<user> users;
            try
            {
                users = userService.listUsers();
            } catch (Exception e)
            {
                Console.WriteLine("Se ha producido un error de conexión con la bbdd");
                return;
            }

            Console.WriteLine("Listado Usuarios");
            foreach (user a in users)
            {
                printLine(a);
            }
        }
        private void printLine(user a)
        {
            Console.WriteLine(a.id + " - " + a.email + " - "
                                + a.login + " - " + a.password
                                + " - " + a.status);
        }
    }
}