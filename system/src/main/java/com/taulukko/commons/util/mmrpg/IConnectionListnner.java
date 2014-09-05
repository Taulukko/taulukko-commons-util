package com.taulukko.commons.util.mmrpg;

import com.taulukko.commons.util.mmrpg.packets.PacketBase;

public interface IConnectionListnner
{
	public void packetLifeOut(PacketBase packet);
	public void disconnect(Session session);
}
