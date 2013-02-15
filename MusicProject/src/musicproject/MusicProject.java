
package musicproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MusicProject 
{
    private static final String HELP_MESSAGE =
        "*** Commands: create, load, find <n>, add, delete, teachers, quit\n" +
        "***           students [ <first> <last> ], classes [ <first> <last> ]";
    
    public static void main(String args[]) 
    {
        BufferedReader stdin = 
                new BufferedReader(new InputStreamReader(System.in));
        String command;
                                    
        Class klasses[] = {Student.class, Accessories.class, 
                           Description.class, Instrument.class};
        HibernateContext.addClasses(klasses);

        do {
            System.out.print("\nCommand? ");
            
            try {
                command = stdin.readLine();
            }
            catch (java.io.IOException ex) {
                command = "?";
            }
            
            String parts[] = command.split(" ");
            
            if (command.equalsIgnoreCase("create")) {
                HibernateContext.createSchema();
            }
            else if (command.equalsIgnoreCase("load")) {
                Student.load();
                Instrument.load();
                Accessories.load();
            }
            else if (command.equalsIgnoreCase("add")) {
                //Student.add();
            }
            else if (command.equalsIgnoreCase("delete")) {
               // Student.delete();
            }
            else if (command.equalsIgnoreCase("list")) {
                Accessories.list();
            }
/*            else if (parts[0].equalsIgnoreCase("find") &&
                    (parts.length >= 2)) {
                long id = Long.parseLong(parts[1]);
                Student student = Student.find(id);
                
                // Because the Student object is fetched from the database,
                // we can access its fields only within a session. 
                // Otherwise, we'll get a LazyInitializationException.
                if (student != null) {
                    student.printInSession();
                }
                else {
                    System.out.printf("*** No student with id %d\n", id);
                }
            }
            else if (parts[0].equalsIgnoreCase("students")) {
                switch (parts.length) {
                    case 1: Student.list(); break;
                    case 3: Student.studentsOf(parts[1], parts[2]); break;
                }
            }
            else if (parts[0].equalsIgnoreCase("classes")) {
                switch (parts.length) {
                    case 1: Klass.list(); break;
                    case 3: Klass.classesOf(parts[1], parts[2]); break;
                }
            }
  
  */
            else if (!command.equalsIgnoreCase("quit")) {
                System.out.println(HELP_MESSAGE);
            }
        } while (!command.equalsIgnoreCase("quit"));
    }
}
