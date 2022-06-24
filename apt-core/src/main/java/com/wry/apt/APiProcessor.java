package com.wry.apt;

import com.wry.annotation.ApiAnnotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

public class APiProcessor extends AbstractProcessor {
    /**
     * 类名后缀
     */
    public static final String SUFFIX = "AutoGenerate";
    /**
     * 类名的前缀
     */
    public static final String PREFIX = "My_";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        Messager messager = processingEnv.getMessager();

        for (TypeElement typeElement : annotations) {

            //返回使用给定注释类型注释的元素。
            for (Element e : env.getElementsAnnotatedWith(typeElement)) {
                //打印
                messager.printMessage(Diagnostic.Kind.WARNING, "Printing:" + e.toString());
                messager.printMessage(Diagnostic.Kind.WARNING, "Printing:" + e.getSimpleName());
                messager.printMessage(Diagnostic.Kind.WARNING, "Printing:" + e.getEnclosedElements().toString());
                //获取注解
                ApiAnnotation annotation = e.getAnnotation(ApiAnnotation.class);
                //获取元素名并将其首字母大写
                String name = e.getSimpleName().toString();
//                char c = Character.toUpperCase(name.charAt(0));
//                name = String.valueOf(c + name.substring(1));

                //包裹注解元素的元素, 也就是其父元素, 比如注解是成员变量或者成员函数, 其上层就是该类
                Element enclosingElement = e.getEnclosingElement();
                //获取父元素的全类名,用来生成报名
                String enclosingQualifiedname;
                if (enclosingElement instanceof PackageElement) {
                    enclosingQualifiedname = ((PackageElement) enclosingElement).getQualifiedName().toString();
                } else {
                    enclosingQualifiedname = ((TypeElement) enclosingElement).getQualifiedName().toString();
                }
                try {
                    //生成包名
//                    String generatePackageName = enclosingQualifiedname.substring(0, enclosingQualifiedname.lastIndexOf("."));
                    String generatePackageName = "com.wry";
                    // 生成的类名
//                    String genarateClassName = PREFIX + enclosingElement.getSimpleName() + SUFFIX;
                    String genarateClassName ="WorldApiImpl";
                    //创建Java 文件
                    JavaFileObject f = processingEnv.getFiler().createSourceFile(genarateClassName);
                    // 在控制台输出文件路径
                    messager.printMessage(Diagnostic.Kind.WARNING, "Printing: " + f.toUri());
                    Writer w = f.openWriter();
                    try {
                        PrintWriter pw = new PrintWriter(w);
                        pw.println("package " + generatePackageName + ";");
                        pw.println("\npublic class " + genarateClassName + " implements ApiInterface { ");

                        pw.println("    @Override");
                        pw.println("    public  void print() {");
                        pw.println("       System.out.println(\"" + annotation.msg() + " \");");
                        pw.println("    }");

//                        pw.println("\n    /** 打印值 */");
//                        pw.println("    public static void " + name + "() {");
//                        pw.println("        // 注解的父元素: " + enclosingElement.toString());
//                        pw.println("        System.out.println(\"代码生成的路径: " + f.toUri() + "\");");
//                        pw.println("        System.out.println(\"注解的元素: " + e.toString() + "\");");
//                        pw.println("        System.out.println(\"注解的版本: " + annotation.version() + "\");");
//                        pw.println("        System.out.println(\"注解的作者: " + annotation.author() + "\");");
//                        pw.println("    }");


                        pw.println("}");
                        pw.flush();
                    } finally {
                        w.close();
                    }
                } catch (IOException e1) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            e1.toString());
                }

            }
        }

//        final Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
//        final JavacElements elementUtils = (JavacElements) processingEnv.getElementUtils();
//        final TreeMaker treeMaker = TreeMaker.instance(context);
//        Set<? extends Element> elements = env.getRootElements();
//
//        for (Element element : env.getElementsAnnotatedWith(ApiAnnotation.class)) {
//            JCTree.JCMethodDecl jcMethodDecl = (JCTree.JCMethodDecl) elementUtils.getTree(element);
//
//            treeMaker.pos = jcMethodDecl.pos;
//            jcMethodDecl.body = treeMaker.Block(0, List.of(
//                    treeMaker.Exec(
//                            treeMaker.Apply(
//                                    List.nil(),
//                                    treeMaker.Select(
//                                            treeMaker.Select(
//                                                    treeMaker.Ident(
//                                                            elementUtils.getName("System")
//                                                    ),
//                                                    elementUtils.getName("out")
//                                            ),
//                                            elementUtils.getName("println")
//                                    ),
//                                    List.of(
//                                            treeMaker.Literal("Hello, world!!!")
//                                    )
//                            )
//                    ),
//                    jcMethodDecl.body
//            ));
//        }

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new HashSet<>();
        annotations.add(ApiAnnotation.class.getName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
