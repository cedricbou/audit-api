package audit.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import audit.api.audit.Audit;
import audit.api.audit.AuditCode;
import audit.api.audit.AuditWithCode;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class AuditInMemoryRepository implements AuditRepository {

	private final Map<AuditCode, AuditWithCode> audits = new HashMap<AuditCode, AuditWithCode>();

	@Override
	public List<AuditWithCode> all() {
		return ImmutableList.<AuditWithCode> builder().addAll(audits.values()).build();
	}

	@Override
	public Optional<Audit> find(AuditCode code) {
		return Optional.fromNullable((Audit)audits.get(code));
	}

	@Override
	public void save(AuditWithCode audit) {
		audits.put(audit.code, audit);
	}
}
