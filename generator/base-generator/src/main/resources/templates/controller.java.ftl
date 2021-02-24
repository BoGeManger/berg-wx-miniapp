package ${package.Service};

import ${package.EditVo}.${table.editVoName};
import ${package.Vo}.${table.voName};
import ${package.PageInVo}.${table.pageInVoName};
import com.berg.dao.page.PageInfo;
import com.berg.message.Result;
import com.berg.vo.common.EntityIdVo;
import com.berg.common.controller.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
* <p>
    * ${table.comment!} 控制器
    * </p>
*
* @author ${author}
* @since ${date}
*/
@RestController
@RequestMapping("/${table.entityName}")
@Api(tags = "${table.comment!}")
@Validated
public class ${table.controllerName} extends AbstractController {

    @Autowired
    ${table.serviceName} ${table.serviceNameVariable};

    @ApiOperation("获取${table.comment!}分页列表")
    @GetMapping(value = "get${table.entityName}Page")
    public Result<PageInfo<${table.voName}>> get${table.entityName}Page(${table.pageInVoName} input) {
        return success("请求成功", ()->${table.serviceNameVariable}.get${table.entityName}Page(input));
    }

    @ApiOperation("获取${table.comment!}")
    @GetMapping(value = "get${table.entityName}")
    public Result<${table.editVoName}> get${table.entityName}(@ApiParam(value = "表id", required = true) @RequestParam Integer id) {
        return success("请求成功", ()->${table.serviceNameVariable}.get${table.entityName}(id));
    }

    @ApiOperation("新增${table.comment!}")
    @PostMapping(value = "add${table.entityName}")
    public Result<Integer> add${table.entityName}(@RequestBody ${table.editVoName} input) {
        return success("请求成功", ()->${table.serviceNameVariable}.add${table.entityName}(input));
    }

    @ApiOperation("修改${table.comment!}")
    @PutMapping(value = "update${table.entityName}")
    public Result<Integer> update${table.entityName}(@RequestBody ${table.editVoName} input) {
        return success("请求成功", ()->${table.serviceNameVariable}.update${table.entityName}(input));
    }

    @ApiOperation("删除${table.comment!}")
    @DeleteMapping(value = "del${table.entityName}")
    public Result<Void> del${table.entityName}(@RequestBody EntityIdVo<Integer> input) {
        return success("请求成功", ()->${table.serviceNameVariable}.del${table.entityName}(input.getId()));
    }
}
