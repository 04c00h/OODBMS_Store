package oodbms_store;

class Registry {

	public static final String header = Human.header + "\t" + Store.header + "\tdate\tpart";

	private Human owner;
	private Store store;
	private double part;
	private double value;
	private String date;

	public Registry(Human owner, Store store, double part, String date) {
		this.owner = owner;
		this.store = store;
		this.part = part;
		this.value = part * store.getCapital();
		this.date = date;
	}

	public Registry() {
	}

	public Human getOwner() {
		return this.owner;
	}

	public Store getStore() {
		return this.store;
	}

	public double getPart() {
		return this.part;
	}

	public double getValue() {
		return this.value;
	}

	public String getDate() {
		return this.date;
	}

	public String toString() {
		return this.owner.toString() + "\t" + this.store.toString() + "\t" + this.date + "\t" + this.part;
	}

}
