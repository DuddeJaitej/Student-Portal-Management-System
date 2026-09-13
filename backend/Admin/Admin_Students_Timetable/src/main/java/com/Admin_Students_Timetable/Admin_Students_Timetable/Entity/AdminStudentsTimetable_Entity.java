package com.Admin_Students_Timetable.Admin_Students_Timetable.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Admin_Students_Timetable")
public class AdminStudentsTimetable_Entity {
	 	@Id
	 	@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false)
	    private String programName;

	    @Column(nullable = false)
	    private String day;

	    @Column(length = 1000)
	    private String slot1;

	    @Column(length = 1000)
	    private String slot2;

	    @Column(length = 1000)
	    private String slot3;

	    @Column(length = 1000)
	    private String slot4;

	    @Column(length = 1000)
	    private String slot5;

	    @Column(length = 1000)
	    private String slot6;

	    @Column(length = 1000)
	    private String slot7;

	    @Column(length = 1000)
	    private String slot8;

	    @Column(length = 1000)
	    private String slot9;

	    @Column(length = 1000)
	    private String slot10;
	    
	    public AdminStudentsTimetable_Entity() {};

		public String getProgramName() {
			return programName;
		}

		public void setProgramName(String programName) {
			this.programName = programName;
		}

		public String getDay() {
			return day;
		}

		public void setDay(String day) {
			this.day = day;
		}

		public String getSlot1() {
			return slot1;
		}

		public void setSlot1(String slot1) {
			this.slot1 = slot1;
		}

		public String getSlot2() {
			return slot2;
		}

		public void setSlot2(String slot2) {
			this.slot2 = slot2;
		}

		public String getSlot3() {
			return slot3;
		}

		public void setSlot3(String slot3) {
			this.slot3 = slot3;
		}

		public String getSlot4() {
			return slot4;
		}

		public void setSlot4(String slot4) {
			this.slot4 = slot4;
		}

		public String getSlot5() {
			return slot5;
		}

		public void setSlot5(String slot5) {
			this.slot5 = slot5;
		}

		public String getSlot6() {
			return slot6;
		}

		public void setSlot6(String slot6) {
			this.slot6 = slot6;
		}

		public String getSlot7() {
			return slot7;
		}

		public void setSlot7(String slot7) {
			this.slot7 = slot7;
		}

		public String getSlot8() {
			return slot8;
		}

		public void setSlot8(String slot8) {
			this.slot8 = slot8;
		}

		public String getSlot9() {
			return slot9;
		}

		public void setSlot9(String slot9) {
			this.slot9 = slot9;
		}

		public String getSlot10() {
			return slot10;
		}

		public void setSlot10(String slot10) {
			this.slot10 = slot10;
		}
}
