package io.qubite.tomoko.configuration;

import io.qubite.tomoko.TomokoException;
import io.qubite.tomoko.util.QuadConsumer;
import io.qubite.tomoko.util.TriConsumer;
import net.jodah.typetools.TypeResolver;

import java.lang.reflect.Parameter;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LambdaDescriptor<T> {

    private final T lambda;
    private final Class<?> clazz;
    private final int parameterCount;
    private final String methodName;

    LambdaDescriptor(T lambda, Class<?> clazz, int parameterCount, String methodName) {
        this.lambda = lambda;
        this.clazz = clazz;
        this.parameterCount = parameterCount;
        this.methodName = methodName;
    }

    public static <A> LambdaDescriptor<Consumer<A>> of(Consumer<A> consumer) {
        return new LambdaDescriptor<>(consumer, Consumer.class, 1, "accept");
    }

    public static <A, B> LambdaDescriptor<BiConsumer<A, B>> of(BiConsumer<A, B> consumer) {
        return new LambdaDescriptor<>(consumer, BiConsumer.class, 2, "accept");
    }

    public static <A, B, C> LambdaDescriptor<TriConsumer<A, B, C>> of(TriConsumer<A, B, C> consumer) {
        return new LambdaDescriptor<>(consumer, TriConsumer.class, 3, "accept");
    }

    public static <A, B, C, D> LambdaDescriptor<QuadConsumer<A, B, C, D>> of(QuadConsumer<A, B, C, D> consumer) {
        return new LambdaDescriptor<>(consumer, QuadConsumer.class, 4, "accept");
    }

    public boolean isNamePresent(int parameterIndex) {
        return extractParameter(parameterIndex).isNamePresent();
    }

    public String extractName(int parameterIndex) {
        if (!isNamePresent(parameterIndex)) {
            throw new ParameterNameNotPresentException();
        }
        return extractParameter(parameterIndex).getName();
    }

    private Parameter extractParameter(int parameterIndex) {
        try {
            Parameter parameter = lambda.getClass().getMethod(methodName, createParameterTypeArray(parameterCount)).getParameters()[parameterIndex];
            return parameter;
        } catch (NoSuchMethodException e) {
            throw new TomokoException(e);
        }
    }

    /**
     * @param parameterIndex
     * @return class if extracted properly, Unknown.class if it cannot be read
     */
    public Class<?> extractParameterClass(int parameterIndex) {
        if (parameterIndex >= parameterCount || parameterIndex < 0) {
            throw new IllegalArgumentException("Invalid parameter index.");
        }
        return TypeResolver.resolveRawArguments(clazz, lambda.getClass())[parameterIndex];
    }

    private Class<?>[] createParameterTypeArray(int parameterCount) {
        Class<?>[] result = new Class<?>[parameterCount];
        for (int i = 0; i < parameterCount; i++) {
            result[i] = Object.class;
        }
        return result;
    }

}
