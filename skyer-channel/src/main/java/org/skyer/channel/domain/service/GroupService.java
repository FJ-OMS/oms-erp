package org.skyer.channel.domain.service;

import org.skyer.channel.domain.entity.Group;

/**
 * 应用服务
 *
 * @author linzhuanghuang@ebc.com 2021-09-03 16:54:36
 */
public interface GroupService {

    /**
     * 获取默认集团
     * @return
     */
    Group getDefaultGroups();

    /**
     * 修改默认的集团信息
     * @param group
     * @return
     */
    int updateDefaultGroups(Group group);
}
