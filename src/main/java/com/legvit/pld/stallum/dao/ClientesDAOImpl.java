package com.legvit.pld.stallum.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

public class ClientesDAOImpl extends SimpleQueryJdbcDaoSupport implements ClientesDAO {

	public List<String> obtenSociedadesMercantiles() {
		String query = getQueries().getProperty("obten.sociedades.mercantiles");
        List<String> data = getJdbcTemplate().query(query, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("PREFIJO_SOC_MERC");
			}
		});
		return data;
	}
}
