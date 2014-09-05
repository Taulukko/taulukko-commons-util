/*
 * Created on 28/10/2004
 *
 */
package com.taulukko.commons.util.data.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import com.taulukko.commons.util.data.EConnectorMSAccess;
import com.taulukko.commons.util.data.ERecordSet;
import com.taulukko.commons.util.lang.EMath;
import com.taulukko.commons.util.lang.EString;
import com.taulukko.commons.util.struct.ESet;
import com.taulukko.commons.util.struct.EStackList;

/**
 * @author Edson Vicente Carli Junior 
 */
public class EEntity
{
	private ESet m_columns = new ESet();
	private String m_sName = "";
	private EConnectorMSAccess m_con;

	public EEntity(EConnectorMSAccess con, String sName, ECollumn columns[])
	{
		//cria o nome
		m_sName = sName;
		m_con = con;

		//configura as colunas
		for (int iCont = 0; iCont < columns.length; iCont++)
		{
			//para cada coluna, a adiciona ao conjunto de colunas
			m_columns.add(columns[iCont]);
		}
	}

	public ECollumn getColumn(String sName)
	{
		Enumeration varEnum = m_columns.getElements();

		while (varEnum.hasMoreElements())
		{
			ECollumn column = (ECollumn) varEnum.nextElement();
			if (column.getName().equals(sName))
			{
				return column;
			}
		}

		//retorna como nao encontrado
		return null;
	}

	public ECollumn setColumn(String sName, String sValue)
	{
		Enumeration varEnum = m_columns.getElements();

		while (varEnum.hasMoreElements())
		{
			ECollumn column = (ECollumn) varEnum.nextElement();
			if (column.getName().equals(sName))
			{
				column.setValue(sValue);
				return column;
			}
		}

		//retorna como nao encontrado
		return null;
	}

