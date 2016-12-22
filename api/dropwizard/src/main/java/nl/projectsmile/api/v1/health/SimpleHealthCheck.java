package nl.projectsmile.api.v1.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by sdegroot on 22/12/2016.
 */
public class SimpleHealthCheck extends HealthCheck {
	private final String template;

	public SimpleHealthCheck(String template) {
		this.template = template;
	}


	@Override
	protected Result check() throws Exception {
		final String saying = String.format(template, "TEST");
		if (!saying.contains("TEST")) {
			return Result.unhealthy("template doesn't include a name");
		}
		return Result.healthy();
	}
}
