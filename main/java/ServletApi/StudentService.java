package ServletApi;

public class StudentService {
    private final StudentDao studentDao;

    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public Student findById(Long id) {
        return studentDao.findById(id).orElseThrow(() -> new StudentNotFoundException("noo" + id));
    }

    public Student create(Student student) {
        return studentDao.create(student);
    }
}
