package nl.projectsmile.api.sparkjavaserver;

import com.querydsl.sql.Configuration;
import com.querydsl.sql.H2Templates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class Database {

	private static DataSource dataSource;

	private static SQLQueryFactory sqlQueryFactory;

	public static DataSource init() {
		configureHikari();
		configureFlyway();
		configureQueryDsl();
		return dataSource;
	}

	private static void configureQueryDsl() {
		// configure querydsl
		final SQLTemplates templates = new H2Templates();
		final Configuration configuration = new Configuration(templates);
		sqlQueryFactory = new SQLQueryFactory(configuration, dataSource);
	}

	private static void configureFlyway() {
		// configure flyway
		Flyway flyway = new Flyway();
		flyway.setDataSource(dataSource);
		flyway.migrate();
	}

	private static void configureHikari() {
		final HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:h2:/tmp/projectsmile-db.h2");
		config.setUsername("bart");
		config.setPassword("51mp50n");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		dataSource = new HikariDataSource(config);
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static SQLQueryFactory getSqlQueryFactory() {
		return sqlQueryFactory;
	}
}
