package org.skyer.order.app.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import org.skyer.core.oauth.CustomUserDetails;
import org.skyer.core.oauth.DetailsHelper;
import org.skyer.order.api.dto.ProcessDefineDTO;
import org.skyer.order.app.service.ProcessDefineService;
import org.skyer.order.domain.entity.ProcessDefine;
import org.skyer.order.domain.repository.ProcessDefineRepository;

import org.skyer.order.infra.common.CommonConstants;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 应用服务默认实现
 *
 * @author linzhuanghuang@ebc.com 2022-01-14 17:53:17
 */
@Service
public class ProcessDefineServiceImpl implements ProcessDefineService {
	@Autowired
	private ProcessDefineRepository processDefineRepository;
	@Autowired
	private ZeebeClient client;
	@Override
	public void saveProcessDefine(ProcessDefineDTO processDefine) throws Exception {
		ProcessDefine define = new ProcessDefine();
		BeanUtils.copyProperties(processDefine,define);
		define.setProcessBpmn(processDefine.getXmlString().getBytes(StandardCharsets.UTF_8));
		define.setStatus(CommonConstants.BpmnDeploy.NO);
		if (StringUtils.isNotBlank(processDefine.getProcessDefineId())) {
			define.setProcessFileName(processDefine.getProcessDefineId() + ".bpmn");
		}
		define.setObjectVersionNumber(1L);
		processDefineRepository.insertSelective(define);
	}

	@Override
	public void updateDeploy(Long id) {
		ProcessDefine processDefine = processDefineRepository.selectByPrimaryKey(id);
		DeploymentEvent event = client.newDeployCommand()
				.addResourceBytes(processDefine.getProcessBpmn(),processDefine.getProcessFileName()).send().join(3, TimeUnit.SECONDS);
		processDefine.setProcessId(event.getProcesses().get(0).getBpmnProcessId());
		processDefine.setStatus(CommonConstants.BpmnDeploy.YES);
		processDefineRepository.updateByPrimaryKeySelective(processDefine);
	}


	private String getXmlString() throws Exception{
			// 读取 xml 文件
			File fileinput = new File("D:/test.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fileinput);


			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);

			// 将转换过的xml的String 样式打印到控制台
			System.out.println(writer.toString());
			return writer.toString();
	}

}
