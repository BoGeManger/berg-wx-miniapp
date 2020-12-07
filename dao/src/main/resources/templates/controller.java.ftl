package ${cfg.packageController};

import com.berg.base.BaseController;
import com.berg.dao.page.PageInfo;
import com.berg.message.Result;
import ${cfg.packageService}.${cfg.serviceModule}.${cfg.model}Service;
import com.berg.vo.common.EntityIdVo;
import ${cfg.packageVo}.${cfg.voModule}.${cfg.model}EditVo;
import ${cfg.packageVo}.${cfg.voModule}.${cfg.model}Vo;
import ${cfg.packageVo}.${cfg.voModule}.in.Get${cfg.model}PageInVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${cfg.modelName}")
@Api(tags = "${cfg.comment!}")
public class ${cfg.model}Controller extends BaseController {

    @Autowired
    ${cfg.model}Service ${cfg.modelName}Service;

    @ApiOperation("获取${cfg.comment!}分页列表")
    @GetMapping(value = "get${cfg.model}Page")
    public Result<PageInfo<${cfg.model}Vo>> get${cfg.model}Page(@Validated Get${cfg.model}PageInVo input) {
        return getSuccessResult("请求成功", ${cfg.modelName}Service.get${cfg.model}Page(input));
    }

    @ApiOperation("获取${cfg.comment!}")
    @GetMapping(value = "get${cfg.model}")
    public Result<${cfg.model}EditVo> get${cfg.model}(@ApiParam(value = "表id", required = true) @RequestParam Integer id) {
        return getSuccessResult("请求成功", ${cfg.modelName}Service.get${cfg.model}(id));
    }

    @ApiOperation("新增${cfg.comment!}")
    @PostMapping(value = "add${cfg.model}")
    public Result<Integer> add${cfg.model}(@RequestBody @Validated ${cfg.model}EditVo input) {
        return getSuccessResult("请求成功", ${cfg.modelName}Service.add${cfg.model}(input));
    }

    @ApiOperation("修改${cfg.comment!}")
    @PutMapping(value = "update${cfg.model}")
    public Result<Integer> update${cfg.model}(@RequestBody @Validated ${cfg.model}EditVo input) {
        return getSuccessResult("请求成功", ${cfg.modelName}Service.update${cfg.model}(input));
    }

    @ApiOperation("删除${cfg.comment!}")
    @DeleteMapping(value = "del${cfg.model}")
    public Result<Boolean> del${cfg.model}(@RequestBody EntityIdVo<Integer> input) {
        ${cfg.modelName}Service.del${cfg.model}(input.getId());
        return getSuccessResult("请求成功", true);
    }
}
