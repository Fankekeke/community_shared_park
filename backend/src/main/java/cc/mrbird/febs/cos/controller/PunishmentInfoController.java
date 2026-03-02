package cc.mrbird.febs.cos.controller;


import cc.mrbird.febs.common.utils.R;
import cc.mrbird.febs.cos.entity.PunishmentInfo;
import cc.mrbird.febs.cos.service.IPunishmentInfoService;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Fank gmail - fan1ke2ke@gmail.com
 */
@RestController
@RequestMapping("/cos/punishment-info")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PunishmentInfoController {

    private final IPunishmentInfoService punishmentInfoService;

    /**
     * 分页获取超时设置信息
     *
     * @param page           分页对象
     * @param punishmentInfo 超时设置信息
     * @return 结果
     */
    @GetMapping("/page")
    public R page(Page<PunishmentInfo> page, PunishmentInfo punishmentInfo) {
        return R.ok();
    }

    /**
     * 超时设置信息详情
     *
     * @param id 超时设置ID
     * @return 结果
     */
    @GetMapping("/{id}")
    public R detail(@PathVariable("id") Integer id) {
        return R.ok(punishmentInfoService.getById(id));
    }

    /**
     * 超时设置信息列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R list() {
        return R.ok(punishmentInfoService.list());
    }

    /**
     * 新增超时设置信息
     *
     * @param punishmentInfo 超时设置信息
     * @return 结果
     */
    @PostMapping
    public R save(PunishmentInfo punishmentInfo) {
        return R.ok(punishmentInfoService.save(punishmentInfo));
    }

    /**
     * 修改超时设置信息
     *
     * @param punishmentInfo 超时设置信息
     * @return 结果
     */
    @PutMapping
    public R edit(PunishmentInfo punishmentInfo) {
        punishmentInfo.setId(1);
        return R.ok(punishmentInfoService.updateById(punishmentInfo));
    }

    /**
     * 删除超时设置信息
     *
     * @param ids ids
     * @return 超时设置信息
     */
    @DeleteMapping("/{ids}")
    public R deleteByIds(@PathVariable("ids") List<Integer> ids) {
        return R.ok(punishmentInfoService.removeByIds(ids));
    }
}
