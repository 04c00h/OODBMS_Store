package oodbms_store;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.db4o.*;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

public class Program {

	static final String formatDate = "dd.MM.YYYY";

	static Date getDate(String strDate) {
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern(Program.formatDate);
		Date date = null;
		try {
			date = format.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	private static void create(ObjectContainer db) {

		ArrayList<Human> humans = new ArrayList<>();
		humans.add(new Human("Кузнецов Иван Васильевич", "Москва", "Восточный", "9 999 999 99 99", "27.12.1998"));
		humans.add(new Human("Пупкин Василий Иванович", "Москва", "Киевский", "9 999 999 99 99", "01.02.2000"));
		humans.add(new Human("Иванов Александр Сергеевич", "Москва", "Кунцевский", "9 999 999 99 99", "15.11.1992"));

		for (Human t : humans) {
			db.store(t);
		}

		ArrayList<Store> stores = new ArrayList<>();
		stores.add(new Store("Фолиант", "Москва", "Ленинский", "9 999 999 99 99", "Книжный", 200000.0));
		stores.add(new Store("Лукошко", "Москва", "Киевский", "9 999 999 99 99", "Продуктовый", 300000.0));

		for (Store t : stores) {
			db.store(t);
		}

		ArrayList<Registry> regs = new ArrayList<>();
		regs.add(new Registry(humans.get(0), stores.get(0), 1, "01.01.2016"));
		regs.add(new Registry(humans.get(0), stores.get(1), 0.3, "01.01.2016"));
		regs.add(new Registry(humans.get(1), stores.get(1), 0.7, "01.01.2016"));

		for (Registry t : regs) {
			db.store(t);
		}

		ArrayList<Supply> supl = new ArrayList<>();
		supl.add(new Supply(humans.get(0), stores.get(1), 180));
		supl.add(new Supply(humans.get(2), stores.get(0), 202));
		supl.add(new Supply(humans.get(2), stores.get(1), 195));

		for (Supply t : supl) {
			db.store(t);
		}
	}

	private static void showHuman(ObjectContainer db) {
		Human prototype = new Human();
		ObjectSet<Human> list = db.queryByExample(prototype);
		System.out.println(Human.header);
		viewList(list);
	}

	private static void showStore(ObjectContainer db) {
		Store prototype = new Store();
		ObjectSet<Store> list = db.queryByExample(prototype);
		System.out.println(Store.header);
		viewList(list);
	}

	private static void showRegistry(ObjectContainer db) {
		Registry prototype = new Registry();
		ObjectSet<Registry> list = db.queryByExample(prototype);
		System.out.println(Registry.header);
		viewList(list);
	}

	private static void showSupply(ObjectContainer db) {
		Supply prototype = new Supply();
		ObjectSet<Supply> list = db.queryByExample(prototype);
		System.out.println(Supply.header);
		viewList(list);
	}

	static void viewList(ObjectSet<?> list) {
		while (list.hasNext()) {
			System.out.println(list.next());
		}
		System.out.println();
	}

	private static void query1(ObjectContainer db) {
		// Определить самого молодого предпринимателя, владеющего собственностью
		// в районе "Киевский"

		Query query = db.query();
		query.constrain(Registry.class);
		query.descend("store").constrain(new Store(null, null, "Киевский", null, null, 0, 0));
		query.descend("owner").descend("age").orderAscending();
		ObjectSet<Registry> result = query.execute();

		System.out
				.println("Определить самого молодого предпринимателя, владеющего собственностью в районе \"Киевский\"");
		System.out.println();
		System.out.println(Human.header);
		if (!result.isEmpty()) {
			System.out.println(result.get(0).getOwner());
		}
		System.out.println();
		System.out.println();
	}

	private static void query2(ObjectContainer db) {
		// Определить случаи, когда регистрировалось владение лицами, не
		// достигшими 18 лет

		Query query = db.query();
		query.descend("owner").descend("age").constrain(new Integer(18)).smaller();
		ObjectSet<Registry> result = query.execute();

		System.out.println("Определить случаи, когда регистрировалось владение лицами, не достигшими 18 лет");
		System.out.println();
		System.out.println(Registry.header);
		viewList(result);
		System.out.println();
	}

	private static void query3(ObjectContainer db) {
		// Определить случаи, когда больше 50% уставного капитала магазина
		// внесено предпринимателем, проживающим в другом районе

		@SuppressWarnings("serial")
		ObjectSet<Registry> result = db.query(new Predicate<Registry>() {
			public boolean match(Registry reg) {
				String d1 = reg.getOwner().getDistrict();
				String d2 = reg.getStore().getDistrict();
				return !d1.equals(d2) && reg.getPart() > 0.5f;
			}
		});

		System.out.println(
				"Определить случаи, когда больше 50% уставного капитала магазина внесено предпринимателем, проживающим в другом районе");
		System.out.println();
		System.out.println(Registry.header);
		viewList(result);
		System.out.println();
	}

	private static void query4(ObjectContainer db) {
		// Вывести список профилей магазинов, которыми владеет предприниматель
		// "Кузнецов" в порядке убывания вложенного в них капитала

		Query query = db.query();

		query.descend("owner").descend("name").constrain("Кузнецов").startsWith(true);
		query.descend("value").orderDescending();

		ObjectSet<Registry> result = query.execute();

		System.out.println(
				"Вывести список профилей магазинов, которыми владеет предприниматель \"Кузнецов\" в порядке убывания вложенного в них капитала");
		System.out.println();
		while (result.hasNext()) {
			System.out.println(result.next().getStore().getProfile());
		}
		System.out.println();
		System.out.println();

	}

	private static void action(ObjectContainer db) {
		showHuman(db);
		showStore(db);
		showRegistry(db);
		showSupply(db);
		System.out.println();
		System.out.println("Запросы:");
		System.out.println();
		query1(db);
		query2(db);
		query3(db);
		query4(db);
	}

	public static void main(String[] args) {
		String dbFileName = "oodbms_store";
		ObjectContainer db;
		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbFileName);
		try {
			create(db);
			action(db);
			db.rollback();
		} finally {
			db.close();
		}
	}

}
