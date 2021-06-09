package code.flatura.edukpi.model.DTO;

import code.flatura.edukpi.model.AbstractEntity;

import java.util.List;

public interface AbstractDto {

    AbstractDto convertFrom(AbstractEntity model);//<? super AbstractEntity> model);

    List<AbstractDto> multipleConvertFrom(List<AbstractEntity> modelList);
}
