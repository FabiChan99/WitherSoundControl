package com.fabichan.WitherSoundControl.Events;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.fabichan.WitherSoundControl.Main;


public class WorldEventListener {
    public WorldEventListener(Main plugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(
                new PacketAdapter(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.WORLD_EVENT) {
                    @Override
                    public void onPacketSending(PacketEvent event) {
                        PacketContainer packetContainer = event.getPacket();
                        if (packetContainer.getIntegers().read(0) == 1023 && plugin.getConfig().getBoolean("Enabled")) {
                            packetContainer.getBooleans().write(0, false);
                        }
                    }
                });
    }
}
