package audit.api.audit;

import com.google.common.base.Optional;

public class Audit {
	public final Optional<String> name;

	public Audit() {
		this.name = Optional.absent();
	}
	
	public Audit(final String name) {
		this.name = Optional.fromNullable(name);
	}
	
	public AuditWithCode withCode(final AuditCode code) {
		return new AuditWithCode(this, code);
	}
}
