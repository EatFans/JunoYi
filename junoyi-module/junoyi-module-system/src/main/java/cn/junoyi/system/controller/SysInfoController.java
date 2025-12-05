package cn.junoyi.system.controller;

import cn.junoyi.framework.core.domain.R;
import cn.junoyi.framework.log.core.JunoLog;
import cn.junoyi.framework.log.core.JunoLogFactory;
import cn.junoyi.framework.stater.config.JunoYiProperties;
import cn.junoyi.system.domain.vo.SystemInfoVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统信息控制类
 *
 * @author Fan
 */
@RestController
@RequestMapping("/system/info")
@RequiredArgsConstructor
public class SysInfoController {

    private final JunoLog log = JunoLogFactory.getLogger(SysInfoController.class);

    private final JunoYiProperties junoYiProperties;

    /**
     * 获取系统信息
     */
    @GetMapping
    public R<SystemInfoVo> getSystemInfo(){
        return R.ok();
    }
}