package ${package.DaoImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Dao}.${table.daoName};
import ${superDaoImplClassPackage};
import org.springframework.stereotype.Repository;
import com.berg.dao.constant.DataSource;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@DS(${table.ds})
@Repository("${table.parentModuleName}.${table.daoImplName}")
public class ${table.daoImplName} extends ${superDaoImplClass}<${table.mapperName}, ${entity}> implements ${table.daoName} {

    @Override
    public ${table.mapperName} getMapper(){
      DynamicDataSourceContextHolder.push(${table.ds});
      return this.getBaseMapper();
    }
}
