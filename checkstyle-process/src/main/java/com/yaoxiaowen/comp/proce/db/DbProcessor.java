package com.yaoxiaowen.comp.proce.db;

import com.google.auto.service.AutoService;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;

/**
 * @author www.yaoxiaowen.com
 */
@AutoService(Processor.class)
public class DbProcessor extends AbstractProcessor {
    private Messager messager;

    private int count = 0;
    private int forCount = 0;
    private StringBuilder generateStr = new StringBuilder();

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        // TODO Auto-generated method stub
        super.init(env);
        messager = env.getMessager();
        String logStr = "enter init(),  进入 init()";
        printMsg(logStr);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        // TODO Auto-generated method stub
        String logStr = "enter process()， 进入process";


        //用来 存储 （className, 输出语句） 这种结构
        Map<String, String> maps = new HashMap<>();

        //得到 使用了 SQLString注解的元素
        Set<? extends Element> eleStrSet = env.getElementsAnnotatedWith(SQLString.class);

        count++;

        for (Element eleStr : eleStrSet) {

            //因为我们知道SQLString元素的使用范围是在域上，所以这里我们进行了强制类型转换
            VariableElement eleStrVari = (VariableElement) eleStr;
            forCount++;

            // 得到该元素的封装类型，也就是 包裹它的父类型
            TypeElement enclosingEle = (TypeElement) eleStrVari.getEnclosingElement();
            String className = enclosingEle.getQualifiedName().toString();

            generateStr.append("className = " + className);
            generateStr.append("\t fieldName = " + eleStrVari.getSimpleName().toString());

            //得到在元素上，使用了注解的相关情况
            SQLString sqlString = eleStrVari.getAnnotation(SQLString.class);
            generateStr.append("\t annotationName = " + sqlString.name());
            generateStr.append("\t annotationValue = " + sqlString.value());
            generateStr.append("\t forCount=" + forCount);
            generateStr.append("\n");
        }

        generateStr.append("test File yaowen");
        generateStr.append("\t count=" + count);
        generateFile(generateStr.toString());
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        // TODO Auto-generated method stub
        Set<String> strings = new TreeSet<>();
        strings.add("com.yaoxiaowen.comp.proce.db.SQLString");
        return strings;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        // TODO Auto-generated method stub
        return SourceVersion.latestSupported();
    }

    //将内容输出到文件
    private void generateFile(String str) {
        try {
            //这是mac环境下的路径
            File file = new File("/Users/guopanjie/dbCustomProcFile");
            FileWriter fw = new FileWriter(file);
            fw.append(str);

            fw.flush();
            fw.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            printMsg(e.toString());
        }
    }

    private void printMsg(String msg) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg);
    }
}