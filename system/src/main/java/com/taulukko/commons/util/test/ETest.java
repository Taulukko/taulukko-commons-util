/*
 * Created on 25/07/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.taulukko.commons.util.test;

/**
 * @author Edson
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ETest implements IETest
{
    private String m_sName = "";
    private String m_sMsgSuccess = "";
    private String m_sMsgFail = "";
    private String m_sMsgOcor = "";
    private String m_sMsgNoEspec = "";
    private String m_sMsgEspec = "";
    private long m_lSuccess = 0;
    private long m_lFail = 0;

    public ETest(
        String sNameTest,
        String sMsgSuccess,
        String sMsgFail,
        String sMsgEspec,
        String sMsgNoEspec,
        String sMsgOcor)
    {
        m_sName = sNameTest;
        m_sMsgFail = sMsgFail;
        m_sMsgSuccess = sMsgSuccess;
        m_sMsgEspec = sMsgEspec;
        m_sMsgNoEspec = sMsgNoEspec;
        m_sMsgOcor = sMsgOcor;
    }

    public void assertNulls(Object oNull)
    {
        testNull(oNull == null, oNull);
    }

    public void assertNotNulls(Object oNull)
    {
        testNull(oNull != null, oNull);
    }

    private void testNull(boolean bSuccess, Object oNull)
    {

        if (bSuccess)
        {
            System.out.println(m_sMsgSuccess);
            System.out.println(
                m_sMsgEspec + " null " + m_sMsgOcor + " " + oNull);
            m_lSuccess++;
        }
        else
        {
            System.out.println(m_sMsgFail);
            System.out.println(
                m_sMsgEspec + " null " + m_sMsgOcor + " " + oNull);
            m_lFail++;
        }
    }

    public void assertEquals(Object sEsperected, Object sOcorred)
    {
        testEqual(
            sEsperected.toString().equals(sOcorred.toString()),
            sEsperected.toString(),
            sOcorred.toString());
    }

    public void assertDiferences(Object sEsperected, Object sOcorred)
    {
        testEqual(
            !sEsperected.toString().equals(sOcorred.toString()),
            sEsperected.toString(),
            sOcorred.toString());
    }

    private void testEqual(
        boolean bSuccess,
        String sEsperected,
        String sOcorred)
    {
        if (bSuccess)
        {
            System.out.println(m_sMsgSuccess);
            System.out.println(
                m_sMsgEspec
                    + " "
                    + sEsperected
                    + " "
                    + m_sMsgOcor
                    + " "
                    + sOcorred);
            m_lSuccess++;
        }
        else
        {
            System.out.println(m_sMsgFail);
            System.out.println(
                m_sMsgEspec
                    + " "
                    + sEsperected
                    + " "
                    + m_sMsgOcor
                    + " "
                    + sOcorred);
            m_lFail++;
        }
    }

 
    public void testNull(Object oNull)
    {
        if (oNull == null)
        {
            System.out.println(m_sMsgSuccess);
            System.out.println(
                m_sMsgEspec + " null " + m_sMsgOcor + " " + oNull);
        }
        else
        {
            System.out.println(m_sMsgFail);
            System.out.println(
                m_sMsgEspec + " null " + m_sMsgOcor + " " + oNull);
        }
    }

    public void run()
    {
        System.out.println("Starting " + m_sName + "...");
    }

    public void finalize()
    {
        System.out.println("Finished: ");
        System.out.println((m_lFail + m_lSuccess) + " tests conclued");
        System.out.println(m_lFail + " Errors and " + m_lSuccess + " Success.");
    }
}
