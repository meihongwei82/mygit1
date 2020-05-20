package cn.com.shipment.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.com.shipment.base.BaseRest;
import cn.com.shipment.base.WebResponse;
import cn.com.shipment.service.inter.IHomeService;
import io.swagger.annotations.ApiOperation;



@RestController
@CrossOrigin
@RequestMapping("/home")
public class HomeRest extends BaseRest {

    @Autowired
    IHomeService service;

    @ApiOperation(value="添加总货物量")
	@RequestMapping(value = "addRootQuantity", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public WebResponse addRootQuantity(@RequestParam(value = "rootQuantity") Double rootQuantity) {
		try {
			service.addRootQuantity(rootQuantity);
		} catch (Exception e) {
			return buildFailedResponse("error", e.getMessage());
		}
		return buildSuccessResponse();
	}
    
    @ApiOperation(value="查询所有批次信息")
	@RequestMapping(value = "queryAllNumberInfo", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public WebResponse queryAllNumberInfo() {
    	Map<String, Object> resMap = new HashMap<>();
		try {
			Map<String, Double> map = service.queryAllNumberInfo();
			resMap.put("data", map);
		} catch (Exception e) {
			return buildFailedResponse("error", e.getMessage());
		}
		return buildSuccessResponse(resMap);
	}
    
    /**
     * 拆分
     * @param oldKey 拆分前的批次号
     * @param newValues 拆分的货物量，多个货物量用逗号分割
     * @return
     */
    @ApiOperation(value="批次分解")
	@RequestMapping(value = "splitNumber", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public WebResponse splitNumber(@RequestParam(value = "oldKey") String oldKey,@RequestParam(value = "newValues") String newValues) {
		try {
			boolean bl = service.splitNumber(oldKey, newValues);
			if(!bl) {
				return buildFailedResponse("error", "操作失败，失败原因：旧批次已经不存在或拆分的总货物量不等于拆分前的货物量");
			}
		} catch (Exception e) {
			return buildFailedResponse("error", e.getMessage());
		}
		return buildSuccessResponse();
	}
    
    /**
     * 合并批次
     * @param oldKeys 要合并的批次，多个批次用逗号分割
     * @return
     */
    @ApiOperation(value="合并批次")
	@RequestMapping(value = "mergeNumber", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public WebResponse mergeNumber(@RequestParam(value = "oldKeys") String oldKeys) {
		try {
			boolean bl = service.mergeNumber(oldKeys);
			if(!bl) {
				return buildFailedResponse("error", "操作失败，失败原因：旧批次已经不存在");
			}
		} catch (Exception e) {
			return buildFailedResponse("error", e.getMessage());
		}
		return buildSuccessResponse();
	}
    
    @ApiOperation(value="修改总货物量")
	@RequestMapping(value = "changeRootQuantity", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public WebResponse changeRootQuantity(@RequestParam(value = "rootQuantity") Double rootQuantity) {
		try {
			service.changeRootQuantity(rootQuantity);
		} catch (Exception e) {
			return buildFailedResponse("error", e.getMessage());
		}
		return buildSuccessResponse();
	}
}