	public void validColumns() throws EEntityException
	{
		Enumeration varEnum = m_columns.getElements();

		while (varEnum.hasMoreElements())
		{

			ECollumn column = (ECollumn) varEnum.nextElement();
			if (column.getValue() != null)
			{

				//valida as colunas						
				switch (column.getType())
				{
					case ECollumn.TP_AUTONUMERIC :
						{

							if (!EMath.isDecimal(column.getValue())) 
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " is autonumeric and not accept text values!",
									201);
							}
							else if (!EMath.isInteger(column.getValue()))
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " is autonumeric and not accept decimal values!",
									202);
							}

							break;
						}
					case ECollumn.TP_BINARY :
						{
							//FALTA VALIDAR E CONFIGURAR ENTRADA NO DB						
							break;
						}
					case ECollumn.TP_BYTE :
						{
							if (!EMath.isDecimal(column.getValue()))
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " is byte and not accept text values!",
									203);
							}
							else if (!EMath.isInteger(column.getValue()))
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " is byte and not accept decimal values!",
									204);
							}
							else if (
								Long.parseLong(column.getValue())
									> Byte.MAX_VALUE)
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " is byte and not accept values > "
										+ Byte.MAX_VALUE
										+ "!",
									205);
							}
							else if (
								Long.parseLong(column.getValue())
									< Byte.MIN_VALUE)
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " is byte and not accept values < "
										+ Byte.MIN_VALUE
										+ "!",
									206);
							}
							break;
						}
					case ECollumn.TP_CHAR :
						{
							if (column.getValue().length() > 1)
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " is char and not accept text values!",
									207);
							}
							break;
						}
					case ECollumn.TP_DATE :
						{
							//FALTA VALIDAR
							break;
						}
					case ECollumn.TP_DOUBLE :
						{
							if (!EMath.isDecimal(column.getValue()))
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " is double and not accept text values!",
									208);
							}
							break;
						}

					case ECollumn.TP_INTEGER :
						{
							if (!EMath.isDecimal(column.getValue()))
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " is integer and not accept text values!",
									209);
							}
							else if (!EMath.isInteger(column.getValue()))
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " is integer and not accept decimal values!",
									210);
							}
							else if (
								Integer.parseInt(column.getValue())
									> Integer.MAX_VALUE)
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " is integer and not accept values > "
										+ Integer.MAX_VALUE
										+ "!",
									211);
							}
							else if (
								Integer.parseInt(column.getValue())
									< Integer.MIN_VALUE)
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " is integer and not accept values < "
										+ Integer.MIN_VALUE
										+ "!",
									212);
							}
							break;
						}
					case ECollumn.TP_LONG :
						{
							if (!EMath.isDecimal(column.getValue()))
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " is long and not accept text values!",
									213);
							}
							else if (EMath.isInteger(column.getValue()))
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " is long and not accept decimal values!",
									214);
							}
							break;

						}
					case ECollumn.TP_STRING :
						{
							break;
						}
					case ECollumn.TP_TIMESTAMP :
						{
							//falta validacao
							break;
						}
				}
			}
		}
	}

	public void get() throws EEntityException, SQLException
	{
		//verifica se existe o que incluir
		if (m_columns.getLength() == 0)
		{
			throw new EEntityException("Column no exist!", 101);
		}

		//valida as colinas
		validColumns();

		String sSQL = "SELECT ";
		String sColumns = "";
		String sValues = "";

		Enumeration varEnum = m_columns.getElements();

		while (varEnum.hasMoreElements())
		{

			ECollumn column = (ECollumn) varEnum.nextElement();

			String sValue = column.getValue();
			String sColumn = column.getName();

			if (column.getValue() != null)
			{

				//valida as colunas						
				switch (column.getType())
				{
					case ECollumn.TP_AUTONUMERIC :
					case ECollumn.TP_BYTE :
					case ECollumn.TP_DOUBLE :
					case ECollumn.TP_INTEGER :
					case ECollumn.TP_LONG :
					case ECollumn.TP_TIMESTAMP :
						{
							break;
						}
					case ECollumn.TP_CHAR :
					case ECollumn.TP_DATE :
					case ECollumn.TP_STRING :
						{
							sValue =
								(sValue == null) ? "null" : "'" + sValue + "'";
							break;
						}
					default :
						{
							throw new EEntityException(
								"Column "
									+ column.getName()
									+ " have a incorrect type!",
								102);
						}

				}

				//adiciona o valor
				sValues =
					sValues
						+ this.getName()
						+ "."
						+ sColumn
						+ "="
						+ sValue
						+ " AND ";

			}
			//adiciona a coluna
			sColumns = sColumns + this.getName() + "." + sColumn + ",";

		}
		//ignora o ultimo caracter e o ultimo AND
		sValues = new EString(sValues).left(sValues.length() - 4).toString();
		sColumns = new EString(sColumns).left(sColumns.length() - 1).toString();

		//finaliza a construcao de SQL
		sSQL =
			sSQL + sColumns + " FROM " + this.getName() + " WHERE " + sValues;

		//executa a insercao 
		ERecordSet ers = m_con.getRecordSet(sSQL);
		ResultSet rs = ers.getResultSet();

		if (rs.next())
		{

			varEnum = m_columns.getElements();

			while (varEnum.hasMoreElements())
			{

				ECollumn column = (ECollumn) varEnum.nextElement();
				this.setColumn(
					column.getName(),
					rs.getString(column.getName()));

			}
			//fecha o eresultset
			ers.close();
		}
		else
		{

			//fecha o eresultset
			ers.close();

			throw new EEntityException("Row not exist!", 103);
		}

	}

	public void add() throws EEntityException, SQLException
	{

		//verifica se existe o que incluir
		if (m_columns.getLength() == 0)
		{
			throw new EEntityException("Column no exist!", 1);
		}

		//verifica se o registro ja n�o existe
		try
		{
			this.get();
			//deveria ocorrer erro se n�o existisse
			throw new EEntityException("Duplicate row values!", 2);
		}
		catch (EEntityException e)
		{
			if (e.getCode() != 103)
			{
				//erro diferente de n�o existe
				throw e;
			}
			//se  n�o , caso seja 103 esta correto
		}

		//valida as colinas
		validColumns();

		String sSQL = "INSERT INTO " + m_sName + "(";
		String sColumns = "";
		String sValues = "";

		Enumeration varEnum = m_columns.getElements();

		while (varEnum.hasMoreElements())
		{

			ECollumn column = (ECollumn) varEnum.nextElement();

			String sValue = column.getValue();
			String sColumn = column.getName();

			//valida as colunas						
			switch (column.getType())
			{
				case ECollumn.TP_AUTONUMERIC :
					{
						if (column.getValue() != null)
						{
							throw new EEntityException(
								"Column "
									+ column.getName()
									+ " is autonumeric and not accept values!",
								3);
						}
						break;
					}
				case ECollumn.TP_BYTE :
				case ECollumn.TP_DOUBLE :
				case ECollumn.TP_INTEGER :
				case ECollumn.TP_LONG :
				case ECollumn.TP_TIMESTAMP :
					{
						break;
					}
				case ECollumn.TP_CHAR :
				case ECollumn.TP_DATE :
				case ECollumn.TP_STRING :
					{
						sValue = (sValue != null) ? "'" + sValue + "'" : "null";
						break;
					}
				default :
					{
						throw new EEntityException(
							"Column "
								+ column.getName()
								+ " have a incorrect type!",
							4);
					}

			}

			//adiciona o valor
			if (sValue != null)
			{
				sValues = sValues + sValue + ",";
				sColumns = sColumns + sColumn + ",";
			}

		}

		//ignora o �ltimo caracter
		sValues = new EString(sValues).left(sValues.length() - 1).toString();
		sColumns = new EString(sColumns).left(sColumns.length() - 1).toString();

		//finaliza a construcao de SQL
		sSQL = sSQL + sColumns + ") VALUES(" + sValues + ")";

		//executa a inser��o
		m_con.execute(sSQL);

		//atualiza as informacoes
		this.get();

	}

	public void remove() throws EEntityException, SQLException
	{

		//verifica se existe o que incluir
		if (m_columns.getLength() == 0)
		{
			throw new EEntityException("Column no exist!", 1);
		}

		//verifica se existe as chaves
		validColumns();

		String sSQL = "DELETE FROM " + this.getName() + " WHERE ";
		String sWhere = "";

		Enumeration  varEnum = m_columns.getElements();

		while (varEnum.hasMoreElements())
		{

			ECollumn column = (ECollumn) varEnum.nextElement();

			String sItem = "";

			if (column.getIsKey())
			{
				if (column.getValue() != null)
				{

					//valida as colunas						
					switch (column.getType())
					{
						case ECollumn.TP_AUTONUMERIC :
						case ECollumn.TP_BYTE :
						case ECollumn.TP_DOUBLE :
						case ECollumn.TP_INTEGER :
						case ECollumn.TP_LONG :
						case ECollumn.TP_TIMESTAMP :
							{
								sItem =
									column.getName()
										+ " = "
										+ column.getValue()
										+ " AND ";
								break;
							}
						case ECollumn.TP_CHAR :
						case ECollumn.TP_DATE :
						case ECollumn.TP_STRING :
							{
								sItem =
									column.getName()
										+ " = '"
										+ column.getValue()
										+ "' AND ";
								break;
							}
						default :
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " have a incorrect type!",
									301);
							}

					}
				}
				else
				{
					throw new EEntityException(
						"Key " + column.getName() + " is null!",
						302);
				}
				//adiciona o valor
				if (sItem != null)
				{
					sWhere = sWhere + sItem;
				}
			}

		}

		//ignora o �ltimo caracter
		sWhere = new EString(sWhere).left(sWhere.length() - 4).toString();

		//finaliza a construcao de SQL
		sSQL = sSQL + sWhere;

		//Captura e verifica se o registro existe
		this.get();

		//executa a inser��o
		m_con.execute(sSQL);

		//atualiza as informacoes
		varEnum = m_columns.getElements();

		while (varEnum.hasMoreElements())
		{
			//limpa as colunas
			ECollumn column = (ECollumn) varEnum.nextElement();
			column.setValue(null);
		}
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return m_sName;
	}
	public static ECollumn[] getColumns()
	{
		return new ECollumn[] {
		};
	}

	public void update() throws EEntityException, SQLException
	{

		//verifica se existe o que incluir
		if (m_columns.getLength() == 0)
		{
			throw new EEntityException("Column no exist!", 401);
		}

		//verifica se o registro existe
		EEntity entity = (EEntity) this.clone();
		Enumeration  varEnum = entity.getElements();

		//apaga os valores da copia
		while (varEnum.hasMoreElements())
		{

			ECollumn column = (ECollumn) varEnum.nextElement();

			if (!column.getIsKey())
			{
				column.setValue(null);
			}
		}

		//verifica se existe 
		entity.get();

		//valida as colinas
		validColumns();

		String sSQL = "UPDATE " + this.getName() + " SET ";

		String sUpdates = "";
		String sWhere = " WHERE ";

		varEnum = m_columns.getElements();

		while (varEnum.hasMoreElements())
		{

			ECollumn column = (ECollumn) varEnum.nextElement();

			String sValue = column.getValue();
			if (column.getValue() != null)
			{

				if (column.getIsKey())
				{
					//valida as colunas						
					switch (column.getType())
					{
						case ECollumn.TP_AUTONUMERIC :
						case ECollumn.TP_BYTE :
						case ECollumn.TP_DOUBLE :
						case ECollumn.TP_INTEGER :
						case ECollumn.TP_LONG :
						case ECollumn.TP_TIMESTAMP :
							{
								sWhere =
									sWhere
										+ column.getName()
										+ "="
										+ sValue
										+ " AND ";
								break;
							}
						case ECollumn.TP_CHAR :
						case ECollumn.TP_DATE :
						case ECollumn.TP_STRING :
							{
								sWhere =
									sWhere
										+ column.getName()
										+ " = "
										+ ((sValue == null)
											? " null "
											: "'" + sValue + "' ")
										+ " AND ";
								break;
							}
						default :
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " have a incorrect type!",
									402);
							}
					}
				}
				else
				{

					//valida as colunas						
					switch (column.getType())
					{
						case ECollumn.TP_AUTONUMERIC :
							{
								sValue = null;
								break;
							}
						case ECollumn.TP_BYTE :
						case ECollumn.TP_DOUBLE :
						case ECollumn.TP_INTEGER :
						case ECollumn.TP_LONG :
						case ECollumn.TP_TIMESTAMP :
							{
								break;
							}
						case ECollumn.TP_CHAR :
						case ECollumn.TP_DATE :
						case ECollumn.TP_STRING :
							{
								sValue =
									(sValue != null)
										? "'" + sValue + "'"
										: "null";
								break;
							}
						default :
							{
								throw new EEntityException(
									"Column "
										+ column.getName()
										+ " have a incorrect type!",
									403);
							}

					}

					if (sValue != null)
					{
						//adiciona o valor
						sUpdates =
							sUpdates + column.getName() + "=" + sValue + ",";
					}

				}

			}
		}

		//ignora o �ltimo caracter
		sUpdates = new EString(sUpdates).left(sUpdates.length() - 1).toString();
		sWhere = new EString(sWhere).left(sWhere.length() - 4).toString();

		//atualiza a clausula
		sSQL = sSQL + sUpdates + sWhere;

		//executa a atualiza��o
		m_con.execute(sSQL);

		//atualiza as informacoes
		this.get();

	}

	public Object clone()
	{

		Enumeration  varEnum = m_columns.getElements();
		ECollumn columns[] = new ECollumn[m_columns.getLength()];
		int iCont = 0;

		while (varEnum.hasMoreElements())
		{
			columns[iCont++] =
				(ECollumn) ((ECollumn) varEnum.nextElement()).clone();
		}

		//retorna a copia
		return new EEntity(m_con, this.getName(), columns);
	}

	public Enumeration getElements()
	{
		return m_columns.getElements();
	}

	protected EStackList getAll(String sCondition)
		throws EEntityException, SQLException
	{
		//retorno
		EStackList ret = new EStackList();

		//verifica se existe o que incluir
		if (m_columns.getLength() == 0)
		{
			throw new EEntityException("Column no exist!", 501);
		}

		//valida as colinas
		validColumns();

		String sSQL = "SELECT ";
		String sColumns = "";

		Enumeration varEnum = m_columns.getElements();

		while (varEnum.hasMoreElements())
		{

			ECollumn column = (ECollumn) varEnum.nextElement();

			//adiciona o valor
			sColumns = sColumns + this.getName() + "." + column.getName() + ",";

		}

		//ignora o �ltimo caracter e o �ltimo AND
		sColumns = new EString(sColumns).left(sColumns.length() - 1).toString();

		//finaliza a construcao de SQL
		sSQL = sSQL + sColumns + " FROM " + this.getName() + " " + sCondition;

		//executa a inser��o
		ERecordSet ers = m_con.getRecordSet(sSQL);
		ResultSet rs = ers.getResultSet();

		while (rs.next())
		{
			EEntity entity = (EEntity) this.clone();

			varEnum = m_columns.getElements();

			while (varEnum.hasMoreElements())
			{

				ECollumn column = (ECollumn) varEnum.nextElement();
				entity.setColumn(
					column.getName(),
					rs.getString(column.getName()));

			}

			//adiciona ao retorno
			ret.add(entity);

		}

		//fecha o eresultset
		ers.close();

		//retorna
		return ret;
	}

	public EStackList getAll() throws EEntityException, SQLException
	{
		return getAll("");
	}

}