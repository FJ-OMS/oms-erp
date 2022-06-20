package org.skyer.goods.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.skyer.goods.domain.entity.Spu;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author wusc 2021-01-07 17：53
 */
@ApiModel("SPU集合")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
public class SpuListDTO {
    public SpuListDTO(){}
    public SpuListDTO(Spu spu){
        this.spuList = new ArrayList<Spu>(){{add(spu);}};
    }
    public SpuListDTO(List<Spu> spuList){
        this.spuList = spuList;
    }
    @ApiModelProperty("spu集合")
    private List<Spu> spuList;

    private Boolean deleteFlag;
}
