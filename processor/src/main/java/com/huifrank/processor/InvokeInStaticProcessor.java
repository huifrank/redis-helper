package com.huifrank.processor;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.huifrank.common.util.invoker.InvokeInStatic;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Initializer;


import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor8;
import javax.lang.model.util.Types;
import java.util.Set;

@AutoService(InvokeInStatic.class)
@SupportedAnnotationTypes("InvokeInStatic")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class InvokeInStaticProcessor extends AbstractProcessor {

    private Messager messager;
    private Elements elementUtils;
    private Filer filer;
    private Types typeUtils;
    private ProcessingEnvironment processingEnv;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        this.elementUtils = processingEnv.getElementUtils();
        this.filer = processingEnv.getFiler();
        this.typeUtils = processingEnv.getTypeUtils();
        this.processingEnv = processingEnv;
    }



    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(InvokeInStatic.class.getCanonicalName());
    }
    @Override
    public SourceVersion getSupportedSourceVersion() {
        if (SourceVersion.latest().compareTo(SourceVersion.RELEASE_8) > 0) {
            return SourceVersion.latest();
        } else {
            return SourceVersion.RELEASE_8;
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(InvokeInStatic.class);
        elements.forEach(element -> {
            element.accept(new SimpleElementVisitor8<TypeElement,Object>(){
                @Override
                public TypeElement visitType(TypeElement e, Object o) {
                    //静态代码块
                    AST ast = AST.newAST(AST.JLS8);
                    Initializer initializer = ast.newInitializer();
                    return super.visitType(e, o);
                }
            },null);
        });


        return false;
    }
}
