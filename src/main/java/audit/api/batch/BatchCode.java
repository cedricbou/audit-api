package audit.api.batch;

import java.util.Objects;

import audit.api.audit.AuditCode;

public class BatchCode {

	public final AuditCode auditCode;
	public final String code;
	
	public BatchCode(final AuditCode auditCode, final String code) {
		this.code = code;
		this.auditCode = auditCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof BatchCode
			&& this.auditCode.equals(((BatchCode)obj).auditCode)
			&& this.code.equals(((BatchCode)obj).code);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(code, auditCode);
	}
}
