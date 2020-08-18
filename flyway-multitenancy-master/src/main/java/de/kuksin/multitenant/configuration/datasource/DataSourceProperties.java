package de.kuksin.multitenant.configuration.datasource;

import de.kuksin.multitenant.entities.Tenant;
import de.kuksin.multitenant.entities.mapper.TenantRowMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "tenants")
public class DataSourceProperties {

    private Map<Object, Object> datasources = new LinkedHashMap<>();

    public Map<Object, Object> getDatasources() {
        return datasources;
    }

    public void setDatasources(Map<String, Map<String, String>> datasources) {

        Map<String, String> masterProps=datasources.get("master");
        List<Tenant> tenants= findAll(convert(masterProps));

        for(Tenant t:tenants){
            Map<String,String> map=new LinkedHashMap<>();
            map.put("jdbcUrl",t.getUrl());
            map.put("driverClassName",t.getDriverClassName());
            map.put("username",t.getUsername());
            map.put("password",t.getPassword());
            datasources.put(t.getTenantId(), map);
            //this.datasources.put(t.getTenantId(),convert(t));
        }
        System.out.println(this.datasources);

        datasources
                .forEach((key, value) -> this.datasources.put(key, convert(value)));
    }

    public List<Tenant> findAll(DataSource dataSource) {
        String sql = "SELECT * FROM tenants";
        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
        List<Tenant> tenants = jdbcTemplate.query(
                sql,
                new TenantRowMapper());

        return tenants;
    }

    public DataSource convert(Map<String, String> source) {
        return DataSourceBuilder.create()
                .url(source.get("jdbcUrl"))
                .driverClassName(source.get("driverClassName"))
                .username(source.get("username"))
                .password(source.get("password"))
                .build();
    }

    public DataSource convert(Tenant source) {
        return DataSourceBuilder.create()
                .url(source.getUrl())
                .driverClassName(source.getDriverClassName())
                .username(source.getUsername())
                .password(source.getPassword())
                .build();
    }
}
