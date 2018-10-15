package com.lhiot.resource.api;

import com.google.zxing.WriterException;
import com.lhiot.resource.util.QRCodeFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 二维码生成接口
 * @author Leon (234239150@qq.com) created in 15:59 18.10.11
 */
@Slf4j
@RestController
@Api(description = "二维码生成接口")
@RequestMapping("/qr-code")
public class QrCodeApi {

    @GetMapping("/")
    @ApiOperation(value = "二维码生成 - 好看的二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "info", value = "二维码信息", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "logo", value = "二维码中间logo 可以为空", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "尺寸大小(PX)", required = true, dataType = "int")
    })
    public void createSeniorQrImg(@RequestParam String info, @RequestParam(required = false) String logo, @RequestParam int size, @ApiIgnore HttpServletResponse response) {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            new QRCodeFactory().create(info, "jpg", response.getOutputStream(), logo, size, size);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
    }
}
