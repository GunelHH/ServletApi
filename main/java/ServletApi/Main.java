package ServletApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server=new Server(8080);
        final ObjectMapper objectMapper=new ObjectMapper();
        final StudentDao studentDao=new StudentsPostgresDao();

        final StudentService studentService=new StudentService(studentDao);
        final StudentServlet studentServlet=new StudentServlet(studentService,objectMapper);
        final ServletContextHandler handler=new ServletContextHandler();
        handler.addServlet(new ServletHolder(studentServlet),"/api/v1/students");

        server.setHandler(handler);
        server.join();
        server.start();
    }
}
