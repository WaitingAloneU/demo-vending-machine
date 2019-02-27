package com.example.vm.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("coin")
public class Coin extends Model<Coin> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("amount")
    private Integer amount;

    @TableField("count")
    private Integer count;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "coin{" +
                "id=" + id +
                ", amount=" + amount +
                ", count=" + count +
                "}";
    }
}
