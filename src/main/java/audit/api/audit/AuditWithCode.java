package audit.api.audit;


public class AuditWithCode extends Audit {
	
	public final AuditCode code;
	
	public AuditWithCode(final Audit audit, final AuditCode code) {
		super(audit.name.orNull());
		this.code = code;
	}
}
