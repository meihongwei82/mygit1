package cn.com.shipment.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Properties;

/**
 * rest公共返回定义
 *
 * @author qinzhaojun
 */
@ApiModel(value="WebResponse返回对象",description="WebResponse返回对象")
public class WebResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 响应状态码
     */
    @ApiModelProperty(value="响应状态码",name="statusCode",example="200")
    private String statusCode;
    /**
     * 对result的状态文字描述
     */
    @ApiModelProperty(value="对result的状态文字描述",name="message",example="成功！")
    private String message;
    /**
     * 实际返回对象
     */
    @ApiModelProperty(value="返回对象",name="result")
    private T result;
    /**
     * 版本
     */
    @ApiModelProperty(value="版本号",name="version",example="3.0.0")
    private String version;

    public WebResponse() {
    }
    public WebResponse(String message, String apiName, T data) {
        this.message = message;
        this.result = data;
    }
    public WebResponse(String message, T data) {
        this.message = message;
        this.result = data;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    public String getVersion() {
        Properties pro;
        String version = "";
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public Object getResult() {
        return result;
    }
    public void setResult(T result) {
        this.result = result;
    }
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private WebResponse target;
        public WebResponse build() {
            return target;
        }
        public Builder() {
            target = new WebResponse();
        }
        public Builder statusCode(String statusCode) {
            target.setStatusCode(statusCode);
            return this;
        }
        public Builder message(String message) {
            target.setMessage(message);
            return this;
        }
        public <T> Builder result(T result) {
            target.result = result;
            return this;
        }
        public Builder version(String version) {
            target.version = version;
            return this;
        }
    }
}
