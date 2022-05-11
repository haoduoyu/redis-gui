package com.rain.constant;

import cn.hutool.core.lang.UUID;

/**
 * @author rain.z
 * @description ConnectionMenuItemId
 * @date 2022/05/12
 */
public class ConnectionMenuItemId {
    // 只需要在APP运行期间保证不变即可，下次启动可以与本次不同，但需要保证唯一，可以考虑转换成手动分配
    public final static String CONNECTION_MENU_ITEM = UUID.fastUUID().toString();
    public final static String DELETE_MENU_ITEM = UUID.fastUUID().toString();
}
