package cn.com.shipment.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS("200", "成功");
    private String code;
    private String message;
}
