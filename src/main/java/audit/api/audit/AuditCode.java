package audit.api.audit;

import java.util.Objects;

public class AuditCode {

	public final String code;
	
	public AuditCode(final String code) {
		this.code = code;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof AuditCode
			&& this.code.equals(((AuditCode)obj).code);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(code);
	}
}
