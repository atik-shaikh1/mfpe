package com.cognizant.medicalrepresentativeschedule.model;

import java.time.LocalDate;
import java.util.Arrays;

public class RepSchedule {

	private int id;
	private String repName;
	private String doctorName;
	private String meetingSlot;
	private LocalDate meetingDate;
	private String doctorContactNumber;
	private String[] medicines;
	private String treatingAilment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRepName() {
		return repName;
	}

	public void setRepName(String repName) {
		this.repName = repName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getMeetingSlot() {
		return meetingSlot;
	}

	public void setMeetingSlot(String meetingSlot) {
		this.meetingSlot = meetingSlot;
	}

	public LocalDate getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(LocalDate meetingDate) {
		this.meetingDate = meetingDate;
	}

	public String getDoctorContactNumber() {
		return doctorContactNumber;
	}

	public void setDoctorContactNumber(String doctorContactNumber) {
		this.doctorContactNumber = doctorContactNumber;
	}

	public String[] getMedicines() {
		return medicines;
	}

	public void setMedicines(String[] medicines) {
		this.medicines = medicines;
	}

	public String getTreatingAilment() {
		return treatingAilment;
	}

	public void setTreatingAilment(String treatingAilment) {
		this.treatingAilment = treatingAilment;
	}

	@Override
	public String toString() {
		return "RepSchedule [id=" + id + ", repName=" + repName + ", doctorName=" + doctorName + ", meetingSlot="
				+ meetingSlot + ", meetingDate=" + meetingDate + ", doctorContactNumber=" + doctorContactNumber
				+ ", medicines=" + medicines + ", treatingAilment=" + treatingAilment + "]";
	}

}
