package oodbms_store;

import java.util.Calendar;
import java.util.Date;

class Human extends Subject {

	//public static final String header = Subject.header + "\tbirthday";
	public static final String header = "ID\tФИО\tГород\tРайон\tТелефон\tДата рождения";

	private String birthday;
	private int age;

	public Human(String name, String address, String district, String phone, String birthday) {
		super(name, address, district, phone);
		this.birthday = birthday;
		this.age = getAge();
	}

	public Human() {
	}

	public int getAge() {
		int age = 0;
		Date date = Program.getDate(this.birthday);
		if (this.birthday != null && date != null) {
			Calendar b = Calendar.getInstance(); // birthday
			Calendar t = Calendar.getInstance(); // today
			b.setTime(date);
			b.add(Calendar.DAY_OF_MONTH, -1);
			age = t.get(Calendar.YEAR) - b.get(Calendar.YEAR);
			if (!(t.get(Calendar.DAY_OF_YEAR) > b.get(Calendar.DAY_OF_YEAR))) {
				age--;
			}
		}
		return age;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public String toString() {
		return super.toString() + "\t" + this.birthday;
	}

}
