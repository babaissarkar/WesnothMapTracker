package wml;

public abstract class ConfigAttributeBase {
	
	public abstract String write(int indentLevel);
	
	public abstract String getKey();
	public abstract int intValue();
	public abstract boolean boolValue();
	public abstract String stringValue();
	
	public String toString() {
		return write(0);
	}
}
