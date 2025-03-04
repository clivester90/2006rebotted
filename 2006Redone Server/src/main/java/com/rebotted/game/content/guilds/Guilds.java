package com.rebotted.game.content.guilds;

import com.rebotted.GameConstants;
import com.rebotted.game.content.quests.QuestAssistant;
import com.rebotted.game.content.skills.SkillData;
import com.rebotted.game.globalworldobjects.PassDoor;
import com.rebotted.game.objects.impl.Climbing;
import com.rebotted.game.objects.impl.UseOther;
import com.rebotted.game.players.Player;

/**
 * Guilds Class
 * @author Andrew (I'm A Boss on Rune-Server, Mr Extremez on Moparscape & Runelocus)
 */

public class Guilds {

	private static boolean hasCompletedLegends = false;

	public static void attemptGuild(Player player, int objectId) {
		if (!GameConstants.GUILDS) {
			player.getPacketSender().sendMessage("Guilds are currently disabled.");
			return;
		}
		if (checkRequirements(player, objectId)) {
			player.getPacketSender().sendMessage("You pass through the guild.");
			movePlayer(player, objectId);
		}
	}

	public static void movePlayer(Player player, int objectId) {
		switch (objectId) {
		case 2514: // Range Guild
			if (player.absX == 2659 && player.absY == 3438) {
				PassDoor.passThroughDoor(player, objectId, 1, 2, 9, -1, 1, 0);
			} else if (player.absX == 2658 && player.absY == 3437) {
				PassDoor.passThroughDoor(player, objectId, 1, 2, 9, -1, 1, 0);
			} else if (player.absX == 2658 && player.absY == 3439) {
				PassDoor.passThroughDoor(player, objectId, 1, 2, 9, 1, -1, 0);
			} else if (player.absX == 2657 && player.absY == 3438) {
				PassDoor.passThroughDoor(player, objectId, 1, 2, 9, 1, -1, 0);
			}
			break;
		case 1805: // Champions Guild
			if (player.absY == 3362 && player.absX != 3192 && player.absX != 3190) {
				player.getPlayerAssistant().movePlayer(player.absX, player.absY + 1, 0);
			} else if (player.absY == 3363 && player.absX != 3192 && player.absX != 3190) {
				player.getPlayerAssistant().movePlayer(player.absX, player.absY - 1, 0);
			}
			break;
		case 2641: // Monastery
			if (player.heightLevel == 0) {
				Climbing.climbUp(player);
			} else if (player.heightLevel == 1) {
				Climbing.climbDown(player);
			}
			break;
		case 2712: // Cooks Guild
			if (player.absY == 3443) {
				player.getPlayerAssistant().movePlayer(player.absX, player.absY + 1, 0);
			} else if (player.absY == 3444) {
				player.getPlayerAssistant().movePlayer(player.absX, player.absY - 1, 0);
			}
			break;
		case 2647: // Crafting Guild
			if (player.absY == 3289) {
				player.getPlayerAssistant().movePlayer(player.absX, player.absY - 1, 0);
			} else if (player.absY == 3288) {
				player.getPlayerAssistant().movePlayer(player.absX, player.absY + 1, 0);
			}
			break;
		case 2113: // Mining Guild
			UseOther.useDown(player, objectId);
			break;
		case 1755: // Mining Guild
			UseOther.useUp(player, objectId);
			break;
		case 2025: // Fishing Guild
			if (player.absY == 3393) {
				player.getPlayerAssistant().movePlayer(player.absX, player.absY + 1, 0);
			} else if (player.absY == 3394) {
				player.getPlayerAssistant().movePlayer(player.absX, player.absY - 1, 0);
			}
			break;
		case 1600: // Wizards Guild
		case 1601:
			if (player.absX == 2597) {
				player.getPlayerAssistant().movePlayer(player.absX - 1, player.absY, 0);
			} else if (player.absX == 2596) {
				player.getPlayerAssistant().movePlayer(player.absX + 1, player.absY, 0);
			}
			break;
		case 2624: // Heroes Guild
		case 2625:
			if (player.absX == 2902) {
				player.getPlayerAssistant().movePlayer(player.absX - 1, player.absY, 0);
			} else if (player.absX == 2901) {
				player.getPlayerAssistant().movePlayer(player.absX + 1, player.absY, 0);
			}
			break;
		case 2392: // Legends Guild
		case 2391:
			if (player.absY == 3349) {
				player.getPlayerAssistant().movePlayer(player.absX, player.absY + 1, 0);
			} else if (player.absY == 3350) {
				player.getPlayerAssistant().movePlayer(player.absX, player.absY - 1, 0);
			}
			break;
		default:
			player.getPacketSender().sendMessage(
					"You can't access this guild from here.");
			break;
		}
	}

	public static boolean checkRequirements(Player player, int objectId) {
		switch (objectId) {
		case 1805: // Champions Guild
			int requiredQP = Math.min(32, QuestAssistant.MAXIMUM_QUESTPOINTS);
			if (player.questPoints < requiredQP) {
				player.getDialogueHandler().sendStatement("You need " + requiredQP + " quest points to enter this guild!");
				player.nextChat = 0;
				return false;
			}
			break;
		case 2392:
		case 2391:
			if (!hasCompletedLegends && player.getPlayerRights() < 3) {
				player.getDialogueHandler().sendStatement("You need to complete Legends Quest to enter this guild!");
				player.nextChat = 0;
				return false;
			}
			break;
		case 2641: // Prayer Guild
			if (player.getPlayerAssistant().getLevelForXP(player.playerXP[5]) < 31) {
				player.getDialogueHandler().sendStatement("You need 31 prayer to enter this guild!");
				player.nextChat = 0;
				return false;
			}
			break;
		case 2712: // Cooking Guild
			if (player.playerLevel[SkillData.COOKING.getId()] < 32 || player.playerEquipment[player.playerHat] != 1949) {
				player.getDialogueHandler().sendStatement("You need 32 cooking and a chefs hat to enter this guild!");
				player.nextChat = 0;
				return false;
			}
			break;
		case 2647: // Crafting Guild
			if (player.playerLevel[SkillData.CRAFTING.getId()] < 40 || player.playerEquipment[player.playerChest] != 1757) {
				player.getDialogueHandler().sendStatement("You need 40 Crafting and a Brown Apron to enter this guild!");
				player.nextChat = 0;
				return false;
			}
			break;
		case 2113: // Mining Guild
			if (player.playerLevel[SkillData.MINING.getId()] < 60) {
				player.getDialogueHandler().sendStatement("You need 60 Mining to enter this guild!");
				player.nextChat = 0;
				return false;
			}
			break;
		case 2025: // Fishing Guild
			if (player.playerLevel[SkillData.FISHING.getId()] < 68) {
				player.getDialogueHandler().sendStatement("You need 68 Fishing to enter this guild!");
				player.nextChat = 0;
				return false;
			}
			break;
		case 1600:
		case 1601: // Wizards Guild
			if (player.playerLevel[SkillData.MAGIC.getId()] < 66) {
				player.getDialogueHandler().sendStatement("You need 66 Magic to enter this guild!");
				player.nextChat = 0;
				return false;
			}
			break;
		case 2514:
			if (player.playerLevel[SkillData.RANGED.getId()] < 40) { // Ranging Guild
				player.getDialogueHandler().sendStatement("You need 40 Range to enter this guild!");
				player.nextChat = 0;
				return false;
			}
			break;
		}
		return true;
	}
}
