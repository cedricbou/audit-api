package audit.api.response;

import java.util.List;

import audit.api.audit.Audit;

public class AuditSimpleReport {

	public final Audit audit;
	public final List<BatchReport> reports;
	
	public AuditSimpleReport(final Audit audit, final List<BatchReport> reports) {
		this.audit = audit;
		this.reports = reports;
	}
	
}
