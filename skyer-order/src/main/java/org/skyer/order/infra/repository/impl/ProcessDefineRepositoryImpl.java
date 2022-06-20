package org.skyer.order.infra.repository.impl;

import org.skyer.core.domain.Page;
import org.skyer.core.exception.CommonException;
import org.skyer.mybatis.base.impl.BaseRepositoryImpl;
import org.skyer.mybatis.common.BaseMapper;
import org.skyer.mybatis.pagehelper.PageHelper;
import org.skyer.mybatis.pagehelper.domain.PageRequest;
import org.skyer.order.api.dto.ProcessDefineDTO;
import org.skyer.order.api.dto.ProcessDefinePageDTO;
import org.skyer.order.api.vo.ProcessDefineDetailsVO;
import org.skyer.order.api.vo.ProcessDefinePageVO;
import org.skyer.order.domain.entity.ProcessDefine;
import org.skyer.order.domain.repository.ProcessDefineRepository;
import org.skyer.order.infra.common.CommonConstants;
import org.skyer.order.infra.mapper.ProcessDefineMapper;

import io.swagger.annotations.ApiModel;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.Object;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import sun.misc.BASE64Encoder;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 *  资源库实现
 *
 * @author linzhuanghuang@ebc.com 2022-01-14 17:53:17
 */
@Component
public class ProcessDefineRepositoryImpl extends BaseRepositoryImpl<ProcessDefine> implements ProcessDefineRepository {

	@Autowired
	private ProcessDefineMapper processDefineMapper;

	@Override
	public ProcessDefineDetailsVO queryProcessDetails(Long id) {
		ProcessDefine processDefine = this.selectByPrimaryKey(id);
		if (ObjectUtils.isEmpty(processDefine)){
			return null;
		}
		ProcessDefineDetailsVO processDefineDetailsVO = new ProcessDefineDetailsVO();
		BeanUtils.copyProperties(processDefine,processDefineDetailsVO);
		byte[] processBpmn = processDefine.getProcessBpmn();
		processDefineDetailsVO.setXmlString(new String(processBpmn));
		return processDefineDetailsVO;
	}

	@Override
	public void deleteProcess(ProcessDefine processDefine) {
		ProcessDefine define = this.selectByPrimaryKey(processDefine.getId());
		if (ObjectUtils.isEmpty(define)){
			throw new CommonException("该流程定义不存在");
		}
		if (CommonConstants.BpmnDeploy.YES.equals(define.getStatus())){
			throw new CommonException("已部署流程无法删除");
		}
		this.deleteByPrimaryKey(processDefine.getId());
	}

	@Override
	public void updateProcessDefine(ProcessDefineDTO processDefine) {
		ProcessDefine define = this.selectByPrimaryKey(processDefine.getId());

		Long versionNumber = define.getObjectVersionNumber();
		BeanUtils.copyProperties(processDefine,define);
		define.setProcessBpmn(processDefine.getXmlString().getBytes(StandardCharsets.UTF_8));
		if (StringUtils.isNotBlank(processDefine.getProcessDefineId())) {
			define.setProcessFileName(processDefine.getProcessDefineId() + ".bpmn");
		}
		// 修改后状态改为未部署
		define.setStatus(CommonConstants.BpmnDeploy.NO);
		define.setObjectVersionNumber(versionNumber+1);
		define.setStatus(CommonConstants.BpmnDeploy.NO);
		this.updateByPrimaryKeySelective(define);
	}

	@Override
	public Page<ProcessDefinePageVO> processPage(PageRequest pageRequest, ProcessDefinePageDTO processDefine) {
		return PageHelper.doPageAndSort(pageRequest, () -> processDefineMapper.getListPage(processDefine));
	}
}
