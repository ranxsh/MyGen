package com.xsr.demo.my_jeesite.com.xsr.jeesite.codegen;

import com.xsr.demo.my_jeesite.com.xsr.jeesite.codegen.def.TableMetadata;
import com.xsr.demo.my_jeesite.com.xsr.jeesite.codegen.trans.TableTrans;
import com.xsr.demo.my_jeesite.com.xsr.jeesite.codegen.utils.DBUtils;
import com.xsr.demo.my_jeesite.com.xsr.jeesite.codegen.utils.TableMetaUtil;
import org.apache.commons.io.FileUtils;
import org.bee.tl.core.GroupTemplate;
import org.bee.tl.core.Template;
import org.mozilla.javascript.optimizer.Codegen;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xs on 2017/6/15.
 */
public class CodeGen {

    private static final String EOL = System.getProperty("line.separator");
    private String rootFilePath;//= App.class.getResource("/").getPath().replace("%20", " ");
    //    private String ROOT_FILE_PATH_DESC = "D:\\temp\\mygen";
    private String tplPath;// = rootFilePath + "/tpl";
    private File genFolder; // = new File(ROOT_FILE_PATH_DESC + "");
    private  String genPackage = "com.xsr.jeesite";
    private GroupTemplate group ; //= new GroupTemplate(new File(PACKAGE_PATH));

    private String host = "localhost";
    private String port = "3306";
    private String userName = "root";
    private String password = "admin";
    private String dbName = "jeesite";
    String codeFileRoot = "D:\\git_ranxsh\\my-jeesite\\src\\main\\java\\com\\xsr\\jeesite";

    List<String> tableNameList;

    //    TableTrans tableTrans;
//    TableMetadata tableMetadata = new TableMetadata();
    Map<String, TableMetadata> tableMetadataMap = new HashMap<String, TableMetadata>();
    Map<String, TableTrans> transMap = new ConcurrentHashMap<String, TableTrans>();

    public CodeGen(){
        init();
    }
    public CodeGen(String host,String port,String userName,String password, String genPackage, List<String> tableNameList){
        this.genPackage = genPackage;
        this.host = host;
        this.port = port;
        this.password = password;
        this.tableNameList = tableNameList;
        init();
    }

    public String genCode() throws Exception{

        String path = "";
        group.setCharset("UTF-8");
        Connection conn = DBUtils.getConn(host,port,userName,password,dbName);
        DatabaseMetaData dbmd = DBUtils.getDatabaseMetaData(conn);
        String dbType = "MySQL" ;
        dbType = dbmd.getDatabaseProductName();


//        Map<String, TableTrans> transMap = new ConcurrentHashMap<String, TableTrans>();

        TableMetaUtil.loadMetadata(dbmd, tableMetadataMap);

        tableNameList = new ArrayList<String>();
        tableNameList.add("sys_user");
        tableNameList.add("test_table");
        tableNameList.add("test_table2");
//        for (String tableName : tableMetadataMap.keySet()) {
        for(String tableName : tableNameList){
            generateXml(tableName, dbType);
            generateDao(tableName, dbType);
            generateEntity(tableName, dbType);

//            generateXml(tableName, dbType);
//            generateDao(tableName, dbType);
//            generateEntity(tableName, dbType);
//            generateService(tableName, dbType);
//            generateWeb(tableName, dbType);
//            generateDatatableHtml(tableName, dbType);
//            generateDialogInfoHtml(tableName, dbType);
//            generateDialogUpdateHtml(tableName, dbType);
        }
        return path;
    }

    private void init(){
        rootFilePath = Codegen.class.getClassLoader().getResource("").getPath() ;
        tplPath = rootFilePath + "/tpl";
        group = new GroupTemplate(new File(tplPath));
        genFolder = new File(codeFileRoot + "");
    }

