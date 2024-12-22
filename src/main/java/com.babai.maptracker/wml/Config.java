package wml;

import java.util.ArrayList;
import java.util.HashMap;

public class Config {
	private HashMap<String, ConfigAttributeBase> attributes;
	private ArrayList<Config> children;
	private String name;
	
	public Config(String name) {
		this.name = name;
		attributes = new HashMap<>();
		children = new ArrayList<>();
	}
	
	//@Nullable
	public ConfigAttributeBase getAttr(String attrName) {
		return attributes.get(attrName);
	}
	
	//@Nullable
	public Config getChild(int i) {
		if (i < children.size()) {
			return children.get(i);
		} else {
			return null;
		}
	}
	
	public void add(Config entry) {
		children.add(entry);
	}
	
	public <T> void add(String key, T value) {
		attributes.put(key, new ConfigAttribute<T>(key, value));
	}

	public String write(int indentLevel) {
		var sb = new StringBuilder();
		int i = 0;
		
		// Start tag
		sb.append("\t".repeat(indentLevel));
		sb.append("[" + name + "]\n");
		
		// Attributes
		for (var attr : attributes.entrySet()) {
			sb.append(attr.getValue().write(indentLevel+1));
			
			if (i < attributes.size()) {
				sb.append('\n');
				i++;
			}
		}
		
		// Subtags
		for (var child : children) {	
			sb.append(child.write(indentLevel+1));
			sb.append('\n');
		}
		
		// End tag
		sb.append("\t".repeat(indentLevel));
		sb.append("[/" + name + "]");
		
		return sb.toString();
	}
	
	public String toString() {
		return write(0);
	}
}
