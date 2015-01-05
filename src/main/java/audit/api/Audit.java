package audit.api;

import com.google.common.base.Optional;

public class Audit {
	public final String id;
	public final Optional<String> name;
	
	public Audit(final String id, final String name) {
		this.id = id;
		this.name = Optional.fromNullable(name);
	}
}
