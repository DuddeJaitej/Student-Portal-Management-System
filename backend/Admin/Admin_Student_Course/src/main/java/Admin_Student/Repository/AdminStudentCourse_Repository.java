package Admin_Student.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.AdminStudentCourse.Admin_Student_Course.Entity.AdminStudentCourse;

@Repository
public interface AdminStudentCourse_Repository extends JpaRepository<AdminStudentCourse, Long>{
	
	List<AdminStudentCourse> findByFacultyName(String facultyName);
	
	AdminStudentCourse findByCourseCode(String courseCode);
	
	List<AdminStudentCourse> findBySection(String section);
	
}
