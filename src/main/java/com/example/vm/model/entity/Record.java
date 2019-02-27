package com.example.vm.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("record")
public class Record extends Model<Record> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("product_id")
    private Long proudctId;

    @TableField("record_status")
    private Integer recordStatus;

    @TableField("amount")
    private Integer amount;

    @TableField("insert_amount")
    private Integer insertAmount;

    @TableField("insert_comprise")
    private String insertComprise;

    @TableField("return_amount")
    private Integer returnAmount;

    @TableField("return_comprise")
    private String returnComprise;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", proudctId=" + proudctId +
                ", recordStatus=" + recordStatus +
                ", amount=" + amount +
                ", insertAmount=" + insertAmount +
                ", insertComprise=" + insertComprise +
                ", returnAmount=" + returnAmount +
                ", returnComprise=" + returnComprise +
                "}";
    }
}
