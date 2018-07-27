package cn.choleece.zhengboot.common.util;

import org.apache.commons.lang.ObjectUtils;
import org.apache.velocity.VelocityContext;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

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
                .getPath().replace("/target/classes/", "")
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
            context.put("generator_jdbc_password", jdbcPassword);
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
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        System.out.println("============结束运行MybatisGenerator============");
        System.out.println("============开始生产Service===========");
        String ctime = new SimpleDateFormat("yyyy/M/d").format(new Date());
        String servicePath = basePath + module + "/" + module + "-rpc-api"+ "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/rpc/api";
        String serviceImplPath = basePath + module + "/" + module + "-rpc-service" + "/src/main/java/" + packageName.replaceAll("\\.", "/") + "/rpc/service/impl";

        for (int i = 0; i < tables.size(); i++) {
            String model = StringUtil.lineHump(ObjectUtils.toString(tables.get(i).get("table_name")));
            String service = servicePath + "/I" + model + "Service.java";
            String serviceMock = servicePath + "/" + model + "ServiceMock.java";
            String serviceImpl = serviceImplPath + "/" + model + "ServiceImpl.java";

            // 生成Service
            genFile(new File(service), packageName, model, ctime, service_vm, service, false);
            // 生成serviceMock
            genFile(new File(serviceMock), packageName, model, ctime, serviceMock_vm, serviceMock, false);
            // 生成serviceImpl
            genFile(new File(serviceImpl), packageName, model, ctime, serviceImpl_vm, serviceImpl, true);
        }
        System.out.println("===============结束生产Service===============");
    }

    /**
     * 递归删除非空文件夹
     * @param dir
     */
    static void deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteDir(files[i]);
            }
            dir.delete();
        }
    }

    /**
     * 生成相应的文件
     * @param file
     * @param packageName
     * @param model
     * @param ctime
     * @param vm
     * @param type
     * @param mapper 是否生成mapper
     * @throws Exception
     */
    static void genFile(File file, String packageName, String model, String ctime, String vm, String type, boolean mapper) throws Exception {
        if (!file.exists()) {
            VelocityContext context = new VelocityContext();
            context.put("package_name", packageName);
            context.put("model", model);
            if (mapper) {
                context.put("mapper", StringUtil.toLowerCaseFirstOne(model));
            }
            context.put("ctime", ctime);
            System.out.println("vm: " + vm + " type: " + type + " context: " + context);
            VelocityUtil.generate(vm, type, context);
        }
    }
}
