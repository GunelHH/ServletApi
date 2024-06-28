package ServletApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.OutputStream;

public class StudentServlet extends HttpServlet {

    private final StudentService studentService;
    private final ObjectMapper objectMapper;

    public StudentServlet(StudentService studentService, ObjectMapper objectMapper) {
        this.studentService = studentService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id.isEmpty() || id == null || id.isBlank()) {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            try (OutputStream os = resp.getOutputStream()) {
                String message = "id: [" + id + "] is invalid!";
                os.write(message.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Student student = null;
        try {
            student = studentService.findById(Long.valueOf(id));
        } catch (StudentNotFoundException se) {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            try (OutputStream os = resp.getOutputStream()) {
                os.write(se.getMessage().getBytes());

            } catch (IOException e) {
                e.printStackTrace();
            }
            se.printStackTrace();
        }
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(student);
            try (OutputStream os = resp.getOutputStream()) {
                resp.setContentType(MediaType.APPLICATION_JSON);
                os.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JsonProcessingException e) {
            resp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       //json gelib gelmediyini nece yoxlamaq olar?
        if (req.getContentType()!=MediaType.APPLICATION_JSON) {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            try (OutputStream os = resp.getOutputStream()) {
                String message = "Json format is invalid!";
                os.write(message.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
