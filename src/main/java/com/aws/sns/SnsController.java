package com.aws.sns;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;

@RestController
public class SnsController {

	@Autowired
	private AmazonSNSClient amazonSnsClient;
	
	
	private static final String TOPIC_ARN="arn:aws:sns:us-east-1:116116812277:my-sns-topic";
	
	@GetMapping("/subscribeToSnsTopic/{email}")
	public String subscribeToSnsTopic(@PathVariable("email")String email) {
		SubscribeRequest subscribeRequest =
                new SubscribeRequest(TOPIC_ARN,"email",email);
		amazonSnsClient.subscribe(subscribeRequest);
		return "Check your Email";
	}
	
	@GetMapping("/publishMsgToTopic/{msg}")
	public String publishMsgtoTopic(@PathVariable("msg") String msg) {
		PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, msg,"Testing-SNS Springboot");
		amazonSnsClient.publish(publishRequest);
		return "Message Published Successfully";
	}
	
	@GetMapping("/subscribeToSnsTopicViaSQSQueue/{sqsArn}")
	public String subscribeToSnsTopicViaSQSQueue(@PathVariable("sqsArn")String sqsArn) {
		SubscribeRequest subscribeRequest =
                new SubscribeRequest(TOPIC_ARN,"sqs",sqsArn);
		amazonSnsClient.subscribe(subscribeRequest);
		return "Check your Email";
	}
	
	@GetMapping("/subscribeToSnsTopicViaSMS/{phoneNumber}")
	public String subscribeToSnsTopicViaSMS(@PathVariable("phoneNumber")String phoneNumber) {
		SubscribeRequest subscribeRequest =
                new SubscribeRequest(TOPIC_ARN,"sms",phoneNumber);
		amazonSnsClient.subscribe(subscribeRequest);
		return "PhoneNumber Subscription Done Successfully.";
	}
	
	@GetMapping("/publishMsgToMobileDevice/{msg}")
	public String publishMsgToMobileDevice(@PathVariable("msg") String msg) {
		Map<String, MessageAttributeValue> setAttribute = new HashMap<>();
		setAttribute.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue().withStringValue("mySenderID").withDataType("String"));
		setAttribute.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue().withStringValue("0.50").withDataType("Number"));
		setAttribute.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue().withStringValue("Transactional").withDataType("String"));
		
		
		PublishRequest publishRequest = new PublishRequest();
		publishRequest.setMessage(msg);
		//publishRequest.setPhoneNumber(phoneNumber);
		publishRequest.setTopicArn(TOPIC_ARN);
		publishRequest.setMessageAttributes(setAttribute);
		amazonSnsClient.publish(publishRequest);
		
		return "Message Published Successfully to the Respective Mobile Device ";
		
	}
}
