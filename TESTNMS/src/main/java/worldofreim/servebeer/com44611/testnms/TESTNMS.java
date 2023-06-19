package worldofreim.servebeer.com44611.testnms;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.ProfilePublicKey;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class TESTNMS extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("phasm").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
                if(sender instanceof Player player) {
                    CraftPlayer craftPlayer = (CraftPlayer) player;
                    ServerPlayer sp = craftPlayer.getHandle();
                    MinecraftServer server = sp.getServer();
                    ServerLevel level = sp.getLevel();
                    GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "SuperMan");
                    ServerPlayer npc = new ServerPlayer(server, level, gameProfile, null);
                    npc.setPos(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());

                    ServerGamePacketListenerImpl ps = sp.connection;
                    ps.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, npc));
                    ps.send(new ClientboundAddPlayerPacket(npc));
                    return true;
                }
                return false;
            }
        });


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
