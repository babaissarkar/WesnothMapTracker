package wml;

public class ConfigAttribute<T> extends ConfigAttributeBase {
	private String key;
	private T value;
	
	public ConfigAttribute(String key, T value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	@Override
	public boolean boolValue() {
		if (value instanceof Boolean) {
			return (boolean) value;
		} else {
			throw new IllegalArgumentException(value.getClass().getName() + " found, boolean expected!");
		}
	}
	
	@Override
	public int intValue() {
		if (value instanceof Integer) {
			return (int) value;
		} else {
			throw new IllegalArgumentException(value.getClass().getName() + " found, int expected!");
		}
	}

	@Override
	public String stringValue() {
		return value.toString();
	}
	
	public String write(int indentLevel) {
		return "\t".repeat(indentLevel)
				+ key
				+ "="
				+ (value instanceof String ? "\"" : "")
				+ value
				+ (value instanceof String ? "\"" : "");
	}
}
