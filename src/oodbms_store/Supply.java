package oodbms_store;

class Supply {

	public static final String header = Human.header + "\t" + Store.header + "\tprice";

	private Human provider;
	private Store store;
	private double price;

	public Supply(Human provider, Store store, double price) {
		this.provider = provider;
		this.store = store;
		this.price = price;
	}

	public Supply() {
	}

	public Human getProvider() {
		return this.provider;
	}

	public Store getStore() {
		return this.store;
	}

	public double getPrice() {
		return this.price;
	}

	public String toString() {
		return this.provider.toString() + "\t" + this.store.toString() + "\t" + this.price;
	}

}
