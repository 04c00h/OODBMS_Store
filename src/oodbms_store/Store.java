package oodbms_store;

class Store extends Subject {

	//public static final String header = Subject.header + "\tprofile\tcapital";
	public static final String header = "ID\tНазвание\tГород\tРайон\tТелефон\tПрофиль\tУставной капитал";

	private String profile;
	private double capital;

	public Store(String name, String address, String district, String phone, String profile, double capital) {
		super(name, address, district, phone);
		this.profile = profile;
		this.capital = capital;
	}

	public Store(String name, String address, String district, String phone, String profile, double capital, int id) {
		super(name, address, district, phone, id);
		this.profile = profile;
		this.capital = capital;
	}

	public Store() {
	}

	public String getProfile() {
		return this.profile;
	}

	public double getCapital() {
		return this.capital;
	}

	public String toString() {
		return super.toString() + "\t" + this.profile + "\t" + this.capital;
	}

}
