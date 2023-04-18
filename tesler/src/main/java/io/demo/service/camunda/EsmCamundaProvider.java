package io.demo.service.camunda;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class EsmCamundaProvider {

	private final ProcessEngine processEngine;

	private final TaskService taskService;

	public ProcessInstance startProcessInstance(@NonNull String processDefinitionKey, String businessKey, Map<String, Object> variables) {
		return processEngine.getRuntimeService().startProcessInstanceByKey(
				processDefinitionKey,
				businessKey,
				variables
		);
	}

	public void completeUserTaskWithVariablesInReturn(@NonNull String businessKey, Map<String, Object> variables) {
		String taskId = taskService.createTaskQuery()
				.processInstanceBusinessKey(businessKey)
				.singleResult()
				.getId();
		taskService.completeWithVariablesInReturn(taskId, variables, true);
	}

}
