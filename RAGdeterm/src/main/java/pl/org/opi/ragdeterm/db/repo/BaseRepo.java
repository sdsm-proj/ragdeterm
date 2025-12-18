package pl.org.opi.ragdeterm.db.repo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.org.opi.ragdeterm.db.exception.DbException;
import pl.org.opi.ragdeterm.db.stmt.CallableStatementAfterExec;
import pl.org.opi.ragdeterm.db.stmt.CallableStatementSetParams;
import pl.org.opi.ragdeterm.db.stmt.PreparedStatementSetParams;
import pl.org.opi.ragdeterm.db.stmt.ProcessResultSet;
import pl.org.opi.ragdeterm.db.trx.Trx;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Slf4j
public abstract class BaseRepo {

    @Getter
    private final Trx trx;

    public BaseRepo(Trx trx) {
        this.trx = trx;
    }

    protected Connection getConn() {
        return trx.getConn();
    }

    public int executeWrite(String sql, PreparedStatementSetParams setParams) {
        try (PreparedStatement stmt = trx.getConn().prepareStatement(sql)) {
            setParams.setStmtParams(stmt);
            return stmt.executeUpdate();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

    public void executeSelect(String sql, PreparedStatementSetParams setParams, ProcessResultSet processResultSet) {
        try (PreparedStatement stmt = trx.getConn().prepareStatement(sql)) {
            setParams.setStmtParams(stmt);
            ResultSet rs = stmt.executeQuery();
            processResultSet.process(rs);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

    public void executeCall(String sql, CallableStatementSetParams setParams, CallableStatementAfterExec afterExec) {
        try (CallableStatement stmt = trx.getConn().prepareCall(sql)) {
            setParams.setStmtParams(stmt);
            stmt.execute();
            afterExec.afterExec(stmt);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new DbException(ex.getMessage(), ex);
        }
    }

}
