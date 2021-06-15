package com.ruoyi.wx.controller;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.file.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * 文件上传
 *
 * @author wyt
 */
@Slf4j
@RestController
@RequestMapping("/wx/{appId}/file")
public class FileController {

    /**
     * 上传文件
     */
    @PreAuthorize("hasAuthority('consumer') or hasAuthority('nurse')")
    @PostMapping("/upload")
    public AjaxResult add(@RequestParam(value = "file", required = false) MultipartFile file, @Validated @NotNull String uploadDir) throws IOException {
        if (!file.isEmpty()) {
            String path = FileUploadUtils.upload(RuoYiConfig.getProfile() + uploadDir, file);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("path", path);
            return ajax;
        }
        return AjaxResult.error("上传文件异常，请联系管理员");
    }

}
