package com.legvit.pld.stallum.dao;
import java.util.Properties;

import org.springframework.jdbc.core.support.JdbcDaoSupport;


/**
 * Clase que almacena los statements de la base de datos.
 * 
 * @author Meltsan.
 */
public abstract class SimpleQueryJdbcDaoSupport extends JdbcDaoSupport {
    
    /**
     * Statements de la base de datos.
     */
    private Properties queries;
    
    /**
     * Checa la afectacion de inserts, updates, deletes que sea unicamente un registro
     * 
     * @param affected
     */
    protected void checkAffected(int affected) {
        if (affected > 1) {
            throw new RuntimeException("La operacion afecto mas de 1 registro.");
        }
        if (affected < 1) {
            throw new RuntimeException("La operacion no afecto ningun registro.");
        }
    }
    
    /**
     * @return the queries
     */
    public Properties getQueries() {
        return queries;
    }
    
    /**
     * @param queries
     *            the queries to set
     */
    public void setQueries(Properties queries) {
        this.queries = queries;
    }
}
