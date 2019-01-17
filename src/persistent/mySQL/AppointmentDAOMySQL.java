package persistent.mySQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import application.classesApp.Appointment;
import application.classesApp.MyDate;
import application.classesApp.RevisionSession;
import application.classesApp.SingleSession;
import application.classesApp.Student;
import application.classesApp.Subject;
import persistent.DAO.AppointmentDAO;

/**
 * @author julienroumagnac lucadebeir
 */

/**
 * <b>Appointment</b> is a<b>Single Session</b>or a <b>Revision Session</b>
 */
public class AppointmentDAOMySQL extends AppointmentDAO {
    
	public AppointmentDAOMySQL(Connection con) {
		super(con);
    }

	@Override
	public void createSingleSession(SingleSession s) {
		// TODO Auto-generated method stub
		try {
			this.con.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY).executeUpdate("INSERT INTO SingleSession VALUES (NULL,'" + s.getDateAppointment() + "','" + s.getIdSubject() + "','" + s.getTeacher().getId() + "','" + s.getStudent().getId() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Appointment getAppointmentById(int id) {
		// TODO Auto-generated method stub
		RevisionSession appointment = null;
		Student teacher;
		Student student;
		ArrayList<Student> listStudent = new ArrayList<Student>();
		    
		try {
			ResultSet result = this.con.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM RevisionSession WHERE idRevisionSession = " + id);
			  
			if (result.first()) {
				  
				ResultSet resultParticipate = this.con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Participate WHERE idRevisionSession = " + id);
	  
				while(resultParticipate.next()) {
					
					ResultSet resultStudent = this.con.createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Student WHERE idStudent = " + resultParticipate.getInt("idStudent"));
					
					student = new Student(
							resultStudent.getInt("idStudent"),
							resultStudent.getString("nameStudent"),
							resultStudent.getString("firstNameStudent"),
							resultStudent.getString("emailStudent"),
							resultStudent.getString("password"),
							resultStudent.getString("loginID"),
					        null);
					
					listStudent.add(student);
				}
				
				ResultSet resultTeacher = this.con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Student WHERE idStudent = " + result.getInt("idTeacher"));
				  
				teacher = new Student(
						resultTeacher.getInt("idStudent"),
						resultTeacher.getString("nameStudent"),
						resultTeacher.getString("firstNameStudent"),
						resultTeacher.getString("emailStudent"),
						resultTeacher.getString("password"),
						resultTeacher.getString("loginID"),
				        null);
				
				appointment = new RevisionSession(id, teacher, listStudent);
			}
			  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return appointment;
	}
	
	

	@Override
	public Student getTeacherStudentById(int id) {
		// TODO Auto-generated method stub
		Student student = null;
		
	    try {
		    ResultSet result = this.con.createStatement(
		    ResultSet.TYPE_SCROLL_INSENSITIVE,
		    ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Student WHERE idStudent = " + id);
		    if(result.first())
		      student = new Student(
		    	id,
		        result.getString("nameStudent"),
		        result.getString("firstNameStudent"),
		        result.getString("emailStudent"),
		        result.getString("password"),
		        result.getString("loginID"),
		        null);         
		  } catch (SQLException e) {
		    e.printStackTrace();
		  }
		  return student;
	}

	@Override
	public SingleSession getSingleSessionBySubject(String subject) {
		// TODO Auto-generated method stub
		SingleSession singleSession = null;
		Student teacher;
		Student student;
		
		try {
			ResultSet resultSubj = this.con.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Subject WHERE nameSubject = " + subject);
			
			if (resultSubj.first()) {
				Subject subj = new Subject(
						resultSubj.getInt("idSubject"),
						subject);
					
				ResultSet result = this.con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM SingleSession WHERE idSubject = " + resultSubj.getInt("idSubject"));
					
				if (result.first()) {
					ResultSet resultTeacher = this.con.createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Student WHERE idTeacher = " + result.getInt("idTeacher"));
					
					teacher = new Student(
							resultTeacher.getInt("idStudent"),
							resultTeacher.getString("nameStudent"),
							resultTeacher.getString("firstNameStudent"),
							resultTeacher.getString("emailStudent"),
							resultTeacher.getString("password"),
							resultTeacher.getString("loginID"),
					        null);
					
					ResultSet resultStudent = this.con.createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Student WHERE idStudent = " + result.getInt("idStudent"));
					
					student = new Student(
							resultStudent.getInt("idStudent"),
							resultStudent.getString("nameStudent"),
							resultStudent.getString("firstNameStudent"),
							resultStudent.getString("emailStudent"),
							resultStudent.getString("password"),
							resultStudent.getString("loginID"),
					        null);
					
					singleSession = new SingleSession(result.getInt("idSingleSession"), teacher, student);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return singleSession;
	}

	@Override
	public void createRevisionSession(RevisionSession r) {
		// TODO Auto-generated method stub
		try {
			this.con.createStatement(
			ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY).executeUpdate("INSERT INTO RevisionSession VALUES (NULL,'" + r.getDateAppointment() + "','" + r.getIdSubject() + "','" + r.getTeacher().getId() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Appointment getSingleSessionById(int id) {
		// TODO Auto-generated method stub
		SingleSession appointment = null;
		Student teacher;
		Student student;
		    
		try {
			ResultSet result = this.con.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM SingleSession WHERE idSingleSession = " + id);
			  
			if (result.first()) {
				  
				ResultSet resultStudent = this.con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Student WHERE idStudent = " + result.getInt("idStudent"));
				  
				student = new Student(
						resultStudent.getInt("idStudent"),
						resultStudent.getString("nameStudent"),
						resultStudent.getString("firstNameStudent"),
						resultStudent.getString("emailStudent"),
						resultStudent.getString("password"),
						resultStudent.getString("loginID"),
				        null);
								
				ResultSet resultTeacher = this.con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Student WHERE idStudent = " + result.getInt("idTeacher"));
				  
				teacher = new Student(
						resultTeacher.getInt("idStudent"),
						resultTeacher.getString("nameStudent"),
						resultTeacher.getString("firstNameStudent"),
						resultTeacher.getString("emailStudent"),
						resultTeacher.getString("password"),
						resultTeacher.getString("loginID"),
				        null);
				  
				appointment = new SingleSession(id, teacher, student);
			}
			  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return appointment;
	}
	
	public Student getStudentOfOneAppointment(int idStudent) throws SQLException {
		Student student = null;
		ResultSet resultStudent = this.con.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Student WHERE idStudent = " + idStudent);
		if(resultStudent.first()) {
			student = new Student(
					idStudent,
					resultStudent.getString("nameStudent"),
					resultStudent.getString("firstNameStudent"),
					resultStudent.getString("emailStudent"),
					resultStudent.getString("password"),
					resultStudent.getString("loginID"),
			        null);
		}
		return student;
	}
	
	public ArrayList<Student> getListStudentOfOneAppointment(RevisionSession rs) throws SQLException {
		ArrayList<Student> listStudent = new ArrayList<Student>();
		for(Student s : rs.getStudent()) {
			listStudent.add(getStudentOfOneAppointment(s.getId()));
		}
		return listStudent;
	}
	
	public Subject getSubjectOfOneAppointmentById(int idSubject) throws SQLException {
		Subject subject = null;
		ResultSet resultSubject = this.con.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Subject WHERE idSubject = " + idSubject);
		if(resultSubject.next()) {
			subject = new Subject(idSubject, resultSubject.getString("nameSubject"));
			System.out.println("subject : " + resultSubject.getString("nameSubject"));
		}
		return subject;
	}
	
	/**
     * @param idClass
     * @return list of SingleSession of the class who corresponds to idClass
     */
	public ArrayList<SingleSession> getSingleSessionByClass(int idClass) {
		// TODO Auto-generated method stub
		SingleSession appointment = null;
		Student teacher;
		Subject subject;
		ArrayList<SingleSession> listSingleSession = new ArrayList<SingleSession>();
		    
		try {
			ResultSet result = this.con.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM SingleSession WHERE idClass = " + idClass);
			
			while (result.next()) {
				teacher = new Student(result.getInt("idTeacher"));
				subject = new Subject(result.getInt("idSubject"), null);
				appointment = new SingleSession(result.getInt("idSingleRevision"), teacher, null,new MyDate( result.getString("dateAppointement")), subject);
				listSingleSession.add(appointment);
			}
			
			/*for (SingleSession ss : listSingleSession) {
				teacher = getStudentOfOneAppointmentById(ss.getTeacher().getId());
				System.out.println("nameTeacherStudent : " + teacher.getNameStudent());
				ss.setTeacher(teacher);
			}
			
			for (SingleSession ss : listSingleSession) {
				subject = getSubjectOfOneAppointmentById(ss.getIdSubject());
				ss.setSubject(subject);
			}*/
			  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listSingleSession;
	}
	
	/**
     * @param idClass
     * @return list of RevisionSession of the class who corresponds to idClass
     */
	public ArrayList<RevisionSession> getAppointmentByClass(int idClass) {
		// TODO Auto-generated method stub
		RevisionSession appointment = null;
		Student teacher;
		Student student;
		Subject subject;
		ArrayList<Student> listStudent = new ArrayList<Student>();
		ArrayList<RevisionSession> listRevisionSession = new ArrayList<RevisionSession>();
		    
		try {
			ResultSet result = this.con.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM RevisionSession WHERE idClass = " + idClass);
			
			while(result.next()) {
				teacher = new Student(result.getInt("idTeacher"));
				subject = new Subject(result.getInt("idSubject"), null);
				appointment = new RevisionSession(result.getInt("idRevisionSession"), teacher, listStudent, subject, new MyDate(result.getString("dateAppointement")));
				listRevisionSession.add(appointment);
			}
			result.close();
			
			for (RevisionSession rs : listRevisionSession) {
				ArrayList<Student> listStudentRS = new ArrayList<Student>();
				ResultSet resultParticipate = this.con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Participate WHERE idRevisionSession = " + rs.getIdAppointment());
				while(resultParticipate.next()) {
					student = new Student(resultParticipate.getInt("idStudent"));
					listStudentRS.add(student);
					rs.setStudent(listStudentRS);
				}
				resultParticipate.close();
			}
						
			for (RevisionSession rs : listRevisionSession) {
				listStudent = getListStudentOfOneAppointment(rs);
				rs.setStudent(listStudent);
			}
			
			for (RevisionSession rs : listRevisionSession) {
				teacher = getStudentOfOneAppointment(rs.getTeacher().getId());
				rs.setTeacher(teacher);
			}
			
			for (RevisionSession rs : listRevisionSession) {
				subject = getSubjectOfOneAppointmentById(rs.getSubject().getId());
				rs.setSubject(subject);
			}
			
			
			  
			/*while (result.first()) {
				  
				ResultSet resultParticipate = this.con.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Participate WHERE idSingleSession = " + result.getInt("idRevisionSession"));
	  
				while(resultParticipate.next()) {
					
					ResultSet resultStudent = this.con.createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Student WHERE idStudent = " + resultParticipate.getInt("idStudent"));
					
					while(resultStudent.next()) {
						student = new Student(
								resultStudent.getInt("idStudent"),
								resultStudent.getString("nameStudent"),
								resultStudent.getString("firstNameStudent"),
								resultStudent.getString("emailStudent"),
								resultStudent.getString("password"),
								resultStudent.getString("loginID"),
						        null);
						
						listStudent.add(student);
						
						ResultSet resultTeacher = this.con.createStatement(
								ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Student WHERE idStudent = " + result.getInt("idTeacher"));
						
						while(resultTeacher.next()) {
							teacher = new Student(
									resultTeacher.getInt("idStudent"),
									resultTeacher.getString("nameStudent"),
									resultTeacher.getString("firstNameStudent"),
									resultTeacher.getString("emailStudent"),
									resultTeacher.getString("password"),
									resultTeacher.getString("loginID"),
							        null);
							
							appointment = new RevisionSession(result.getInt("idRevisionSession"), teacher, listStudent, result.getInt("idSubject"), result.getDate("dateAppointement"));
							listRevisionSession.add(appointment);
						}
					}
				}  
				
			}*/
			  
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listRevisionSession;
	}
	
	public void recommendStudent(Student student) {
		
	}
	public ArrayList<Appointment> getAppointmentByStudent(int studentid) {
		ArrayList<Appointment> appointments = new ArrayList<Appointment>(); 
		Appointment appt = new Appointment(0, null, 0, null);
		// on recupere tous les appt il est eleve d'une single session 
		try {
			ResultSet result = this.con.createStatement().executeQuery("SELECT s.idSingleRevision ,t.nameStudent,b.nameSubject,s.dateAppointement as date FROM SingleSession s,Student t,Subject b WHERE s.idSubject=b.idSubject AND  s.idStudent ="+studentid+" AND s.idTeacher = t.idStudent; ");
			
			while(result.next()){ 
				
				
				appt = new Appointment(
					result.getInt("idSingleRevision"),
					new Student(0,result.getString("nameStudent")),
					new Subject(0,result.getString("nameSubject")),
					new MyDate(result.getString("date"))
		  	        
		  	    );
				
				appointments.add(appt);
			}
			
			// on recupere tous les appt il est prof d'une single session 			
			ResultSet resultb = this.con.createStatement().executeQuery("SELECT s.idSingleRevision ,t.nameStudent,b.nameSubject,s.dateAppointement as date FROM SingleSession s,Student t,Subject b WHERE s.idSubject=b.idSubject AND  s.idTeacher ="+studentid+" AND s.idTeacher = t.idStudent; ");
		while(result.next()){ 
		appt = new Appointment(
			resultb.getInt("idSingleRevision"),
			new Student(0,resultb.getString("nameStudent")),
  	        new Subject(0,resultb.getString("nameSubject")),
	  	    new MyDate(resultb.getString("date"))
	  	        
	  	    );
		System.out.println(resultb.getString("date"));
			appointments.add(appt);		}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return appointments;
		
	}

}