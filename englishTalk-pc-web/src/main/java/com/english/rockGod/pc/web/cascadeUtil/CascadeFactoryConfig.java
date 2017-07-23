package com.english.rockGod.pc.web.cascadeUtil;

import java.beans.ConstructorProperties;
import java.util.List;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public class CascadeFactoryConfig {
    private int threadCount = 1;
    private List<InvocationInterceptorFactory> invocationInterceptorFactories;
    private List<ParameterResolverFactory> parameterResolverFactories;
    public static final CascadeFactoryConfig DEFAULT = new CascadeFactoryConfig();

    public int getThreadCount() {
        return this.threadCount;
    }

    public List<InvocationInterceptorFactory> getInvocationInterceptorFactories() {
        return this.invocationInterceptorFactories;
    }

    public List<ParameterResolverFactory> getParameterResolverFactories() {
        return this.parameterResolverFactories;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public void setInvocationInterceptorFactories(List<InvocationInterceptorFactory> invocationInterceptorFactories) {
        this.invocationInterceptorFactories = invocationInterceptorFactories;
    }

    public void setParameterResolverFactories(List<ParameterResolverFactory> parameterResolverFactories) {
        this.parameterResolverFactories = parameterResolverFactories;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof CascadeFactoryConfig)) {
            return false;
        } else {
            CascadeFactoryConfig other = (CascadeFactoryConfig)o;
            if(!other.canEqual(this)) {
                return false;
            } else if(this.getThreadCount() != other.getThreadCount()) {
                return false;
            } else {
                List this$invocationInterceptorFactories = this.getInvocationInterceptorFactories();
                List other$invocationInterceptorFactories = other.getInvocationInterceptorFactories();
                if(this$invocationInterceptorFactories == null) {
                    if(other$invocationInterceptorFactories != null) {
                        return false;
                    }
                } else if(!this$invocationInterceptorFactories.equals(other$invocationInterceptorFactories)) {
                    return false;
                }

                List this$parameterResolverFactories = this.getParameterResolverFactories();
                List other$parameterResolverFactories = other.getParameterResolverFactories();
                if(this$parameterResolverFactories == null) {
                    if(other$parameterResolverFactories != null) {
                        return false;
                    }
                } else if(!this$parameterResolverFactories.equals(other$parameterResolverFactories)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof CascadeFactoryConfig;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        int result1 = result * 59 + this.getThreadCount();
        List $invocationInterceptorFactories = this.getInvocationInterceptorFactories();
        result1 = result1 * 59 + ($invocationInterceptorFactories == null?0:$invocationInterceptorFactories.hashCode());
        List $parameterResolverFactories = this.getParameterResolverFactories();
        result1 = result1 * 59 + ($parameterResolverFactories == null?0:$parameterResolverFactories.hashCode());
        return result1;
    }

    public String toString() {
        return "CascadeFactoryConfig(threadCount=" + this.getThreadCount() + ", invocationInterceptorFactories=" + this.getInvocationInterceptorFactories() + ", parameterResolverFactories=" + this.getParameterResolverFactories() + ")";
    }

    @ConstructorProperties({"threadCount", "invocationInterceptorFactories", "parameterResolverFactories"})
    public CascadeFactoryConfig(int threadCount, List<InvocationInterceptorFactory> invocationInterceptorFactories, List<ParameterResolverFactory> parameterResolverFactories) {
        this.threadCount = threadCount;
        this.invocationInterceptorFactories = invocationInterceptorFactories;
        this.parameterResolverFactories = parameterResolverFactories;
    }

    public CascadeFactoryConfig() {
    }
}
