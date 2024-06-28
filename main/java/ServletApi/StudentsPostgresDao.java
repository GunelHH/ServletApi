package ServletApi;

import java.sql.*;
import java.util.Optional;

public class StudentsPostgresDao implements StudentDao{
    private static final String FIND_BY_ID_SQL="SELECT * FROM students WHERE id=?;";
    @Override
    public Optional<Student> findById(Long id) {
        try(final Connection conn= DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                "postgres","mysecretpassword")){
            PreparedStatement preparedStatement=conn.prepareStatement(FIND_BY_ID_SQL);

            preparedStatement.setLong(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            resultSet.next();
            long studentId=resultSet.getLong("id");
            String studentName=resultSet.getString("name");
            String surname=resultSet.getString("surname");
            int age=resultSet.getInt("age");

            Student student=new Student(studentId,studentName,surname,age);
            return Optional.of(student);

        }catch (SQLException e){
            return  Optional.empty();
        }
    }

    @Override
    public Student create(Student student) {
        return null;
    }
}
