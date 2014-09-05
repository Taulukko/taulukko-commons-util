package com.taulukko.commons.util.struct;

public class EDoubleNode
{
   EDoubleNode m_parent=null;
   EDoubleNode m_NextChild=null;
   EDoubleNode m_PreviousChild=null;
   Object m_info=null;
   
   EDoubleNode getParent()
   {
   		return m_parent;
   }

   void setParent(EDoubleNode no)
   {
   		m_parent = no;
   }
   
   EDoubleNode getNextChild()
   {
		return m_NextChild;
   }

   void setNextChild(EDoubleNode no)
   {
		m_NextChild= no;
   }
   
   EDoubleNode getPreviousChild()
   {
		return m_PreviousChild;
   }

   void setPreviousChild(EDoubleNode no)
   {
		m_PreviousChild= no;
   }
   
   Object getInfo()
   {
   		return m_info;
   }
   
   void setInfo(Object inf)
   {
   		m_info = inf;
   }
}