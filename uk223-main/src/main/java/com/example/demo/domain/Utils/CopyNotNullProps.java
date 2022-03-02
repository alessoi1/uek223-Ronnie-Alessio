package com.example.demo.domain.Utils;

import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtilsBean;

public class CopyNotNullProps extends BeanUtilsBean {

    @Override
    @SneakyThrows
    public void copyProperty(Object bean, String name, Object value) {
        if (value == null) {
            return;
        }

        super.copyProperty(bean, name, value);
    }
}
