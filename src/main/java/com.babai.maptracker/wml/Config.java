package wml;

import java.util.ArrayList;

public class Config extends ConfigEntry {
	private ArrayList<ConfigEntry> entries;
	private String name;
	
	public Config(String name) {
		this.name = name;
		entries = new ArrayList<>();
	}
	
	public void add(ConfigEntry entry) {
		entries.add(entry);
	}
	
	public <T> void add(String key, T value) {
		entries.add(new ConfigValue<T>(key, value));
	}

	public String write(int indentLevel) {
		var sb = new StringBuilder();
		int i = 0;
		
		// Start tag
		sb.append("\t".repeat(indentLevel));
		sb.append("[" + name + "]\n");
		
		// Body
		for (var entry : entries) {	
			sb.append(entry.write(indentLevel+1));
			
			if (i < entries.size()) {
				sb.append('\n');
				i++;
			}
		}
		
		// End tag
		sb.append("\t".repeat(indentLevel));
		sb.append("[/" + name + "]");
		
		return sb.toString();
	}
}
