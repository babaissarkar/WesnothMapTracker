package wml;

import java.util.Vector;

public class Definition {
	private String name;
	private String value;
	private Vector<String> args = new Vector<>();
	
	public Definition(String name, String value) {
		this.name = name;
		this.value = value;
	}
	
	public Definition(String name, String value, Vector<String> args) {
		this.name = name;
		this.value = value;
		this.args = args;
	}
	
	public void addArg(String arg) {
		args.add(arg);
	}
	
	public String getValue() {
		return this.value;
	}
	
	/** Expand the macro, substituting any given args */
	public String expand(Vector<String> values) {
		String unparsed = this.value;
		if (values.size() != args.size()) {
			throw new IllegalArgumentException("Wrong number of arguments supplied to macro " + this.name);
		}
		
		int i = 0;
		for (var arg : args) {
			unparsed = unparsed.replace("{" + arg + "}", values.get(i));
			i++;
		}
		return unparsed;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("#define");
		sb.append(name);
		for (var arg : args) {
			sb.append(" " + arg);
		}
		sb.append("\n");
		sb.append(value);
		sb.append("#enddef");
		return sb.toString();
	}

}
