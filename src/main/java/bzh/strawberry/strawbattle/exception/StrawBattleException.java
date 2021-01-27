package bzh.strawberry.strawbattle.exception;

import bzh.strawberry.strawbattle.StrawBattle;

/*
 * This file StrawBattleException is part of a project StrawBattle.StrawBattle.
 * It was created on 27/01/2021 16:32 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 *  Also this comment shouldn't get remove from the file. (see Licence)
 */
public class StrawBattleException extends RuntimeException {

    public StrawBattleException(String message) {
        super(message);
        StrawBattle.STRAW_BATTLE.getServer().shutdown();
    }
}
