package audit.api.response;

import java.util.List;

import audit.api.Audit;

public class AuditSimpleReport {

	public final Audit controller;
	public final List<AuditBatchReport> reports;
	
	public AuditSimpleReport(final Audit controller, final List<AuditBatchReport> reports) {
		this.controller = controller;
		this.reports = reports;
	}
	
}
