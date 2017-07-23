package com.english.rockGod.pc.web.dto;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/23/023.
 */
public class Field {

    public static final String DEFAULT_CATEGORY = "query";
    private static final List<Field> EMPTY_CHILDREN = Lists.newArrayList();
    private static final List<String> ALL_PROPS = Lists.newArrayList();
    private String as;
    private String type;
    private String category = "query";
    private Map params = Maps.newHashMap();
    private List<Field> children;
    private List<String> props;

    public String getComputedAs() {
        String as = this.getAs();
        if(StringUtils.isNotBlank(as)) {
            return as;
        } else {
            as = StringUtils.uncapitalize(this.getType());
            if(StringUtils.isNotBlank(this.getCategory()) && !this.getCategory().equals("query")) {
                as = as + "_" + this.getCategory();
            }

            return as;
        }
    }

    public Field() {
        this.children = EMPTY_CHILDREN;
        this.props = ALL_PROPS;
    }

    public String getAs() {
        return this.as;
    }

    public String getType() {
        return this.type;
    }

    public String getCategory() {
        return this.category;
    }

    public Map getParams() {
        return this.params;
    }

    public List<Field> getChildren() {
        return this.children;
    }

    public List<String> getProps() {
        return this.props;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public void setChildren(List<Field> children) {
        this.children = children;
    }

    public void setProps(List<String> props) {
        this.props = props;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof Field)) {
            return false;
        } else {
            Field other = (Field)o;
            if(!other.canEqual(this)) {
                return false;
            } else {
                String this$as = this.getAs();
                String other$as = other.getAs();
                if(this$as == null) {
                    if(other$as != null) {
                        return false;
                    }
                } else if(!this$as.equals(other$as)) {
                    return false;
                }

                String this$type = this.getType();
                String other$type = other.getType();
                if(this$type == null) {
                    if(other$type != null) {
                        return false;
                    }
                } else if(!this$type.equals(other$type)) {
                    return false;
                }

                String this$category = this.getCategory();
                String other$category = other.getCategory();
                if(this$category == null) {
                    if(other$category != null) {
                        return false;
                    }
                } else if(!this$category.equals(other$category)) {
                    return false;
                }

                label62: {
                    Map this$params = this.getParams();
                    Map other$params = other.getParams();
                    if(this$params == null) {
                        if(other$params == null) {
                            break label62;
                        }
                    } else if(this$params.equals(other$params)) {
                        break label62;
                    }

                    return false;
                }

                label55: {
                    List this$children = this.getChildren();
                    List other$children = other.getChildren();
                    if(this$children == null) {
                        if(other$children == null) {
                            break label55;
                        }
                    } else if(this$children.equals(other$children)) {
                        break label55;
                    }

                    return false;
                }

                List this$props = this.getProps();
                List other$props = other.getProps();
                if(this$props == null) {
                    if(other$props != null) {
                        return false;
                    }
                } else if(!this$props.equals(other$props)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Field;
    }

    public int hashCode() {
        boolean PRIME = true;
        byte result = 1;
        String $as = this.getAs();
        int result1 = result * 59 + ($as == null?0:$as.hashCode());
        String $type = this.getType();
        result1 = result1 * 59 + ($type == null?0:$type.hashCode());
        String $category = this.getCategory();
        result1 = result1 * 59 + ($category == null?0:$category.hashCode());
        Map $params = this.getParams();
        result1 = result1 * 59 + ($params == null?0:$params.hashCode());
        List $children = this.getChildren();
        result1 = result1 * 59 + ($children == null?0:$children.hashCode());
        List $props = this.getProps();
        result1 = result1 * 59 + ($props == null?0:$props.hashCode());
        return result1;
    }

    public String toString() {
        return "Field(as=" + this.getAs() + ", type=" + this.getType() + ", category=" + this.getCategory() + ", params=" + this.getParams() + ", children=" + this.getChildren() + ", props=" + this.getProps() + ")";
    }
}
