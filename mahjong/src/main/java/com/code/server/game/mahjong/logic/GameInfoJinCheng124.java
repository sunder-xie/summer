package com.code.server.game.mahjong.logic;


import java.util.List;

/**
 * Created by win7 on 2017/2/24.
 */
public class GameInfoJinCheng124 extends GameInfo {

    protected static final String JC124_Ping_HAVEFENG_CANTING = "124051314";//12表示进城124，03表示无风（13表示有风），14表示需要听（04表示不需要听）平胡05  大胡 15
    protected static final String JC124_Ping_HAVEFENG_NOTING = "124051304";
    protected static final String JC124_Ping_NOFENG_CANTING = "124050314";
    protected static final String JC124_Ping_NOFENG_NOTING = "124050304";
    protected static final String JC124_DA_HAVEFENG_CANTING = "124151314";
    protected static final String JC124_DA_HAVEFENG_NOTING = "124151304";
    protected static final String JC124_DA_NOFENG_CANTING = "124150314";
    protected static final String JC124_DA_NOFENG_NOTING = "124150304";
	
    
    /**
     * 初始化方法
     *
     * @param firstTurn
     * @param users
     */
    @Override
    public void init(int gameId, int firstTurn, List<Integer> users, RoomInfo room, RoomDao roomDao, UserRecodeDao userRecodeDao, UserDao userDao, GameDao gameDao) {
        this.gameId = gameId;
        this.userDao = userDao;
        this.gameDao = gameDao;
        this.roomDao = roomDao;
        this.userRecodeDao = userRecodeDao;
        this.firstTurn = firstTurn;
        this.turnId = firstTurn;
        remainCards.addAll(CardTypeUtil.ALL_CARD);
        this.users.addAll(users);
        this.room = room;
        this.cardSize = 13;
        this.playerSize = room.getPersonNumber();
        //不带风
        if ("3".equals(room.getMode()) || "4".equals(room.getMode()) || JC124_Ping_NOFENG_CANTING.equals(room.getMode()) || JC124_Ping_NOFENG_NOTING.equals(room.getMode())|| JC124_DA_NOFENG_CANTING.equals(room.getMode())|| JC124_DA_NOFENG_NOTING.equals(room.getMode())) {
            remainCards.removeAll(CardTypeUtil.FENG_CARD);
            remainCards.removeAll(CardTypeUtil.ZI_CARD);
        }
    }

    protected void handleHu(ServerContext serverContext, PlayerCardsInfo playerCardsInfo) {
        isAlreadyHu = true;
        sendResult(serverContext, true, playerCardsInfo.userId);
        //圈
        if (this.getFirstTurn() != playerCardsInfo.getUserId()) {
            //换庄
            room.addOneToCircleNumber();
            int nextId = nextTurnId(this.getFirstTurn());
            room.setBankerId(nextId);
        }
        noticeDissolutionResult(serverContext);
        room.clearReadyStatus();
    }

    protected boolean isRoomOver() {
        return room.getCurCircle() > room.maxCircle;
    }
}
