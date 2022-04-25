package com.robsonkades.exchange.core.domain;

import org.apache.commons.lang3.builder.*;

public class Value {
    private static final ToStringStyle STYLE = new StandardToStringStyle() {
        {
            setUseShortClassName(true);
            setUseFieldNames(false);
            setUseIdentityHashCode(false);
            setContentStart("(");
            setContentEnd(")");
        }
    };

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, STYLE);
    }
}
