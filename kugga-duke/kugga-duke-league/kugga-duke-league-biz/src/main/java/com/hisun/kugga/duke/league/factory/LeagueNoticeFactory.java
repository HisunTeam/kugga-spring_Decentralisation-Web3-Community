package com.hisun.kugga.duke.league.factory;

import com.hisun.kugga.duke.league.handler.notice.AbstractLeagueNoticeHandler;
import com.hisun.kugga.framework.common.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;

import static com.hisun.kugga.duke.league.constants.TaskConstants.TASK_PARAM_ERROR;

/**
 * @Author: ZhouAnYan
 * @Email: yan_zay@163.com
 * @Date: 2022-07-25 10:22
 */
public class LeagueNoticeFactory {
    private static Map<Integer, AbstractLeagueNoticeHandler> map = new HashMap<>();

    public static AbstractLeagueNoticeHandler build(int type) {
        AbstractLeagueNoticeHandler handler = map.get(type);
        if (handler == null) {
            ServiceException.throwServiceException(TASK_PARAM_ERROR);
        }
        return handler;
    }

    public static void register(int type, AbstractLeagueNoticeHandler obj) {
        map.put(type, obj);
    }
}
