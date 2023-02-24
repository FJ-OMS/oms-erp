package org.skyer.channel.domain.service.impl;

import org.skyer.channel.domain.entity.Group;
import org.skyer.channel.domain.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.skyer.channel.domain.service.GroupService;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 应用服务默认实现
 *
 * @author linzhuanghuang@ebc.com 2021-09-03 16:54:36
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group getDefaultGroups() {
        List<Group> groupList = groupRepository.selectAll();
        if (ObjectUtils.isEmpty(groupList)){
            return new Group();
        }else {
            return groupList.get(0);
        }
    }

    @Override
    public int updateDefaultGroups(Group group) {
        List<Group> groupList = groupRepository.selectAll();
        if (ObjectUtils.isEmpty(groupList)){
            return groupRepository.insert(group);
        }else {
            group.setId(groupList.get(0).getId());
            group.setObjectVersionNumber(groupList.get(0).getObjectVersionNumber());
            return groupRepository.updateByPrimaryKeySelective(group);
        }
    }
}
