package audit.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.joda.time.LocalDateTime;
import org.joda.time.Period;

import audit.api.AuditBatchEntry;
import audit.api.Audit;
import audit.api.response.AuditBatchReport;
import audit.api.response.AuditSimpleReport;
import audit.repository.AuditRepository;

import com.codahale.metrics.annotation.Metered;
import com.google.common.collect.ImmutableList;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/audit")
@Api("/audit")
public class AuditResource {

	private final AuditRepository repo;
	
	public AuditResource(final AuditRepository repo) {
		this.repo = repo;
	}
	
	@Path("/{auditId}")
	@ApiOperation("Declare a new audit log")
	@Metered
	@PUT
	public void newAudit(final @PathParam("auditId") String auditId, final String name) {
		this.repo.declareController(new Audit(auditId, name));
	}
	
	@Path("/{controllerId}/batch/{batchId}")
	@ApiOperation("Record a new entry for a controller batch")
	@Metered
	@POST
	public void recordBatchOfEntries(final @PathParam("controllerId") String controllerId, final @PathParam("batchId") String batchId, final AuditBatchEntry entry) {
		this.repo.recordBatchEntry(controllerId, batchId, entry);
	}
	
	@Path("/report")
	@ApiOperation("Returns a shorten report for all batch executions during the last 24 hours")
	@Metered
	@GET
	public List<AuditSimpleReport> batch24HoursReport() {
		return ImmutableList.of(
			new AuditSimpleReport(new Audit("contrat2bp", "Contrat -> BP"),
					ImmutableList.of(
							new AuditBatchReport(LocalDateTime.now(), 25, 214),
							new AuditBatchReport(LocalDateTime.now().minus(Period.minutes(5)), 25, 146),
							new AuditBatchReport(LocalDateTime.now().minus(Period.minutes(10)), 10, 152),
							new AuditBatchReport(LocalDateTime.now().minus(Period.minutes(15)), 0, 98),
							new AuditBatchReport(LocalDateTime.now().minus(Period.minutes(20)), 3, 136),
							new AuditBatchReport(LocalDateTime.now().minus(Period.minutes(25)), 1, 120))
		), 	new AuditSimpleReport(new Audit("bp2bl", "BP -> BL"),
				ImmutableList.of(
						new AuditBatchReport(LocalDateTime.now(), 25, 214),
						new AuditBatchReport(LocalDateTime.now().minus(Period.minutes(5)), 25, 146),
						new AuditBatchReport(LocalDateTime.now().minus(Period.minutes(10)), 10, 152),
						new AuditBatchReport(LocalDateTime.now().minus(Period.minutes(15)), 0, 98),
						new AuditBatchReport(LocalDateTime.now().minus(Period.minutes(20)), 3, 136),
						new AuditBatchReport(LocalDateTime.now().minus(Period.minutes(25)), 1, 120))
	));
		
		/*
		final List<ControllerSimpleReport> reports = new LinkedList<ControllerSimpleReport>();
		
		for(final Controller c : repo.controllers()) {
			reports.add(new ControllerSimpleReport(c, repo.controlBatchSimpleReport(c.id)));
		}
		
		return reports;
		*//*return ImmutableList.of(
				new ControlBatchReport(LocalDateTime.now(), 25, 214),
				new ControlBatchReport(LocalDateTime.now().minus(Period.minutes(5)), 25, 146),
				new ControlBatchReport(LocalDateTime.now().minus(Period.minutes(10)), 10, 152),
				new ControlBatchReport(LocalDateTime.now().minus(Period.minutes(15)), 0, 98),
				new ControlBatchReport(LocalDateTime.now().minus(Period.minutes(20)), 3, 136),
				new ControlBatchReport(LocalDateTime.now().minus(Period.minutes(25)), 1, 120)
		);*/
	}
}
