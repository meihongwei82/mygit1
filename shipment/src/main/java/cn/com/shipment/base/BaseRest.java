package cn.com.shipment.base;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="system.run")
public class BaseRest {
	private String debuggerType;//是否是debugger模式
	private String errInfo;//输出信息
	/**
     * 成功响应结果（无返回数据）
     *
     * @return
     */
    public WebResponse buildSuccessResponse() {
        WebResponse webResponse = new WebResponse();
        webResponse.setMessage(ResponseCode.SUCCESS.getMessage());
        webResponse.setStatusCode(ResponseCode.SUCCESS.getCode());
        return webResponse;
    }

	/**
	 * 成功响应结果（返回数据）
	 * @param data
	 * @return
	 */
    public <T> WebResponse buildSuccessResponse(T data) {
        WebResponse<T> webResponse = new WebResponse<T>();
        webResponse.setResult(data);
        webResponse.setStatusCode(ResponseCode.SUCCESS.getCode());
        webResponse.setMessage(ResponseCode.SUCCESS.getMessage());
        return webResponse;
    }
    /**
     * 错误响应结果
     * @param message
     * @return
     */
    public WebResponse buildFailedResponse(String statusCode, String message) {
        WebResponse webResponse = new WebResponse();
        webResponse.setMessage(message);
        webResponse.setStatusCode(statusCode);
        return webResponse;
    }
	public String getDebuggerType() {
		return debuggerType;
	}
	public void setDebuggerType(String debuggerType) {
		this.debuggerType = debuggerType;
	}
	public String getErrInfo() {
		return errInfo;
	}
	public void setErrInfo(String errInfo) {
		this.errInfo = errInfo;
	}
}
