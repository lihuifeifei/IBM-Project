package com.example.demo.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.common.Info;
import com.example.demo.dao.QAcommonmapper;
import com.example.demo.dao.QAlogmapper;
import com.example.demo.po.QAcommonEntity;
import com.example.demo.po.Status;
import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.assistant.v1.model.Context;
import com.ibm.watson.developer_cloud.assistant.v1.model.DialogNode;
import com.ibm.watson.developer_cloud.assistant.v1.model.ExampleCollection;
import com.ibm.watson.developer_cloud.assistant.v1.model.GetDialogNodeOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.InputData;
import com.ibm.watson.developer_cloud.assistant.v1.model.ListExamplesOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.service.security.IamOptions;

@Controller
public class TestController {
	
	@Autowired
	private QAcommonmapper qacommonmapper;
	
	@Autowired
	private QAlogmapper qalogmapper;
	
	private Assistant service= null;
	private String workspaceId = Info.workspaceId;
	private IamOptions iamOptions = null;
	
	private Context context = null;
	
	

	
	
	
	//获取intent的例子
	@RequestMapping("/talk")
	@ResponseBody
	public ExampleCollection talk(@RequestParam("intent")String intent) {
		String usernameString= Info.username;
		String passwordString= Info.password;
		Assistant service = new Assistant("2018-08-30");
		service.setUsernameAndPassword(usernameString, passwordString);
		String workspaceId= Info.workspaceId;
		ListExamplesOptions options = new ListExamplesOptions.Builder(workspaceId, intent).build();
		ExampleCollection response = service.listExamples(options).execute();
		return response;
	}
	
	//实现一个会话
	@RequestMapping("/getdialog")
	@ResponseBody
	public MessageResponse getDialog(@RequestParam("dialogNode")String dialogNode) {
		Assistant service = new Assistant("2018-08-30");
		service.setUsernameAndPassword(Info.username, Info.password);
		String workspaceId = Info.workspaceId;
		InputData input = new InputData.Builder(dialogNode).build();
		MessageOptions options = new MessageOptions.Builder(workspaceId)
		  .input(input)
		  .build();
		MessageResponse response = service.message(options).execute();
		String tt = response.getOutput().getText().get(0);
		if(tt != null) {
			System.out.println(tt);
		}
		return response;
	}
	
	//创建一个会话节点
	@RequestMapping("/createdialog")
	@ResponseBody
	public void createDialog() {
/*		String workId=Util.lastspaceId(type);
		Assistant service=Util.CommonUtil();
		String dialogNode = req;
		String conditions = "#"+req;
		String title = req;		
		try {//更新对话和意图
			//1、创建对话节点
			GetDialogNodeOptions o = new GetDialogNodeOptions.Builder(workId, dialogNode).build();
			DialogNode r = service.getDialogNode(o).execute();
			Map<String, ArrayList<String>> newOutput = new HashMap<String, ArrayList<String>>();
			ArrayList<String>al1=new ArrayList<String>();
			al1.add(r.getOutput().get("text").toString());
			al1.add(respon);			
			newOutput.put("text",al1);
			
			//2、获取对话节点
			UpdateDialogNodeOptions options = new UpdateDialogNodeOptions.Builder(workId, dialogNode)
			  .newOutput(newOutput)
			  .build();
			DialogNode response = service.updateDialogNode(options).execute();
		}*/
		//更新会话节点
//		Assistant service = new Assistant("2018-07-10");
//		service.setUsernameAndPassword("{username}", "{password}");
//		String workspaceId = "9978a49e-ea89-4493-b33d-82298d3db20d";
//		String dialogNode = "greeting";
//		Map<String, String> newOutput = new HashMap<String, String>();
//		newOutput.put("text","Hello! What can I do for you?");
//		UpdateDialogNodeOptions options = new UpdateDialogNodeOptions.Builder(workspaceId, dialogNode)
//		  .newOutput(newOutput)
//		  .build();
//		DialogNode response = service.updateDialogNode(options).execute();
//
//		System.out.println(response);
	}
	//获取所有的常见问题
	@RequestMapping("/getcommonQA")
	@ResponseBody
	public List<QAcommonEntity> selectall(){
		List<QAcommonEntity> list = qacommonmapper.selectall();
		return list;
	}
	
	//clear
	@RequestMapping("/clearDialog")
	@ResponseBody
	public Status clearDialog() {
		Status status = new Status();
		System.out.println("this is clear");
		
		context=null;
		status.setIsSucces("success");
		return status;
	}
	
	//对提问进行回答，并记录到数据库
	@RequestMapping("/ansandlog")
	@ResponseBody
	public MessageResponse ansAndLog(@RequestParam("dialogNode")String dialogNode) {
		if(service==null) {
//			service = new Assistant("2018-08-30");
//			service.setUsernameAndPassword(Info.username, Info.password);
			iamOptions = new IamOptions.Builder()
				    .apiKey(Info.apikey)
				    .build();
			service = new Assistant("2018-08-30", iamOptions);
			service.setEndPoint(Info.Url);
		}
		System.out.println("数据");
		System.out.println(dialogNode);

		InputData input = new InputData.Builder(dialogNode).build();
		MessageOptions options = new MessageOptions.Builder(workspaceId)
		  .input(input).context(context)
		  .build();
		MessageResponse response = service.message(options).execute();
		context = response.getContext();
	//	String ans = response.getOutput().getText().get(0);
//		if(ans != null) {
//			qalogmapper.insertItem(dialogNode, ans);
//		}
		return response;
	}
	
	@RequestMapping("/getDialogNode")
	@ResponseBody
	public void getDialogNode(@Param("dialogNode")String dialogNode) {
	
		IamOptions iamOptions = new IamOptions.Builder().apiKey("{apiKey}").build();
		Assistant service = new Assistant("2018-09-20", iamOptions);

		String workspaceId = Info.workspaceId;

		GetDialogNodeOptions options = new GetDialogNodeOptions.Builder(workspaceId, dialogNode).build();

		DialogNode response = service.getDialogNode(options).execute();

		System.out.println(response);
	}

}
