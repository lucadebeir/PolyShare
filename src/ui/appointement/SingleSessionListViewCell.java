package ui.appointement;

import java.io.IOException;
import application.classesApp.SingleSession;
import application.classesApp.Student;
import facades.AppointmentsFacade;
import facades.LoginFacade;
import facades.exceptions.DisconnectedStudentException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class SingleSessionListViewCell extends ListCell<SingleSessionCell> {
	
	private AppointmentController controller;
	
	private AppointmentsFacade appointmentsFacade = new AppointmentsFacade();
	
	@FXML 
	private Label cellLabelnameTeacher;
	
	@FXML 
	private Label cellLabelnameSubject;
	
	@FXML 
	private Label cellLabelExplication;
	
	@FXML 
	private Label cellLabelDateAppointment;
	
	@FXML 
	private Label cellLabelPlace;
	
	@FXML 
	private Label cellLabelTimeAppointment;
	
	@FXML 
	private Button buttonAccept;
	
	@FXML 
	private  HBox cellLayout;
	
	FXMLLoader mLLoader;
	
	public SingleSessionListViewCell(AppointmentController controller) {
		super();
		this.controller = controller;
	}
	
	@Override
	protected void updateItem(SingleSessionCell ssc, boolean empty) {
        super.updateItem(ssc, empty);
        
        if(empty || ssc == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("SingleSessionListViewCell.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            
            this.cellLabelnameTeacher.setText(String.valueOf(ssc.getTeacherCell().getNameStudent() + " " + ssc.getTeacherCell().getFirstNameStudent()));
            this.cellLabelnameSubject.setText(String.valueOf(ssc.getSubjectCell().getNameSubject()));
            this.cellLabelExplication.setText(String.valueOf(ssc.getMessage()));
            this.cellLabelDateAppointment.setText(String.valueOf(ssc.getDateRevisionSessionCell().getShowingDate()));
            this.cellLabelPlace.setText(String.valueOf(ssc.getPlaceCell()));
            this.cellLabelTimeAppointment.setText(String.valueOf(ssc.getMeetingTimeCell()));
           
            this.buttonAccept.setOnAction(new EventHandler<ActionEvent>() {
            	@Override
            	public void handle(ActionEvent event) {
            		SingleSession ss = appointmentsFacade.getSingleSessionById(ssc.getIdSingleSessionCell());
            		try {
						Student student = LoginFacade.getInstance().getConnectedStudent();
						ss.setStudent(student);
						controller.updateSingleSession(ss);
					} catch (DisconnectedStudentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}                            
            });
            
            setText(null);
            setGraphic(cellLayout);
        }

    }

}
