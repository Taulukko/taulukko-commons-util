/*
 * Created on 25/08/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import com.taulukko.commons.util.lang.EString;

/**
 * @author ecarli
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EConectorMSSQL extends EConnector implements IEConector
{
    private String m_sUserName = null;
    private String m_sPassword = null;
    private String m_sHost = null;
    private String m_sCatalog = null;
    private Connection m_con = null;
    

    public EConectorMSSQL(
        String sUsername,
        String sPassword,
        String sHost,
        String sCatalog)
    {
        this.m_sCatalog = sCatalog;
        this.m_sPassword = sPassword;
        this.m_sHost = sHost;
        this.m_sUserName = sUsername;
    }

	
	public boolean getIsOpen()
	{
		try
		{
			return !m_con.isClosed();
		}
		catch(Exception e)
		{
			return false;
		}
	}  
	

    public void open() throws SQLException
    {
    	
		
        //jdbc URL
        String sDbUrl = "jdbc:microsoft:sqlserver://" + this.m_sHost + ";databaseName=" +this.m_sCatalog + ";selectMethod=cursor;";

        //driver a ser usado
        String sDbClass = "com.microsoft.jdbc.sqlserver.SQLServerDriver";

        //cria o log
        if (this.getCreateLog())
        {
            System.out.println("Loading JDBC driver '" + sDbClass + "'");
            System.out.println("Open Connection to " + this.m_sUserName);
        }

        try
        {
            Class.forName(sDbClass).newInstance();
        }
        catch (Exception ex)
        {
            SQLException sqle = new SQLException(ex.getMessage());
            /*NAO FUNCIONA NO WEBSPHERE
            sqle.setStackTrace(ex.getStackTrace());*/
            ex.printStackTrace();
            throw sqle;
        }

      
            try
            {
                //se conecta
                this.m_con =
                    DriverManager.getConnection(
                        sDbUrl,
                        this.m_sUserName,
                        this.m_sPassword);
                if (this.m_con == null)
                {
                    throw new SQLException("User not have permission for this catalog!");
                }
                else if(this.m_con.isClosed())
                {
                    throw new SQLException("DataBase Server is bussnes!");
                }
            }
            catch (Exception ex)
            {
	            SQLException sqle = new SQLException(ex.getMessage());
				/*NAO FUNCIONA NO WEBSPHERE
   		         sqle.setStackTrace(ex.getStackTrace());*/
   		         ex.printStackTrace();
                 throw sqle;
            }
       
    }

    public EConectorMSSQL(String sUsername, String sPassword, String sCatalog)
    {
        this(sUsername, sPassword, "localhost:1433", sCatalog);
    }

    /* (non-Javadoc)
     * Autor:ecarli
     * Data:25/08/2003
     * Objetivo: Inicializa uma transa��o.
     * @see br.com.vb.data.IConector#BeginTrans()
     */
    public void beginTrans() throws SQLException
    {
    	if (m_con != null)
    	{
	    	m_con.setAutoCommit(false);
    	}
    	else
    	{
    		throw new SQLException("Conection closed!");
    	}
    }

    /* (non-Javadoc)
     * Autor:ecarli
     * Data:25/08/2003
     * Objetivo:
     * @see br.com.vb.data.IConector#CommitTrans()
     */
    public void commitTrans() throws SQLException
    {
    	if (m_con != null)
    	{
    		
	    	//comita e seta como false o autocomit
 	       	m_con.commit();
  	   		m_con.setAutoCommit(false);
  	     }
    	else
    	{
    		throw new SQLException("Conection closed!");
    	}

    }

    /* (non-Javadoc)
     * Autor:ecarli
     * Data:25/08/2003
     * Objetivo:
     * @see br.com.vb.data.IConector#execute(java.lang.String)
     */
    public void execute(String sSQL) throws SQLException
    {
        //gera a clausula
        Statement st = this.m_con.createStatement();

        //executa a clausula
        st.executeUpdate(sSQL);
        _affectedRows = st.getUpdateCount();
        //fecha o statenent
        st.close();
    }

    /* (non-Javadoc)
     * Autor:ecarli
     * Data:25/08/2003
     * Objetivo:
     * @see br.com.vb.data.IConector#getRecordSet(java.lang.String)
     */
    public ERecordSet getRecordSet(String sSQL) throws SQLException
    {
    	
		if(m_con==null)
		{
			throw new SQLException("Connector fail!");
		}


        //gera a clausula
        Statement st = m_con.createStatement();
        

		if(st==null)
		{
			throw new SQLException("Connector fail!");
		}

		//cria o resultset
		ResultSet rs = st.executeQuery(sSQL);
		
        //selecionando itens        
        return new ERecordSet(rs,st);
    }

    /* (non-Javadoc)
     * Autor:ecarli
     * Data:25/08/2003
     * Objetivo:
     * @see br.com.vb.data.IConector#RollbackTrans()
     */
    public void rollbackTrans() throws SQLException
    {
		if(m_con != null)
		{
    		
			m_con.rollback();
	 		m_con.setAutoCommit(false);
 		}
    	else
    	{
    		throw new SQLException("Conection closed!");
    	}   
    }

    public void close()
    {

		//VERIFICAR POOL DE CONEX�ES
       
       try
        {
            if (!this.m_con.isClosed())
            {
                this.m_con.close();

                //cria log
                if (this.getCreateLog())
                {
                    System.out.println(
                        "Close Connection to " + this.m_sUserName);
                }
            }
        }
        catch (Exception e)
        {
            
            
             //em caso de erro ignora(é porque a conexao ja esta fechada,
             //e fechar em exesso não é considerado erro no Conector

        }
    }

    public Object clone()
    {
        //retorna uma cópia
        return new EConectorMSSQL(
            m_sUserName,
            m_sPassword,
            m_sHost,
            m_sCatalog);
    }


	public long getSleepTime()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String clearString(String value)
	{
		EString ret = new EString(value.trim());
		ret = ret.replace(new EString("\\"), new EString("\\\\"));
		ret = ret.replace(new EString("'"), new EString("''"));
		return ret.toString();
	}


	public boolean exist(String sql)
	{
		// TODO Auto-generated method stub
		return false;
	}


	public Savepoint createSavePoint() throws SQLException
	{
		// TODO Auto-generated method stub
		return null;
	}


	public void removeSavePoint(Savepoint save) throws SQLException
	{
		// TODO Auto-generated method stub
		
	}


	public void rollbackTrans(Savepoint save) throws SQLException
	{
		// TODO Auto-generated method stub
		
	}


	public void commitTrans(Savepoint save) throws SQLException
	{
		// TODO Auto-generated method stub
		
	}


	public boolean inTransaction() throws SQLException
	{
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void keepalive()
	{
		// TODO Auto-generated method stub
		
	}

}
