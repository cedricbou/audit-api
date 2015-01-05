package audit.app;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerDropwizard;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import audit.repository.AuditInMemoryRepository;
import audit.resources.AuditResource;

import com.google.common.collect.ImmutableMap;

public class AuditApplication extends Application<AuditConfiguration> {

	public static void main(String[] args) throws Exception {
		new AuditApplication().run(args);
	}
	
	private final SwaggerDropwizard swaggerDropwizard = new SwaggerDropwizard();
	
	@Override
	public String getName() {
		return "audit.it";
	}
	
	@Override
	public void initialize(Bootstrap<AuditConfiguration> bootstrap) {
	    bootstrap.addBundle(new AssetsBundle("/assets/", "/html", "index.html", "pages"));
		bootstrap.addBundle(new AssetsBundle("/assets/css", "/html/css", null, "css"));
	    bootstrap.addBundle(new AssetsBundle("/assets/js", "/html/js", null, "js"));
	    bootstrap.addBundle(new AssetsBundle("/assets/fonts", "/html/fonts", null, "fonts"));
	    bootstrap.addBundle(new AssetsBundle("/assets/partials", "/html/partials", null, "partials"));
	    
		swaggerDropwizard.onInitialize(bootstrap);
	}
	
	@Override
	public void run(AuditConfiguration conf, Environment env)
			throws Exception {

		/* Allows CORS requests */
		env.getApplicationContext().addFilter(CrossOriginFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST))
			.setInitParameters(ImmutableMap.of(
					"allowedOrigins", "*",
					"allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin",
					"allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD"
					));
		
		final AuditResource controllers = new AuditResource(new AuditInMemoryRepository());
		
		env.jersey().register(controllers);
		
		/* Init swagger */
		swaggerDropwizard.onRun(conf, env);
	}
	
}
