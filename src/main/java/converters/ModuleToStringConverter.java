/*
 * Copyright � 2017. All information contained here included the intellectual and technical concepts are property of Null Point Software.
 */

package converters;

import domain.Module;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Created by daviddelatorre on 29/3/17.
 */
@Component
@Transactional
public class ModuleToStringConverter implements Converter<Module, String> {


    @Override
    public String convert(Module actor) {
        Assert.notNull(actor);
        String result;
        result = String.valueOf(actor.getId());
        return result;
    }
}
