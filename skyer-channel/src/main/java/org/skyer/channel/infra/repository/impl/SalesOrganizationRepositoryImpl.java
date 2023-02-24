package org.skyer.channel.infra.repository.impl;

import org.springframework.stereotype.Component;

import org.skyer.channel.domain.entity.SalesOrganization;
import org.skyer.channel.domain.repository.SalesOrganizationReposotory;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;

/**
 * @author: lzh
 * @date: 2021-06-18
 * @description:
 */
@Component
public class SalesOrganizationRepositoryImpl extends BaseRepositoryImpl<SalesOrganization> implements SalesOrganizationReposotory {
}
