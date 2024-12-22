package wml;

public abstract class ConfigEntry {
	
	public abstract String write(int indentLevel);
	
	public String toString() {
		return write(0);
	}
}
