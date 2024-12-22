package main;

import wml.Config;

public class Main {

	public static void main(String... args) {
		Config cfg = new Config("unit");
		cfg.add("level", 3);
		cfg.add("name", "Erinna");
		
		Config atkCfg = new Config("attack");
		atkCfg.add("id", "staff");
		atkCfg.add(new Config("specials"));
		cfg.add(atkCfg);
		
		System.out.println(cfg);
	}
}
