package utility;

import java.io.Serializable;

public class WrapperValue<T> implements Serializable {

	public T value;
	
	public WrapperValue(T value) {
		this.value = value;
	}
}
