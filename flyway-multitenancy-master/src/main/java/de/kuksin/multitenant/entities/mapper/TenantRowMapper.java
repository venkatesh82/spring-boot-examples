package de.kuksin.multitenant.entities.mapper;

import de.kuksin.multitenant.entities.Tenant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TenantRowMapper implements RowMapper<Tenant> {

    @Override
    public Tenant mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Tenant(rs.getString("tenant_id"),
                                 rs.getString("url"),
                                 rs.getString("username"),
                                 rs.getString("password"),
                                 rs.getString("driver_class_name"));
    }
}
