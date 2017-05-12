using sdi3_13.Cli_CSharp.UserService;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
namespace ConsoleApplication1
{
    class Program
    {
        static void Main(string [] args){
            EjbUserServiceService us = new EjbUserServiceService();
            //EjbAlumnosServiceService ws =  new EjbAlumnosServiceService();
            user[] list = us.listUsers();

            foreach(user usher in list)
                printLine(usher);
            Console.WriteLine("\n -- Servicio Web consumido desde C#.Net --");
            Console.ReadKey();
        }

        private static  void printLine(user usher){
            string format = "{0,-15} {1,-15} {2,-10} {3,-20}";
            string line = String.Format(format,usher.id, usher.email, usher.login, usher.password, usher.status);
            Console.WriteLine(line);
        }

        private static void printHeader(){
            Console.WriteLine("\n\t\t * *Listado de alumnos * *\n\n");
            string format ="{0,-15} {1, -15} {2, -10} {3, -20} {4, -10}";
            string line = String.Format(format, "ID_________", "EMAIL____________", "LOGIN", "PASSWORD__________", "STATUS");
            Console.WriteLine(line);
        }
}
}