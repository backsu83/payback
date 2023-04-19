package com.ebaykorea.payback.batch.repository.opayreward.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SsgVerifySumEntity {
    private Long sumCount;
    private BigDecimal sumAmount;
}
