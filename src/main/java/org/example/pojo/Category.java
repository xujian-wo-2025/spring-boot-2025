package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
@Data
public class Category {
    @javax.validation.constraints.NotNull(groups = Update.class )
    private Integer id;//主键ID
    @NotEmpty(groups = {Add.class,Update.class} )
    private String categoryName;//分类名称
    @NotEmpty(groups = {Add.class,Update.class} )
    private String categoryAlias;//分类别名
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间

    public interface Add{

    }

    public interface Update{

    }

}
