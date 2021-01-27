package net.legacyfabric.fabric.test.command;

import net.minecraft.class_1999;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import net.legacyfabric.fabric.api.command.v1.CommandRegistrationCallback;

public class CommandTest extends class_1999 implements ModInitializer {
	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((manager, server) -> manager.method_29056(this));
	}

	@Override
	public String method_29277() {
		return "mods";
	}

	@Override
	public String method_29275(CommandSource commandSource) {
		return "Lists all mods";
	}

	@Override
	public void method_29272(MinecraftServer server, CommandSource source, String[] args) throws CommandException {
		FabricLoader.getInstance().getAllMods().stream().map(ModContainer::getMetadata).forEach(meta -> {
			LiteralText modText = new LiteralText(meta.getName());
			LiteralText metaText = new LiteralText("");
			modText.setStyle(modText.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, metaText)));
			LiteralText idText = new LiteralText("Id: " + meta.getId());
			idText.setStyle(idText.getStyle().setColor(Formatting.YELLOW));
			metaText.append(idText).append("\n");
			metaText.append("Version: " + meta.getVersion());
			source.sendSystemMessage(modText);
		});
	}
}