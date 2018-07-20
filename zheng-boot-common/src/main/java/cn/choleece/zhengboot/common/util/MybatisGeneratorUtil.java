package cn.choleece.zhengboot.common.util;

import org.apache.commons.lang.ObjectUtils;
import org.apache.velocity.VelocityContext;

import javax.security.auth.login.Configuration;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author choleece
 * @description: 代码生成类
 * @date 2018/7/20 12:07
 */
public class MybatisGeneratorUtil {
    /**
     * generatorConfig模板路径
     */
    private static String generatorConfig_vm = "/template/generatorConfig.vm";

    /**
     * Service 模板路径
     */
    private static String service_vm = "/template/Service.vm";

    /**
     * ServiceMock 模板路径
     */
    private static String serviceMock_vm = "/template/ServiceMock.vm";

    /**
     * ServiceImpl模板路径
     */
    private static String serviceImpl_vm = "/template/ServiceImpl.vm";

    public static void generator(
            String jdbcDriver,
            String jdbcUrl,
            String jdbcUsername,
            String jdbcPassword,
            String module,
            String database,
            String tablePrefix,
            String packageName,
            Map<String, String> lastInsertIdTables) throws Exception {
        String os = System.getProperty("os.name");
        String targetProject = module + "/" + module + "-dao";

        String basePath = MybatisGeneratorUtil.class.getResource("/")
                .getPath().replace("/target/classes", "")
                .replace(targetProject, "");
        if (os.startsWith("win")) {
            generatorConfig_vm = MybatisGeneratorUtil.class.getResource(generatorConfig_vm)
                    .getPath().replaceFirst("/", "");
            service_vm = MybatisGeneratorUtil.class.getResource(service_vm)
                    .getPath().replaceFirst("/", "");
            serviceMock_vm = MybatisGeneratorUtil.class.getResource(serviceMock_vm)
                    .getPath().replaceFirst("/", "");
            serviceImpl_vm = MybatisGeneratorUtil.class.getResource(serviceImpl_vm)
                    .getPath().replaceFirst("/", "");
        } else {
            generatorConfig_vm = MybatisGeneratorUtil.class.getResource(generatorConfig_vm).getPath();
            service_vm = MybatisGeneratorUtil.class.getResource(service_vm).getPath();
            serviceMock_vm = MybatisGeneratorUtil.class.getResource(serviceMock_vm).getPath();
            serviceImpl_vm = MybatisGeneratorUtil.class.getResource(serviceImpl_vm).getPath();
        }

        String generatorConfigXml = MybatisGeneratorUtil.class.getResource("/")
                .getPath().replace("/target/classes", "") + "/src/main/resources/generatorConfig.xml";

        targetProject += basePath;

        String sql = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = '" + database + "' AND table_name LIKE '" + tablePrefix + "_%';";

        System.out.println("=============== 开始生成generatorConfig.xml文件===========");
        List<Map<String, Object>> tables = new ArrayList<Map<String, Object>>();

        try {
            VelocityContext context = new VelocityContext();
            Map<String, Object> table;
            JdbcUtil jdbcUtil = new JdbcUtil(jdbcDriver, jdbcUrl, jdbcUsername, jdbcPassword);
            List<Map> result = jdbcUtil.selectedByParams(sql, null);
            for (Map map : result) {
                System.out.println(map.get("TABLE_NAME"));
                table = new HashMap<>(2);
                table.put("table_name", map.get("TABLE_NAME"));
                table.put("model_name", StringUtil.lineHump(ObjectUtils.toString(map.get("TABLE_NAME"))));
                tables.add(table);
            }
            jdbcUtil.release();

            String targetProjectSqlMap = basePath + module + "/" + module + "-rpc-service";
            context.put("tables", tables);
            context.put("generator_javaModelGenerator_targetPackage", packageName + ".dao.model");
            context.put("generator_sqlMapGenerator_targetPackage", packageName + ".dao.mapper");
            context.put("generator_javaClientGenerator_targetPackage", packageName + ".dao.mapper");
            context.put("targetProject", targetProject);
            context.put("targetProject_sqlMap", targetProjectSqlMap);
            context.put("generator_jdbc_password", AESUtil.aesDecode(jdbcPassword));
            context.put("last_insert_id_tables", lastInsertIdTables);

            VelocityUtil.generate(generatorConfig_vm, generatorConfigXml, context);
            // 删除旧代码
            deleteDir(new File(targetProject + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/dao/model"));
            deleteDir(new File(targetProject + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/dao/mapper"));
            deleteDir(new File(targetProjectSqlMap + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/dao/mapper"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("================结束生成generatorConfig.xml文件============");

        System.out.println("================ 开始运行MybatisGenerator============");

        List<String> warnings = new ArrayList<String>();
        File configFile = new File(generatorConfigXml);

    }

    static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteDir(files[i]);
            }
            dir.delete();
        }
    }
}
