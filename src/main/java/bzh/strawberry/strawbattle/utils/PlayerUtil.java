package bzh.strawberry.strawbattle.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;


/*
 * This file PlayerUtil is part of a project StrawBattle.StrawBattle.
 * It was created on 28/01/2021 16:25 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class PlayerUtil {

    public static Scoreboard score = Bukkit.getScoreboardManager().getMainScoreboard();

    public static void setTag(Player player, String name, int power, String prefix, String suffix) {
        Team team = score.getTeam(power + name);
        if (team == null)
            team = score.registerNewTeam(power + name);

        if (suffix != null) team.setSuffix(suffix);
        if (prefix != null) team.setPrefix(prefix);
        team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        team.addEntry(player.getName());
        player.setScoreboard(score);
    }

}