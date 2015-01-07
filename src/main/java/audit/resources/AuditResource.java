package audit.resources;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

import audit.api.audit.Audit;
import audit.api.audit.AuditCode;
import audit.api.audit.AuditWithCode;
import audit.api.batch.Batch;
import audit.api.batch.BatchCode;
import audit.api.batch.BatchWithCode;
import audit.api.response.AuditSimpleReport;
import audit.domain.AuditReporter;
import audit.repository.AuditRepository;
import audit.repository.BatchRepository;

import com.codahale.metrics.annotation.Metered;
import com.google.common.base.Optional;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Path("/audit")
@Api("/audit")
public class AuditResource {

	private final AuditRepository auditRepo;
	private final BatchRepository batchRepo;
	private final AuditReporter reporter = new AuditReporter();
	
	public AuditResource(final AuditRepository auditRepo, final BatchRepository batchRepo) {
		this.auditRepo = auditRepo;
		this.batchRepo = batchRepo;
	}
	
	@Path("/{auditId}")
	@ApiOperation("Declare a new audit log")
	@Metered
	@PUT
	public void newAudit(final @PathParam("auditId") String auditId, final String name) {
		this.auditRepo.save((new Audit(name).withCode(new AuditCode(auditId))));
	}
	
	@Path("/{auditId}/batch/{batchId}")
	@ApiOperation("Record a new entry for a controller batch")
	@Metered
	@POST
	public void recordBatchOfEntries(final @PathParam("auditId") String auditId, final @PathParam("batchId") String batchId, final Batch batch) {
		this.batchRepo.save(batch.withCode(new BatchCode(new AuditCode(auditId), batchId)));
	}
	
	@Path("/{auditId}/batch/{batchId}")
	@ApiOperation("Find a batch record for a given audit")
	@Metered
	@GET
	public Batch findBatch(final @PathParam("auditId")  String auditId, final @PathParam("batchId")  String batchId) {
		final Optional<BatchWithCode> opt = batchRepo.find(new BatchCode(new AuditCode(auditId), batchId));
		if(opt.isPresent()) {
			return opt.get();
		}
		else {
			throw new WebApplicationException(404);
		}
	}
	
	@Path("/report")
	@ApiOperation("Returns a shorten report for all batch executions during the last 24 hours")
	@Metered
	@GET
	public List<AuditSimpleReport> batch24HoursReport() {
		
		final List<AuditSimpleReport> audits = new LinkedList<AuditSimpleReport>();
		
		for(final AuditWithCode audit : auditRepo.all()) {
			audits.add(new AuditSimpleReport(audit, reporter.simpleReport(batchRepo.find(audit.code))));
		}
		
		return audits;
		/*
		return ImmutableList.of(
			new AuditSimpleReport(new Audit("contrat2bp", "Contrat -> BP"),
					ImmutableList.of(
							new BatchReport(LocalDateTime.now(), 25, 214),
							new BatchReport(LocalDateTime.now().minus(Period.minutes(5)), 25, 146),
							new BatchReport(LocalDateTime.now().minus(Period.minutes(10)), 10, 152),
							new BatchReport(LocalDateTime.now().minus(Period.minutes(15)), 0, 98),
							new BatchReport(LocalDateTime.now().minus(Period.minutes(20)), 3, 136),
							new BatchReport(LocalDateTime.now().minus(Period.minutes(25)), 1, 120))
		), 	new AuditSimpleReport(new Audit("bp2bl", "BP -> BL"),
				ImmutableList.of(
						new BatchReport(LocalDateTime.now(), 25, 214),
						new BatchReport(LocalDateTime.now().minus(Period.minutes(5)), 25, 146),
						new BatchReport(LocalDateTime.now().minus(Period.minutes(10)), 10, 152),
						new BatchReport(LocalDateTime.now().minus(Period.minutes(15)), 0, 98),
						new BatchReport(LocalDateTime.now().minus(Period.minutes(20)), 3, 136),
						new BatchReport(LocalDateTime.now().minus(Period.minutes(25)), 1, 120))
	));
		*/
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
