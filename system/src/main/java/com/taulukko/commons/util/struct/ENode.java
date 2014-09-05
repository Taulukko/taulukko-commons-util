package com.taulukko.commons.util.struct;

public class ENode<V>
{
   ENode<V> m_parent=null;
   ENode<V> m_child=null;
   V m_info=null;
   
   ENode<V> getParent()
   {
   		return m_parent;
   }

   void setParent(ENode<V> no)
   {
   		m_parent = no;
   }
   
   ENode<V> getChild()
   {
   		return m_child;
   }

   void setChild(ENode<V> no)
   {
   		m_child = no;
   }
   
   V getInfo()
   {
   		return m_info;
   }
   
   void setInfo(V inf)
   {
   		m_info = inf;
   }
}