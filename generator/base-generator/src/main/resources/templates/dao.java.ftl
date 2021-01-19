package ${package.Dao};

import ${package.Entity}.${entity};
import ${superDaoClassPackage};
import ${package.Mapper}.${table.mapperName};

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${table.daoName} extends ${superDaoClass}<${entity}> {
   ${table.mapperName} getMapper();
}
