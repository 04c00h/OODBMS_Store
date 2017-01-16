package oodbms_store;

abstract class Subject {

	public static final String header = "id\tname\taddress\tdistrict\tphone";

	private static int ids = 0;

	private int id;
	private String name;
	private String address;
	private String district;
	private String phone;

	public Subject(String name, String address, String district, String phone) {
		this.id = ++ids;
		this.name = name;
		this.address = address;
		this.district = district;
		this.phone = phone;
	}

	public Subject() {
	}

	public Subject(String name, String address, String district, String phone, int id) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.district = district;
		this.phone = phone;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getAddress() {
		return this.address;
	}

	public String getDistrict() {
		return this.district;
	}

	public String getPhone() {
		return this.phone;
	}

	public String toString() {
		return this.id + "\t" + this.name + "\t" + this.address + "\t" + this.district + "\t" + this.phone;
	}

}
