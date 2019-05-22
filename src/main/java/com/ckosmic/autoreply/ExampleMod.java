package com.ckosmic.autoreply;

import com.mojang.brigadier.Command;
import io.github.cottonmc.cotton.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.client.util.InputUtil;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.StringTextComponent;
import net.minecraft.text.TextComponent;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static net.minecraft.server.command.CommandManager.argument;

public class ExampleMod implements ClientModInitializer {
	public static AutoReplyConfig config;
	private static FabricKeyBinding keyBinding;

	@Override
	public void onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		config = ConfigManager.loadConfig(AutoReplyConfig.class);

		registerCommands();

		Helper.setupChatMessages();

		KeyBindingRegistry.INSTANCE.addCategory("ckosmic.autoreply");
		keyBinding = FabricKeyBinding.Builder.create(
				new Identifier("ckosmic:autoreply"),
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_R,
				"ckosmic.autoreply"
		).build();
		KeyBindingRegistry.INSTANCE.register(keyBinding);

		ClientTickCallback.EVENT.register(e -> {
			if(keyBinding.wasPressed()) {
				arreload();
				e.player.addChatMessage(new StringTextComponent("AutoReply config reloaded."), false);
			}
		});
	}

	private void registerCommands() {
		//arrecurtick [Number of Ticks Between Sends]
		CommandRegistry.INSTANCE.register(false, (dispatcher) -> dispatcher.register(
				CommandManager.literal("arrecurtick")
						.then(argument("Number of Ticks Between Sends", integer())
								.executes(c -> {
									config.timeBetween = getInteger(c, "Number of Ticks Between Sends");
									ConfigManager.saveConfig(config, "AutoReplyConfigFile");
									c.getSource().sendFeedback(new StringTextComponent("Recurring message will be sent every " + getInteger(c, "Number of Ticks Between Sends") + " ticks."), false);
									return Command.SINGLE_SUCCESS;
								}))
		));
		//arrecurphrase [Recurring Phrase]
		CommandRegistry.INSTANCE.register(false, (dispatcher) -> dispatcher.register(
				CommandManager.literal("arrecurphrase")
						.then(argument("Recurring Phrase", string())
								.executes(c -> {
									config.persistentPhrase = getString(c, "Recurring Phrase");
									ConfigManager.saveConfig(config, "AutoReplyConfigFile");
									c.getSource().sendFeedback(new StringTextComponent("Recurring message set to \"" + getString(c, "Recurring Phrase") + "\"."), false);
									return Command.SINGLE_SUCCESS;
								}))
		));
		//arrecurtoggle
		CommandRegistry.INSTANCE.register(false, (dispatcher) -> dispatcher.register(
				CommandManager.literal("arrecurtoggle")
						.executes(c -> {
							config.persistentChat = !config.persistentChat;
							ConfigManager.saveConfig(config, "AutoReplyConfigFile");
							c.getSource().sendFeedback(new StringTextComponent("Recurring message has been turned " + (config.persistentChat ? "on" : "off") + "."), false);
							return Command.SINGLE_SUCCESS;
						})
		));
		//arrecur [Enabled?]
		CommandRegistry.INSTANCE.register(false, (dispatcher) -> dispatcher.register(
				CommandManager.literal("arrecur")
						.then(argument("Enabled?", bool())
								.executes(c -> {
									config.persistentChat = getBool(c, "Enabled?");
									ConfigManager.saveConfig(config, "AutoReplyConfigFile");
									c.getSource().sendFeedback(new StringTextComponent("Recurring message set to \"" + (config.persistentChat ? "on" : "off") + "\"."), false);
									return Command.SINGLE_SUCCESS;
								}))
		));
		//aradditem [Item Name] [Trigger Term]
		CommandRegistry.INSTANCE.register(false, (dispatcher) -> dispatcher.register(
				CommandManager.literal("araddterm")
						.then(argument("Item Name", string())
								.then(argument("Trigger Term", string())
								.executes(c -> {
									config.terms.add(getString(c, "Trigger Term") + "|" + getString(c, "Item Name"));
									ConfigManager.saveConfig(config, "AutoReplyConfigFile");
									Helper.setupChatMessages();
									c.getSource().sendFeedback(new StringTextComponent("Added term \"" + getString(c, "Trigger Term") + "\" to item \"" + getString(c, "Item Name") + "\"."), false);
									return Command.SINGLE_SUCCESS;
								})))
		));
		//araddin [In Phrase]
		CommandRegistry.INSTANCE.register(false, (dispatcher) -> dispatcher.register(
				CommandManager.literal("araddin")
						.then(argument("In Phrase", string())
								.executes(c -> {
									config.ins.add(getString(c, "In Phrase"));
									ConfigManager.saveConfig(config, "AutoReplyConfigFile");
									c.getSource().sendFeedback(new StringTextComponent("Added in-phrase \"" + getString(c, "In Phrase") + "\"."), false);
									return Command.SINGLE_SUCCESS;
								}))
		));
		//araddout [Out Phrase]
		CommandRegistry.INSTANCE.register(false, (dispatcher) -> dispatcher.register(
				CommandManager.literal("araddout")
						.then(argument("Out Phrase", string())
								.executes(c -> {
									config.outs.add(getString(c, "Out Phrase"));
									ConfigManager.saveConfig(config, "AutoReplyConfigFile");
									c.getSource().sendFeedback(new StringTextComponent("Added out-phrase \"" + getString(c, "Out Phrase") + "\"."), false);
									return Command.SINGLE_SUCCESS;
								}))
		));
		//arreload
		CommandRegistry.INSTANCE.register(false, (dispatcher) -> dispatcher.register(
				CommandManager.literal("arreload")
						.executes(c -> {
							arreload();
							c.getSource().sendFeedback(new StringTextComponent("AutoReply config reloaded."), false);
							return Command.SINGLE_SUCCESS;
						})
		));
		//araddshopitem [Item Name] [Quantity] [Price]
		CommandRegistry.INSTANCE.register(false, (dispatcher) -> dispatcher.register(
				CommandManager.literal("araddshopitem")
						.then(argument("Item Name", string()).then(argument("Quantity", string()).then(argument("Price", string())
						.executes(c -> {
							config.shopItems.add(getString(c, "Item Name") + "|" + getString(c, "Quantity") + "|$" + getString(c, "Price"));
							ConfigManager.saveConfig(config, "AutoReplyConfigFile");
							c.getSource().sendFeedback(new StringTextComponent("Added " + getString(c, "Item Name") + " to shop stock."), false);
							return Command.SINGLE_SUCCESS;
						}))))
		));
	}

	private void arreload() {
		config = ConfigManager.loadConfig(AutoReplyConfig.class);
		Helper.setupChatMessages();
	}

	private String[] addToArray(String[] array, String element) {
		String[] newArray = new String[array.length + 1];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		newArray[array.length] = element;
		return newArray;
	}
	private String[][] addToArray(String[][] array, String[] element) {
		String[][] newArray = new String[array.length + 1][];
		for(int i = 0; i < array.length; i++) {
			newArray[i] = array[i];
		}
		newArray[array.length] = element;
		return newArray;
	}
}