    private void generateDialogInfoHtml(String tableName, String dbType) throws Exception {
        Template template = group.getFileTemplate(dbType + "-dialog-info-html.txt");
        if (template == null)
            throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));

        TableTrans trans = new TableTrans(tableName, tableMetadataMap);

        template.set("package", genPackage);
        template.set("table", trans);
        template.set("title", "tableName管理");
        template.set("startTag_", "${");
        template.set("endTag_", "}");
        writeTag(trans, "/list/" + trans.getLowerStartClassName() + "-info.jsp");
        FileUtils.write(new File(genFolder, "/list/" + trans.getLowerStartClassName() + "-info.jsp"), template.getTextAsString(), "UTF-8", true);
    }

    private void generateDialogUpdateHtml(String tableName, String dbType) throws Exception {
        Template template = group.getFileTemplate(dbType + "-dialog-update-html.txt");
        if (template == null)
            throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));

        TableTrans trans = new TableTrans(tableName, tableMetadataMap);

        template.set("package", genPackage);
        template.set("table", trans);
        template.set("title", "tableName管理");
        template.set("startTag_", "${");
        template.set("endTag_", "}");
        writeTag(trans, "/list/" + trans.getLowerStartClassName() + "-update.jsp");
        FileUtils.write(new File(genFolder, "/list/" + trans.getLowerStartClassName() + "-update.jsp"), template.getTextAsString(), "UTF-8", true);
    }

    private void writeTag(TableTrans trans, String basePath) throws Exception {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\"	pageEncoding=\"UTF-8\"%>").append(EOL);
        buffer.append("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>").append(EOL);
        buffer.append("<%@ taglib prefix=\"fmt\" uri=\"http://java.sun.com/jsp/jstl/fmt\"%>").append(EOL);
        buffer.append("<c:set var=\"ctx\" value=\"${pageContext.request.contextPath}\" />").append(EOL);
        FileUtils.write(new File(genFolder, basePath), buffer.toString());
    }

    private void generateDatatableHtml(String tableName, String dbType) throws Exception {
        Template template = group.getFileTemplate(dbType + "-datatable-html.txt");
        if (template == null)
            throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));
        template.set("startTag_", "${");
        template.set("endTag_", "}");
        template.set("title", "tableName管理");
        template.set("addEntity", "添加");
        template.set("startTime", "开始时间");
        template.set("endTime", "结束时间");
        template.set("searchText", "searchText");
        template.set("searchButton", "searchButton");
        template.set("export", "导出");
        template.set("name", "操作");
        template.set("infoName", "详细");
        template.set("deleteName", "删除");
        template.set("updateName", "更新");

        TableTrans trans = new TableTrans(tableName, tableMetadataMap);

        template.set("package", genPackage);
        template.set("table", trans);
        writeTag(trans, "/list/" + trans.getLowerStartClassName() + "-list.jsp");
        FileUtils.write(new File(genFolder, "/list/" + trans.getLowerStartClassName() + "-list.jsp"), template.getTextAsString(), "UTF-8", true);
    }

    private void generateXml(String tableName, String dbType) throws Exception {
        Template template = group.getFileTemplate(dbType + "-dao-xml.txt");
        if (template == null)
            throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));

        TableTrans trans = new TableTrans(tableName, tableMetadataMap);

        template.set("package", genPackage);
        template.set("table", trans);
        FileUtils.write(new File(genFolder, "/mapper/" + trans.getUpperStartClassName() + "Dao.xml"), template.getTextAsString(), "UTF-8");
    }

    private void generateDao(String tableName, String dbType) throws Exception {
        Template template = group.getFileTemplate(dbType + "-dao-java.txt");
        if (template == null)
            throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));

        TableTrans trans = new TableTrans(tableName, tableMetadataMap);

        template.set("package", genPackage);
        template.set("table", trans);
        FileUtils.write(new File(genFolder, "/dao/" + trans.getUpperStartClassName() + "Dao.java"), template.getTextAsString(), "UTF-8");
    }

    private void generateWeb(String tableName, String dbType) throws Exception {
        Template template = group.getFileTemplate(dbType + "-web-java.txt");
        if (template == null)
            throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));

        TableTrans trans = new TableTrans(tableName, tableMetadataMap);

        template.set("package", genPackage);
        template.set("table", trans);
        FileUtils.write(new File(genFolder, "/controller/" + trans.getUpperStartClassName() + "Controller.java"), template.getTextAsString(),
                "UTF-8");
    }

    private void generateService(String tableName, String dbType) throws Exception {
        Template template = group.getFileTemplate(dbType + "-service-java.txt");
        if (template == null)
            throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));

        TableTrans trans = new TableTrans(tableName, tableMetadataMap);

        template.set("package", genPackage);
        template.set("table", trans);
        FileUtils.write(new File(genFolder, "/service/" + trans.getUpperStartClassName() + "Service.java"), template.getTextAsString(), "UTF-8");
    }

    private void generateEntity(String tableName, String dbType) throws Exception {
        Template template = group.getFileTemplate(dbType + "-domain.txt");
        if (template == null)
            throw new RuntimeException(String.format("未支持的数据库类型【%s】", dbType));

        TableTrans trans = new TableTrans(tableName, tableMetadataMap);

        template.set("package", genPackage);
        template.set("table", trans);
        FileUtils.write(new File(genFolder, "/entity/" + trans.getUpperStartClassName() + ".java"), template.getTextAsString());
    }

    public static void main(String[] args){
        CodeGen codeGen = new CodeGen();
        try {
            codeGen.genCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
