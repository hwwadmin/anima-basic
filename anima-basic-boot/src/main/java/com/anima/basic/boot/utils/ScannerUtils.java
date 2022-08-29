package com.anima.basic.boot.utils;

import cn.hutool.core.lang.Assert;
import com.anima.basic.common.exception.UtilsException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.asm.ClassReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * 注解扫描工具类
 *
 * @author hww
 */
public abstract class ScannerUtils {

    /**
     * 批量扫包
     */
    public static List<Class<?>> scanAnnotationInType(List<String> basePackages, Class<?> annotation) {
        if (CollectionUtils.isEmpty(basePackages)) {
            return Lists.newArrayList();
        }
        List<Class<?>> classList = Lists.newArrayList();
        for (String basePackage : basePackages) {
            classList.addAll(scanAnnotationInType(basePackage, annotation));
        }
        return classList;
    }

    /**
     * 在指定包及其子包下面查找指定注解类
     */
    public static List<Class<?>> scanAnnotationInType(String basePackage, Class<?> annotation) {
        Assert.isTrue(StringUtils.isNotBlank(basePackage), "要扫描的包路径不能为空");
        Assert.isTrue(Objects.nonNull(annotation), "要扫描的注解不能为空");
        Assert.isTrue(annotation.isAnnotation(), "要扫描的对象必须是类注解");
        try {
            String packageSearchPath = String.format("classpath*:%s/**/*.class", ClassUtils.convertClassNameToResourcePath(basePackage));
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
            List<Class<?>> classList = Lists.newArrayList();
            for (Resource resource : resources) {
                if (resource.isReadable()) {
//                    MetadataReader metadataReader = new ClassMetadataReader(resource, ClassUtils.getDefaultClassLoader());
                    MetadataReader metadataReader = new SimpleMetadataReaderFactory(ClassUtils.getDefaultClassLoader()).getMetadataReader(resource);
                    if (metadataReader.getAnnotationMetadata().hasAnnotation(annotation.getName())) {
                        classList.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
                    }
                }
            }
            return classList;
        } catch (Exception e) {
            throw UtilsException.exception(String.format("在包[%s]下扫描注解[%s]失败", basePackage, annotation.getName()), e);
        }
    }

    /**
     * 类元数据解析器
     */
    static class ClassMetadataReader implements MetadataReader {

        private final Resource resource;

        private final ClassMetadata classMetadata;

        private final AnnotationMetadata annotationMetadata;

        ClassMetadataReader(Resource resource, ClassLoader classLoader) throws Exception {
            InputStream is = new BufferedInputStream(resource.getInputStream());
            ClassReader classReader;
            try {
                classReader = new ClassReader(is);
            } catch (IllegalArgumentException ex) {
                throw ex;
            } finally {
                is.close();
            }
            AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor(
                    classLoader);
            classReader.accept(visitor, ClassReader.SKIP_DEBUG);
            this.annotationMetadata = visitor;
            this.classMetadata = visitor;
            this.resource = resource;
        }

        @Override
        public Resource getResource() {
            return this.resource;
        }

        @Override
        public ClassMetadata getClassMetadata() {
            return this.classMetadata;
        }

        @Override
        public AnnotationMetadata getAnnotationMetadata() {
            return this.annotationMetadata;
        }
    }

}
