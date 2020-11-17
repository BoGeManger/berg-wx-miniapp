package com.berg.mq;

import com.alibaba.fastjson.JSON;
import com.berg.constant.AppConstants;
import com.berg.utils.JsonHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class MqSender implements ReturnCallback, ConfirmCallback, InitializingBean {

    @Autowired
    AppConstants appConstans;
    @Autowired
    RabbitTemplate rabbitTemplate;
    //@Autowired
    //RedisTemplate<String, MqReturnedMessage> redisTemplate;

    static final String ENCODING = Charset.defaultCharset().name();
    static final SerializerMessageConverter SERIALIZER_MESSAGE_CONVERTER = new SerializerMessageConverter();

    //region 基础方法
    @Override
    public void afterPropertiesSet() {
        rabbitTemplate.setConfirmCallback(this::confirm);
        rabbitTemplate.setReturnCallback(this::returnedMessage);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("MQ消息确认: " + correlationData + "确认" + (ack ? "成功!" : "失败!"));
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        log.error("MQ消息发送失败退回   message：" + message.toString() + ",reply code：" + i + ",reply text：" + s + ",the exchange：" + s1 + ",routing key:" + s2);
        String messageStr = getBodyContentAsString(message);
        if (StringUtils.isNotBlank(messageStr)) {
            MqReturnedMessage mqReturnedMessage = new MqReturnedMessage(messageStr, i, s, s1, s2);
            //redisTemplate.opsForList().rightPush(RedisKeyConstants.Mq.FAILED_RETURNED_MESSAGE, mqReturnedMessage);
        } else {
            log.error("MQ获取消息body失败：" + message.toString());
        }
    }

    private String getBodyContentAsString(Message message) {
        if (message.getBody() == null) {
            return null;
        }
        try {
            String contentType = (message.getMessageProperties() != null) ? message.getMessageProperties().getContentType() : null;
            if (MessageProperties.CONTENT_TYPE_SERIALIZED_OBJECT.equals(contentType)) {
                return SERIALIZER_MESSAGE_CONVERTER.fromMessage(message).toString();
            }
            if (MessageProperties.CONTENT_TYPE_TEXT_PLAIN.equals(contentType)
                    || MessageProperties.CONTENT_TYPE_JSON.equals(contentType)
                    || MessageProperties.CONTENT_TYPE_JSON_ALT.equals(contentType)
                    || MessageProperties.CONTENT_TYPE_XML.equals(contentType)) {
                return new String(message.getBody(), ENCODING);
            }
        } catch (Exception e) {
            log.error("MQ message body 解析失败！", e);
        }
        return null;
    }
    //endregion

    //region 基础类
    @Data
    public class MqReturnedMessage implements Serializable {
        static final long serialVersionUID = 1L;
        String message;
        int replyCode;
        String replyText;
        String exchange;
        String routingKey;
        public MqReturnedMessage(String message,int replyCode,String replyTex,String exchange,String routingKey){
            this.message=message;
            this.replyCode=replyCode;
            this.replyText=replyTex;
            this.exchange=exchange;
            this.routingKey=routingKey;
        }
    }
    //endregion


    /**
     * 公共发送方法
     * @param exchange
     * @param queue
     * @param data
     * @throws Exception
     */
    public void send(String exchange,String queue,Object data) throws  Exception{
        log.info("queue :"+queue);
        String json =  JSON.toJSONString(data);
        this.rabbitTemplate.convertAndSend(exchange, queue, json,new CorrelationData(json));
    }

    /**
     * 公共发送方法(多业务共用MQ，类型区分)
     * @param exchange
     * @param queue
     * @param type
     * @param data
     * @throws Exception
     */
    public void send(String exchange,String queue,String type,Object data) throws  Exception{
        log.info("queue :"+queue);
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("type", type);
        context.put("data", JSON.toJSONString(data));
        String json =  JsonHelper.toJson(context,null);
        log.info("send :"+type);
        this.rabbitTemplate.convertAndSend(exchange, queue, json,new CorrelationData(json));
    }

}