package com.huifrank.processor;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.huifrank.common.util.invoker.InvokeInStatic;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.apt.model.Factory;
import org.eclipse.jdt.internal.compiler.apt.model.VariableElementImpl;


import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

@AutoService(InvokeInStatic.class)
@SupportedAnnotationTypes("InvokeInStatic")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class InvokeInStaticProcessor extends AbstractProcessor {

    private Messager messager;
    private Elements elementUtils;
    private Filer filer;
    private ProcessingEnvironment processingEnv;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
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

        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(InvokeInStatic.class);
        elementsAnnotatedWith.forEach(ele->{




        });

        return false;
    }
}
