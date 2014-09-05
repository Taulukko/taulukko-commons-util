package com.taulukko.commons.util.struct;
import java.util.Enumeration;
import java.util.Iterator;

public class EVector<T>
{
    Object m_obj[];

    public EVector(T[] t)
    {
        this.redim(t.length);
        for(int iCont=0;iCont<t.length;iCont++)
        {
            this.set(iCont,t[iCont]);
        }
    }
    
    public EVector(int iSize)
    {
        this.redim(iSize);
    }
    //redimensiona o vetor
    public void redim(int iSize)
    {
        m_obj = new Object[iSize];
    }
    //redimensiona o vetor
    public void redimPreserve(int iSize)
    {
        Object novo[] = new Object[iSize];
        //copia todo o vetor
        for (int iCont = 0; iCont < m_obj.length; iCont++)
        {
            novo[iCont] = m_obj[iCont];
        }
        //substitui o vetor
        m_obj = novo;
    }
    public void set(int iIndex, T obj)
    {
        m_obj[iIndex] = obj;
    }
    public T get(int iIndex)
    {
        return (T)m_obj[iIndex];
    }
    public int getLength()
    {
        return m_obj.length;
    }
    public Iterator<T> getItens()
    {
        return new EVisitorVector(this);
    }
    public Enumeration<T> getElements()
    {
        return new EVisitorVector(this);
    }
    public EVector<T> union(EVector<T> vector)
    {
        try
        {
            //clona o objeto atual
            EVector<T> ret = (EVector<T>) this.clone();
            //aumenta seu tamanho com o do vetor recebido
            ret.redimPreserve(this.getLength() + vector.getLength());
            //adiciona os elementos do vetor recebido
            Enumeration varEnum = vector.getElements();
            for (int iCont = this.getLength(); varEnum.hasMoreElements(); iCont++)
            {
                ret.set(iCont, (T)varEnum.nextElement());
            }
            //devolve a uniao
            return ret;
        }
        catch (Exception e)
        {
            //imprime o erro e sai
            e.printStackTrace();
            return null;
        }
    }
    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public EVector<T> clone() throws CloneNotSupportedException
    {
        //cria o retorno
        EVector<T> ret = new EVector<T>(this.getLength());
        Enumeration varEnum = this.getElements();
        //Para cada item em this , ele copia        
        for (int iCont = 0; varEnum.hasMoreElements(); iCont++)
        {
            ret.set(iCont, (T) varEnum.nextElement());
        }
        //retorna o item
        return ret;
    }
    public EVector<T> revert()
    {
        //cria o retorno
        EVector<T> ret = new EVector<T>(this.getLength());
        for (int iCont = this.getLength() - 1; iCont >= 0; iCont--)
        {
            ret.set(iCont, this.get(this.getLength() - (iCont + 1)));
        }
        //retorna
        return ret;
    }
    
    public Object[] toArray()
    {
    	Object ret[] = new Object[this.getLength()];
    	
    	for(int iCount = 0 ; iCount<this.getLength();iCount++)
    	{
    		ret[iCount]=this.get(iCount);
    	}
    	
    	return ret;
    }
}