package org.springside.fi.service.running;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.fi.entity.Runner;
import org.springside.fi.web.exception.RestExceptionCode;
import org.springside.fi.web.vo.attention.AttentionVo;

/**
 * @author tunding:wzc@tcl.com
 * @description 关注相关接口服务
 * @version 1.0
 * @date 创建时间：2015年8月5日 下午6:54:55
 */
@Service
public class AttentionService {
	@Autowired
	private RunnerService runnerService;
	@Autowired
	private RelationshipService relationshipService;
	 // 不发送消息，只在本地数据库关注对方,存储表明当前用户关注了哪个用户
	public void attentionRelationship(long id, String passiveAttentionUuid, AttentionVo attentionVo){
		String attentionUuid = getUuid(id);
		if(relationshipService.isAttention(id, passiveAttentionUuid)){
			attentionVo.setResult(RestExceptionCode.REST_ALREADY_ATTENTION_CODE);
			attentionVo.setData(RestExceptionCode.REST_ALREADY_ATTENTION_MSG);
		}else{
			relationshipService.attention(attentionUuid, passiveAttentionUuid);
			attentionVo.setResult(RestExceptionCode.REST_ATTENTION_SUCCESS_CODE);
			attentionVo.setData(RestExceptionCode.REST_ATTENTION_SUCCESS_MSG);
		}
	}
	//通过id获取用户的uuid
	private String getUuid(long id){
		Runner runner = runnerService.getRunner(id);
		return runner.getUuid();
	}
}