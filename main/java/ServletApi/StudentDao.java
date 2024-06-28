package ServletApi;

import java.util.Optional;

public interface StudentDao {

    Optional<Student> findById(Long id);
    Student create(Student student);
}
