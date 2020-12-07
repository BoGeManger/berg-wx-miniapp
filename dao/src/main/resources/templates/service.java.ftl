package ${cfg.packageService}.${cfg.serviceModule};

import com.berg.dao.page.PageInfo;
import ${cfg.packageVo}.${cfg.voModule}.${cfg.model}EditVo;
import ${cfg.packageVo}.${cfg.voModule}.${cfg.model}Vo;
import ${cfg.packageVo}.${cfg.voModule}.in.Get${cfg.model}PageInVo;

public interface ${cfg.model}Service {

    PageInfo<${cfg.model}Vo> get${cfg.model}Page(Get${cfg.model}PageInVo input);

    ${cfg.model}EditVo get${cfg.model}(Integer id);

    Integer add${cfg.model}(${cfg.model}EditVo input);

    Integer update${cfg.model}(${cfg.model}EditVo input);

    void del${cfg.model}(Integer id);
}
